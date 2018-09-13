package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblJournalStaff(
  code: Int,
  journalcode: Option[Int] = None,
  perscode: Option[Int] = None,
  ispodrazd: Option[Short] = None,
  divisionId: Option[Int] = None,
  assignTime: Option[DateTime] = None) {

  def save()(implicit session: DBSession = PblJournalStaff.autoSession): PblJournalStaff = PblJournalStaff.save(this)(session)

  def destroy()(implicit session: DBSession = PblJournalStaff.autoSession): Int = PblJournalStaff.destroy(this)(session)

}


object PblJournalStaff extends SQLSyntaxSupport[PblJournalStaff] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_journal_staff"

  override val columns = Seq("code", "journalcode", "perscode", "ispodrazd", "division_id", "assign_time")

  def apply(pjs: SyntaxProvider[PblJournalStaff])(rs: WrappedResultSet): PblJournalStaff = autoConstruct(rs, pjs)
  def apply(pjs: ResultName[PblJournalStaff])(rs: WrappedResultSet): PblJournalStaff = autoConstruct(rs, pjs)

  val pjs = PblJournalStaff.syntax("pjs")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblJournalStaff] = {
    withSQL {
      select.from(PblJournalStaff as pjs).where.eq(pjs.code, code)
    }.map(PblJournalStaff(pjs.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblJournalStaff] = {
    withSQL(select.from(PblJournalStaff as pjs)).map(PblJournalStaff(pjs.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblJournalStaff as pjs)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblJournalStaff] = {
    withSQL {
      select.from(PblJournalStaff as pjs).where.append(where)
    }.map(PblJournalStaff(pjs.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblJournalStaff] = {
    withSQL {
      select.from(PblJournalStaff as pjs).where.append(where)
    }.map(PblJournalStaff(pjs.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblJournalStaff as pjs).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    journalcode: Option[Int] = None,
    perscode: Option[Int] = None,
    ispodrazd: Option[Short] = None,
    divisionId: Option[Int] = None,
    assignTime: Option[DateTime] = None)(implicit session: DBSession = autoSession): PblJournalStaff = {
    val generatedKey = withSQL {
      insert.into(PblJournalStaff).namedValues(
        column.journalcode -> journalcode,
        column.perscode -> perscode,
        column.ispodrazd -> ispodrazd,
        column.divisionId -> divisionId,
        column.assignTime -> assignTime
      )
    }.updateAndReturnGeneratedKey.apply()

    PblJournalStaff(
      code = generatedKey.toInt,
      journalcode = journalcode,
      perscode = perscode,
      ispodrazd = ispodrazd,
      divisionId = divisionId,
      assignTime = assignTime)
  }

  def batchInsert(entities: collection.Seq[PblJournalStaff])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'journalcode -> entity.journalcode,
        'perscode -> entity.perscode,
        'ispodrazd -> entity.ispodrazd,
        'divisionId -> entity.divisionId,
        'assignTime -> entity.assignTime))
    SQL("""insert into pbl_journal_staff(
      journalcode,
      perscode,
      ispodrazd,
      division_id,
      assign_time
    ) values (
      {journalcode},
      {perscode},
      {ispodrazd},
      {divisionId},
      {assignTime}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblJournalStaff)(implicit session: DBSession = autoSession): PblJournalStaff = {
    withSQL {
      update(PblJournalStaff).set(
        column.code -> entity.code,
        column.journalcode -> entity.journalcode,
        column.perscode -> entity.perscode,
        column.ispodrazd -> entity.ispodrazd,
        column.divisionId -> entity.divisionId,
        column.assignTime -> entity.assignTime
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblJournalStaff)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblJournalStaff).where.eq(column.code, entity.code) }.update.apply()
  }

}
