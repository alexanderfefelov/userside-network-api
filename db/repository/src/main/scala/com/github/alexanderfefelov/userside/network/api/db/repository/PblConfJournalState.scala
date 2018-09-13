package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfJournalState(
  code: Int,
  nazv: Option[String] = None,
  sysrole: Option[Short] = None) {

  def save()(implicit session: DBSession = PblConfJournalState.autoSession): PblConfJournalState = PblConfJournalState.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfJournalState.autoSession): Int = PblConfJournalState.destroy(this)(session)

}


object PblConfJournalState extends SQLSyntaxSupport[PblConfJournalState] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_journal_state"

  override val columns = Seq("code", "nazv", "sysrole")

  def apply(pcjs: SyntaxProvider[PblConfJournalState])(rs: WrappedResultSet): PblConfJournalState = autoConstruct(rs, pcjs)
  def apply(pcjs: ResultName[PblConfJournalState])(rs: WrappedResultSet): PblConfJournalState = autoConstruct(rs, pcjs)

  val pcjs = PblConfJournalState.syntax("pcjs")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfJournalState] = {
    withSQL {
      select.from(PblConfJournalState as pcjs).where.eq(pcjs.code, code)
    }.map(PblConfJournalState(pcjs.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfJournalState] = {
    withSQL(select.from(PblConfJournalState as pcjs)).map(PblConfJournalState(pcjs.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfJournalState as pcjs)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfJournalState] = {
    withSQL {
      select.from(PblConfJournalState as pcjs).where.append(where)
    }.map(PblConfJournalState(pcjs.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfJournalState] = {
    withSQL {
      select.from(PblConfJournalState as pcjs).where.append(where)
    }.map(PblConfJournalState(pcjs.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfJournalState as pcjs).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    nazv: Option[String] = None,
    sysrole: Option[Short] = None)(implicit session: DBSession = autoSession): PblConfJournalState = {
    val generatedKey = withSQL {
      insert.into(PblConfJournalState).namedValues(
        column.nazv -> nazv,
        column.sysrole -> sysrole
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfJournalState(
      code = generatedKey.toInt,
      nazv = nazv,
      sysrole = sysrole)
  }

  def batchInsert(entities: collection.Seq[PblConfJournalState])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'nazv -> entity.nazv,
        'sysrole -> entity.sysrole))
    SQL("""insert into pbl_conf_journal_state(
      nazv,
      sysrole
    ) values (
      {nazv},
      {sysrole}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfJournalState)(implicit session: DBSession = autoSession): PblConfJournalState = {
    withSQL {
      update(PblConfJournalState).set(
        column.code -> entity.code,
        column.nazv -> entity.nazv,
        column.sysrole -> entity.sysrole
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfJournalState)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfJournalState).where.eq(column.code, entity.code) }.update.apply()
  }

}
