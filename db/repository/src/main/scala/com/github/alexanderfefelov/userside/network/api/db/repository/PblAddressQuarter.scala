package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblAddressQuarter(
  id: Int,
  name: Option[String] = None,
  addressProvinceId: Option[Int] = None,
  addressDistrictId: Option[Int] = None,
  addressCityId: Option[Int] = None,
  addressAreaId: Option[Int] = None,
  addressStreetId: Option[Int] = None,
  newId: Option[Int] = None) {

  def save()(implicit session: DBSession = PblAddressQuarter.autoSession): PblAddressQuarter = PblAddressQuarter.save(this)(session)

  def destroy()(implicit session: DBSession = PblAddressQuarter.autoSession): Int = PblAddressQuarter.destroy(this)(session)

}


object PblAddressQuarter extends SQLSyntaxSupport[PblAddressQuarter] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_address_quarter"

  override val columns = Seq("id", "name", "address_province_id", "address_district_id", "address_city_id", "address_area_id", "address_street_id", "new_id")

  def apply(paq: SyntaxProvider[PblAddressQuarter])(rs: WrappedResultSet): PblAddressQuarter = autoConstruct(rs, paq)
  def apply(paq: ResultName[PblAddressQuarter])(rs: WrappedResultSet): PblAddressQuarter = autoConstruct(rs, paq)

  val paq = PblAddressQuarter.syntax("paq")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblAddressQuarter] = {
    withSQL {
      select.from(PblAddressQuarter as paq).where.eq(paq.id, id)
    }.map(PblAddressQuarter(paq.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblAddressQuarter] = {
    withSQL(select.from(PblAddressQuarter as paq)).map(PblAddressQuarter(paq.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblAddressQuarter as paq)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblAddressQuarter] = {
    withSQL {
      select.from(PblAddressQuarter as paq).where.append(where)
    }.map(PblAddressQuarter(paq.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblAddressQuarter] = {
    withSQL {
      select.from(PblAddressQuarter as paq).where.append(where)
    }.map(PblAddressQuarter(paq.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblAddressQuarter as paq).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: Option[String] = None,
    addressProvinceId: Option[Int] = None,
    addressDistrictId: Option[Int] = None,
    addressCityId: Option[Int] = None,
    addressAreaId: Option[Int] = None,
    addressStreetId: Option[Int] = None,
    newId: Option[Int] = None)(implicit session: DBSession = autoSession): PblAddressQuarter = {
    val generatedKey = withSQL {
      insert.into(PblAddressQuarter).namedValues(
        column.name -> name,
        column.addressProvinceId -> addressProvinceId,
        column.addressDistrictId -> addressDistrictId,
        column.addressCityId -> addressCityId,
        column.addressAreaId -> addressAreaId,
        column.addressStreetId -> addressStreetId,
        column.newId -> newId
      )
    }.updateAndReturnGeneratedKey.apply()

    PblAddressQuarter(
      id = generatedKey.toInt,
      name = name,
      addressProvinceId = addressProvinceId,
      addressDistrictId = addressDistrictId,
      addressCityId = addressCityId,
      addressAreaId = addressAreaId,
      addressStreetId = addressStreetId,
      newId = newId)
  }

  def batchInsert(entities: collection.Seq[PblAddressQuarter])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'name -> entity.name,
        'addressProvinceId -> entity.addressProvinceId,
        'addressDistrictId -> entity.addressDistrictId,
        'addressCityId -> entity.addressCityId,
        'addressAreaId -> entity.addressAreaId,
        'addressStreetId -> entity.addressStreetId,
        'newId -> entity.newId))
    SQL("""insert into pbl_address_quarter(
      name,
      address_province_id,
      address_district_id,
      address_city_id,
      address_area_id,
      address_street_id,
      new_id
    ) values (
      {name},
      {addressProvinceId},
      {addressDistrictId},
      {addressCityId},
      {addressAreaId},
      {addressStreetId},
      {newId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblAddressQuarter)(implicit session: DBSession = autoSession): PblAddressQuarter = {
    withSQL {
      update(PblAddressQuarter).set(
        column.id -> entity.id,
        column.name -> entity.name,
        column.addressProvinceId -> entity.addressProvinceId,
        column.addressDistrictId -> entity.addressDistrictId,
        column.addressCityId -> entity.addressCityId,
        column.addressAreaId -> entity.addressAreaId,
        column.addressStreetId -> entity.addressStreetId,
        column.newId -> entity.newId
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblAddressQuarter)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblAddressQuarter).where.eq(column.id, entity.id) }.update.apply()
  }

}
