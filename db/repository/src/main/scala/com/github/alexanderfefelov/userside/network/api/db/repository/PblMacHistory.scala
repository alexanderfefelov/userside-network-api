package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblMacHistory(
  code: Int,
  usercode: Option[Int] = None,
  datestop: Option[DateTime] = None,
  userip: Option[BigDecimal] = None,
  mac: Option[String] = None) {

  def save()(implicit session: DBSession = PblMacHistory.autoSession): PblMacHistory = PblMacHistory.save(this)(session)

  def destroy()(implicit session: DBSession = PblMacHistory.autoSession): Int = PblMacHistory.destroy(this)(session)

}


object PblMacHistory extends SQLSyntaxSupport[PblMacHistory] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_mac_history"

  override val columns = Seq("code", "usercode", "datestop", "userip", "mac")

  def apply(pmh: SyntaxProvider[PblMacHistory])(rs: WrappedResultSet): PblMacHistory = autoConstruct(rs, pmh)
  def apply(pmh: ResultName[PblMacHistory])(rs: WrappedResultSet): PblMacHistory = autoConstruct(rs, pmh)

  val pmh = PblMacHistory.syntax("pmh")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblMacHistory] = {
    withSQL {
      select.from(PblMacHistory as pmh).where.eq(pmh.code, code)
    }.map(PblMacHistory(pmh.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblMacHistory] = {
    withSQL(select.from(PblMacHistory as pmh)).map(PblMacHistory(pmh.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblMacHistory as pmh)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblMacHistory] = {
    withSQL {
      select.from(PblMacHistory as pmh).where.append(where)
    }.map(PblMacHistory(pmh.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblMacHistory] = {
    withSQL {
      select.from(PblMacHistory as pmh).where.append(where)
    }.map(PblMacHistory(pmh.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblMacHistory as pmh).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    usercode: Option[Int] = None,
    datestop: Option[DateTime] = None,
    userip: Option[BigDecimal] = None,
    mac: Option[String] = None)(implicit session: DBSession = autoSession): PblMacHistory = {
    val generatedKey = withSQL {
      insert.into(PblMacHistory).namedValues(
        column.usercode -> usercode,
        column.datestop -> datestop,
        column.userip -> userip,
        column.mac -> mac
      )
    }.updateAndReturnGeneratedKey.apply()

    PblMacHistory(
      code = generatedKey.toInt,
      usercode = usercode,
      datestop = datestop,
      userip = userip,
      mac = mac)
  }

  def batchInsert(entities: collection.Seq[PblMacHistory])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'usercode -> entity.usercode,
        'datestop -> entity.datestop,
        'userip -> entity.userip,
        'mac -> entity.mac))
    SQL("""insert into pbl_mac_history(
      usercode,
      datestop,
      userip,
      mac
    ) values (
      {usercode},
      {datestop},
      {userip},
      {mac}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblMacHistory)(implicit session: DBSession = autoSession): PblMacHistory = {
    withSQL {
      update(PblMacHistory).set(
        column.code -> entity.code,
        column.usercode -> entity.usercode,
        column.datestop -> entity.datestop,
        column.userip -> entity.userip,
        column.mac -> entity.mac
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblMacHistory)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblMacHistory).where.eq(column.code, entity.code) }.update.apply()
  }

}
