package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblGps(
  code: Int,
  objtyper: Option[Short] = None,
  typer: Option[Int] = None,
  objcode: Option[Int] = None,
  datedo: Option[DateTime] = None,
  geoX: Option[BigDecimal] = None,
  geoY: Option[BigDecimal] = None,
  speed: Option[Int] = None,
  raw: Option[String] = None,
  positionText: Option[String] = None) {

  def save()(implicit session: DBSession = PblGps.autoSession): PblGps = PblGps.save(this)(session)

  def destroy()(implicit session: DBSession = PblGps.autoSession): Int = PblGps.destroy(this)(session)

}


object PblGps extends SQLSyntaxSupport[PblGps] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_gps"

  override val columns = Seq("code", "objtyper", "typer", "objcode", "datedo", "geo_x", "geo_y", "speed", "raw", "position_text")

  def apply(pg: SyntaxProvider[PblGps])(rs: WrappedResultSet): PblGps = autoConstruct(rs, pg)
  def apply(pg: ResultName[PblGps])(rs: WrappedResultSet): PblGps = autoConstruct(rs, pg)

  val pg = PblGps.syntax("pg")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblGps] = {
    withSQL {
      select.from(PblGps as pg).where.eq(pg.code, code)
    }.map(PblGps(pg.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblGps] = {
    withSQL(select.from(PblGps as pg)).map(PblGps(pg.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblGps as pg)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblGps] = {
    withSQL {
      select.from(PblGps as pg).where.append(where)
    }.map(PblGps(pg.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblGps] = {
    withSQL {
      select.from(PblGps as pg).where.append(where)
    }.map(PblGps(pg.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblGps as pg).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    objtyper: Option[Short] = None,
    typer: Option[Int] = None,
    objcode: Option[Int] = None,
    datedo: Option[DateTime] = None,
    geoX: Option[BigDecimal] = None,
    geoY: Option[BigDecimal] = None,
    speed: Option[Int] = None,
    raw: Option[String] = None,
    positionText: Option[String] = None)(implicit session: DBSession = autoSession): PblGps = {
    val generatedKey = withSQL {
      insert.into(PblGps).namedValues(
        column.objtyper -> objtyper,
        column.typer -> typer,
        column.objcode -> objcode,
        column.datedo -> datedo,
        column.geoX -> geoX,
        column.geoY -> geoY,
        column.speed -> speed,
        column.raw -> raw,
        column.positionText -> positionText
      )
    }.updateAndReturnGeneratedKey.apply()

    PblGps(
      code = generatedKey.toInt,
      objtyper = objtyper,
      typer = typer,
      objcode = objcode,
      datedo = datedo,
      geoX = geoX,
      geoY = geoY,
      speed = speed,
      raw = raw,
      positionText = positionText)
  }

  def batchInsert(entities: collection.Seq[PblGps])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'objtyper -> entity.objtyper,
        'typer -> entity.typer,
        'objcode -> entity.objcode,
        'datedo -> entity.datedo,
        'geoX -> entity.geoX,
        'geoY -> entity.geoY,
        'speed -> entity.speed,
        'raw -> entity.raw,
        'positionText -> entity.positionText))
    SQL("""insert into pbl_gps(
      objtyper,
      typer,
      objcode,
      datedo,
      geo_x,
      geo_y,
      speed,
      raw,
      position_text
    ) values (
      {objtyper},
      {typer},
      {objcode},
      {datedo},
      {geoX},
      {geoY},
      {speed},
      {raw},
      {positionText}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblGps)(implicit session: DBSession = autoSession): PblGps = {
    withSQL {
      update(PblGps).set(
        column.code -> entity.code,
        column.objtyper -> entity.objtyper,
        column.typer -> entity.typer,
        column.objcode -> entity.objcode,
        column.datedo -> entity.datedo,
        column.geoX -> entity.geoX,
        column.geoY -> entity.geoY,
        column.speed -> entity.speed,
        column.raw -> entity.raw,
        column.positionText -> entity.positionText
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblGps)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblGps).where.eq(column.code, entity.code) }.update.apply()
  }

}
