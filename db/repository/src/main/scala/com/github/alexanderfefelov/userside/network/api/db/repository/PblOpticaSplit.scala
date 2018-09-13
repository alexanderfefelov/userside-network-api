package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblOpticaSplit(
  code: Int,
  uzelcode: Option[Int] = None,
  port1: Option[Short] = None,
  port2: Option[Short] = None,
  rotation: Option[Short] = None,
  x1: Option[Int] = None,
  y1: Option[Int] = None,
  pg: Option[String] = None,
  pgCaption: Option[String] = None,
  layers: Option[String] = None,
  opis: Option[String] = None,
  skladcode: Option[Int] = None,
  dateadd: Option[DateTime] = None,
  isplan: Option[Short] = None) {

  def save()(implicit session: DBSession = PblOpticaSplit.autoSession): PblOpticaSplit = PblOpticaSplit.save(this)(session)

  def destroy()(implicit session: DBSession = PblOpticaSplit.autoSession): Int = PblOpticaSplit.destroy(this)(session)

}


object PblOpticaSplit extends SQLSyntaxSupport[PblOpticaSplit] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_optica_split"

  override val columns = Seq("code", "uzelcode", "port1", "port2", "rotation", "x1", "y1", "pg", "pg_caption", "layers", "opis", "skladcode", "dateadd", "isplan")

  def apply(pos: SyntaxProvider[PblOpticaSplit])(rs: WrappedResultSet): PblOpticaSplit = autoConstruct(rs, pos)
  def apply(pos: ResultName[PblOpticaSplit])(rs: WrappedResultSet): PblOpticaSplit = autoConstruct(rs, pos)

  val pos = PblOpticaSplit.syntax("pos")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblOpticaSplit] = {
    withSQL {
      select.from(PblOpticaSplit as pos).where.eq(pos.code, code)
    }.map(PblOpticaSplit(pos.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblOpticaSplit] = {
    withSQL(select.from(PblOpticaSplit as pos)).map(PblOpticaSplit(pos.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblOpticaSplit as pos)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblOpticaSplit] = {
    withSQL {
      select.from(PblOpticaSplit as pos).where.append(where)
    }.map(PblOpticaSplit(pos.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblOpticaSplit] = {
    withSQL {
      select.from(PblOpticaSplit as pos).where.append(where)
    }.map(PblOpticaSplit(pos.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblOpticaSplit as pos).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    uzelcode: Option[Int] = None,
    port1: Option[Short] = None,
    port2: Option[Short] = None,
    rotation: Option[Short] = None,
    x1: Option[Int] = None,
    y1: Option[Int] = None,
    pg: Option[String] = None,
    pgCaption: Option[String] = None,
    layers: Option[String] = None,
    opis: Option[String] = None,
    skladcode: Option[Int] = None,
    dateadd: Option[DateTime] = None,
    isplan: Option[Short] = None)(implicit session: DBSession = autoSession): PblOpticaSplit = {
    val generatedKey = withSQL {
      insert.into(PblOpticaSplit).namedValues(
        column.uzelcode -> uzelcode,
        column.port1 -> port1,
        column.port2 -> port2,
        column.rotation -> rotation,
        column.x1 -> x1,
        column.y1 -> y1,
        column.pg -> pg,
        column.pgCaption -> pgCaption,
        column.layers -> layers,
        column.opis -> opis,
        column.skladcode -> skladcode,
        column.dateadd -> dateadd,
        column.isplan -> isplan
      )
    }.updateAndReturnGeneratedKey.apply()

    PblOpticaSplit(
      code = generatedKey.toInt,
      uzelcode = uzelcode,
      port1 = port1,
      port2 = port2,
      rotation = rotation,
      x1 = x1,
      y1 = y1,
      pg = pg,
      pgCaption = pgCaption,
      layers = layers,
      opis = opis,
      skladcode = skladcode,
      dateadd = dateadd,
      isplan = isplan)
  }

  def batchInsert(entities: collection.Seq[PblOpticaSplit])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'uzelcode -> entity.uzelcode,
        'port1 -> entity.port1,
        'port2 -> entity.port2,
        'rotation -> entity.rotation,
        'x1 -> entity.x1,
        'y1 -> entity.y1,
        'pg -> entity.pg,
        'pgCaption -> entity.pgCaption,
        'layers -> entity.layers,
        'opis -> entity.opis,
        'skladcode -> entity.skladcode,
        'dateadd -> entity.dateadd,
        'isplan -> entity.isplan))
    SQL("""insert into pbl_optica_split(
      uzelcode,
      port1,
      port2,
      rotation,
      x1,
      y1,
      pg,
      pg_caption,
      layers,
      opis,
      skladcode,
      dateadd,
      isplan
    ) values (
      {uzelcode},
      {port1},
      {port2},
      {rotation},
      {x1},
      {y1},
      {pg},
      {pgCaption},
      {layers},
      {opis},
      {skladcode},
      {dateadd},
      {isplan}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblOpticaSplit)(implicit session: DBSession = autoSession): PblOpticaSplit = {
    withSQL {
      update(PblOpticaSplit).set(
        column.code -> entity.code,
        column.uzelcode -> entity.uzelcode,
        column.port1 -> entity.port1,
        column.port2 -> entity.port2,
        column.rotation -> entity.rotation,
        column.x1 -> entity.x1,
        column.y1 -> entity.y1,
        column.pg -> entity.pg,
        column.pgCaption -> entity.pgCaption,
        column.layers -> entity.layers,
        column.opis -> entity.opis,
        column.skladcode -> entity.skladcode,
        column.dateadd -> entity.dateadd,
        column.isplan -> entity.isplan
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblOpticaSplit)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblOpticaSplit).where.eq(column.code, entity.code) }.update.apply()
  }

}
