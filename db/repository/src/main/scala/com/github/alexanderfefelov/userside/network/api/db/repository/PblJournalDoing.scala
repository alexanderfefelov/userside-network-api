package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblJournalDoing(
  code: Int,
  journalcode: Option[Int] = None,
  datedo: Option[DateTime] = None,
  opercode: Option[Int] = None,
  typer: Option[Int] = None,
  typer2: Option[Int] = None,
  opis: Option[String] = None,
  typer3: Option[Int] = None) {

  def save()(implicit session: DBSession = PblJournalDoing.autoSession): PblJournalDoing = PblJournalDoing.save(this)(session)

  def destroy()(implicit session: DBSession = PblJournalDoing.autoSession): Int = PblJournalDoing.destroy(this)(session)

}


object PblJournalDoing extends SQLSyntaxSupport[PblJournalDoing] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_journal_doing"

  override val columns = Seq("code", "journalcode", "datedo", "opercode", "typer", "typer2", "opis", "typer3")

  def apply(pjd: SyntaxProvider[PblJournalDoing])(rs: WrappedResultSet): PblJournalDoing = autoConstruct(rs, pjd)
  def apply(pjd: ResultName[PblJournalDoing])(rs: WrappedResultSet): PblJournalDoing = autoConstruct(rs, pjd)

  val pjd = PblJournalDoing.syntax("pjd")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblJournalDoing] = {
    withSQL {
      select.from(PblJournalDoing as pjd).where.eq(pjd.code, code)
    }.map(PblJournalDoing(pjd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblJournalDoing] = {
    withSQL(select.from(PblJournalDoing as pjd)).map(PblJournalDoing(pjd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblJournalDoing as pjd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblJournalDoing] = {
    withSQL {
      select.from(PblJournalDoing as pjd).where.append(where)
    }.map(PblJournalDoing(pjd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblJournalDoing] = {
    withSQL {
      select.from(PblJournalDoing as pjd).where.append(where)
    }.map(PblJournalDoing(pjd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblJournalDoing as pjd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    journalcode: Option[Int] = None,
    datedo: Option[DateTime] = None,
    opercode: Option[Int] = None,
    typer: Option[Int] = None,
    typer2: Option[Int] = None,
    opis: Option[String] = None,
    typer3: Option[Int] = None)(implicit session: DBSession = autoSession): PblJournalDoing = {
    val generatedKey = withSQL {
      insert.into(PblJournalDoing).namedValues(
        column.journalcode -> journalcode,
        column.datedo -> datedo,
        column.opercode -> opercode,
        column.typer -> typer,
        column.typer2 -> typer2,
        column.opis -> opis,
        column.typer3 -> typer3
      )
    }.updateAndReturnGeneratedKey.apply()

    PblJournalDoing(
      code = generatedKey.toInt,
      journalcode = journalcode,
      datedo = datedo,
      opercode = opercode,
      typer = typer,
      typer2 = typer2,
      opis = opis,
      typer3 = typer3)
  }

  def batchInsert(entities: collection.Seq[PblJournalDoing])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'journalcode -> entity.journalcode,
        'datedo -> entity.datedo,
        'opercode -> entity.opercode,
        'typer -> entity.typer,
        'typer2 -> entity.typer2,
        'opis -> entity.opis,
        'typer3 -> entity.typer3))
    SQL("""insert into pbl_journal_doing(
      journalcode,
      datedo,
      opercode,
      typer,
      typer2,
      opis,
      typer3
    ) values (
      {journalcode},
      {datedo},
      {opercode},
      {typer},
      {typer2},
      {opis},
      {typer3}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblJournalDoing)(implicit session: DBSession = autoSession): PblJournalDoing = {
    withSQL {
      update(PblJournalDoing).set(
        column.code -> entity.code,
        column.journalcode -> entity.journalcode,
        column.datedo -> entity.datedo,
        column.opercode -> entity.opercode,
        column.typer -> entity.typer,
        column.typer2 -> entity.typer2,
        column.opis -> entity.opis,
        column.typer3 -> entity.typer3
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblJournalDoing)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblJournalDoing).where.eq(column.code, entity.code) }.update.apply()
  }

}
