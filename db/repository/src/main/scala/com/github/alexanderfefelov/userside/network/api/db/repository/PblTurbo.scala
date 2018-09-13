package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblTurbo(
  code: Int,
  usercode: Option[Int] = None,
  turbocode: Option[Int] = None,
  dateadd: Option[DateTime] = None,
  datestop: Option[DateTime] = None,
  status: Option[Short] = None,
  profit: Option[BigDecimal] = None) {

  def save()(implicit session: DBSession = PblTurbo.autoSession): PblTurbo = PblTurbo.save(this)(session)

  def destroy()(implicit session: DBSession = PblTurbo.autoSession): Int = PblTurbo.destroy(this)(session)

}


object PblTurbo extends SQLSyntaxSupport[PblTurbo] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_turbo"

  override val columns = Seq("code", "usercode", "turbocode", "dateadd", "datestop", "status", "profit")

  def apply(pt: SyntaxProvider[PblTurbo])(rs: WrappedResultSet): PblTurbo = autoConstruct(rs, pt)
  def apply(pt: ResultName[PblTurbo])(rs: WrappedResultSet): PblTurbo = autoConstruct(rs, pt)

  val pt = PblTurbo.syntax("pt")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblTurbo] = {
    withSQL {
      select.from(PblTurbo as pt).where.eq(pt.code, code)
    }.map(PblTurbo(pt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblTurbo] = {
    withSQL(select.from(PblTurbo as pt)).map(PblTurbo(pt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblTurbo as pt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblTurbo] = {
    withSQL {
      select.from(PblTurbo as pt).where.append(where)
    }.map(PblTurbo(pt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblTurbo] = {
    withSQL {
      select.from(PblTurbo as pt).where.append(where)
    }.map(PblTurbo(pt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblTurbo as pt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    usercode: Option[Int] = None,
    turbocode: Option[Int] = None,
    dateadd: Option[DateTime] = None,
    datestop: Option[DateTime] = None,
    status: Option[Short] = None,
    profit: Option[BigDecimal] = None)(implicit session: DBSession = autoSession): PblTurbo = {
    val generatedKey = withSQL {
      insert.into(PblTurbo).namedValues(
        column.usercode -> usercode,
        column.turbocode -> turbocode,
        column.dateadd -> dateadd,
        column.datestop -> datestop,
        column.status -> status,
        column.profit -> profit
      )
    }.updateAndReturnGeneratedKey.apply()

    PblTurbo(
      code = generatedKey.toInt,
      usercode = usercode,
      turbocode = turbocode,
      dateadd = dateadd,
      datestop = datestop,
      status = status,
      profit = profit)
  }

  def batchInsert(entities: collection.Seq[PblTurbo])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'usercode -> entity.usercode,
        'turbocode -> entity.turbocode,
        'dateadd -> entity.dateadd,
        'datestop -> entity.datestop,
        'status -> entity.status,
        'profit -> entity.profit))
    SQL("""insert into pbl_turbo(
      usercode,
      turbocode,
      dateadd,
      datestop,
      status,
      profit
    ) values (
      {usercode},
      {turbocode},
      {dateadd},
      {datestop},
      {status},
      {profit}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblTurbo)(implicit session: DBSession = autoSession): PblTurbo = {
    withSQL {
      update(PblTurbo).set(
        column.code -> entity.code,
        column.usercode -> entity.usercode,
        column.turbocode -> entity.turbocode,
        column.dateadd -> entity.dateadd,
        column.datestop -> entity.datestop,
        column.status -> entity.status,
        column.profit -> entity.profit
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblTurbo)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblTurbo).where.eq(column.code, entity.code) }.update.apply()
  }

}
