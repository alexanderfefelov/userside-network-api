package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfRightsProfileInc(
  code: Int,
  profilecode: Option[Int] = None,
  rightcode: Option[Int] = None) {

  def save()(implicit session: DBSession = PblConfRightsProfileInc.autoSession): PblConfRightsProfileInc = PblConfRightsProfileInc.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfRightsProfileInc.autoSession): Int = PblConfRightsProfileInc.destroy(this)(session)

}


object PblConfRightsProfileInc extends SQLSyntaxSupport[PblConfRightsProfileInc] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_rights_profile_inc"

  override val columns = Seq("code", "profilecode", "rightcode")

  def apply(pcrpi: SyntaxProvider[PblConfRightsProfileInc])(rs: WrappedResultSet): PblConfRightsProfileInc = autoConstruct(rs, pcrpi)
  def apply(pcrpi: ResultName[PblConfRightsProfileInc])(rs: WrappedResultSet): PblConfRightsProfileInc = autoConstruct(rs, pcrpi)

  val pcrpi = PblConfRightsProfileInc.syntax("pcrpi")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfRightsProfileInc] = {
    withSQL {
      select.from(PblConfRightsProfileInc as pcrpi).where.eq(pcrpi.code, code)
    }.map(PblConfRightsProfileInc(pcrpi.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfRightsProfileInc] = {
    withSQL(select.from(PblConfRightsProfileInc as pcrpi)).map(PblConfRightsProfileInc(pcrpi.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfRightsProfileInc as pcrpi)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfRightsProfileInc] = {
    withSQL {
      select.from(PblConfRightsProfileInc as pcrpi).where.append(where)
    }.map(PblConfRightsProfileInc(pcrpi.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfRightsProfileInc] = {
    withSQL {
      select.from(PblConfRightsProfileInc as pcrpi).where.append(where)
    }.map(PblConfRightsProfileInc(pcrpi.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfRightsProfileInc as pcrpi).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    profilecode: Option[Int] = None,
    rightcode: Option[Int] = None)(implicit session: DBSession = autoSession): PblConfRightsProfileInc = {
    val generatedKey = withSQL {
      insert.into(PblConfRightsProfileInc).namedValues(
        column.profilecode -> profilecode,
        column.rightcode -> rightcode
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfRightsProfileInc(
      code = generatedKey.toInt,
      profilecode = profilecode,
      rightcode = rightcode)
  }

  def batchInsert(entities: collection.Seq[PblConfRightsProfileInc])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'profilecode -> entity.profilecode,
        'rightcode -> entity.rightcode))
    SQL("""insert into pbl_conf_rights_profile_inc(
      profilecode,
      rightcode
    ) values (
      {profilecode},
      {rightcode}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfRightsProfileInc)(implicit session: DBSession = autoSession): PblConfRightsProfileInc = {
    withSQL {
      update(PblConfRightsProfileInc).set(
        column.code -> entity.code,
        column.profilecode -> entity.profilecode,
        column.rightcode -> entity.rightcode
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfRightsProfileInc)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfRightsProfileInc).where.eq(column.code, entity.code) }.update.apply()
  }

}
