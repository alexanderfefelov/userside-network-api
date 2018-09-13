package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblOptica(
  code: Int,
  opis: Option[String] = None,
  port: Option[Short] = None,
  dateadd: Option[DateTime] = None,
  dop: Option[String] = None,
  uzelcode1: Option[Int] = None,
  obj2Typer: Option[Short] = None,
  uzelcode2: Option[Int] = None,
  opticalen: Option[Int] = None,
  uzel1rotat: Option[Short] = None,
  uzel1pos: Option[Int] = None,
  uzel2rotat: Option[Short] = None,
  uzel2pos: Option[Int] = None,
  opticalen2: Option[Int] = None,
  pg: Option[String] = None,
  cabletype: Option[String] = None,
  ishide: Option[Short] = None,
  cablecode: Option[Int] = None,
  layers: Option[String] = None,
  coord: Option[String] = None,
  obj1Typer: Option[Short] = None,
  colcol: Option[String] = None,
  isplan: Option[Short] = None,
  marking1: Option[String] = None,
  marking2: Option[String] = None,
  cableType: Option[Short] = None) {

  def save()(implicit session: DBSession = PblOptica.autoSession): PblOptica = PblOptica.save(this)(session)

  def destroy()(implicit session: DBSession = PblOptica.autoSession): Int = PblOptica.destroy(this)(session)

}


object PblOptica extends SQLSyntaxSupport[PblOptica] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_optica"

  override val columns = Seq("code", "opis", "port", "dateadd", "dop", "uzelcode1", "obj2_typer", "uzelcode2", "opticalen", "uzel1rotat", "uzel1pos", "uzel2rotat", "uzel2pos", "opticalen2", "pg", "cabletype", "ishide", "cablecode", "layers", "coord", "obj1_typer", "colcol", "isplan", "marking1", "marking2", "cable_type")

  def apply(po: SyntaxProvider[PblOptica])(rs: WrappedResultSet): PblOptica = autoConstruct(rs, po)
  def apply(po: ResultName[PblOptica])(rs: WrappedResultSet): PblOptica = autoConstruct(rs, po)

  val po = PblOptica.syntax("po")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblOptica] = {
    withSQL {
      select.from(PblOptica as po).where.eq(po.code, code)
    }.map(PblOptica(po.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblOptica] = {
    withSQL(select.from(PblOptica as po)).map(PblOptica(po.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblOptica as po)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblOptica] = {
    withSQL {
      select.from(PblOptica as po).where.append(where)
    }.map(PblOptica(po.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblOptica] = {
    withSQL {
      select.from(PblOptica as po).where.append(where)
    }.map(PblOptica(po.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblOptica as po).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    opis: Option[String] = None,
    port: Option[Short] = None,
    dateadd: Option[DateTime] = None,
    dop: Option[String] = None,
    uzelcode1: Option[Int] = None,
    obj2Typer: Option[Short] = None,
    uzelcode2: Option[Int] = None,
    opticalen: Option[Int] = None,
    uzel1rotat: Option[Short] = None,
    uzel1pos: Option[Int] = None,
    uzel2rotat: Option[Short] = None,
    uzel2pos: Option[Int] = None,
    opticalen2: Option[Int] = None,
    pg: Option[String] = None,
    cabletype: Option[String] = None,
    ishide: Option[Short] = None,
    cablecode: Option[Int] = None,
    layers: Option[String] = None,
    coord: Option[String] = None,
    obj1Typer: Option[Short] = None,
    colcol: Option[String] = None,
    isplan: Option[Short] = None,
    marking1: Option[String] = None,
    marking2: Option[String] = None,
    cableType: Option[Short] = None)(implicit session: DBSession = autoSession): PblOptica = {
    val generatedKey = withSQL {
      insert.into(PblOptica).namedValues(
        column.opis -> opis,
        column.port -> port,
        column.dateadd -> dateadd,
        column.dop -> dop,
        column.uzelcode1 -> uzelcode1,
        column.obj2Typer -> obj2Typer,
        column.uzelcode2 -> uzelcode2,
        column.opticalen -> opticalen,
        column.uzel1rotat -> uzel1rotat,
        column.uzel1pos -> uzel1pos,
        column.uzel2rotat -> uzel2rotat,
        column.uzel2pos -> uzel2pos,
        column.opticalen2 -> opticalen2,
        column.pg -> pg,
        column.cabletype -> cabletype,
        column.ishide -> ishide,
        column.cablecode -> cablecode,
        column.layers -> layers,
        column.coord -> coord,
        column.obj1Typer -> obj1Typer,
        column.colcol -> colcol,
        column.isplan -> isplan,
        column.marking1 -> marking1,
        column.marking2 -> marking2,
        column.cableType -> cableType
      )
    }.updateAndReturnGeneratedKey.apply()

    PblOptica(
      code = generatedKey.toInt,
      opis = opis,
      port = port,
      dateadd = dateadd,
      dop = dop,
      uzelcode1 = uzelcode1,
      obj2Typer = obj2Typer,
      uzelcode2 = uzelcode2,
      opticalen = opticalen,
      uzel1rotat = uzel1rotat,
      uzel1pos = uzel1pos,
      uzel2rotat = uzel2rotat,
      uzel2pos = uzel2pos,
      opticalen2 = opticalen2,
      pg = pg,
      cabletype = cabletype,
      ishide = ishide,
      cablecode = cablecode,
      layers = layers,
      coord = coord,
      obj1Typer = obj1Typer,
      colcol = colcol,
      isplan = isplan,
      marking1 = marking1,
      marking2 = marking2,
      cableType = cableType)
  }

  def batchInsert(entities: collection.Seq[PblOptica])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'opis -> entity.opis,
        'port -> entity.port,
        'dateadd -> entity.dateadd,
        'dop -> entity.dop,
        'uzelcode1 -> entity.uzelcode1,
        'obj2Typer -> entity.obj2Typer,
        'uzelcode2 -> entity.uzelcode2,
        'opticalen -> entity.opticalen,
        'uzel1rotat -> entity.uzel1rotat,
        'uzel1pos -> entity.uzel1pos,
        'uzel2rotat -> entity.uzel2rotat,
        'uzel2pos -> entity.uzel2pos,
        'opticalen2 -> entity.opticalen2,
        'pg -> entity.pg,
        'cabletype -> entity.cabletype,
        'ishide -> entity.ishide,
        'cablecode -> entity.cablecode,
        'layers -> entity.layers,
        'coord -> entity.coord,
        'obj1Typer -> entity.obj1Typer,
        'colcol -> entity.colcol,
        'isplan -> entity.isplan,
        'marking1 -> entity.marking1,
        'marking2 -> entity.marking2,
        'cableType -> entity.cableType))
    SQL("""insert into pbl_optica(
      opis,
      port,
      dateadd,
      dop,
      uzelcode1,
      obj2_typer,
      uzelcode2,
      opticalen,
      uzel1rotat,
      uzel1pos,
      uzel2rotat,
      uzel2pos,
      opticalen2,
      pg,
      cabletype,
      ishide,
      cablecode,
      layers,
      coord,
      obj1_typer,
      colcol,
      isplan,
      marking1,
      marking2,
      cable_type
    ) values (
      {opis},
      {port},
      {dateadd},
      {dop},
      {uzelcode1},
      {obj2Typer},
      {uzelcode2},
      {opticalen},
      {uzel1rotat},
      {uzel1pos},
      {uzel2rotat},
      {uzel2pos},
      {opticalen2},
      {pg},
      {cabletype},
      {ishide},
      {cablecode},
      {layers},
      {coord},
      {obj1Typer},
      {colcol},
      {isplan},
      {marking1},
      {marking2},
      {cableType}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblOptica)(implicit session: DBSession = autoSession): PblOptica = {
    withSQL {
      update(PblOptica).set(
        column.code -> entity.code,
        column.opis -> entity.opis,
        column.port -> entity.port,
        column.dateadd -> entity.dateadd,
        column.dop -> entity.dop,
        column.uzelcode1 -> entity.uzelcode1,
        column.obj2Typer -> entity.obj2Typer,
        column.uzelcode2 -> entity.uzelcode2,
        column.opticalen -> entity.opticalen,
        column.uzel1rotat -> entity.uzel1rotat,
        column.uzel1pos -> entity.uzel1pos,
        column.uzel2rotat -> entity.uzel2rotat,
        column.uzel2pos -> entity.uzel2pos,
        column.opticalen2 -> entity.opticalen2,
        column.pg -> entity.pg,
        column.cabletype -> entity.cabletype,
        column.ishide -> entity.ishide,
        column.cablecode -> entity.cablecode,
        column.layers -> entity.layers,
        column.coord -> entity.coord,
        column.obj1Typer -> entity.obj1Typer,
        column.colcol -> entity.colcol,
        column.isplan -> entity.isplan,
        column.marking1 -> entity.marking1,
        column.marking2 -> entity.marking2,
        column.cableType -> entity.cableType
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblOptica)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblOptica).where.eq(column.code, entity.code) }.update.apply()
  }

}
