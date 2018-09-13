package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblJournalRepeat(
  code: Int,
  param: Option[String] = None,
  lastdate: Option[DateTime] = None,
  iswork: Option[Short] = None) {

  def save()(implicit session: DBSession = PblJournalRepeat.autoSession): PblJournalRepeat = PblJournalRepeat.save(this)(session)

  def destroy()(implicit session: DBSession = PblJournalRepeat.autoSession): Int = PblJournalRepeat.destroy(this)(session)

}


object PblJournalRepeat extends SQLSyntaxSupport[PblJournalRepeat] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_journal_repeat"

  override val columns = Seq("code", "param", "lastdate", "iswork")

  def apply(pjr: SyntaxProvider[PblJournalRepeat])(rs: WrappedResultSet): PblJournalRepeat = autoConstruct(rs, pjr)
  def apply(pjr: ResultName[PblJournalRepeat])(rs: WrappedResultSet): PblJournalRepeat = autoConstruct(rs, pjr)

  val pjr = PblJournalRepeat.syntax("pjr")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblJournalRepeat] = {
    withSQL {
      select.from(PblJournalRepeat as pjr).where.eq(pjr.code, code)
    }.map(PblJournalRepeat(pjr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblJournalRepeat] = {
    withSQL(select.from(PblJournalRepeat as pjr)).map(PblJournalRepeat(pjr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblJournalRepeat as pjr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblJournalRepeat] = {
    withSQL {
      select.from(PblJournalRepeat as pjr).where.append(where)
    }.map(PblJournalRepeat(pjr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblJournalRepeat] = {
    withSQL {
      select.from(PblJournalRepeat as pjr).where.append(where)
    }.map(PblJournalRepeat(pjr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblJournalRepeat as pjr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    param: Option[String] = None,
    lastdate: Option[DateTime] = None,
    iswork: Option[Short] = None)(implicit session: DBSession = autoSession): PblJournalRepeat = {
    val generatedKey = withSQL {
      insert.into(PblJournalRepeat).namedValues(
        column.param -> param,
        column.lastdate -> lastdate,
        column.iswork -> iswork
      )
    }.updateAndReturnGeneratedKey.apply()

    PblJournalRepeat(
      code = generatedKey.toInt,
      param = param,
      lastdate = lastdate,
      iswork = iswork)
  }

  def batchInsert(entities: collection.Seq[PblJournalRepeat])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'param -> entity.param,
        'lastdate -> entity.lastdate,
        'iswork -> entity.iswork))
    SQL("""insert into pbl_journal_repeat(
      param,
      lastdate,
      iswork
    ) values (
      {param},
      {lastdate},
      {iswork}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblJournalRepeat)(implicit session: DBSession = autoSession): PblJournalRepeat = {
    withSQL {
      update(PblJournalRepeat).set(
        column.code -> entity.code,
        column.param -> entity.param,
        column.lastdate -> entity.lastdate,
        column.iswork -> entity.iswork
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblJournalRepeat)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblJournalRepeat).where.eq(column.code, entity.code) }.update.apply()
  }

}
