package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblDeviceIfaceCashe(
  id: Int,
  deviceId: Option[Int] = None,
  dateAdd: Option[DateTime] = None,
  dataType: Option[Short] = None,
  value: Option[String] = None) {

  def save()(implicit session: DBSession = PblDeviceIfaceCashe.autoSession): PblDeviceIfaceCashe = PblDeviceIfaceCashe.save(this)(session)

  def destroy()(implicit session: DBSession = PblDeviceIfaceCashe.autoSession): Int = PblDeviceIfaceCashe.destroy(this)(session)

}


object PblDeviceIfaceCashe extends SQLSyntaxSupport[PblDeviceIfaceCashe] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_device_iface_cashe"

  override val columns = Seq("id", "device_id", "date_add", "data_type", "value")

  def apply(pdic: SyntaxProvider[PblDeviceIfaceCashe])(rs: WrappedResultSet): PblDeviceIfaceCashe = autoConstruct(rs, pdic)
  def apply(pdic: ResultName[PblDeviceIfaceCashe])(rs: WrappedResultSet): PblDeviceIfaceCashe = autoConstruct(rs, pdic)

  val pdic = PblDeviceIfaceCashe.syntax("pdic")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblDeviceIfaceCashe] = {
    withSQL {
      select.from(PblDeviceIfaceCashe as pdic).where.eq(pdic.id, id)
    }.map(PblDeviceIfaceCashe(pdic.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblDeviceIfaceCashe] = {
    withSQL(select.from(PblDeviceIfaceCashe as pdic)).map(PblDeviceIfaceCashe(pdic.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblDeviceIfaceCashe as pdic)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblDeviceIfaceCashe] = {
    withSQL {
      select.from(PblDeviceIfaceCashe as pdic).where.append(where)
    }.map(PblDeviceIfaceCashe(pdic.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblDeviceIfaceCashe] = {
    withSQL {
      select.from(PblDeviceIfaceCashe as pdic).where.append(where)
    }.map(PblDeviceIfaceCashe(pdic.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblDeviceIfaceCashe as pdic).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    deviceId: Option[Int] = None,
    dateAdd: Option[DateTime] = None,
    dataType: Option[Short] = None,
    value: Option[String] = None)(implicit session: DBSession = autoSession): PblDeviceIfaceCashe = {
    val generatedKey = withSQL {
      insert.into(PblDeviceIfaceCashe).namedValues(
        column.deviceId -> deviceId,
        column.dateAdd -> dateAdd,
        column.dataType -> dataType,
        column.value -> value
      )
    }.updateAndReturnGeneratedKey.apply()

    PblDeviceIfaceCashe(
      id = generatedKey.toInt,
      deviceId = deviceId,
      dateAdd = dateAdd,
      dataType = dataType,
      value = value)
  }

  def batchInsert(entities: collection.Seq[PblDeviceIfaceCashe])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'deviceId -> entity.deviceId,
        'dateAdd -> entity.dateAdd,
        'dataType -> entity.dataType,
        'value -> entity.value))
    SQL("""insert into pbl_device_iface_cashe(
      device_id,
      date_add,
      data_type,
      value
    ) values (
      {deviceId},
      {dateAdd},
      {dataType},
      {value}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblDeviceIfaceCashe)(implicit session: DBSession = autoSession): PblDeviceIfaceCashe = {
    withSQL {
      update(PblDeviceIfaceCashe).set(
        column.id -> entity.id,
        column.deviceId -> entity.deviceId,
        column.dateAdd -> entity.dateAdd,
        column.dataType -> entity.dataType,
        column.value -> entity.value
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblDeviceIfaceCashe)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblDeviceIfaceCashe).where.eq(column.id, entity.id) }.update.apply()
  }

}
