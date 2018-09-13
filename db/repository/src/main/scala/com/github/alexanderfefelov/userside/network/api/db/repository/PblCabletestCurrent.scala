package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblCabletestCurrent(
  id: Int,
  deviceId: Option[Int] = None,
  port: Option[Int] = None,
  datePolling: Option[DateTime] = None,
  length: Option[Int] = None) {

  def save()(implicit session: DBSession = PblCabletestCurrent.autoSession): PblCabletestCurrent = PblCabletestCurrent.save(this)(session)

  def destroy()(implicit session: DBSession = PblCabletestCurrent.autoSession): Int = PblCabletestCurrent.destroy(this)(session)

}


object PblCabletestCurrent extends SQLSyntaxSupport[PblCabletestCurrent] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_cabletest_current"

  override val columns = Seq("id", "device_id", "port", "date_polling", "length")

  def apply(pcc: SyntaxProvider[PblCabletestCurrent])(rs: WrappedResultSet): PblCabletestCurrent = autoConstruct(rs, pcc)
  def apply(pcc: ResultName[PblCabletestCurrent])(rs: WrappedResultSet): PblCabletestCurrent = autoConstruct(rs, pcc)

  val pcc = PblCabletestCurrent.syntax("pcc")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblCabletestCurrent] = {
    withSQL {
      select.from(PblCabletestCurrent as pcc).where.eq(pcc.id, id)
    }.map(PblCabletestCurrent(pcc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblCabletestCurrent] = {
    withSQL(select.from(PblCabletestCurrent as pcc)).map(PblCabletestCurrent(pcc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblCabletestCurrent as pcc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblCabletestCurrent] = {
    withSQL {
      select.from(PblCabletestCurrent as pcc).where.append(where)
    }.map(PblCabletestCurrent(pcc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblCabletestCurrent] = {
    withSQL {
      select.from(PblCabletestCurrent as pcc).where.append(where)
    }.map(PblCabletestCurrent(pcc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblCabletestCurrent as pcc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    deviceId: Option[Int] = None,
    port: Option[Int] = None,
    datePolling: Option[DateTime] = None,
    length: Option[Int] = None)(implicit session: DBSession = autoSession): PblCabletestCurrent = {
    val generatedKey = withSQL {
      insert.into(PblCabletestCurrent).namedValues(
        column.deviceId -> deviceId,
        column.port -> port,
        column.datePolling -> datePolling,
        column.length -> length
      )
    }.updateAndReturnGeneratedKey.apply()

    PblCabletestCurrent(
      id = generatedKey.toInt,
      deviceId = deviceId,
      port = port,
      datePolling = datePolling,
      length = length)
  }

  def batchInsert(entities: collection.Seq[PblCabletestCurrent])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'deviceId -> entity.deviceId,
        'port -> entity.port,
        'datePolling -> entity.datePolling,
        'length -> entity.length))
    SQL("""insert into pbl_cabletest_current(
      device_id,
      port,
      date_polling,
      length
    ) values (
      {deviceId},
      {port},
      {datePolling},
      {length}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblCabletestCurrent)(implicit session: DBSession = autoSession): PblCabletestCurrent = {
    withSQL {
      update(PblCabletestCurrent).set(
        column.id -> entity.id,
        column.deviceId -> entity.deviceId,
        column.port -> entity.port,
        column.datePolling -> entity.datePolling,
        column.length -> entity.length
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblCabletestCurrent)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblCabletestCurrent).where.eq(column.id, entity.id) }.update.apply()
  }

}
