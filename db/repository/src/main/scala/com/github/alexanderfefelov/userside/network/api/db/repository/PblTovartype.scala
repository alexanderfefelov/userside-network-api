package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblTovartype(
  code: Int,
  tovarnazv: Option[String] = None,
  edizm: Option[String] = None,
  basecode: Option[Int] = None,
  elcount: Option[BigDecimal] = None,
  typecode: Option[Int] = None,
  reserv: Option[String] = None,
  param: Option[String] = None,
  dateEquipmentLastsync: Option[DateTime] = None,
  isRequireSn: Option[Short] = None,
  equipmentBasedata: Option[String] = None,
  snPattern: Option[String] = None,
  isRequireMac: Option[Short] = None,
  additionalFieldList: Option[String] = None) {

  def save()(implicit session: DBSession = PblTovartype.autoSession): PblTovartype = PblTovartype.save(this)(session)

  def destroy()(implicit session: DBSession = PblTovartype.autoSession): Int = PblTovartype.destroy(this)(session)

}


object PblTovartype extends SQLSyntaxSupport[PblTovartype] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_tovartype"

  override val columns = Seq("code", "tovarnazv", "edizm", "basecode", "elcount", "typecode", "reserv", "param", "date_equipment_lastsync", "is_require_sn", "equipment_basedata", "sn_pattern", "is_require_mac", "additional_field_list")

  def apply(pt: SyntaxProvider[PblTovartype])(rs: WrappedResultSet): PblTovartype = autoConstruct(rs, pt)
  def apply(pt: ResultName[PblTovartype])(rs: WrappedResultSet): PblTovartype = autoConstruct(rs, pt)

  val pt = PblTovartype.syntax("pt")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblTovartype] = {
    withSQL {
      select.from(PblTovartype as pt).where.eq(pt.code, code)
    }.map(PblTovartype(pt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblTovartype] = {
    withSQL(select.from(PblTovartype as pt)).map(PblTovartype(pt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblTovartype as pt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblTovartype] = {
    withSQL {
      select.from(PblTovartype as pt).where.append(where)
    }.map(PblTovartype(pt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblTovartype] = {
    withSQL {
      select.from(PblTovartype as pt).where.append(where)
    }.map(PblTovartype(pt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblTovartype as pt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    tovarnazv: Option[String] = None,
    edizm: Option[String] = None,
    basecode: Option[Int] = None,
    elcount: Option[BigDecimal] = None,
    typecode: Option[Int] = None,
    reserv: Option[String] = None,
    param: Option[String] = None,
    dateEquipmentLastsync: Option[DateTime] = None,
    isRequireSn: Option[Short] = None,
    equipmentBasedata: Option[String] = None,
    snPattern: Option[String] = None,
    isRequireMac: Option[Short] = None,
    additionalFieldList: Option[String] = None)(implicit session: DBSession = autoSession): PblTovartype = {
    val generatedKey = withSQL {
      insert.into(PblTovartype).namedValues(
        column.tovarnazv -> tovarnazv,
        column.edizm -> edizm,
        column.basecode -> basecode,
        column.elcount -> elcount,
        column.typecode -> typecode,
        column.reserv -> reserv,
        column.param -> param,
        column.dateEquipmentLastsync -> dateEquipmentLastsync,
        column.isRequireSn -> isRequireSn,
        column.equipmentBasedata -> equipmentBasedata,
        column.snPattern -> snPattern,
        column.isRequireMac -> isRequireMac,
        column.additionalFieldList -> additionalFieldList
      )
    }.updateAndReturnGeneratedKey.apply()

    PblTovartype(
      code = generatedKey.toInt,
      tovarnazv = tovarnazv,
      edizm = edizm,
      basecode = basecode,
      elcount = elcount,
      typecode = typecode,
      reserv = reserv,
      param = param,
      dateEquipmentLastsync = dateEquipmentLastsync,
      isRequireSn = isRequireSn,
      equipmentBasedata = equipmentBasedata,
      snPattern = snPattern,
      isRequireMac = isRequireMac,
      additionalFieldList = additionalFieldList)
  }

  def batchInsert(entities: collection.Seq[PblTovartype])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'tovarnazv -> entity.tovarnazv,
        'edizm -> entity.edizm,
        'basecode -> entity.basecode,
        'elcount -> entity.elcount,
        'typecode -> entity.typecode,
        'reserv -> entity.reserv,
        'param -> entity.param,
        'dateEquipmentLastsync -> entity.dateEquipmentLastsync,
        'isRequireSn -> entity.isRequireSn,
        'equipmentBasedata -> entity.equipmentBasedata,
        'snPattern -> entity.snPattern,
        'isRequireMac -> entity.isRequireMac,
        'additionalFieldList -> entity.additionalFieldList))
    SQL("""insert into pbl_tovartype(
      tovarnazv,
      edizm,
      basecode,
      elcount,
      typecode,
      reserv,
      param,
      date_equipment_lastsync,
      is_require_sn,
      equipment_basedata,
      sn_pattern,
      is_require_mac,
      additional_field_list
    ) values (
      {tovarnazv},
      {edizm},
      {basecode},
      {elcount},
      {typecode},
      {reserv},
      {param},
      {dateEquipmentLastsync},
      {isRequireSn},
      {equipmentBasedata},
      {snPattern},
      {isRequireMac},
      {additionalFieldList}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblTovartype)(implicit session: DBSession = autoSession): PblTovartype = {
    withSQL {
      update(PblTovartype).set(
        column.code -> entity.code,
        column.tovarnazv -> entity.tovarnazv,
        column.edizm -> entity.edizm,
        column.basecode -> entity.basecode,
        column.elcount -> entity.elcount,
        column.typecode -> entity.typecode,
        column.reserv -> entity.reserv,
        column.param -> entity.param,
        column.dateEquipmentLastsync -> entity.dateEquipmentLastsync,
        column.isRequireSn -> entity.isRequireSn,
        column.equipmentBasedata -> entity.equipmentBasedata,
        column.snPattern -> entity.snPattern,
        column.isRequireMac -> entity.isRequireMac,
        column.additionalFieldList -> entity.additionalFieldList
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblTovartype)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblTovartype).where.eq(column.code, entity.code) }.update.apply()
  }

}
