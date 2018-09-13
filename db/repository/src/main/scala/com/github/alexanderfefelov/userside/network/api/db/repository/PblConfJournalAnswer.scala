package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfJournalAnswer(
  code: Int,
  opis: Option[String] = None) {

  def save()(implicit session: DBSession = PblConfJournalAnswer.autoSession): PblConfJournalAnswer = PblConfJournalAnswer.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfJournalAnswer.autoSession): Int = PblConfJournalAnswer.destroy(this)(session)

}


object PblConfJournalAnswer extends SQLSyntaxSupport[PblConfJournalAnswer] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_journal_answer"

  override val columns = Seq("code", "opis")

  def apply(pcja: SyntaxProvider[PblConfJournalAnswer])(rs: WrappedResultSet): PblConfJournalAnswer = autoConstruct(rs, pcja)
  def apply(pcja: ResultName[PblConfJournalAnswer])(rs: WrappedResultSet): PblConfJournalAnswer = autoConstruct(rs, pcja)

  val pcja = PblConfJournalAnswer.syntax("pcja")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfJournalAnswer] = {
    withSQL {
      select.from(PblConfJournalAnswer as pcja).where.eq(pcja.code, code)
    }.map(PblConfJournalAnswer(pcja.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfJournalAnswer] = {
    withSQL(select.from(PblConfJournalAnswer as pcja)).map(PblConfJournalAnswer(pcja.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfJournalAnswer as pcja)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfJournalAnswer] = {
    withSQL {
      select.from(PblConfJournalAnswer as pcja).where.append(where)
    }.map(PblConfJournalAnswer(pcja.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfJournalAnswer] = {
    withSQL {
      select.from(PblConfJournalAnswer as pcja).where.append(where)
    }.map(PblConfJournalAnswer(pcja.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfJournalAnswer as pcja).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    opis: Option[String] = None)(implicit session: DBSession = autoSession): PblConfJournalAnswer = {
    val generatedKey = withSQL {
      insert.into(PblConfJournalAnswer).namedValues(
        column.opis -> opis
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfJournalAnswer(
      code = generatedKey.toInt,
      opis = opis)
  }

  def batchInsert(entities: collection.Seq[PblConfJournalAnswer])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'opis -> entity.opis))
    SQL("""insert into pbl_conf_journal_answer(
      opis
    ) values (
      {opis}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfJournalAnswer)(implicit session: DBSession = autoSession): PblConfJournalAnswer = {
    withSQL {
      update(PblConfJournalAnswer).set(
        column.code -> entity.code,
        column.opis -> entity.opis
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfJournalAnswer)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfJournalAnswer).where.eq(column.code, entity.code) }.update.apply()
  }

}
