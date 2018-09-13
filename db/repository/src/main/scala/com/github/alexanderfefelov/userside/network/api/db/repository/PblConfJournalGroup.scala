package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfJournalGroup(
  code: Int,
  nazv: Option[String] = None,
  pos: Option[Int] = None) {

  def save()(implicit session: DBSession = PblConfJournalGroup.autoSession): PblConfJournalGroup = PblConfJournalGroup.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfJournalGroup.autoSession): Int = PblConfJournalGroup.destroy(this)(session)

}


object PblConfJournalGroup extends SQLSyntaxSupport[PblConfJournalGroup] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_journal_group"

  override val columns = Seq("code", "nazv", "pos")

  def apply(pcjg: SyntaxProvider[PblConfJournalGroup])(rs: WrappedResultSet): PblConfJournalGroup = autoConstruct(rs, pcjg)
  def apply(pcjg: ResultName[PblConfJournalGroup])(rs: WrappedResultSet): PblConfJournalGroup = autoConstruct(rs, pcjg)

  val pcjg = PblConfJournalGroup.syntax("pcjg")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfJournalGroup] = {
    withSQL {
      select.from(PblConfJournalGroup as pcjg).where.eq(pcjg.code, code)
    }.map(PblConfJournalGroup(pcjg.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfJournalGroup] = {
    withSQL(select.from(PblConfJournalGroup as pcjg)).map(PblConfJournalGroup(pcjg.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfJournalGroup as pcjg)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfJournalGroup] = {
    withSQL {
      select.from(PblConfJournalGroup as pcjg).where.append(where)
    }.map(PblConfJournalGroup(pcjg.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfJournalGroup] = {
    withSQL {
      select.from(PblConfJournalGroup as pcjg).where.append(where)
    }.map(PblConfJournalGroup(pcjg.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfJournalGroup as pcjg).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    nazv: Option[String] = None,
    pos: Option[Int] = None)(implicit session: DBSession = autoSession): PblConfJournalGroup = {
    val generatedKey = withSQL {
      insert.into(PblConfJournalGroup).namedValues(
        column.nazv -> nazv,
        column.pos -> pos
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfJournalGroup(
      code = generatedKey.toInt,
      nazv = nazv,
      pos = pos)
  }

  def batchInsert(entities: collection.Seq[PblConfJournalGroup])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'nazv -> entity.nazv,
        'pos -> entity.pos))
    SQL("""insert into pbl_conf_journal_group(
      nazv,
      pos
    ) values (
      {nazv},
      {pos}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfJournalGroup)(implicit session: DBSession = autoSession): PblConfJournalGroup = {
    withSQL {
      update(PblConfJournalGroup).set(
        column.code -> entity.code,
        column.nazv -> entity.nazv,
        column.pos -> entity.pos
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfJournalGroup)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfJournalGroup).where.eq(column.code, entity.code) }.update.apply()
  }

}
