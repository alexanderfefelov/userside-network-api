package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblControlProfile(
  code: Int,
  nazv: Option[String] = None,
  ispolling: Option[Short] = None) {

  def save()(implicit session: DBSession = PblControlProfile.autoSession): PblControlProfile = PblControlProfile.save(this)(session)

  def destroy()(implicit session: DBSession = PblControlProfile.autoSession): Int = PblControlProfile.destroy(this)(session)

}


object PblControlProfile extends SQLSyntaxSupport[PblControlProfile] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_control_profile"

  override val columns = Seq("code", "nazv", "ispolling")

  def apply(pcp: SyntaxProvider[PblControlProfile])(rs: WrappedResultSet): PblControlProfile = autoConstruct(rs, pcp)
  def apply(pcp: ResultName[PblControlProfile])(rs: WrappedResultSet): PblControlProfile = autoConstruct(rs, pcp)

  val pcp = PblControlProfile.syntax("pcp")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblControlProfile] = {
    withSQL {
      select.from(PblControlProfile as pcp).where.eq(pcp.code, code)
    }.map(PblControlProfile(pcp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblControlProfile] = {
    withSQL(select.from(PblControlProfile as pcp)).map(PblControlProfile(pcp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblControlProfile as pcp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblControlProfile] = {
    withSQL {
      select.from(PblControlProfile as pcp).where.append(where)
    }.map(PblControlProfile(pcp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblControlProfile] = {
    withSQL {
      select.from(PblControlProfile as pcp).where.append(where)
    }.map(PblControlProfile(pcp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblControlProfile as pcp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    nazv: Option[String] = None,
    ispolling: Option[Short] = None)(implicit session: DBSession = autoSession): PblControlProfile = {
    val generatedKey = withSQL {
      insert.into(PblControlProfile).namedValues(
        column.nazv -> nazv,
        column.ispolling -> ispolling
      )
    }.updateAndReturnGeneratedKey.apply()

    PblControlProfile(
      code = generatedKey.toInt,
      nazv = nazv,
      ispolling = ispolling)
  }

  def batchInsert(entities: collection.Seq[PblControlProfile])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'nazv -> entity.nazv,
        'ispolling -> entity.ispolling))
    SQL("""insert into pbl_control_profile(
      nazv,
      ispolling
    ) values (
      {nazv},
      {ispolling}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblControlProfile)(implicit session: DBSession = autoSession): PblControlProfile = {
    withSQL {
      update(PblControlProfile).set(
        column.code -> entity.code,
        column.nazv -> entity.nazv,
        column.ispolling -> entity.ispolling
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblControlProfile)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblControlProfile).where.eq(column.code, entity.code) }.update.apply()
  }

}
