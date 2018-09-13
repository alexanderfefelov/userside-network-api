package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblOperNotify(
  code: Int,
  opercode: Option[Int] = None,
  dateadd: Option[DateTime] = None,
  typer: Option[Short] = None,
  opis: Option[String] = None,
  url: Option[String] = None,
  dateshow: Option[DateTime] = None,
  dop1: Option[String] = None) {

  def save()(implicit session: DBSession = PblOperNotify.autoSession): PblOperNotify = PblOperNotify.save(this)(session)

  def destroy()(implicit session: DBSession = PblOperNotify.autoSession): Int = PblOperNotify.destroy(this)(session)

}


object PblOperNotify extends SQLSyntaxSupport[PblOperNotify] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_oper_notify"

  override val columns = Seq("code", "opercode", "dateadd", "typer", "opis", "url", "dateshow", "dop1")

  def apply(pon: SyntaxProvider[PblOperNotify])(rs: WrappedResultSet): PblOperNotify = autoConstruct(rs, pon)
  def apply(pon: ResultName[PblOperNotify])(rs: WrappedResultSet): PblOperNotify = autoConstruct(rs, pon)

  val pon = PblOperNotify.syntax("pon")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblOperNotify] = {
    withSQL {
      select.from(PblOperNotify as pon).where.eq(pon.code, code)
    }.map(PblOperNotify(pon.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblOperNotify] = {
    withSQL(select.from(PblOperNotify as pon)).map(PblOperNotify(pon.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblOperNotify as pon)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblOperNotify] = {
    withSQL {
      select.from(PblOperNotify as pon).where.append(where)
    }.map(PblOperNotify(pon.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblOperNotify] = {
    withSQL {
      select.from(PblOperNotify as pon).where.append(where)
    }.map(PblOperNotify(pon.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblOperNotify as pon).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    opercode: Option[Int] = None,
    dateadd: Option[DateTime] = None,
    typer: Option[Short] = None,
    opis: Option[String] = None,
    url: Option[String] = None,
    dateshow: Option[DateTime] = None,
    dop1: Option[String] = None)(implicit session: DBSession = autoSession): PblOperNotify = {
    val generatedKey = withSQL {
      insert.into(PblOperNotify).namedValues(
        column.opercode -> opercode,
        column.dateadd -> dateadd,
        column.typer -> typer,
        column.opis -> opis,
        column.url -> url,
        column.dateshow -> dateshow,
        column.dop1 -> dop1
      )
    }.updateAndReturnGeneratedKey.apply()

    PblOperNotify(
      code = generatedKey.toInt,
      opercode = opercode,
      dateadd = dateadd,
      typer = typer,
      opis = opis,
      url = url,
      dateshow = dateshow,
      dop1 = dop1)
  }

  def batchInsert(entities: collection.Seq[PblOperNotify])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'opercode -> entity.opercode,
        'dateadd -> entity.dateadd,
        'typer -> entity.typer,
        'opis -> entity.opis,
        'url -> entity.url,
        'dateshow -> entity.dateshow,
        'dop1 -> entity.dop1))
    SQL("""insert into pbl_oper_notify(
      opercode,
      dateadd,
      typer,
      opis,
      url,
      dateshow,
      dop1
    ) values (
      {opercode},
      {dateadd},
      {typer},
      {opis},
      {url},
      {dateshow},
      {dop1}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblOperNotify)(implicit session: DBSession = autoSession): PblOperNotify = {
    withSQL {
      update(PblOperNotify).set(
        column.code -> entity.code,
        column.opercode -> entity.opercode,
        column.dateadd -> entity.dateadd,
        column.typer -> entity.typer,
        column.opis -> entity.opis,
        column.url -> entity.url,
        column.dateshow -> entity.dateshow,
        column.dop1 -> entity.dop1
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblOperNotify)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblOperNotify).where.eq(column.code, entity.code) }.update.apply()
  }

}
