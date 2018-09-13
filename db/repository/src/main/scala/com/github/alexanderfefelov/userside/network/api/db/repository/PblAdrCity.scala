package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblAdrCity(
  code: Int,
  nazv: Option[String] = None,
  provincecode: Option[Int] = None,
  districtcode: Option[Int] = None,
  mapcode: Option[Int] = None,
  geoX: Option[BigDecimal] = None,
  geoY: Option[BigDecimal] = None,
  geoScale: Option[Short] = None,
  inmenu: Option[Short] = None,
  newId: Option[Int] = None) {

  def save()(implicit session: DBSession = PblAdrCity.autoSession): PblAdrCity = PblAdrCity.save(this)(session)

  def destroy()(implicit session: DBSession = PblAdrCity.autoSession): Int = PblAdrCity.destroy(this)(session)

}


object PblAdrCity extends SQLSyntaxSupport[PblAdrCity] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_adr_city"

  override val columns = Seq("code", "nazv", "provincecode", "districtcode", "mapcode", "geo_x", "geo_y", "geo_scale", "inmenu", "new_id")

  def apply(pac: SyntaxProvider[PblAdrCity])(rs: WrappedResultSet): PblAdrCity = autoConstruct(rs, pac)
  def apply(pac: ResultName[PblAdrCity])(rs: WrappedResultSet): PblAdrCity = autoConstruct(rs, pac)

  val pac = PblAdrCity.syntax("pac")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblAdrCity] = {
    withSQL {
      select.from(PblAdrCity as pac).where.eq(pac.code, code)
    }.map(PblAdrCity(pac.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblAdrCity] = {
    withSQL(select.from(PblAdrCity as pac)).map(PblAdrCity(pac.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblAdrCity as pac)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblAdrCity] = {
    withSQL {
      select.from(PblAdrCity as pac).where.append(where)
    }.map(PblAdrCity(pac.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblAdrCity] = {
    withSQL {
      select.from(PblAdrCity as pac).where.append(where)
    }.map(PblAdrCity(pac.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblAdrCity as pac).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    nazv: Option[String] = None,
    provincecode: Option[Int] = None,
    districtcode: Option[Int] = None,
    mapcode: Option[Int] = None,
    geoX: Option[BigDecimal] = None,
    geoY: Option[BigDecimal] = None,
    geoScale: Option[Short] = None,
    inmenu: Option[Short] = None,
    newId: Option[Int] = None)(implicit session: DBSession = autoSession): PblAdrCity = {
    val generatedKey = withSQL {
      insert.into(PblAdrCity).namedValues(
        column.nazv -> nazv,
        column.provincecode -> provincecode,
        column.districtcode -> districtcode,
        column.mapcode -> mapcode,
        column.geoX -> geoX,
        column.geoY -> geoY,
        column.geoScale -> geoScale,
        column.inmenu -> inmenu,
        column.newId -> newId
      )
    }.updateAndReturnGeneratedKey.apply()

    PblAdrCity(
      code = generatedKey.toInt,
      nazv = nazv,
      provincecode = provincecode,
      districtcode = districtcode,
      mapcode = mapcode,
      geoX = geoX,
      geoY = geoY,
      geoScale = geoScale,
      inmenu = inmenu,
      newId = newId)
  }

  def batchInsert(entities: collection.Seq[PblAdrCity])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'nazv -> entity.nazv,
        'provincecode -> entity.provincecode,
        'districtcode -> entity.districtcode,
        'mapcode -> entity.mapcode,
        'geoX -> entity.geoX,
        'geoY -> entity.geoY,
        'geoScale -> entity.geoScale,
        'inmenu -> entity.inmenu,
        'newId -> entity.newId))
    SQL("""insert into pbl_adr_city(
      nazv,
      provincecode,
      districtcode,
      mapcode,
      geo_x,
      geo_y,
      geo_scale,
      inmenu,
      new_id
    ) values (
      {nazv},
      {provincecode},
      {districtcode},
      {mapcode},
      {geoX},
      {geoY},
      {geoScale},
      {inmenu},
      {newId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblAdrCity)(implicit session: DBSession = autoSession): PblAdrCity = {
    withSQL {
      update(PblAdrCity).set(
        column.code -> entity.code,
        column.nazv -> entity.nazv,
        column.provincecode -> entity.provincecode,
        column.districtcode -> entity.districtcode,
        column.mapcode -> entity.mapcode,
        column.geoX -> entity.geoX,
        column.geoY -> entity.geoY,
        column.geoScale -> entity.geoScale,
        column.inmenu -> entity.inmenu,
        column.newId -> entity.newId
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblAdrCity)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblAdrCity).where.eq(column.code, entity.code) }.update.apply()
  }

}
