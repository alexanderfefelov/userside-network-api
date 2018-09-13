package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblTerminal(
  code: Int,
  termcode: Option[Short] = None,
  datedo: Option[DateTime] = None,
  termnumber: Option[Long] = None,
  usercode: Option[Int] = None,
  summa: Option[BigDecimal] = None,
  dop: Option[String] = None,
  transcode: Option[Long] = None) {

  def save()(implicit session: DBSession = PblTerminal.autoSession): PblTerminal = PblTerminal.save(this)(session)

  def destroy()(implicit session: DBSession = PblTerminal.autoSession): Int = PblTerminal.destroy(this)(session)

}


object PblTerminal extends SQLSyntaxSupport[PblTerminal] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_terminal"

  override val columns = Seq("code", "termcode", "datedo", "termnumber", "usercode", "summa", "dop", "transcode")

  def apply(pt: SyntaxProvider[PblTerminal])(rs: WrappedResultSet): PblTerminal = autoConstruct(rs, pt)
  def apply(pt: ResultName[PblTerminal])(rs: WrappedResultSet): PblTerminal = autoConstruct(rs, pt)

  val pt = PblTerminal.syntax("pt")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblTerminal] = {
    withSQL {
      select.from(PblTerminal as pt).where.eq(pt.code, code)
    }.map(PblTerminal(pt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblTerminal] = {
    withSQL(select.from(PblTerminal as pt)).map(PblTerminal(pt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblTerminal as pt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblTerminal] = {
    withSQL {
      select.from(PblTerminal as pt).where.append(where)
    }.map(PblTerminal(pt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblTerminal] = {
    withSQL {
      select.from(PblTerminal as pt).where.append(where)
    }.map(PblTerminal(pt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblTerminal as pt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    termcode: Option[Short] = None,
    datedo: Option[DateTime] = None,
    termnumber: Option[Long] = None,
    usercode: Option[Int] = None,
    summa: Option[BigDecimal] = None,
    dop: Option[String] = None,
    transcode: Option[Long] = None)(implicit session: DBSession = autoSession): PblTerminal = {
    val generatedKey = withSQL {
      insert.into(PblTerminal).namedValues(
        column.termcode -> termcode,
        column.datedo -> datedo,
        column.termnumber -> termnumber,
        column.usercode -> usercode,
        column.summa -> summa,
        column.dop -> dop,
        column.transcode -> transcode
      )
    }.updateAndReturnGeneratedKey.apply()

    PblTerminal(
      code = generatedKey.toInt,
      termcode = termcode,
      datedo = datedo,
      termnumber = termnumber,
      usercode = usercode,
      summa = summa,
      dop = dop,
      transcode = transcode)
  }

  def batchInsert(entities: collection.Seq[PblTerminal])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'termcode -> entity.termcode,
        'datedo -> entity.datedo,
        'termnumber -> entity.termnumber,
        'usercode -> entity.usercode,
        'summa -> entity.summa,
        'dop -> entity.dop,
        'transcode -> entity.transcode))
    SQL("""insert into pbl_terminal(
      termcode,
      datedo,
      termnumber,
      usercode,
      summa,
      dop,
      transcode
    ) values (
      {termcode},
      {datedo},
      {termnumber},
      {usercode},
      {summa},
      {dop},
      {transcode}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblTerminal)(implicit session: DBSession = autoSession): PblTerminal = {
    withSQL {
      update(PblTerminal).set(
        column.code -> entity.code,
        column.termcode -> entity.termcode,
        column.datedo -> entity.datedo,
        column.termnumber -> entity.termnumber,
        column.usercode -> entity.usercode,
        column.summa -> entity.summa,
        column.dop -> entity.dop,
        column.transcode -> entity.transcode
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblTerminal)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblTerminal).where.eq(column.code, entity.code) }.update.apply()
  }

}
