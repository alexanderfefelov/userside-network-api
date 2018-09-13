package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfShtraf(
  code: Int,
  nazv: Option[String] = None,
  summa: Option[BigDecimal] = None,
  typer: Option[Short] = None) {

  def save()(implicit session: DBSession = PblConfShtraf.autoSession): PblConfShtraf = PblConfShtraf.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfShtraf.autoSession): Int = PblConfShtraf.destroy(this)(session)

}


object PblConfShtraf extends SQLSyntaxSupport[PblConfShtraf] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_shtraf"

  override val columns = Seq("code", "nazv", "summa", "typer")

  def apply(pcs: SyntaxProvider[PblConfShtraf])(rs: WrappedResultSet): PblConfShtraf = autoConstruct(rs, pcs)
  def apply(pcs: ResultName[PblConfShtraf])(rs: WrappedResultSet): PblConfShtraf = autoConstruct(rs, pcs)

  val pcs = PblConfShtraf.syntax("pcs")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfShtraf] = {
    withSQL {
      select.from(PblConfShtraf as pcs).where.eq(pcs.code, code)
    }.map(PblConfShtraf(pcs.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfShtraf] = {
    withSQL(select.from(PblConfShtraf as pcs)).map(PblConfShtraf(pcs.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfShtraf as pcs)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfShtraf] = {
    withSQL {
      select.from(PblConfShtraf as pcs).where.append(where)
    }.map(PblConfShtraf(pcs.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfShtraf] = {
    withSQL {
      select.from(PblConfShtraf as pcs).where.append(where)
    }.map(PblConfShtraf(pcs.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfShtraf as pcs).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    nazv: Option[String] = None,
    summa: Option[BigDecimal] = None,
    typer: Option[Short] = None)(implicit session: DBSession = autoSession): PblConfShtraf = {
    val generatedKey = withSQL {
      insert.into(PblConfShtraf).namedValues(
        column.nazv -> nazv,
        column.summa -> summa,
        column.typer -> typer
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfShtraf(
      code = generatedKey.toInt,
      nazv = nazv,
      summa = summa,
      typer = typer)
  }

  def batchInsert(entities: collection.Seq[PblConfShtraf])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'nazv -> entity.nazv,
        'summa -> entity.summa,
        'typer -> entity.typer))
    SQL("""insert into pbl_conf_shtraf(
      nazv,
      summa,
      typer
    ) values (
      {nazv},
      {summa},
      {typer}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfShtraf)(implicit session: DBSession = autoSession): PblConfShtraf = {
    withSQL {
      update(PblConfShtraf).set(
        column.code -> entity.code,
        column.nazv -> entity.nazv,
        column.summa -> entity.summa,
        column.typer -> entity.typer
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfShtraf)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfShtraf).where.eq(column.code, entity.code) }.update.apply()
  }

}
