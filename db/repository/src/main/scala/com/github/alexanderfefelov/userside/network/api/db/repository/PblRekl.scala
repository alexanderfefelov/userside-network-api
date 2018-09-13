package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblRekl(
  code: Int,
  typer: Option[Short] = None,
  nazv: Option[String] = None,
  opis: Option[String] = None,
  param: Option[String] = None,
  param2: Option[String] = None,
  datestart: Option[LocalDate] = None,
  datestop: Option[LocalDate] = None,
  result0: Option[Int] = None,
  result7: Option[Int] = None,
  result14: Option[Int] = None,
  result30: Option[Int] = None) {

  def save()(implicit session: DBSession = PblRekl.autoSession): PblRekl = PblRekl.save(this)(session)

  def destroy()(implicit session: DBSession = PblRekl.autoSession): Int = PblRekl.destroy(this)(session)

}


object PblRekl extends SQLSyntaxSupport[PblRekl] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_rekl"

  override val columns = Seq("code", "typer", "nazv", "opis", "param", "param2", "datestart", "datestop", "result_0", "result_7", "result_14", "result_30")

  def apply(pr: SyntaxProvider[PblRekl])(rs: WrappedResultSet): PblRekl = autoConstruct(rs, pr)
  def apply(pr: ResultName[PblRekl])(rs: WrappedResultSet): PblRekl = autoConstruct(rs, pr)

  val pr = PblRekl.syntax("pr")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblRekl] = {
    withSQL {
      select.from(PblRekl as pr).where.eq(pr.code, code)
    }.map(PblRekl(pr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblRekl] = {
    withSQL(select.from(PblRekl as pr)).map(PblRekl(pr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblRekl as pr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblRekl] = {
    withSQL {
      select.from(PblRekl as pr).where.append(where)
    }.map(PblRekl(pr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblRekl] = {
    withSQL {
      select.from(PblRekl as pr).where.append(where)
    }.map(PblRekl(pr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblRekl as pr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    typer: Option[Short] = None,
    nazv: Option[String] = None,
    opis: Option[String] = None,
    param: Option[String] = None,
    param2: Option[String] = None,
    datestart: Option[LocalDate] = None,
    datestop: Option[LocalDate] = None,
    result0: Option[Int] = None,
    result7: Option[Int] = None,
    result14: Option[Int] = None,
    result30: Option[Int] = None)(implicit session: DBSession = autoSession): PblRekl = {
    val generatedKey = withSQL {
      insert.into(PblRekl).namedValues(
        column.typer -> typer,
        column.nazv -> nazv,
        column.opis -> opis,
        column.param -> param,
        column.param2 -> param2,
        column.datestart -> datestart,
        column.datestop -> datestop,
        column.result0 -> result0,
        column.result7 -> result7,
        column.result14 -> result14,
        column.result30 -> result30
      )
    }.updateAndReturnGeneratedKey.apply()

    PblRekl(
      code = generatedKey.toInt,
      typer = typer,
      nazv = nazv,
      opis = opis,
      param = param,
      param2 = param2,
      datestart = datestart,
      datestop = datestop,
      result0 = result0,
      result7 = result7,
      result14 = result14,
      result30 = result30)
  }

  def batchInsert(entities: collection.Seq[PblRekl])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'typer -> entity.typer,
        'nazv -> entity.nazv,
        'opis -> entity.opis,
        'param -> entity.param,
        'param2 -> entity.param2,
        'datestart -> entity.datestart,
        'datestop -> entity.datestop,
        'result0 -> entity.result0,
        'result7 -> entity.result7,
        'result14 -> entity.result14,
        'result30 -> entity.result30))
    SQL("""insert into pbl_rekl(
      typer,
      nazv,
      opis,
      param,
      param2,
      datestart,
      datestop,
      result_0,
      result_7,
      result_14,
      result_30
    ) values (
      {typer},
      {nazv},
      {opis},
      {param},
      {param2},
      {datestart},
      {datestop},
      {result0},
      {result7},
      {result14},
      {result30}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblRekl)(implicit session: DBSession = autoSession): PblRekl = {
    withSQL {
      update(PblRekl).set(
        column.code -> entity.code,
        column.typer -> entity.typer,
        column.nazv -> entity.nazv,
        column.opis -> entity.opis,
        column.param -> entity.param,
        column.param2 -> entity.param2,
        column.datestart -> entity.datestart,
        column.datestop -> entity.datestop,
        column.result0 -> entity.result0,
        column.result7 -> entity.result7,
        column.result14 -> entity.result14,
        column.result30 -> entity.result30
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblRekl)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblRekl).where.eq(column.code, entity.code) }.update.apply()
  }

}
