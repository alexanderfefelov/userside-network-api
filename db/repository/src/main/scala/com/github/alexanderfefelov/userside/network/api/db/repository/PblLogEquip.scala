package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblLogEquip(
  code: Int,
  typer: Option[Short] = None,
  equipcode: Option[Int] = None,
  status: Option[Short] = None,
  datedo: Option[DateTime] = None) {

  def save()(implicit session: DBSession = PblLogEquip.autoSession): PblLogEquip = PblLogEquip.save(this)(session)

  def destroy()(implicit session: DBSession = PblLogEquip.autoSession): Int = PblLogEquip.destroy(this)(session)

}


object PblLogEquip extends SQLSyntaxSupport[PblLogEquip] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_log_equip"

  override val columns = Seq("code", "typer", "equipcode", "status", "datedo")

  def apply(ple: SyntaxProvider[PblLogEquip])(rs: WrappedResultSet): PblLogEquip = autoConstruct(rs, ple)
  def apply(ple: ResultName[PblLogEquip])(rs: WrappedResultSet): PblLogEquip = autoConstruct(rs, ple)

  val ple = PblLogEquip.syntax("ple")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblLogEquip] = {
    withSQL {
      select.from(PblLogEquip as ple).where.eq(ple.code, code)
    }.map(PblLogEquip(ple.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblLogEquip] = {
    withSQL(select.from(PblLogEquip as ple)).map(PblLogEquip(ple.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblLogEquip as ple)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblLogEquip] = {
    withSQL {
      select.from(PblLogEquip as ple).where.append(where)
    }.map(PblLogEquip(ple.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblLogEquip] = {
    withSQL {
      select.from(PblLogEquip as ple).where.append(where)
    }.map(PblLogEquip(ple.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblLogEquip as ple).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    typer: Option[Short] = None,
    equipcode: Option[Int] = None,
    status: Option[Short] = None,
    datedo: Option[DateTime] = None)(implicit session: DBSession = autoSession): PblLogEquip = {
    val generatedKey = withSQL {
      insert.into(PblLogEquip).namedValues(
        column.typer -> typer,
        column.equipcode -> equipcode,
        column.status -> status,
        column.datedo -> datedo
      )
    }.updateAndReturnGeneratedKey.apply()

    PblLogEquip(
      code = generatedKey.toInt,
      typer = typer,
      equipcode = equipcode,
      status = status,
      datedo = datedo)
  }

  def batchInsert(entities: collection.Seq[PblLogEquip])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'typer -> entity.typer,
        'equipcode -> entity.equipcode,
        'status -> entity.status,
        'datedo -> entity.datedo))
    SQL("""insert into pbl_log_equip(
      typer,
      equipcode,
      status,
      datedo
    ) values (
      {typer},
      {equipcode},
      {status},
      {datedo}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblLogEquip)(implicit session: DBSession = autoSession): PblLogEquip = {
    withSQL {
      update(PblLogEquip).set(
        column.code -> entity.code,
        column.typer -> entity.typer,
        column.equipcode -> entity.equipcode,
        column.status -> entity.status,
        column.datedo -> entity.datedo
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblLogEquip)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblLogEquip).where.eq(column.code, entity.code) }.update.apply()
  }

}
