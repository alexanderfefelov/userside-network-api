package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblUzel(
  code: Int,
  codenumber: Option[String] = None,
  housecode: Option[Int] = None,
  podezd: Option[Short] = None,
  location: Option[String] = None,
  dateadd: Option[DateTime] = None,
  opis: Option[String] = None,
  isupd: Option[Short] = None,
  ismufta: Option[Short] = None,
  layers: Option[String] = None,
  citycode: Option[Int] = None,
  adr: Option[String] = None,
  nazv: Option[String] = None,
  keeper: Option[String] = None,
  invno: Option[String] = None,
  mapmarkcode: Option[Int] = None,
  jekcode: Option[Int] = None,
  parentuzel: Option[Int] = None,
  sizeX: Option[Int] = None,
  sizeY: Option[Int] = None,
  isplan: Option[Short] = None,
  latitude: Option[BigDecimal] = None,
  longitude: Option[BigDecimal] = None,
  mainmapGuid: Option[Int] = None,
  flagCustomCoord: Option[Short] = None) {

  def save()(implicit session: DBSession = PblUzel.autoSession): PblUzel = PblUzel.save(this)(session)

  def destroy()(implicit session: DBSession = PblUzel.autoSession): Int = PblUzel.destroy(this)(session)

}


object PblUzel extends SQLSyntaxSupport[PblUzel] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_uzel"

  override val columns = Seq("code", "codenumber", "housecode", "podezd", "location", "dateadd", "opis", "isupd", "ismufta", "layers", "citycode", "adr", "nazv", "keeper", "invno", "mapmarkcode", "jekcode", "parentuzel", "size_x", "size_y", "isplan", "latitude", "longitude", "mainmap_guid", "flag_custom_coord")

  def apply(pu: SyntaxProvider[PblUzel])(rs: WrappedResultSet): PblUzel = autoConstruct(rs, pu)
  def apply(pu: ResultName[PblUzel])(rs: WrappedResultSet): PblUzel = autoConstruct(rs, pu)

  val pu = PblUzel.syntax("pu")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblUzel] = {
    withSQL {
      select.from(PblUzel as pu).where.eq(pu.code, code)
    }.map(PblUzel(pu.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblUzel] = {
    withSQL(select.from(PblUzel as pu)).map(PblUzel(pu.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblUzel as pu)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblUzel] = {
    withSQL {
      select.from(PblUzel as pu).where.append(where)
    }.map(PblUzel(pu.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblUzel] = {
    withSQL {
      select.from(PblUzel as pu).where.append(where)
    }.map(PblUzel(pu.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblUzel as pu).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    codenumber: Option[String] = None,
    housecode: Option[Int] = None,
    podezd: Option[Short] = None,
    location: Option[String] = None,
    dateadd: Option[DateTime] = None,
    opis: Option[String] = None,
    isupd: Option[Short] = None,
    ismufta: Option[Short] = None,
    layers: Option[String] = None,
    citycode: Option[Int] = None,
    adr: Option[String] = None,
    nazv: Option[String] = None,
    keeper: Option[String] = None,
    invno: Option[String] = None,
    mapmarkcode: Option[Int] = None,
    jekcode: Option[Int] = None,
    parentuzel: Option[Int] = None,
    sizeX: Option[Int] = None,
    sizeY: Option[Int] = None,
    isplan: Option[Short] = None,
    latitude: Option[BigDecimal] = None,
    longitude: Option[BigDecimal] = None,
    mainmapGuid: Option[Int] = None,
    flagCustomCoord: Option[Short] = None)(implicit session: DBSession = autoSession): PblUzel = {
    val generatedKey = withSQL {
      insert.into(PblUzel).namedValues(
        column.codenumber -> codenumber,
        column.housecode -> housecode,
        column.podezd -> podezd,
        column.location -> location,
        column.dateadd -> dateadd,
        column.opis -> opis,
        column.isupd -> isupd,
        column.ismufta -> ismufta,
        column.layers -> layers,
        column.citycode -> citycode,
        column.adr -> adr,
        column.nazv -> nazv,
        column.keeper -> keeper,
        column.invno -> invno,
        column.mapmarkcode -> mapmarkcode,
        column.jekcode -> jekcode,
        column.parentuzel -> parentuzel,
        column.sizeX -> sizeX,
        column.sizeY -> sizeY,
        column.isplan -> isplan,
        column.latitude -> latitude,
        column.longitude -> longitude,
        column.mainmapGuid -> mainmapGuid,
        column.flagCustomCoord -> flagCustomCoord
      )
    }.updateAndReturnGeneratedKey.apply()

    PblUzel(
      code = generatedKey.toInt,
      codenumber = codenumber,
      housecode = housecode,
      podezd = podezd,
      location = location,
      dateadd = dateadd,
      opis = opis,
      isupd = isupd,
      ismufta = ismufta,
      layers = layers,
      citycode = citycode,
      adr = adr,
      nazv = nazv,
      keeper = keeper,
      invno = invno,
      mapmarkcode = mapmarkcode,
      jekcode = jekcode,
      parentuzel = parentuzel,
      sizeX = sizeX,
      sizeY = sizeY,
      isplan = isplan,
      latitude = latitude,
      longitude = longitude,
      mainmapGuid = mainmapGuid,
      flagCustomCoord = flagCustomCoord)
  }

  def batchInsert(entities: collection.Seq[PblUzel])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'codenumber -> entity.codenumber,
        'housecode -> entity.housecode,
        'podezd -> entity.podezd,
        'location -> entity.location,
        'dateadd -> entity.dateadd,
        'opis -> entity.opis,
        'isupd -> entity.isupd,
        'ismufta -> entity.ismufta,
        'layers -> entity.layers,
        'citycode -> entity.citycode,
        'adr -> entity.adr,
        'nazv -> entity.nazv,
        'keeper -> entity.keeper,
        'invno -> entity.invno,
        'mapmarkcode -> entity.mapmarkcode,
        'jekcode -> entity.jekcode,
        'parentuzel -> entity.parentuzel,
        'sizeX -> entity.sizeX,
        'sizeY -> entity.sizeY,
        'isplan -> entity.isplan,
        'latitude -> entity.latitude,
        'longitude -> entity.longitude,
        'mainmapGuid -> entity.mainmapGuid,
        'flagCustomCoord -> entity.flagCustomCoord))
    SQL("""insert into pbl_uzel(
      codenumber,
      housecode,
      podezd,
      location,
      dateadd,
      opis,
      isupd,
      ismufta,
      layers,
      citycode,
      adr,
      nazv,
      keeper,
      invno,
      mapmarkcode,
      jekcode,
      parentuzel,
      size_x,
      size_y,
      isplan,
      latitude,
      longitude,
      mainmap_guid,
      flag_custom_coord
    ) values (
      {codenumber},
      {housecode},
      {podezd},
      {location},
      {dateadd},
      {opis},
      {isupd},
      {ismufta},
      {layers},
      {citycode},
      {adr},
      {nazv},
      {keeper},
      {invno},
      {mapmarkcode},
      {jekcode},
      {parentuzel},
      {sizeX},
      {sizeY},
      {isplan},
      {latitude},
      {longitude},
      {mainmapGuid},
      {flagCustomCoord}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblUzel)(implicit session: DBSession = autoSession): PblUzel = {
    withSQL {
      update(PblUzel).set(
        column.code -> entity.code,
        column.codenumber -> entity.codenumber,
        column.housecode -> entity.housecode,
        column.podezd -> entity.podezd,
        column.location -> entity.location,
        column.dateadd -> entity.dateadd,
        column.opis -> entity.opis,
        column.isupd -> entity.isupd,
        column.ismufta -> entity.ismufta,
        column.layers -> entity.layers,
        column.citycode -> entity.citycode,
        column.adr -> entity.adr,
        column.nazv -> entity.nazv,
        column.keeper -> entity.keeper,
        column.invno -> entity.invno,
        column.mapmarkcode -> entity.mapmarkcode,
        column.jekcode -> entity.jekcode,
        column.parentuzel -> entity.parentuzel,
        column.sizeX -> entity.sizeX,
        column.sizeY -> entity.sizeY,
        column.isplan -> entity.isplan,
        column.latitude -> entity.latitude,
        column.longitude -> entity.longitude,
        column.mainmapGuid -> entity.mainmapGuid,
        column.flagCustomCoord -> entity.flagCustomCoord
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblUzel)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblUzel).where.eq(column.code, entity.code) }.update.apply()
  }

}
