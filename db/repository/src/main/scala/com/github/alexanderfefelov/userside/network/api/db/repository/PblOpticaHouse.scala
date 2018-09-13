package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblOpticaHouse(
  code: Int,
  volscode: Option[Int] = None,
  position: Option[Int] = None,
  x1: Option[BigDecimal] = None,
  y1: Option[BigDecimal] = None,
  mapcode: Option[Int] = None) {

  def save()(implicit session: DBSession = PblOpticaHouse.autoSession): PblOpticaHouse = PblOpticaHouse.save(this)(session)

  def destroy()(implicit session: DBSession = PblOpticaHouse.autoSession): Int = PblOpticaHouse.destroy(this)(session)

}


object PblOpticaHouse extends SQLSyntaxSupport[PblOpticaHouse] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_optica_house"

  override val columns = Seq("code", "volscode", "position", "x1", "y1", "mapcode")

  def apply(poh: SyntaxProvider[PblOpticaHouse])(rs: WrappedResultSet): PblOpticaHouse = autoConstruct(rs, poh)
  def apply(poh: ResultName[PblOpticaHouse])(rs: WrappedResultSet): PblOpticaHouse = autoConstruct(rs, poh)

  val poh = PblOpticaHouse.syntax("poh")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblOpticaHouse] = {
    withSQL {
      select.from(PblOpticaHouse as poh).where.eq(poh.code, code)
    }.map(PblOpticaHouse(poh.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblOpticaHouse] = {
    withSQL(select.from(PblOpticaHouse as poh)).map(PblOpticaHouse(poh.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblOpticaHouse as poh)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblOpticaHouse] = {
    withSQL {
      select.from(PblOpticaHouse as poh).where.append(where)
    }.map(PblOpticaHouse(poh.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblOpticaHouse] = {
    withSQL {
      select.from(PblOpticaHouse as poh).where.append(where)
    }.map(PblOpticaHouse(poh.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblOpticaHouse as poh).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    volscode: Option[Int] = None,
    position: Option[Int] = None,
    x1: Option[BigDecimal] = None,
    y1: Option[BigDecimal] = None,
    mapcode: Option[Int] = None)(implicit session: DBSession = autoSession): PblOpticaHouse = {
    val generatedKey = withSQL {
      insert.into(PblOpticaHouse).namedValues(
        column.volscode -> volscode,
        column.position -> position,
        column.x1 -> x1,
        column.y1 -> y1,
        column.mapcode -> mapcode
      )
    }.updateAndReturnGeneratedKey.apply()

    PblOpticaHouse(
      code = generatedKey.toInt,
      volscode = volscode,
      position = position,
      x1 = x1,
      y1 = y1,
      mapcode = mapcode)
  }

  def batchInsert(entities: collection.Seq[PblOpticaHouse])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'volscode -> entity.volscode,
        'position -> entity.position,
        'x1 -> entity.x1,
        'y1 -> entity.y1,
        'mapcode -> entity.mapcode))
    SQL("""insert into pbl_optica_house(
      volscode,
      position,
      x1,
      y1,
      mapcode
    ) values (
      {volscode},
      {position},
      {x1},
      {y1},
      {mapcode}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblOpticaHouse)(implicit session: DBSession = autoSession): PblOpticaHouse = {
    withSQL {
      update(PblOpticaHouse).set(
        column.code -> entity.code,
        column.volscode -> entity.volscode,
        column.position -> entity.position,
        column.x1 -> entity.x1,
        column.y1 -> entity.y1,
        column.mapcode -> entity.mapcode
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblOpticaHouse)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblOpticaHouse).where.eq(column.code, entity.code) }.update.apply()
  }

}
