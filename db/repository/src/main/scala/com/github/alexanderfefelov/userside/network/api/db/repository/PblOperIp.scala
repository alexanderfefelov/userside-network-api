package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblOperIp(
  code: Int,
  opercode: Option[Int] = None,
  userip: Option[String] = None,
  userip2: Option[String] = None) {

  def save()(implicit session: DBSession = PblOperIp.autoSession): PblOperIp = PblOperIp.save(this)(session)

  def destroy()(implicit session: DBSession = PblOperIp.autoSession): Int = PblOperIp.destroy(this)(session)

}


object PblOperIp extends SQLSyntaxSupport[PblOperIp] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_oper_ip"

  override val columns = Seq("code", "opercode", "userip", "userip2")

  def apply(poi: SyntaxProvider[PblOperIp])(rs: WrappedResultSet): PblOperIp = autoConstruct(rs, poi)
  def apply(poi: ResultName[PblOperIp])(rs: WrappedResultSet): PblOperIp = autoConstruct(rs, poi)

  val poi = PblOperIp.syntax("poi")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblOperIp] = {
    withSQL {
      select.from(PblOperIp as poi).where.eq(poi.code, code)
    }.map(PblOperIp(poi.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblOperIp] = {
    withSQL(select.from(PblOperIp as poi)).map(PblOperIp(poi.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblOperIp as poi)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblOperIp] = {
    withSQL {
      select.from(PblOperIp as poi).where.append(where)
    }.map(PblOperIp(poi.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblOperIp] = {
    withSQL {
      select.from(PblOperIp as poi).where.append(where)
    }.map(PblOperIp(poi.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblOperIp as poi).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    opercode: Option[Int] = None,
    userip: Option[String] = None,
    userip2: Option[String] = None)(implicit session: DBSession = autoSession): PblOperIp = {
    val generatedKey = withSQL {
      insert.into(PblOperIp).namedValues(
        column.opercode -> opercode,
        column.userip -> userip,
        column.userip2 -> userip2
      )
    }.updateAndReturnGeneratedKey.apply()

    PblOperIp(
      code = generatedKey.toInt,
      opercode = opercode,
      userip = userip,
      userip2 = userip2)
  }

  def batchInsert(entities: collection.Seq[PblOperIp])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'opercode -> entity.opercode,
        'userip -> entity.userip,
        'userip2 -> entity.userip2))
    SQL("""insert into pbl_oper_ip(
      opercode,
      userip,
      userip2
    ) values (
      {opercode},
      {userip},
      {userip2}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblOperIp)(implicit session: DBSession = autoSession): PblOperIp = {
    withSQL {
      update(PblOperIp).set(
        column.code -> entity.code,
        column.opercode -> entity.opercode,
        column.userip -> entity.userip,
        column.userip2 -> entity.userip2
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblOperIp)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblOperIp).where.eq(column.code, entity.code) }.update.apply()
  }

}
