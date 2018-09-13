package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblOpticaBon(
  code: Int,
  uzelcode: Option[Int] = None,
  port: Option[Short] = None,
  rotation: Option[Short] = None,
  x1: Option[Int] = None,
  y1: Option[Int] = None,
  pg: Option[String] = None,
  portsize: Option[String] = None,
  layers: Option[String] = None,
  opis: Option[String] = None,
  pgCaption: Option[String] = None,
  isplan: Option[Short] = None,
  number: Option[String] = None) {

  def save()(implicit session: DBSession = PblOpticaBon.autoSession): PblOpticaBon = PblOpticaBon.save(this)(session)

  def destroy()(implicit session: DBSession = PblOpticaBon.autoSession): Int = PblOpticaBon.destroy(this)(session)

}


object PblOpticaBon extends SQLSyntaxSupport[PblOpticaBon] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_optica_bon"

  override val columns = Seq("code", "uzelcode", "port", "rotation", "x1", "y1", "pg", "portsize", "layers", "opis", "pg_caption", "isplan", "number")

  def apply(pob: SyntaxProvider[PblOpticaBon])(rs: WrappedResultSet): PblOpticaBon = autoConstruct(rs, pob)
  def apply(pob: ResultName[PblOpticaBon])(rs: WrappedResultSet): PblOpticaBon = autoConstruct(rs, pob)

  val pob = PblOpticaBon.syntax("pob")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblOpticaBon] = {
    withSQL {
      select.from(PblOpticaBon as pob).where.eq(pob.code, code)
    }.map(PblOpticaBon(pob.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblOpticaBon] = {
    withSQL(select.from(PblOpticaBon as pob)).map(PblOpticaBon(pob.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblOpticaBon as pob)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblOpticaBon] = {
    withSQL {
      select.from(PblOpticaBon as pob).where.append(where)
    }.map(PblOpticaBon(pob.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblOpticaBon] = {
    withSQL {
      select.from(PblOpticaBon as pob).where.append(where)
    }.map(PblOpticaBon(pob.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblOpticaBon as pob).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    uzelcode: Option[Int] = None,
    port: Option[Short] = None,
    rotation: Option[Short] = None,
    x1: Option[Int] = None,
    y1: Option[Int] = None,
    pg: Option[String] = None,
    portsize: Option[String] = None,
    layers: Option[String] = None,
    opis: Option[String] = None,
    pgCaption: Option[String] = None,
    isplan: Option[Short] = None,
    number: Option[String] = None)(implicit session: DBSession = autoSession): PblOpticaBon = {
    val generatedKey = withSQL {
      insert.into(PblOpticaBon).namedValues(
        column.uzelcode -> uzelcode,
        column.port -> port,
        column.rotation -> rotation,
        column.x1 -> x1,
        column.y1 -> y1,
        column.pg -> pg,
        column.portsize -> portsize,
        column.layers -> layers,
        column.opis -> opis,
        column.pgCaption -> pgCaption,
        column.isplan -> isplan,
        column.number -> number
      )
    }.updateAndReturnGeneratedKey.apply()

    PblOpticaBon(
      code = generatedKey.toInt,
      uzelcode = uzelcode,
      port = port,
      rotation = rotation,
      x1 = x1,
      y1 = y1,
      pg = pg,
      portsize = portsize,
      layers = layers,
      opis = opis,
      pgCaption = pgCaption,
      isplan = isplan,
      number = number)
  }

  def batchInsert(entities: collection.Seq[PblOpticaBon])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'uzelcode -> entity.uzelcode,
        'port -> entity.port,
        'rotation -> entity.rotation,
        'x1 -> entity.x1,
        'y1 -> entity.y1,
        'pg -> entity.pg,
        'portsize -> entity.portsize,
        'layers -> entity.layers,
        'opis -> entity.opis,
        'pgCaption -> entity.pgCaption,
        'isplan -> entity.isplan,
        'number -> entity.number))
    SQL("""insert into pbl_optica_bon(
      uzelcode,
      port,
      rotation,
      x1,
      y1,
      pg,
      portsize,
      layers,
      opis,
      pg_caption,
      isplan,
      number
    ) values (
      {uzelcode},
      {port},
      {rotation},
      {x1},
      {y1},
      {pg},
      {portsize},
      {layers},
      {opis},
      {pgCaption},
      {isplan},
      {number}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblOpticaBon)(implicit session: DBSession = autoSession): PblOpticaBon = {
    withSQL {
      update(PblOpticaBon).set(
        column.code -> entity.code,
        column.uzelcode -> entity.uzelcode,
        column.port -> entity.port,
        column.rotation -> entity.rotation,
        column.x1 -> entity.x1,
        column.y1 -> entity.y1,
        column.pg -> entity.pg,
        column.portsize -> entity.portsize,
        column.layers -> entity.layers,
        column.opis -> entity.opis,
        column.pgCaption -> entity.pgCaption,
        column.isplan -> entity.isplan,
        column.number -> entity.number
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblOpticaBon)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblOpticaBon).where.eq(column.code, entity.code) }.update.apply()
  }

}
