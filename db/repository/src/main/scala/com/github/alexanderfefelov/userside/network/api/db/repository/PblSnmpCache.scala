package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblSnmpCache(
  id: Int,
  deviceType: Option[Short] = None,
  deviceId: Option[Int] = None,
  dateRead: Option[DateTime] = None,
  oid: Option[String] = None,
  value: Option[String] = None) {

  def save()(implicit session: DBSession = PblSnmpCache.autoSession): PblSnmpCache = PblSnmpCache.save(this)(session)

  def destroy()(implicit session: DBSession = PblSnmpCache.autoSession): Int = PblSnmpCache.destroy(this)(session)

}


object PblSnmpCache extends SQLSyntaxSupport[PblSnmpCache] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_snmp_cache"

  override val columns = Seq("id", "device_type", "device_id", "date_read", "oid", "value")

  def apply(psc: SyntaxProvider[PblSnmpCache])(rs: WrappedResultSet): PblSnmpCache = autoConstruct(rs, psc)
  def apply(psc: ResultName[PblSnmpCache])(rs: WrappedResultSet): PblSnmpCache = autoConstruct(rs, psc)

  val psc = PblSnmpCache.syntax("psc")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblSnmpCache] = {
    withSQL {
      select.from(PblSnmpCache as psc).where.eq(psc.id, id)
    }.map(PblSnmpCache(psc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblSnmpCache] = {
    withSQL(select.from(PblSnmpCache as psc)).map(PblSnmpCache(psc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblSnmpCache as psc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblSnmpCache] = {
    withSQL {
      select.from(PblSnmpCache as psc).where.append(where)
    }.map(PblSnmpCache(psc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblSnmpCache] = {
    withSQL {
      select.from(PblSnmpCache as psc).where.append(where)
    }.map(PblSnmpCache(psc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblSnmpCache as psc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    deviceType: Option[Short] = None,
    deviceId: Option[Int] = None,
    dateRead: Option[DateTime] = None,
    oid: Option[String] = None,
    value: Option[String] = None)(implicit session: DBSession = autoSession): PblSnmpCache = {
    val generatedKey = withSQL {
      insert.into(PblSnmpCache).namedValues(
        column.deviceType -> deviceType,
        column.deviceId -> deviceId,
        column.dateRead -> dateRead,
        column.oid -> oid,
        column.value -> value
      )
    }.updateAndReturnGeneratedKey.apply()

    PblSnmpCache(
      id = generatedKey.toInt,
      deviceType = deviceType,
      deviceId = deviceId,
      dateRead = dateRead,
      oid = oid,
      value = value)
  }

  def batchInsert(entities: collection.Seq[PblSnmpCache])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'deviceType -> entity.deviceType,
        'deviceId -> entity.deviceId,
        'dateRead -> entity.dateRead,
        'oid -> entity.oid,
        'value -> entity.value))
    SQL("""insert into pbl_snmp_cache(
      device_type,
      device_id,
      date_read,
      oid,
      value
    ) values (
      {deviceType},
      {deviceId},
      {dateRead},
      {oid},
      {value}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblSnmpCache)(implicit session: DBSession = autoSession): PblSnmpCache = {
    withSQL {
      update(PblSnmpCache).set(
        column.id -> entity.id,
        column.deviceType -> entity.deviceType,
        column.deviceId -> entity.deviceId,
        column.dateRead -> entity.dateRead,
        column.oid -> entity.oid,
        column.value -> entity.value
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblSnmpCache)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblSnmpCache).where.eq(column.id, entity.id) }.update.apply()
  }

}
