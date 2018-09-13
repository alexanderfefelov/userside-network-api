package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblPersDolg(
  code: Int,
  otdelcode: Option[Int] = None,
  perscode: Option[Int] = None,
  dolg: Option[String] = None,
  dateadd: Option[LocalDate] = None,
  dateout: Option[LocalDate] = None,
  iswork: Option[Short] = None,
  ismain: Option[Short] = None,
  isdel: Option[Short] = None) {

  def save()(implicit session: DBSession = PblPersDolg.autoSession): PblPersDolg = PblPersDolg.save(this)(session)

  def destroy()(implicit session: DBSession = PblPersDolg.autoSession): Int = PblPersDolg.destroy(this)(session)

}


object PblPersDolg extends SQLSyntaxSupport[PblPersDolg] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_pers_dolg"

  override val columns = Seq("code", "otdelcode", "perscode", "dolg", "dateadd", "dateout", "iswork", "ismain", "isdel")

  def apply(ppd: SyntaxProvider[PblPersDolg])(rs: WrappedResultSet): PblPersDolg = autoConstruct(rs, ppd)
  def apply(ppd: ResultName[PblPersDolg])(rs: WrappedResultSet): PblPersDolg = autoConstruct(rs, ppd)

  val ppd = PblPersDolg.syntax("ppd")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblPersDolg] = {
    withSQL {
      select.from(PblPersDolg as ppd).where.eq(ppd.code, code)
    }.map(PblPersDolg(ppd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblPersDolg] = {
    withSQL(select.from(PblPersDolg as ppd)).map(PblPersDolg(ppd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblPersDolg as ppd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblPersDolg] = {
    withSQL {
      select.from(PblPersDolg as ppd).where.append(where)
    }.map(PblPersDolg(ppd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblPersDolg] = {
    withSQL {
      select.from(PblPersDolg as ppd).where.append(where)
    }.map(PblPersDolg(ppd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblPersDolg as ppd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    otdelcode: Option[Int] = None,
    perscode: Option[Int] = None,
    dolg: Option[String] = None,
    dateadd: Option[LocalDate] = None,
    dateout: Option[LocalDate] = None,
    iswork: Option[Short] = None,
    ismain: Option[Short] = None,
    isdel: Option[Short] = None)(implicit session: DBSession = autoSession): PblPersDolg = {
    val generatedKey = withSQL {
      insert.into(PblPersDolg).namedValues(
        column.otdelcode -> otdelcode,
        column.perscode -> perscode,
        column.dolg -> dolg,
        column.dateadd -> dateadd,
        column.dateout -> dateout,
        column.iswork -> iswork,
        column.ismain -> ismain,
        column.isdel -> isdel
      )
    }.updateAndReturnGeneratedKey.apply()

    PblPersDolg(
      code = generatedKey.toInt,
      otdelcode = otdelcode,
      perscode = perscode,
      dolg = dolg,
      dateadd = dateadd,
      dateout = dateout,
      iswork = iswork,
      ismain = ismain,
      isdel = isdel)
  }

  def batchInsert(entities: collection.Seq[PblPersDolg])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'otdelcode -> entity.otdelcode,
        'perscode -> entity.perscode,
        'dolg -> entity.dolg,
        'dateadd -> entity.dateadd,
        'dateout -> entity.dateout,
        'iswork -> entity.iswork,
        'ismain -> entity.ismain,
        'isdel -> entity.isdel))
    SQL("""insert into pbl_pers_dolg(
      otdelcode,
      perscode,
      dolg,
      dateadd,
      dateout,
      iswork,
      ismain,
      isdel
    ) values (
      {otdelcode},
      {perscode},
      {dolg},
      {dateadd},
      {dateout},
      {iswork},
      {ismain},
      {isdel}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblPersDolg)(implicit session: DBSession = autoSession): PblPersDolg = {
    withSQL {
      update(PblPersDolg).set(
        column.code -> entity.code,
        column.otdelcode -> entity.otdelcode,
        column.perscode -> entity.perscode,
        column.dolg -> entity.dolg,
        column.dateadd -> entity.dateadd,
        column.dateout -> entity.dateout,
        column.iswork -> entity.iswork,
        column.ismain -> entity.ismain,
        column.isdel -> entity.isdel
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblPersDolg)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblPersDolg).where.eq(column.code, entity.code) }.update.apply()
  }

}
