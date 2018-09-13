package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblHouse(
  code: Int,
  adr: Option[String] = None,
  citycode: Option[Int] = None,
  areacode: Option[Int] = None,
  streetcode: Option[Int] = None,
  house: Option[Int] = None,
  houseBlock: Option[String] = None,
  houseB: Option[String] = None,
  streetcode2: Option[Int] = None,
  house2: Option[Int] = None,
  houseB2: Option[String] = None,
  dop: Option[String] = None,
  podezd: Option[Short] = None,
  floor: Option[Short] = None,
  vihod: Option[String] = None,
  kluch: Option[String] = None,
  workdop: Option[String] = None,
  ismark: Option[Short] = None,
  ishide: Option[Short] = None,
  apartc: Option[Short] = None,
  notused: Option[Short] = None,
  isdel: Option[Short] = None,
  customnazv: Option[String] = None,
  layers: Option[String] = None,
  scheme: Option[String] = None,
  typer: Option[Short] = None,
  postindex: Option[String] = None,
  dateadd: Option[LocalDate] = None,
  latitude: Option[BigDecimal] = None,
  longitude: Option[BigDecimal] = None,
  mainmapGuid: Option[Int] = None,
  coordinates: Option[String] = None,
  addressQuarterId: Option[Int] = None,
  tariffGroupId: Option[Int] = None,
  isShowLblOnMap: Option[Short] = None,
  construct: Option[String] = None,
  newId: Option[Int] = None) {

  def save()(implicit session: DBSession = PblHouse.autoSession): PblHouse = PblHouse.save(this)(session)

  def destroy()(implicit session: DBSession = PblHouse.autoSession): Int = PblHouse.destroy(this)(session)

}


object PblHouse extends SQLSyntaxSupport[PblHouse] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_house"

  override val columns = Seq("code", "adr", "citycode", "areacode", "streetcode", "house", "house_block", "house_b", "streetcode2", "house2", "house_b2", "dop", "podezd", "floor", "vihod", "kluch", "workdop", "ismark", "ishide", "apartc", "notused", "isdel", "customnazv", "layers", "scheme", "typer", "postindex", "dateadd", "latitude", "longitude", "mainmap_guid", "coordinates", "address_quarter_id", "tariff_group_id", "is_show_lbl_on_map", "construct", "new_id")

  def apply(ph: SyntaxProvider[PblHouse])(rs: WrappedResultSet): PblHouse = autoConstruct(rs, ph)
  def apply(ph: ResultName[PblHouse])(rs: WrappedResultSet): PblHouse = autoConstruct(rs, ph)

  val ph = PblHouse.syntax("ph")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblHouse] = {
    withSQL {
      select.from(PblHouse as ph).where.eq(ph.code, code)
    }.map(PblHouse(ph.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblHouse] = {
    withSQL(select.from(PblHouse as ph)).map(PblHouse(ph.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblHouse as ph)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblHouse] = {
    withSQL {
      select.from(PblHouse as ph).where.append(where)
    }.map(PblHouse(ph.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblHouse] = {
    withSQL {
      select.from(PblHouse as ph).where.append(where)
    }.map(PblHouse(ph.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblHouse as ph).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    adr: Option[String] = None,
    citycode: Option[Int] = None,
    areacode: Option[Int] = None,
    streetcode: Option[Int] = None,
    house: Option[Int] = None,
    houseBlock: Option[String] = None,
    houseB: Option[String] = None,
    streetcode2: Option[Int] = None,
    house2: Option[Int] = None,
    houseB2: Option[String] = None,
    dop: Option[String] = None,
    podezd: Option[Short] = None,
    floor: Option[Short] = None,
    vihod: Option[String] = None,
    kluch: Option[String] = None,
    workdop: Option[String] = None,
    ismark: Option[Short] = None,
    ishide: Option[Short] = None,
    apartc: Option[Short] = None,
    notused: Option[Short] = None,
    isdel: Option[Short] = None,
    customnazv: Option[String] = None,
    layers: Option[String] = None,
    scheme: Option[String] = None,
    typer: Option[Short] = None,
    postindex: Option[String] = None,
    dateadd: Option[LocalDate] = None,
    latitude: Option[BigDecimal] = None,
    longitude: Option[BigDecimal] = None,
    mainmapGuid: Option[Int] = None,
    coordinates: Option[String] = None,
    addressQuarterId: Option[Int] = None,
    tariffGroupId: Option[Int] = None,
    isShowLblOnMap: Option[Short] = None,
    construct: Option[String] = None,
    newId: Option[Int] = None)(implicit session: DBSession = autoSession): PblHouse = {
    val generatedKey = withSQL {
      insert.into(PblHouse).namedValues(
        column.adr -> adr,
        column.citycode -> citycode,
        column.areacode -> areacode,
        column.streetcode -> streetcode,
        column.house -> house,
        column.houseBlock -> houseBlock,
        column.houseB -> houseB,
        column.streetcode2 -> streetcode2,
        column.house2 -> house2,
        column.houseB2 -> houseB2,
        column.dop -> dop,
        column.podezd -> podezd,
        column.floor -> floor,
        column.vihod -> vihod,
        column.kluch -> kluch,
        column.workdop -> workdop,
        column.ismark -> ismark,
        column.ishide -> ishide,
        column.apartc -> apartc,
        column.notused -> notused,
        column.isdel -> isdel,
        column.customnazv -> customnazv,
        column.layers -> layers,
        column.scheme -> scheme,
        column.typer -> typer,
        column.postindex -> postindex,
        column.dateadd -> dateadd,
        column.latitude -> latitude,
        column.longitude -> longitude,
        column.mainmapGuid -> mainmapGuid,
        column.coordinates -> coordinates,
        column.addressQuarterId -> addressQuarterId,
        column.tariffGroupId -> tariffGroupId,
        column.isShowLblOnMap -> isShowLblOnMap,
        column.construct -> construct,
        column.newId -> newId
      )
    }.updateAndReturnGeneratedKey.apply()

    PblHouse(
      code = generatedKey.toInt,
      adr = adr,
      citycode = citycode,
      areacode = areacode,
      streetcode = streetcode,
      house = house,
      houseBlock = houseBlock,
      houseB = houseB,
      streetcode2 = streetcode2,
      house2 = house2,
      houseB2 = houseB2,
      dop = dop,
      podezd = podezd,
      floor = floor,
      vihod = vihod,
      kluch = kluch,
      workdop = workdop,
      ismark = ismark,
      ishide = ishide,
      apartc = apartc,
      notused = notused,
      isdel = isdel,
      customnazv = customnazv,
      layers = layers,
      scheme = scheme,
      typer = typer,
      postindex = postindex,
      dateadd = dateadd,
      latitude = latitude,
      longitude = longitude,
      mainmapGuid = mainmapGuid,
      coordinates = coordinates,
      addressQuarterId = addressQuarterId,
      tariffGroupId = tariffGroupId,
      isShowLblOnMap = isShowLblOnMap,
      construct = construct,
      newId = newId)
  }

  def batchInsert(entities: collection.Seq[PblHouse])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'adr -> entity.adr,
        'citycode -> entity.citycode,
        'areacode -> entity.areacode,
        'streetcode -> entity.streetcode,
        'house -> entity.house,
        'houseBlock -> entity.houseBlock,
        'houseB -> entity.houseB,
        'streetcode2 -> entity.streetcode2,
        'house2 -> entity.house2,
        'houseB2 -> entity.houseB2,
        'dop -> entity.dop,
        'podezd -> entity.podezd,
        'floor -> entity.floor,
        'vihod -> entity.vihod,
        'kluch -> entity.kluch,
        'workdop -> entity.workdop,
        'ismark -> entity.ismark,
        'ishide -> entity.ishide,
        'apartc -> entity.apartc,
        'notused -> entity.notused,
        'isdel -> entity.isdel,
        'customnazv -> entity.customnazv,
        'layers -> entity.layers,
        'scheme -> entity.scheme,
        'typer -> entity.typer,
        'postindex -> entity.postindex,
        'dateadd -> entity.dateadd,
        'latitude -> entity.latitude,
        'longitude -> entity.longitude,
        'mainmapGuid -> entity.mainmapGuid,
        'coordinates -> entity.coordinates,
        'addressQuarterId -> entity.addressQuarterId,
        'tariffGroupId -> entity.tariffGroupId,
        'isShowLblOnMap -> entity.isShowLblOnMap,
        'construct -> entity.construct,
        'newId -> entity.newId))
    SQL("""insert into pbl_house(
      adr,
      citycode,
      areacode,
      streetcode,
      house,
      house_block,
      house_b,
      streetcode2,
      house2,
      house_b2,
      dop,
      podezd,
      floor,
      vihod,
      kluch,
      workdop,
      ismark,
      ishide,
      apartc,
      notused,
      isdel,
      customnazv,
      layers,
      scheme,
      typer,
      postindex,
      dateadd,
      latitude,
      longitude,
      mainmap_guid,
      coordinates,
      address_quarter_id,
      tariff_group_id,
      is_show_lbl_on_map,
      construct,
      new_id
    ) values (
      {adr},
      {citycode},
      {areacode},
      {streetcode},
      {house},
      {houseBlock},
      {houseB},
      {streetcode2},
      {house2},
      {houseB2},
      {dop},
      {podezd},
      {floor},
      {vihod},
      {kluch},
      {workdop},
      {ismark},
      {ishide},
      {apartc},
      {notused},
      {isdel},
      {customnazv},
      {layers},
      {scheme},
      {typer},
      {postindex},
      {dateadd},
      {latitude},
      {longitude},
      {mainmapGuid},
      {coordinates},
      {addressQuarterId},
      {tariffGroupId},
      {isShowLblOnMap},
      {construct},
      {newId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblHouse)(implicit session: DBSession = autoSession): PblHouse = {
    withSQL {
      update(PblHouse).set(
        column.code -> entity.code,
        column.adr -> entity.adr,
        column.citycode -> entity.citycode,
        column.areacode -> entity.areacode,
        column.streetcode -> entity.streetcode,
        column.house -> entity.house,
        column.houseBlock -> entity.houseBlock,
        column.houseB -> entity.houseB,
        column.streetcode2 -> entity.streetcode2,
        column.house2 -> entity.house2,
        column.houseB2 -> entity.houseB2,
        column.dop -> entity.dop,
        column.podezd -> entity.podezd,
        column.floor -> entity.floor,
        column.vihod -> entity.vihod,
        column.kluch -> entity.kluch,
        column.workdop -> entity.workdop,
        column.ismark -> entity.ismark,
        column.ishide -> entity.ishide,
        column.apartc -> entity.apartc,
        column.notused -> entity.notused,
        column.isdel -> entity.isdel,
        column.customnazv -> entity.customnazv,
        column.layers -> entity.layers,
        column.scheme -> entity.scheme,
        column.typer -> entity.typer,
        column.postindex -> entity.postindex,
        column.dateadd -> entity.dateadd,
        column.latitude -> entity.latitude,
        column.longitude -> entity.longitude,
        column.mainmapGuid -> entity.mainmapGuid,
        column.coordinates -> entity.coordinates,
        column.addressQuarterId -> entity.addressQuarterId,
        column.tariffGroupId -> entity.tariffGroupId,
        column.isShowLblOnMap -> entity.isShowLblOnMap,
        column.construct -> entity.construct,
        column.newId -> entity.newId
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblHouse)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblHouse).where.eq(column.code, entity.code) }.update.apply()
  }

}
