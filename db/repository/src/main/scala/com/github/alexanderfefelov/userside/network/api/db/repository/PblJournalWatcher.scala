package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblJournalWatcher(
  code: Int,
  journalcode: Option[Int] = None,
  opercode: Option[Int] = None) {

  def save()(implicit session: DBSession = PblJournalWatcher.autoSession): PblJournalWatcher = PblJournalWatcher.save(this)(session)

  def destroy()(implicit session: DBSession = PblJournalWatcher.autoSession): Int = PblJournalWatcher.destroy(this)(session)

}


object PblJournalWatcher extends SQLSyntaxSupport[PblJournalWatcher] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_journal_watcher"

  override val columns = Seq("code", "journalcode", "opercode")

  def apply(pjw: SyntaxProvider[PblJournalWatcher])(rs: WrappedResultSet): PblJournalWatcher = autoConstruct(rs, pjw)
  def apply(pjw: ResultName[PblJournalWatcher])(rs: WrappedResultSet): PblJournalWatcher = autoConstruct(rs, pjw)

  val pjw = PblJournalWatcher.syntax("pjw")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblJournalWatcher] = {
    withSQL {
      select.from(PblJournalWatcher as pjw).where.eq(pjw.code, code)
    }.map(PblJournalWatcher(pjw.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblJournalWatcher] = {
    withSQL(select.from(PblJournalWatcher as pjw)).map(PblJournalWatcher(pjw.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblJournalWatcher as pjw)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblJournalWatcher] = {
    withSQL {
      select.from(PblJournalWatcher as pjw).where.append(where)
    }.map(PblJournalWatcher(pjw.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblJournalWatcher] = {
    withSQL {
      select.from(PblJournalWatcher as pjw).where.append(where)
    }.map(PblJournalWatcher(pjw.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblJournalWatcher as pjw).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    journalcode: Option[Int] = None,
    opercode: Option[Int] = None)(implicit session: DBSession = autoSession): PblJournalWatcher = {
    val generatedKey = withSQL {
      insert.into(PblJournalWatcher).namedValues(
        column.journalcode -> journalcode,
        column.opercode -> opercode
      )
    }.updateAndReturnGeneratedKey.apply()

    PblJournalWatcher(
      code = generatedKey.toInt,
      journalcode = journalcode,
      opercode = opercode)
  }

  def batchInsert(entities: collection.Seq[PblJournalWatcher])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'journalcode -> entity.journalcode,
        'opercode -> entity.opercode))
    SQL("""insert into pbl_journal_watcher(
      journalcode,
      opercode
    ) values (
      {journalcode},
      {opercode}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblJournalWatcher)(implicit session: DBSession = autoSession): PblJournalWatcher = {
    withSQL {
      update(PblJournalWatcher).set(
        column.code -> entity.code,
        column.journalcode -> entity.journalcode,
        column.opercode -> entity.opercode
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblJournalWatcher)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblJournalWatcher).where.eq(column.code, entity.code) }.update.apply()
  }

}
