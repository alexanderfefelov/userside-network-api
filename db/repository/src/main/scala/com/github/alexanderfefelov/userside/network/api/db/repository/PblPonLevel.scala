package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblPonLevel(
  id: Int,
  deviceId: Option[Int] = None,
  ponIface: Option[String] = None,
  onuName: Option[String] = None,
  level: Option[BigDecimal] = None,
  dateFrom: Option[DateTime] = None,
  dateTo: Option[DateTime] = None) {

  def save()(implicit session: DBSession = PblPonLevel.autoSession): PblPonLevel = PblPonLevel.save(this)(session)

  def destroy()(implicit session: DBSession = PblPonLevel.autoSession): Int = PblPonLevel.destroy(this)(session)

}


object PblPonLevel extends SQLSyntaxSupport[PblPonLevel] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_pon_level"

  override val columns = Seq("id", "device_id", "pon_iface", "onu_name", "level", "date_from", "date_to")

  def apply(ppl: SyntaxProvider[PblPonLevel])(rs: WrappedResultSet): PblPonLevel = autoConstruct(rs, ppl)
  def apply(ppl: ResultName[PblPonLevel])(rs: WrappedResultSet): PblPonLevel = autoConstruct(rs, ppl)

  val ppl = PblPonLevel.syntax("ppl")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblPonLevel] = {
    withSQL {
      select.from(PblPonLevel as ppl).where.eq(ppl.id, id)
    }.map(PblPonLevel(ppl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblPonLevel] = {
    withSQL(select.from(PblPonLevel as ppl)).map(PblPonLevel(ppl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblPonLevel as ppl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblPonLevel] = {
    withSQL {
      select.from(PblPonLevel as ppl).where.append(where)
    }.map(PblPonLevel(ppl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblPonLevel] = {
    withSQL {
      select.from(PblPonLevel as ppl).where.append(where)
    }.map(PblPonLevel(ppl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblPonLevel as ppl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    deviceId: Option[Int] = None,
    ponIface: Option[String] = None,
    onuName: Option[String] = None,
    level: Option[BigDecimal] = None,
    dateFrom: Option[DateTime] = None,
    dateTo: Option[DateTime] = None)(implicit session: DBSession = autoSession): PblPonLevel = {
    val generatedKey = withSQL {
      insert.into(PblPonLevel).namedValues(
        column.deviceId -> deviceId,
        column.ponIface -> ponIface,
        column.onuName -> onuName,
        column.level -> level,
        column.dateFrom -> dateFrom,
        column.dateTo -> dateTo
      )
    }.updateAndReturnGeneratedKey.apply()

    PblPonLevel(
      id = generatedKey.toInt,
      deviceId = deviceId,
      ponIface = ponIface,
      onuName = onuName,
      level = level,
      dateFrom = dateFrom,
      dateTo = dateTo)
  }

  def batchInsert(entities: collection.Seq[PblPonLevel])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'deviceId -> entity.deviceId,
        'ponIface -> entity.ponIface,
        'onuName -> entity.onuName,
        'level -> entity.level,
        'dateFrom -> entity.dateFrom,
        'dateTo -> entity.dateTo))
    SQL("""insert into pbl_pon_level(
      device_id,
      pon_iface,
      onu_name,
      level,
      date_from,
      date_to
    ) values (
      {deviceId},
      {ponIface},
      {onuName},
      {level},
      {dateFrom},
      {dateTo}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblPonLevel)(implicit session: DBSession = autoSession): PblPonLevel = {
    withSQL {
      update(PblPonLevel).set(
        column.id -> entity.id,
        column.deviceId -> entity.deviceId,
        column.ponIface -> entity.ponIface,
        column.onuName -> entity.onuName,
        column.level -> entity.level,
        column.dateFrom -> entity.dateFrom,
        column.dateTo -> entity.dateTo
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblPonLevel)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblPonLevel).where.eq(column.id, entity.id) }.update.apply()
  }

}
