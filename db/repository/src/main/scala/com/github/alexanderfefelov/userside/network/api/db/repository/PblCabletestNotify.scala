package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblCabletestNotify(
  id: Int,
  dateDo: Option[DateTime] = None,
  deviceId: Option[Int] = None,
  port: Option[Int] = None,
  prevLength: Option[Int] = None,
  curLength: Option[Int] = None) {

  def save()(implicit session: DBSession = PblCabletestNotify.autoSession): PblCabletestNotify = PblCabletestNotify.save(this)(session)

  def destroy()(implicit session: DBSession = PblCabletestNotify.autoSession): Int = PblCabletestNotify.destroy(this)(session)

}


object PblCabletestNotify extends SQLSyntaxSupport[PblCabletestNotify] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_cabletest_notify"

  override val columns = Seq("id", "date_do", "device_id", "port", "prev_length", "cur_length")

  def apply(pcn: SyntaxProvider[PblCabletestNotify])(rs: WrappedResultSet): PblCabletestNotify = autoConstruct(rs, pcn)
  def apply(pcn: ResultName[PblCabletestNotify])(rs: WrappedResultSet): PblCabletestNotify = autoConstruct(rs, pcn)

  val pcn = PblCabletestNotify.syntax("pcn")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblCabletestNotify] = {
    withSQL {
      select.from(PblCabletestNotify as pcn).where.eq(pcn.id, id)
    }.map(PblCabletestNotify(pcn.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblCabletestNotify] = {
    withSQL(select.from(PblCabletestNotify as pcn)).map(PblCabletestNotify(pcn.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblCabletestNotify as pcn)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblCabletestNotify] = {
    withSQL {
      select.from(PblCabletestNotify as pcn).where.append(where)
    }.map(PblCabletestNotify(pcn.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblCabletestNotify] = {
    withSQL {
      select.from(PblCabletestNotify as pcn).where.append(where)
    }.map(PblCabletestNotify(pcn.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblCabletestNotify as pcn).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    dateDo: Option[DateTime] = None,
    deviceId: Option[Int] = None,
    port: Option[Int] = None,
    prevLength: Option[Int] = None,
    curLength: Option[Int] = None)(implicit session: DBSession = autoSession): PblCabletestNotify = {
    val generatedKey = withSQL {
      insert.into(PblCabletestNotify).namedValues(
        column.dateDo -> dateDo,
        column.deviceId -> deviceId,
        column.port -> port,
        column.prevLength -> prevLength,
        column.curLength -> curLength
      )
    }.updateAndReturnGeneratedKey.apply()

    PblCabletestNotify(
      id = generatedKey.toInt,
      dateDo = dateDo,
      deviceId = deviceId,
      port = port,
      prevLength = prevLength,
      curLength = curLength)
  }

  def batchInsert(entities: collection.Seq[PblCabletestNotify])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'dateDo -> entity.dateDo,
        'deviceId -> entity.deviceId,
        'port -> entity.port,
        'prevLength -> entity.prevLength,
        'curLength -> entity.curLength))
    SQL("""insert into pbl_cabletest_notify(
      date_do,
      device_id,
      port,
      prev_length,
      cur_length
    ) values (
      {dateDo},
      {deviceId},
      {port},
      {prevLength},
      {curLength}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblCabletestNotify)(implicit session: DBSession = autoSession): PblCabletestNotify = {
    withSQL {
      update(PblCabletestNotify).set(
        column.id -> entity.id,
        column.dateDo -> entity.dateDo,
        column.deviceId -> entity.deviceId,
        column.port -> entity.port,
        column.prevLength -> entity.prevLength,
        column.curLength -> entity.curLength
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblCabletestNotify)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblCabletestNotify).where.eq(column.id, entity.id) }.update.apply()
  }

}
