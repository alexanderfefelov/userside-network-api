package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblMemoProfile(
  id: Int,
  chapterId: Option[Int] = None,
  profileId: Option[Int] = None) {

  def save()(implicit session: DBSession = PblMemoProfile.autoSession): PblMemoProfile = PblMemoProfile.save(this)(session)

  def destroy()(implicit session: DBSession = PblMemoProfile.autoSession): Int = PblMemoProfile.destroy(this)(session)

}


object PblMemoProfile extends SQLSyntaxSupport[PblMemoProfile] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_memo_profile"

  override val columns = Seq("id", "chapter_id", "profile_id")

  def apply(pmp: SyntaxProvider[PblMemoProfile])(rs: WrappedResultSet): PblMemoProfile = autoConstruct(rs, pmp)
  def apply(pmp: ResultName[PblMemoProfile])(rs: WrappedResultSet): PblMemoProfile = autoConstruct(rs, pmp)

  val pmp = PblMemoProfile.syntax("pmp")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblMemoProfile] = {
    withSQL {
      select.from(PblMemoProfile as pmp).where.eq(pmp.id, id)
    }.map(PblMemoProfile(pmp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblMemoProfile] = {
    withSQL(select.from(PblMemoProfile as pmp)).map(PblMemoProfile(pmp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblMemoProfile as pmp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblMemoProfile] = {
    withSQL {
      select.from(PblMemoProfile as pmp).where.append(where)
    }.map(PblMemoProfile(pmp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblMemoProfile] = {
    withSQL {
      select.from(PblMemoProfile as pmp).where.append(where)
    }.map(PblMemoProfile(pmp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblMemoProfile as pmp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    chapterId: Option[Int] = None,
    profileId: Option[Int] = None)(implicit session: DBSession = autoSession): PblMemoProfile = {
    val generatedKey = withSQL {
      insert.into(PblMemoProfile).namedValues(
        column.chapterId -> chapterId,
        column.profileId -> profileId
      )
    }.updateAndReturnGeneratedKey.apply()

    PblMemoProfile(
      id = generatedKey.toInt,
      chapterId = chapterId,
      profileId = profileId)
  }

  def batchInsert(entities: collection.Seq[PblMemoProfile])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'chapterId -> entity.chapterId,
        'profileId -> entity.profileId))
    SQL("""insert into pbl_memo_profile(
      chapter_id,
      profile_id
    ) values (
      {chapterId},
      {profileId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblMemoProfile)(implicit session: DBSession = autoSession): PblMemoProfile = {
    withSQL {
      update(PblMemoProfile).set(
        column.id -> entity.id,
        column.chapterId -> entity.chapterId,
        column.profileId -> entity.profileId
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblMemoProfile)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblMemoProfile).where.eq(column.id, entity.id) }.update.apply()
  }

}
