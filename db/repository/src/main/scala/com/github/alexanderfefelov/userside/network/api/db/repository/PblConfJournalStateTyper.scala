package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfJournalStateTyper(
  code: Int,
  typercode: Option[Int] = None,
  statecode: Option[Int] = None) {

  def save()(implicit session: DBSession = PblConfJournalStateTyper.autoSession): PblConfJournalStateTyper = PblConfJournalStateTyper.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfJournalStateTyper.autoSession): Int = PblConfJournalStateTyper.destroy(this)(session)

}


object PblConfJournalStateTyper extends SQLSyntaxSupport[PblConfJournalStateTyper] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_journal_state_typer"

  override val columns = Seq("code", "typercode", "statecode")

  def apply(pcjst: SyntaxProvider[PblConfJournalStateTyper])(rs: WrappedResultSet): PblConfJournalStateTyper = autoConstruct(rs, pcjst)
  def apply(pcjst: ResultName[PblConfJournalStateTyper])(rs: WrappedResultSet): PblConfJournalStateTyper = autoConstruct(rs, pcjst)

  val pcjst = PblConfJournalStateTyper.syntax("pcjst")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfJournalStateTyper] = {
    withSQL {
      select.from(PblConfJournalStateTyper as pcjst).where.eq(pcjst.code, code)
    }.map(PblConfJournalStateTyper(pcjst.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfJournalStateTyper] = {
    withSQL(select.from(PblConfJournalStateTyper as pcjst)).map(PblConfJournalStateTyper(pcjst.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfJournalStateTyper as pcjst)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfJournalStateTyper] = {
    withSQL {
      select.from(PblConfJournalStateTyper as pcjst).where.append(where)
    }.map(PblConfJournalStateTyper(pcjst.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfJournalStateTyper] = {
    withSQL {
      select.from(PblConfJournalStateTyper as pcjst).where.append(where)
    }.map(PblConfJournalStateTyper(pcjst.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfJournalStateTyper as pcjst).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    typercode: Option[Int] = None,
    statecode: Option[Int] = None)(implicit session: DBSession = autoSession): PblConfJournalStateTyper = {
    val generatedKey = withSQL {
      insert.into(PblConfJournalStateTyper).namedValues(
        column.typercode -> typercode,
        column.statecode -> statecode
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfJournalStateTyper(
      code = generatedKey.toInt,
      typercode = typercode,
      statecode = statecode)
  }

  def batchInsert(entities: collection.Seq[PblConfJournalStateTyper])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'typercode -> entity.typercode,
        'statecode -> entity.statecode))
    SQL("""insert into pbl_conf_journal_state_typer(
      typercode,
      statecode
    ) values (
      {typercode},
      {statecode}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfJournalStateTyper)(implicit session: DBSession = autoSession): PblConfJournalStateTyper = {
    withSQL {
      update(PblConfJournalStateTyper).set(
        column.code -> entity.code,
        column.typercode -> entity.typercode,
        column.statecode -> entity.statecode
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfJournalStateTyper)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfJournalStateTyper).where.eq(column.code, entity.code) }.update.apply()
  }

}
