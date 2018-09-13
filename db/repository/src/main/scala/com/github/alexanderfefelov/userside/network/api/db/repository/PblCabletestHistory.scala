package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblCabletestHistory(
  id: Int,
  deviceId: Option[Int] = None,
  port: Option[Int] = None,
  dateChange: Option[DateTime] = None,
  length: Option[Int] = None) {

  def save()(implicit session: DBSession = PblCabletestHistory.autoSession): PblCabletestHistory = PblCabletestHistory.save(this)(session)

  def destroy()(implicit session: DBSession = PblCabletestHistory.autoSession): Int = PblCabletestHistory.destroy(this)(session)

}


object PblCabletestHistory extends SQLSyntaxSupport[PblCabletestHistory] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_cabletest_history"

  override val columns = Seq("id", "device_id", "port", "date_change", "length")

  def apply(pch: SyntaxProvider[PblCabletestHistory])(rs: WrappedResultSet): PblCabletestHistory = autoConstruct(rs, pch)
  def apply(pch: ResultName[PblCabletestHistory])(rs: WrappedResultSet): PblCabletestHistory = autoConstruct(rs, pch)

  val pch = PblCabletestHistory.syntax("pch")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblCabletestHistory] = {
    withSQL {
      select.from(PblCabletestHistory as pch).where.eq(pch.id, id)
    }.map(PblCabletestHistory(pch.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblCabletestHistory] = {
    withSQL(select.from(PblCabletestHistory as pch)).map(PblCabletestHistory(pch.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblCabletestHistory as pch)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblCabletestHistory] = {
    withSQL {
      select.from(PblCabletestHistory as pch).where.append(where)
    }.map(PblCabletestHistory(pch.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblCabletestHistory] = {
    withSQL {
      select.from(PblCabletestHistory as pch).where.append(where)
    }.map(PblCabletestHistory(pch.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblCabletestHistory as pch).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    deviceId: Option[Int] = None,
    port: Option[Int] = None,
    dateChange: Option[DateTime] = None,
    length: Option[Int] = None)(implicit session: DBSession = autoSession): PblCabletestHistory = {
    val generatedKey = withSQL {
      insert.into(PblCabletestHistory).namedValues(
        column.deviceId -> deviceId,
        column.port -> port,
        column.dateChange -> dateChange,
        column.length -> length
      )
    }.updateAndReturnGeneratedKey.apply()

    PblCabletestHistory(
      id = generatedKey.toInt,
      deviceId = deviceId,
      port = port,
      dateChange = dateChange,
      length = length)
  }

  def batchInsert(entities: collection.Seq[PblCabletestHistory])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'deviceId -> entity.deviceId,
        'port -> entity.port,
        'dateChange -> entity.dateChange,
        'length -> entity.length))
    SQL("""insert into pbl_cabletest_history(
      device_id,
      port,
      date_change,
      length
    ) values (
      {deviceId},
      {port},
      {dateChange},
      {length}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblCabletestHistory)(implicit session: DBSession = autoSession): PblCabletestHistory = {
    withSQL {
      update(PblCabletestHistory).set(
        column.id -> entity.id,
        column.deviceId -> entity.deviceId,
        column.port -> entity.port,
        column.dateChange -> entity.dateChange,
        column.length -> entity.length
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblCabletestHistory)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblCabletestHistory).where.eq(column.id, entity.id) }.update.apply()
  }

}
