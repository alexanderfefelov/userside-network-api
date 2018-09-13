package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblMedHouse(
  code: Int,
  linecode: Option[Int] = None,
  position: Option[Int] = None,
  x1: Option[BigDecimal] = None,
  y1: Option[BigDecimal] = None,
  mapcode: Option[Int] = None) {

  def save()(implicit session: DBSession = PblMedHouse.autoSession): PblMedHouse = PblMedHouse.save(this)(session)

  def destroy()(implicit session: DBSession = PblMedHouse.autoSession): Int = PblMedHouse.destroy(this)(session)

}


object PblMedHouse extends SQLSyntaxSupport[PblMedHouse] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_med_house"

  override val columns = Seq("code", "linecode", "position", "x1", "y1", "mapcode")

  def apply(pmh: SyntaxProvider[PblMedHouse])(rs: WrappedResultSet): PblMedHouse = autoConstruct(rs, pmh)
  def apply(pmh: ResultName[PblMedHouse])(rs: WrappedResultSet): PblMedHouse = autoConstruct(rs, pmh)

  val pmh = PblMedHouse.syntax("pmh")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblMedHouse] = {
    withSQL {
      select.from(PblMedHouse as pmh).where.eq(pmh.code, code)
    }.map(PblMedHouse(pmh.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblMedHouse] = {
    withSQL(select.from(PblMedHouse as pmh)).map(PblMedHouse(pmh.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblMedHouse as pmh)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblMedHouse] = {
    withSQL {
      select.from(PblMedHouse as pmh).where.append(where)
    }.map(PblMedHouse(pmh.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblMedHouse] = {
    withSQL {
      select.from(PblMedHouse as pmh).where.append(where)
    }.map(PblMedHouse(pmh.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblMedHouse as pmh).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    linecode: Option[Int] = None,
    position: Option[Int] = None,
    x1: Option[BigDecimal] = None,
    y1: Option[BigDecimal] = None,
    mapcode: Option[Int] = None)(implicit session: DBSession = autoSession): PblMedHouse = {
    val generatedKey = withSQL {
      insert.into(PblMedHouse).namedValues(
        column.linecode -> linecode,
        column.position -> position,
        column.x1 -> x1,
        column.y1 -> y1,
        column.mapcode -> mapcode
      )
    }.updateAndReturnGeneratedKey.apply()

    PblMedHouse(
      code = generatedKey.toInt,
      linecode = linecode,
      position = position,
      x1 = x1,
      y1 = y1,
      mapcode = mapcode)
  }

  def batchInsert(entities: collection.Seq[PblMedHouse])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'linecode -> entity.linecode,
        'position -> entity.position,
        'x1 -> entity.x1,
        'y1 -> entity.y1,
        'mapcode -> entity.mapcode))
    SQL("""insert into pbl_med_house(
      linecode,
      position,
      x1,
      y1,
      mapcode
    ) values (
      {linecode},
      {position},
      {x1},
      {y1},
      {mapcode}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblMedHouse)(implicit session: DBSession = autoSession): PblMedHouse = {
    withSQL {
      update(PblMedHouse).set(
        column.code -> entity.code,
        column.linecode -> entity.linecode,
        column.position -> entity.position,
        column.x1 -> entity.x1,
        column.y1 -> entity.y1,
        column.mapcode -> entity.mapcode
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblMedHouse)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblMedHouse).where.eq(column.code, entity.code) }.update.apply()
  }

}
