package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblGlobalPort(
  code: Int,
  typer: Option[Int] = None,
  equipcode: Option[Int] = None,
  storona: Option[Short] = None,
  port: Option[Long] = None,
  typer2: Option[Int] = None,
  equipcode2: Option[Int] = None,
  storona2: Option[Short] = None,
  port2: Option[Long] = None,
  dopdata: Option[String] = None,
  x1: Option[Int] = None,
  y1: Option[Int] = None,
  coord: Option[String] = None,
  color: Option[String] = None,
  dateadd: Option[DateTime] = None) {

  def save()(implicit session: DBSession = PblGlobalPort.autoSession): PblGlobalPort = PblGlobalPort.save(this)(session)

  def destroy()(implicit session: DBSession = PblGlobalPort.autoSession): Int = PblGlobalPort.destroy(this)(session)

}


object PblGlobalPort extends SQLSyntaxSupport[PblGlobalPort] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_global_port"

  override val columns = Seq("code", "typer", "equipcode", "storona", "port", "typer2", "equipcode2", "storona2", "port2", "dopdata", "x1", "y1", "coord", "color", "dateadd")

  def apply(pgp: SyntaxProvider[PblGlobalPort])(rs: WrappedResultSet): PblGlobalPort = autoConstruct(rs, pgp)
  def apply(pgp: ResultName[PblGlobalPort])(rs: WrappedResultSet): PblGlobalPort = autoConstruct(rs, pgp)

  val pgp = PblGlobalPort.syntax("pgp")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblGlobalPort] = {
    withSQL {
      select.from(PblGlobalPort as pgp).where.eq(pgp.code, code)
    }.map(PblGlobalPort(pgp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblGlobalPort] = {
    withSQL(select.from(PblGlobalPort as pgp)).map(PblGlobalPort(pgp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblGlobalPort as pgp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblGlobalPort] = {
    withSQL {
      select.from(PblGlobalPort as pgp).where.append(where)
    }.map(PblGlobalPort(pgp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblGlobalPort] = {
    withSQL {
      select.from(PblGlobalPort as pgp).where.append(where)
    }.map(PblGlobalPort(pgp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblGlobalPort as pgp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    typer: Option[Int] = None,
    equipcode: Option[Int] = None,
    storona: Option[Short] = None,
    port: Option[Long] = None,
    typer2: Option[Int] = None,
    equipcode2: Option[Int] = None,
    storona2: Option[Short] = None,
    port2: Option[Long] = None,
    dopdata: Option[String] = None,
    x1: Option[Int] = None,
    y1: Option[Int] = None,
    coord: Option[String] = None,
    color: Option[String] = None,
    dateadd: Option[DateTime] = None)(implicit session: DBSession = autoSession): PblGlobalPort = {
    val generatedKey = withSQL {
      insert.into(PblGlobalPort).namedValues(
        column.typer -> typer,
        column.equipcode -> equipcode,
        column.storona -> storona,
        column.port -> port,
        column.typer2 -> typer2,
        column.equipcode2 -> equipcode2,
        column.storona2 -> storona2,
        column.port2 -> port2,
        column.dopdata -> dopdata,
        column.x1 -> x1,
        column.y1 -> y1,
        column.coord -> coord,
        column.color -> color,
        column.dateadd -> dateadd
      )
    }.updateAndReturnGeneratedKey.apply()

    PblGlobalPort(
      code = generatedKey.toInt,
      typer = typer,
      equipcode = equipcode,
      storona = storona,
      port = port,
      typer2 = typer2,
      equipcode2 = equipcode2,
      storona2 = storona2,
      port2 = port2,
      dopdata = dopdata,
      x1 = x1,
      y1 = y1,
      coord = coord,
      color = color,
      dateadd = dateadd)
  }

  def batchInsert(entities: collection.Seq[PblGlobalPort])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'typer -> entity.typer,
        'equipcode -> entity.equipcode,
        'storona -> entity.storona,
        'port -> entity.port,
        'typer2 -> entity.typer2,
        'equipcode2 -> entity.equipcode2,
        'storona2 -> entity.storona2,
        'port2 -> entity.port2,
        'dopdata -> entity.dopdata,
        'x1 -> entity.x1,
        'y1 -> entity.y1,
        'coord -> entity.coord,
        'color -> entity.color,
        'dateadd -> entity.dateadd))
    SQL("""insert into pbl_global_port(
      typer,
      equipcode,
      storona,
      port,
      typer2,
      equipcode2,
      storona2,
      port2,
      dopdata,
      x1,
      y1,
      coord,
      color,
      dateadd
    ) values (
      {typer},
      {equipcode},
      {storona},
      {port},
      {typer2},
      {equipcode2},
      {storona2},
      {port2},
      {dopdata},
      {x1},
      {y1},
      {coord},
      {color},
      {dateadd}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblGlobalPort)(implicit session: DBSession = autoSession): PblGlobalPort = {
    withSQL {
      update(PblGlobalPort).set(
        column.code -> entity.code,
        column.typer -> entity.typer,
        column.equipcode -> entity.equipcode,
        column.storona -> entity.storona,
        column.port -> entity.port,
        column.typer2 -> entity.typer2,
        column.equipcode2 -> entity.equipcode2,
        column.storona2 -> entity.storona2,
        column.port2 -> entity.port2,
        column.dopdata -> entity.dopdata,
        column.x1 -> entity.x1,
        column.y1 -> entity.y1,
        column.coord -> entity.coord,
        column.color -> entity.color,
        column.dateadd -> entity.dateadd
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblGlobalPort)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblGlobalPort).where.eq(column.code, entity.code) }.update.apply()
  }

}
