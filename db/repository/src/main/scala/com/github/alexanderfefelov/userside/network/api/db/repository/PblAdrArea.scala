package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblAdrArea(
  code: Int,
  nazv: Option[String] = None,
  citycode: Option[Int] = None,
  mapcode: Option[Int] = None,
  geoX: Option[BigDecimal] = None,
  geoY: Option[BigDecimal] = None,
  geoScale: Option[Short] = None,
  inmenu: Option[Short] = None,
  newId: Option[Int] = None) {

  def save()(implicit session: DBSession = PblAdrArea.autoSession): PblAdrArea = PblAdrArea.save(this)(session)

  def destroy()(implicit session: DBSession = PblAdrArea.autoSession): Int = PblAdrArea.destroy(this)(session)

}


object PblAdrArea extends SQLSyntaxSupport[PblAdrArea] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_adr_area"

  override val columns = Seq("code", "nazv", "citycode", "mapcode", "geo_x", "geo_y", "geo_scale", "inmenu", "new_id")

  def apply(paa: SyntaxProvider[PblAdrArea])(rs: WrappedResultSet): PblAdrArea = autoConstruct(rs, paa)
  def apply(paa: ResultName[PblAdrArea])(rs: WrappedResultSet): PblAdrArea = autoConstruct(rs, paa)

  val paa = PblAdrArea.syntax("paa")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblAdrArea] = {
    withSQL {
      select.from(PblAdrArea as paa).where.eq(paa.code, code)
    }.map(PblAdrArea(paa.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblAdrArea] = {
    withSQL(select.from(PblAdrArea as paa)).map(PblAdrArea(paa.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblAdrArea as paa)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblAdrArea] = {
    withSQL {
      select.from(PblAdrArea as paa).where.append(where)
    }.map(PblAdrArea(paa.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblAdrArea] = {
    withSQL {
      select.from(PblAdrArea as paa).where.append(where)
    }.map(PblAdrArea(paa.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblAdrArea as paa).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    nazv: Option[String] = None,
    citycode: Option[Int] = None,
    mapcode: Option[Int] = None,
    geoX: Option[BigDecimal] = None,
    geoY: Option[BigDecimal] = None,
    geoScale: Option[Short] = None,
    inmenu: Option[Short] = None,
    newId: Option[Int] = None)(implicit session: DBSession = autoSession): PblAdrArea = {
    val generatedKey = withSQL {
      insert.into(PblAdrArea).namedValues(
        column.nazv -> nazv,
        column.citycode -> citycode,
        column.mapcode -> mapcode,
        column.geoX -> geoX,
        column.geoY -> geoY,
        column.geoScale -> geoScale,
        column.inmenu -> inmenu,
        column.newId -> newId
      )
    }.updateAndReturnGeneratedKey.apply()

    PblAdrArea(
      code = generatedKey.toInt,
      nazv = nazv,
      citycode = citycode,
      mapcode = mapcode,
      geoX = geoX,
      geoY = geoY,
      geoScale = geoScale,
      inmenu = inmenu,
      newId = newId)
  }

  def batchInsert(entities: collection.Seq[PblAdrArea])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'nazv -> entity.nazv,
        'citycode -> entity.citycode,
        'mapcode -> entity.mapcode,
        'geoX -> entity.geoX,
        'geoY -> entity.geoY,
        'geoScale -> entity.geoScale,
        'inmenu -> entity.inmenu,
        'newId -> entity.newId))
    SQL("""insert into pbl_adr_area(
      nazv,
      citycode,
      mapcode,
      geo_x,
      geo_y,
      geo_scale,
      inmenu,
      new_id
    ) values (
      {nazv},
      {citycode},
      {mapcode},
      {geoX},
      {geoY},
      {geoScale},
      {inmenu},
      {newId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblAdrArea)(implicit session: DBSession = autoSession): PblAdrArea = {
    withSQL {
      update(PblAdrArea).set(
        column.code -> entity.code,
        column.nazv -> entity.nazv,
        column.citycode -> entity.citycode,
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

  def destroy(entity: PblAdrArea)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblAdrArea).where.eq(column.code, entity.code) }.update.apply()
  }

}
