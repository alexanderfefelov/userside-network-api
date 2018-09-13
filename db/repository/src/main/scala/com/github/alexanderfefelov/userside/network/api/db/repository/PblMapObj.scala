package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblMapObj(
  code: Int,
  mapcode: Option[Int] = None,
  typer: Option[Short] = None,
  coord: Option[String] = None,
  nazv: Option[String] = None) {

  def save()(implicit session: DBSession = PblMapObj.autoSession): PblMapObj = PblMapObj.save(this)(session)

  def destroy()(implicit session: DBSession = PblMapObj.autoSession): Int = PblMapObj.destroy(this)(session)

}


object PblMapObj extends SQLSyntaxSupport[PblMapObj] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_map_obj"

  override val columns = Seq("code", "mapcode", "typer", "coord", "nazv")

  def apply(pmo: SyntaxProvider[PblMapObj])(rs: WrappedResultSet): PblMapObj = autoConstruct(rs, pmo)
  def apply(pmo: ResultName[PblMapObj])(rs: WrappedResultSet): PblMapObj = autoConstruct(rs, pmo)

  val pmo = PblMapObj.syntax("pmo")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblMapObj] = {
    withSQL {
      select.from(PblMapObj as pmo).where.eq(pmo.code, code)
    }.map(PblMapObj(pmo.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblMapObj] = {
    withSQL(select.from(PblMapObj as pmo)).map(PblMapObj(pmo.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblMapObj as pmo)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblMapObj] = {
    withSQL {
      select.from(PblMapObj as pmo).where.append(where)
    }.map(PblMapObj(pmo.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblMapObj] = {
    withSQL {
      select.from(PblMapObj as pmo).where.append(where)
    }.map(PblMapObj(pmo.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblMapObj as pmo).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    mapcode: Option[Int] = None,
    typer: Option[Short] = None,
    coord: Option[String] = None,
    nazv: Option[String] = None)(implicit session: DBSession = autoSession): PblMapObj = {
    val generatedKey = withSQL {
      insert.into(PblMapObj).namedValues(
        column.mapcode -> mapcode,
        column.typer -> typer,
        column.coord -> coord,
        column.nazv -> nazv
      )
    }.updateAndReturnGeneratedKey.apply()

    PblMapObj(
      code = generatedKey.toInt,
      mapcode = mapcode,
      typer = typer,
      coord = coord,
      nazv = nazv)
  }

  def batchInsert(entities: collection.Seq[PblMapObj])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'mapcode -> entity.mapcode,
        'typer -> entity.typer,
        'coord -> entity.coord,
        'nazv -> entity.nazv))
    SQL("""insert into pbl_map_obj(
      mapcode,
      typer,
      coord,
      nazv
    ) values (
      {mapcode},
      {typer},
      {coord},
      {nazv}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblMapObj)(implicit session: DBSession = autoSession): PblMapObj = {
    withSQL {
      update(PblMapObj).set(
        column.code -> entity.code,
        column.mapcode -> entity.mapcode,
        column.typer -> entity.typer,
        column.coord -> entity.coord,
        column.nazv -> entity.nazv
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblMapObj)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblMapObj).where.eq(column.code, entity.code) }.update.apply()
  }

}
