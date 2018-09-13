package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblCoord(
  code: Int,
  typer: Option[Short] = None,
  mapcode: Option[Int] = None,
  usercode: Option[Int] = None,
  coord: Option[String] = None,
  ishide: Option[Short] = None,
  geo: Option[String] = None,
  centerLat: Option[BigDecimal] = None,
  centerLon: Option[BigDecimal] = None) {

  def save()(implicit session: DBSession = PblCoord.autoSession): PblCoord = PblCoord.save(this)(session)

  def destroy()(implicit session: DBSession = PblCoord.autoSession): Int = PblCoord.destroy(this)(session)

}


object PblCoord extends SQLSyntaxSupport[PblCoord] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_coord"

  override val columns = Seq("code", "typer", "mapcode", "usercode", "coord", "ishide", "geo", "center_lat", "center_lon")

  def apply(pc: SyntaxProvider[PblCoord])(rs: WrappedResultSet): PblCoord = autoConstruct(rs, pc)
  def apply(pc: ResultName[PblCoord])(rs: WrappedResultSet): PblCoord = autoConstruct(rs, pc)

  val pc = PblCoord.syntax("pc")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblCoord] = {
    withSQL {
      select.from(PblCoord as pc).where.eq(pc.code, code)
    }.map(PblCoord(pc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblCoord] = {
    withSQL(select.from(PblCoord as pc)).map(PblCoord(pc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblCoord as pc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblCoord] = {
    withSQL {
      select.from(PblCoord as pc).where.append(where)
    }.map(PblCoord(pc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblCoord] = {
    withSQL {
      select.from(PblCoord as pc).where.append(where)
    }.map(PblCoord(pc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblCoord as pc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    typer: Option[Short] = None,
    mapcode: Option[Int] = None,
    usercode: Option[Int] = None,
    coord: Option[String] = None,
    ishide: Option[Short] = None,
    geo: Option[String] = None,
    centerLat: Option[BigDecimal] = None,
    centerLon: Option[BigDecimal] = None)(implicit session: DBSession = autoSession): PblCoord = {
    val generatedKey = withSQL {
      insert.into(PblCoord).namedValues(
        column.typer -> typer,
        column.mapcode -> mapcode,
        column.usercode -> usercode,
        column.coord -> coord,
        column.ishide -> ishide,
        column.geo -> geo,
        column.centerLat -> centerLat,
        column.centerLon -> centerLon
      )
    }.updateAndReturnGeneratedKey.apply()

    PblCoord(
      code = generatedKey.toInt,
      typer = typer,
      mapcode = mapcode,
      usercode = usercode,
      coord = coord,
      ishide = ishide,
      geo = geo,
      centerLat = centerLat,
      centerLon = centerLon)
  }

  def batchInsert(entities: collection.Seq[PblCoord])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'typer -> entity.typer,
        'mapcode -> entity.mapcode,
        'usercode -> entity.usercode,
        'coord -> entity.coord,
        'ishide -> entity.ishide,
        'geo -> entity.geo,
        'centerLat -> entity.centerLat,
        'centerLon -> entity.centerLon))
    SQL("""insert into pbl_coord(
      typer,
      mapcode,
      usercode,
      coord,
      ishide,
      geo,
      center_lat,
      center_lon
    ) values (
      {typer},
      {mapcode},
      {usercode},
      {coord},
      {ishide},
      {geo},
      {centerLat},
      {centerLon}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblCoord)(implicit session: DBSession = autoSession): PblCoord = {
    withSQL {
      update(PblCoord).set(
        column.code -> entity.code,
        column.typer -> entity.typer,
        column.mapcode -> entity.mapcode,
        column.usercode -> entity.usercode,
        column.coord -> entity.coord,
        column.ishide -> entity.ishide,
        column.geo -> entity.geo,
        column.centerLat -> entity.centerLat,
        column.centerLon -> entity.centerLon
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblCoord)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblCoord).where.eq(column.code, entity.code) }.update.apply()
  }

}
