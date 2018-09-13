package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfJournalStatus(
  code: Int,
  nazv: Option[String] = None,
  isact: Option[Short] = None) {

  def save()(implicit session: DBSession = PblConfJournalStatus.autoSession): PblConfJournalStatus = PblConfJournalStatus.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfJournalStatus.autoSession): Int = PblConfJournalStatus.destroy(this)(session)

}


object PblConfJournalStatus extends SQLSyntaxSupport[PblConfJournalStatus] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_journal_status"

  override val columns = Seq("code", "nazv", "isact")

  def apply(pcjs: SyntaxProvider[PblConfJournalStatus])(rs: WrappedResultSet): PblConfJournalStatus = autoConstruct(rs, pcjs)
  def apply(pcjs: ResultName[PblConfJournalStatus])(rs: WrappedResultSet): PblConfJournalStatus = autoConstruct(rs, pcjs)

  val pcjs = PblConfJournalStatus.syntax("pcjs")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfJournalStatus] = {
    withSQL {
      select.from(PblConfJournalStatus as pcjs).where.eq(pcjs.code, code)
    }.map(PblConfJournalStatus(pcjs.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfJournalStatus] = {
    withSQL(select.from(PblConfJournalStatus as pcjs)).map(PblConfJournalStatus(pcjs.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfJournalStatus as pcjs)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfJournalStatus] = {
    withSQL {
      select.from(PblConfJournalStatus as pcjs).where.append(where)
    }.map(PblConfJournalStatus(pcjs.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfJournalStatus] = {
    withSQL {
      select.from(PblConfJournalStatus as pcjs).where.append(where)
    }.map(PblConfJournalStatus(pcjs.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfJournalStatus as pcjs).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    nazv: Option[String] = None,
    isact: Option[Short] = None)(implicit session: DBSession = autoSession): PblConfJournalStatus = {
    val generatedKey = withSQL {
      insert.into(PblConfJournalStatus).namedValues(
        column.nazv -> nazv,
        column.isact -> isact
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfJournalStatus(
      code = generatedKey.toInt,
      nazv = nazv,
      isact = isact)
  }

  def batchInsert(entities: collection.Seq[PblConfJournalStatus])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'nazv -> entity.nazv,
        'isact -> entity.isact))
    SQL("""insert into pbl_conf_journal_status(
      nazv,
      isact
    ) values (
      {nazv},
      {isact}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfJournalStatus)(implicit session: DBSession = autoSession): PblConfJournalStatus = {
    withSQL {
      update(PblConfJournalStatus).set(
        column.code -> entity.code,
        column.nazv -> entity.nazv,
        column.isact -> entity.isact
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfJournalStatus)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfJournalStatus).where.eq(column.code, entity.code) }.update.apply()
  }

}
