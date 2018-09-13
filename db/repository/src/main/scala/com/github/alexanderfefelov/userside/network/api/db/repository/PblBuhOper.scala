package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblBuhOper(
  code: Int,
  typer: Option[Int] = None,
  scet1: Option[Long] = None,
  scet2: Option[Long] = None,
  tovarcode: Option[Int] = None,
  summa: Option[BigDecimal] = None,
  kolvo: Option[BigDecimal] = None,
  dateadd: Option[DateTime] = None,
  opercode: Option[Int] = None,
  dop: Option[String] = None) {

  def save()(implicit session: DBSession = PblBuhOper.autoSession): PblBuhOper = PblBuhOper.save(this)(session)

  def destroy()(implicit session: DBSession = PblBuhOper.autoSession): Int = PblBuhOper.destroy(this)(session)

}


object PblBuhOper extends SQLSyntaxSupport[PblBuhOper] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_buh_oper"

  override val columns = Seq("code", "typer", "scet1", "scet2", "tovarcode", "summa", "kolvo", "dateadd", "opercode", "dop")

  def apply(pbo: SyntaxProvider[PblBuhOper])(rs: WrappedResultSet): PblBuhOper = autoConstruct(rs, pbo)
  def apply(pbo: ResultName[PblBuhOper])(rs: WrappedResultSet): PblBuhOper = autoConstruct(rs, pbo)

  val pbo = PblBuhOper.syntax("pbo")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblBuhOper] = {
    withSQL {
      select.from(PblBuhOper as pbo).where.eq(pbo.code, code)
    }.map(PblBuhOper(pbo.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblBuhOper] = {
    withSQL(select.from(PblBuhOper as pbo)).map(PblBuhOper(pbo.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblBuhOper as pbo)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblBuhOper] = {
    withSQL {
      select.from(PblBuhOper as pbo).where.append(where)
    }.map(PblBuhOper(pbo.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblBuhOper] = {
    withSQL {
      select.from(PblBuhOper as pbo).where.append(where)
    }.map(PblBuhOper(pbo.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblBuhOper as pbo).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    typer: Option[Int] = None,
    scet1: Option[Long] = None,
    scet2: Option[Long] = None,
    tovarcode: Option[Int] = None,
    summa: Option[BigDecimal] = None,
    kolvo: Option[BigDecimal] = None,
    dateadd: Option[DateTime] = None,
    opercode: Option[Int] = None,
    dop: Option[String] = None)(implicit session: DBSession = autoSession): PblBuhOper = {
    val generatedKey = withSQL {
      insert.into(PblBuhOper).namedValues(
        column.typer -> typer,
        column.scet1 -> scet1,
        column.scet2 -> scet2,
        column.tovarcode -> tovarcode,
        column.summa -> summa,
        column.kolvo -> kolvo,
        column.dateadd -> dateadd,
        column.opercode -> opercode,
        column.dop -> dop
      )
    }.updateAndReturnGeneratedKey.apply()

    PblBuhOper(
      code = generatedKey.toInt,
      typer = typer,
      scet1 = scet1,
      scet2 = scet2,
      tovarcode = tovarcode,
      summa = summa,
      kolvo = kolvo,
      dateadd = dateadd,
      opercode = opercode,
      dop = dop)
  }

  def batchInsert(entities: collection.Seq[PblBuhOper])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'typer -> entity.typer,
        'scet1 -> entity.scet1,
        'scet2 -> entity.scet2,
        'tovarcode -> entity.tovarcode,
        'summa -> entity.summa,
        'kolvo -> entity.kolvo,
        'dateadd -> entity.dateadd,
        'opercode -> entity.opercode,
        'dop -> entity.dop))
    SQL("""insert into pbl_buh_oper(
      typer,
      scet1,
      scet2,
      tovarcode,
      summa,
      kolvo,
      dateadd,
      opercode,
      dop
    ) values (
      {typer},
      {scet1},
      {scet2},
      {tovarcode},
      {summa},
      {kolvo},
      {dateadd},
      {opercode},
      {dop}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblBuhOper)(implicit session: DBSession = autoSession): PblBuhOper = {
    withSQL {
      update(PblBuhOper).set(
        column.code -> entity.code,
        column.typer -> entity.typer,
        column.scet1 -> entity.scet1,
        column.scet2 -> entity.scet2,
        column.tovarcode -> entity.tovarcode,
        column.summa -> entity.summa,
        column.kolvo -> entity.kolvo,
        column.dateadd -> entity.dateadd,
        column.opercode -> entity.opercode,
        column.dop -> entity.dop
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblBuhOper)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblBuhOper).where.eq(column.code, entity.code) }.update.apply()
  }

}
