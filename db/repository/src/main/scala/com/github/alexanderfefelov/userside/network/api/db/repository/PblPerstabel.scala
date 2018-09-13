package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblPerstabel(
  code: Int,
  perscode: Option[Int] = None,
  datework: Option[LocalDate] = None,
  hour: Option[Int] = None,
  typer: Option[Int] = None) {

  def save()(implicit session: DBSession = PblPerstabel.autoSession): PblPerstabel = PblPerstabel.save(this)(session)

  def destroy()(implicit session: DBSession = PblPerstabel.autoSession): Int = PblPerstabel.destroy(this)(session)

}


object PblPerstabel extends SQLSyntaxSupport[PblPerstabel] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_perstabel"

  override val columns = Seq("code", "perscode", "datework", "hour", "typer")

  def apply(pp: SyntaxProvider[PblPerstabel])(rs: WrappedResultSet): PblPerstabel = autoConstruct(rs, pp)
  def apply(pp: ResultName[PblPerstabel])(rs: WrappedResultSet): PblPerstabel = autoConstruct(rs, pp)

  val pp = PblPerstabel.syntax("pp")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblPerstabel] = {
    withSQL {
      select.from(PblPerstabel as pp).where.eq(pp.code, code)
    }.map(PblPerstabel(pp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblPerstabel] = {
    withSQL(select.from(PblPerstabel as pp)).map(PblPerstabel(pp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblPerstabel as pp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblPerstabel] = {
    withSQL {
      select.from(PblPerstabel as pp).where.append(where)
    }.map(PblPerstabel(pp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblPerstabel] = {
    withSQL {
      select.from(PblPerstabel as pp).where.append(where)
    }.map(PblPerstabel(pp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblPerstabel as pp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    perscode: Option[Int] = None,
    datework: Option[LocalDate] = None,
    hour: Option[Int] = None,
    typer: Option[Int] = None)(implicit session: DBSession = autoSession): PblPerstabel = {
    val generatedKey = withSQL {
      insert.into(PblPerstabel).namedValues(
        column.perscode -> perscode,
        column.datework -> datework,
        column.hour -> hour,
        column.typer -> typer
      )
    }.updateAndReturnGeneratedKey.apply()

    PblPerstabel(
      code = generatedKey.toInt,
      perscode = perscode,
      datework = datework,
      hour = hour,
      typer = typer)
  }

  def batchInsert(entities: collection.Seq[PblPerstabel])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'perscode -> entity.perscode,
        'datework -> entity.datework,
        'hour -> entity.hour,
        'typer -> entity.typer))
    SQL("""insert into pbl_perstabel(
      perscode,
      datework,
      hour,
      typer
    ) values (
      {perscode},
      {datework},
      {hour},
      {typer}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblPerstabel)(implicit session: DBSession = autoSession): PblPerstabel = {
    withSQL {
      update(PblPerstabel).set(
        column.code -> entity.code,
        column.perscode -> entity.perscode,
        column.datework -> entity.datework,
        column.hour -> entity.hour,
        column.typer -> entity.typer
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblPerstabel)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblPerstabel).where.eq(column.code, entity.code) }.update.apply()
  }

}
