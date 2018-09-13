package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblControlProfileOid(
  code: Int,
  profilecode: Option[Int] = None,
  oidcode: Option[Int] = None) {

  def save()(implicit session: DBSession = PblControlProfileOid.autoSession): PblControlProfileOid = PblControlProfileOid.save(this)(session)

  def destroy()(implicit session: DBSession = PblControlProfileOid.autoSession): Int = PblControlProfileOid.destroy(this)(session)

}


object PblControlProfileOid extends SQLSyntaxSupport[PblControlProfileOid] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_control_profile_oid"

  override val columns = Seq("code", "profilecode", "oidcode")

  def apply(pcpo: SyntaxProvider[PblControlProfileOid])(rs: WrappedResultSet): PblControlProfileOid = autoConstruct(rs, pcpo)
  def apply(pcpo: ResultName[PblControlProfileOid])(rs: WrappedResultSet): PblControlProfileOid = autoConstruct(rs, pcpo)

  val pcpo = PblControlProfileOid.syntax("pcpo")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblControlProfileOid] = {
    withSQL {
      select.from(PblControlProfileOid as pcpo).where.eq(pcpo.code, code)
    }.map(PblControlProfileOid(pcpo.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblControlProfileOid] = {
    withSQL(select.from(PblControlProfileOid as pcpo)).map(PblControlProfileOid(pcpo.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblControlProfileOid as pcpo)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblControlProfileOid] = {
    withSQL {
      select.from(PblControlProfileOid as pcpo).where.append(where)
    }.map(PblControlProfileOid(pcpo.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblControlProfileOid] = {
    withSQL {
      select.from(PblControlProfileOid as pcpo).where.append(where)
    }.map(PblControlProfileOid(pcpo.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblControlProfileOid as pcpo).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    profilecode: Option[Int] = None,
    oidcode: Option[Int] = None)(implicit session: DBSession = autoSession): PblControlProfileOid = {
    val generatedKey = withSQL {
      insert.into(PblControlProfileOid).namedValues(
        column.profilecode -> profilecode,
        column.oidcode -> oidcode
      )
    }.updateAndReturnGeneratedKey.apply()

    PblControlProfileOid(
      code = generatedKey.toInt,
      profilecode = profilecode,
      oidcode = oidcode)
  }

  def batchInsert(entities: collection.Seq[PblControlProfileOid])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'profilecode -> entity.profilecode,
        'oidcode -> entity.oidcode))
    SQL("""insert into pbl_control_profile_oid(
      profilecode,
      oidcode
    ) values (
      {profilecode},
      {oidcode}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblControlProfileOid)(implicit session: DBSession = autoSession): PblControlProfileOid = {
    withSQL {
      update(PblControlProfileOid).set(
        column.code -> entity.code,
        column.profilecode -> entity.profilecode,
        column.oidcode -> entity.oidcode
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblControlProfileOid)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblControlProfileOid).where.eq(column.code, entity.code) }.update.apply()
  }

}
