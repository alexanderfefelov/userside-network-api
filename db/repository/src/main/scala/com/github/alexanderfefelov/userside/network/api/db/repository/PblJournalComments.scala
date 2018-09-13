package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblJournalComments(
  code: Int,
  journalcode: Option[Int] = None,
  opercode: Option[Int] = None,
  dateadd: Option[DateTime] = None,
  opis: Option[String] = None) {

  def save()(implicit session: DBSession = PblJournalComments.autoSession): PblJournalComments = PblJournalComments.save(this)(session)

  def destroy()(implicit session: DBSession = PblJournalComments.autoSession): Int = PblJournalComments.destroy(this)(session)

}


object PblJournalComments extends SQLSyntaxSupport[PblJournalComments] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_journal_comments"

  override val columns = Seq("code", "journalcode", "opercode", "dateadd", "opis")

  def apply(pjc: SyntaxProvider[PblJournalComments])(rs: WrappedResultSet): PblJournalComments = autoConstruct(rs, pjc)
  def apply(pjc: ResultName[PblJournalComments])(rs: WrappedResultSet): PblJournalComments = autoConstruct(rs, pjc)

  val pjc = PblJournalComments.syntax("pjc")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblJournalComments] = {
    withSQL {
      select.from(PblJournalComments as pjc).where.eq(pjc.code, code)
    }.map(PblJournalComments(pjc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblJournalComments] = {
    withSQL(select.from(PblJournalComments as pjc)).map(PblJournalComments(pjc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblJournalComments as pjc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblJournalComments] = {
    withSQL {
      select.from(PblJournalComments as pjc).where.append(where)
    }.map(PblJournalComments(pjc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblJournalComments] = {
    withSQL {
      select.from(PblJournalComments as pjc).where.append(where)
    }.map(PblJournalComments(pjc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblJournalComments as pjc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    journalcode: Option[Int] = None,
    opercode: Option[Int] = None,
    dateadd: Option[DateTime] = None,
    opis: Option[String] = None)(implicit session: DBSession = autoSession): PblJournalComments = {
    val generatedKey = withSQL {
      insert.into(PblJournalComments).namedValues(
        column.journalcode -> journalcode,
        column.opercode -> opercode,
        column.dateadd -> dateadd,
        column.opis -> opis
      )
    }.updateAndReturnGeneratedKey.apply()

    PblJournalComments(
      code = generatedKey.toInt,
      journalcode = journalcode,
      opercode = opercode,
      dateadd = dateadd,
      opis = opis)
  }

  def batchInsert(entities: collection.Seq[PblJournalComments])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'journalcode -> entity.journalcode,
        'opercode -> entity.opercode,
        'dateadd -> entity.dateadd,
        'opis -> entity.opis))
    SQL("""insert into pbl_journal_comments(
      journalcode,
      opercode,
      dateadd,
      opis
    ) values (
      {journalcode},
      {opercode},
      {dateadd},
      {opis}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblJournalComments)(implicit session: DBSession = autoSession): PblJournalComments = {
    withSQL {
      update(PblJournalComments).set(
        column.code -> entity.code,
        column.journalcode -> entity.journalcode,
        column.opercode -> entity.opercode,
        column.dateadd -> entity.dateadd,
        column.opis -> entity.opis
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblJournalComments)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblJournalComments).where.eq(column.code, entity.code) }.update.apply()
  }

}
