package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblCabletestLog(
  id: Int,
  dateDo: Option[DateTime] = None,
  deviceId: Option[Int] = None,
  isFinish: Option[Short] = None,
  result: Option[String] = None) {

  def save()(implicit session: DBSession = PblCabletestLog.autoSession): PblCabletestLog = PblCabletestLog.save(this)(session)

  def destroy()(implicit session: DBSession = PblCabletestLog.autoSession): Int = PblCabletestLog.destroy(this)(session)

}


object PblCabletestLog extends SQLSyntaxSupport[PblCabletestLog] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_cabletest_log"

  override val columns = Seq("id", "date_do", "device_id", "is_finish", "result")

  def apply(pcl: SyntaxProvider[PblCabletestLog])(rs: WrappedResultSet): PblCabletestLog = autoConstruct(rs, pcl)
  def apply(pcl: ResultName[PblCabletestLog])(rs: WrappedResultSet): PblCabletestLog = autoConstruct(rs, pcl)

  val pcl = PblCabletestLog.syntax("pcl")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblCabletestLog] = {
    withSQL {
      select.from(PblCabletestLog as pcl).where.eq(pcl.id, id)
    }.map(PblCabletestLog(pcl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblCabletestLog] = {
    withSQL(select.from(PblCabletestLog as pcl)).map(PblCabletestLog(pcl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblCabletestLog as pcl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblCabletestLog] = {
    withSQL {
      select.from(PblCabletestLog as pcl).where.append(where)
    }.map(PblCabletestLog(pcl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblCabletestLog] = {
    withSQL {
      select.from(PblCabletestLog as pcl).where.append(where)
    }.map(PblCabletestLog(pcl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblCabletestLog as pcl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    dateDo: Option[DateTime] = None,
    deviceId: Option[Int] = None,
    isFinish: Option[Short] = None,
    result: Option[String] = None)(implicit session: DBSession = autoSession): PblCabletestLog = {
    val generatedKey = withSQL {
      insert.into(PblCabletestLog).namedValues(
        column.dateDo -> dateDo,
        column.deviceId -> deviceId,
        column.isFinish -> isFinish,
        column.result -> result
      )
    }.updateAndReturnGeneratedKey.apply()

    PblCabletestLog(
      id = generatedKey.toInt,
      dateDo = dateDo,
      deviceId = deviceId,
      isFinish = isFinish,
      result = result)
  }

  def batchInsert(entities: collection.Seq[PblCabletestLog])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'dateDo -> entity.dateDo,
        'deviceId -> entity.deviceId,
        'isFinish -> entity.isFinish,
        'result -> entity.result))
    SQL("""insert into pbl_cabletest_log(
      date_do,
      device_id,
      is_finish,
      result
    ) values (
      {dateDo},
      {deviceId},
      {isFinish},
      {result}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblCabletestLog)(implicit session: DBSession = autoSession): PblCabletestLog = {
    withSQL {
      update(PblCabletestLog).set(
        column.id -> entity.id,
        column.dateDo -> entity.dateDo,
        column.deviceId -> entity.deviceId,
        column.isFinish -> entity.isFinish,
        column.result -> entity.result
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblCabletestLog)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblCabletestLog).where.eq(column.id, entity.id) }.update.apply()
  }

}
