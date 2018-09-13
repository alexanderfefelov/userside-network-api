package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblJournalStatus(
  code: Int,
  journalcode: Option[Int] = None,
  statuscode: Option[Int] = None,
  opercode: Option[Int] = None,
  dateadd: Option[DateTime] = None) {

  def save()(implicit session: DBSession = PblJournalStatus.autoSession): PblJournalStatus = PblJournalStatus.save(this)(session)

  def destroy()(implicit session: DBSession = PblJournalStatus.autoSession): Int = PblJournalStatus.destroy(this)(session)

}


object PblJournalStatus extends SQLSyntaxSupport[PblJournalStatus] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_journal_status"

  override val columns = Seq("code", "journalcode", "statuscode", "opercode", "dateadd")

  def apply(pjs: SyntaxProvider[PblJournalStatus])(rs: WrappedResultSet): PblJournalStatus = autoConstruct(rs, pjs)
  def apply(pjs: ResultName[PblJournalStatus])(rs: WrappedResultSet): PblJournalStatus = autoConstruct(rs, pjs)

  val pjs = PblJournalStatus.syntax("pjs")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblJournalStatus] = {
    withSQL {
      select.from(PblJournalStatus as pjs).where.eq(pjs.code, code)
    }.map(PblJournalStatus(pjs.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblJournalStatus] = {
    withSQL(select.from(PblJournalStatus as pjs)).map(PblJournalStatus(pjs.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblJournalStatus as pjs)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblJournalStatus] = {
    withSQL {
      select.from(PblJournalStatus as pjs).where.append(where)
    }.map(PblJournalStatus(pjs.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblJournalStatus] = {
    withSQL {
      select.from(PblJournalStatus as pjs).where.append(where)
    }.map(PblJournalStatus(pjs.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblJournalStatus as pjs).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    journalcode: Option[Int] = None,
    statuscode: Option[Int] = None,
    opercode: Option[Int] = None,
    dateadd: Option[DateTime] = None)(implicit session: DBSession = autoSession): PblJournalStatus = {
    val generatedKey = withSQL {
      insert.into(PblJournalStatus).namedValues(
        column.journalcode -> journalcode,
        column.statuscode -> statuscode,
        column.opercode -> opercode,
        column.dateadd -> dateadd
      )
    }.updateAndReturnGeneratedKey.apply()

    PblJournalStatus(
      code = generatedKey.toInt,
      journalcode = journalcode,
      statuscode = statuscode,
      opercode = opercode,
      dateadd = dateadd)
  }

  def batchInsert(entities: collection.Seq[PblJournalStatus])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'journalcode -> entity.journalcode,
        'statuscode -> entity.statuscode,
        'opercode -> entity.opercode,
        'dateadd -> entity.dateadd))
    SQL("""insert into pbl_journal_status(
      journalcode,
      statuscode,
      opercode,
      dateadd
    ) values (
      {journalcode},
      {statuscode},
      {opercode},
      {dateadd}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblJournalStatus)(implicit session: DBSession = autoSession): PblJournalStatus = {
    withSQL {
      update(PblJournalStatus).set(
        column.code -> entity.code,
        column.journalcode -> entity.journalcode,
        column.statuscode -> entity.statuscode,
        column.opercode -> entity.opercode,
        column.dateadd -> entity.dateadd
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblJournalStatus)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblJournalStatus).where.eq(column.code, entity.code) }.update.apply()
  }

}
