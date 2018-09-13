package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblShtraf(
  code: Int,
  perscode: Option[Int] = None,
  shtrafcode: Option[Int] = None,
  dateadd: Option[LocalDate] = None,
  opercode: Option[Int] = None,
  dop: Option[String] = None) {

  def save()(implicit session: DBSession = PblShtraf.autoSession): PblShtraf = PblShtraf.save(this)(session)

  def destroy()(implicit session: DBSession = PblShtraf.autoSession): Int = PblShtraf.destroy(this)(session)

}


object PblShtraf extends SQLSyntaxSupport[PblShtraf] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_shtraf"

  override val columns = Seq("code", "perscode", "shtrafcode", "dateadd", "opercode", "dop")

  def apply(ps: SyntaxProvider[PblShtraf])(rs: WrappedResultSet): PblShtraf = autoConstruct(rs, ps)
  def apply(ps: ResultName[PblShtraf])(rs: WrappedResultSet): PblShtraf = autoConstruct(rs, ps)

  val ps = PblShtraf.syntax("ps")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblShtraf] = {
    withSQL {
      select.from(PblShtraf as ps).where.eq(ps.code, code)
    }.map(PblShtraf(ps.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblShtraf] = {
    withSQL(select.from(PblShtraf as ps)).map(PblShtraf(ps.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblShtraf as ps)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblShtraf] = {
    withSQL {
      select.from(PblShtraf as ps).where.append(where)
    }.map(PblShtraf(ps.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblShtraf] = {
    withSQL {
      select.from(PblShtraf as ps).where.append(where)
    }.map(PblShtraf(ps.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblShtraf as ps).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    perscode: Option[Int] = None,
    shtrafcode: Option[Int] = None,
    dateadd: Option[LocalDate] = None,
    opercode: Option[Int] = None,
    dop: Option[String] = None)(implicit session: DBSession = autoSession): PblShtraf = {
    val generatedKey = withSQL {
      insert.into(PblShtraf).namedValues(
        column.perscode -> perscode,
        column.shtrafcode -> shtrafcode,
        column.dateadd -> dateadd,
        column.opercode -> opercode,
        column.dop -> dop
      )
    }.updateAndReturnGeneratedKey.apply()

    PblShtraf(
      code = generatedKey.toInt,
      perscode = perscode,
      shtrafcode = shtrafcode,
      dateadd = dateadd,
      opercode = opercode,
      dop = dop)
  }

  def batchInsert(entities: collection.Seq[PblShtraf])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'perscode -> entity.perscode,
        'shtrafcode -> entity.shtrafcode,
        'dateadd -> entity.dateadd,
        'opercode -> entity.opercode,
        'dop -> entity.dop))
    SQL("""insert into pbl_shtraf(
      perscode,
      shtrafcode,
      dateadd,
      opercode,
      dop
    ) values (
      {perscode},
      {shtrafcode},
      {dateadd},
      {opercode},
      {dop}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblShtraf)(implicit session: DBSession = autoSession): PblShtraf = {
    withSQL {
      update(PblShtraf).set(
        column.code -> entity.code,
        column.perscode -> entity.perscode,
        column.shtrafcode -> entity.shtrafcode,
        column.dateadd -> entity.dateadd,
        column.opercode -> entity.opercode,
        column.dop -> entity.dop
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblShtraf)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblShtraf).where.eq(column.code, entity.code) }.update.apply()
  }

}
