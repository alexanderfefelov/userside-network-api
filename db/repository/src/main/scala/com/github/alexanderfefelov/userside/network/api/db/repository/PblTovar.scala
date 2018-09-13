package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblTovar(
  code: Int,
  tovarcode: Option[Int] = None,
  count: Option[Int] = None,
  summa: Option[BigDecimal] = None,
  prodav: Option[Int] = None,
  sn: Option[String] = None,
  inv: Option[String] = None,
  shtrih: Option[String] = None,
  dop: Option[String] = None,
  operation: Option[String] = None,
  scet: Option[Long] = None,
  param: Option[String] = None,
  documentNumber: Option[String] = None,
  documentDate: Option[LocalDate] = None) {

  def save()(implicit session: DBSession = PblTovar.autoSession): PblTovar = PblTovar.save(this)(session)

  def destroy()(implicit session: DBSession = PblTovar.autoSession): Int = PblTovar.destroy(this)(session)

}


object PblTovar extends SQLSyntaxSupport[PblTovar] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_tovar"

  override val columns = Seq("code", "tovarcode", "count", "summa", "prodav", "sn", "inv", "shtrih", "dop", "operation", "scet", "param", "document_number", "document_date")

  def apply(pt: SyntaxProvider[PblTovar])(rs: WrappedResultSet): PblTovar = autoConstruct(rs, pt)
  def apply(pt: ResultName[PblTovar])(rs: WrappedResultSet): PblTovar = autoConstruct(rs, pt)

  val pt = PblTovar.syntax("pt")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblTovar] = {
    withSQL {
      select.from(PblTovar as pt).where.eq(pt.code, code)
    }.map(PblTovar(pt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblTovar] = {
    withSQL(select.from(PblTovar as pt)).map(PblTovar(pt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblTovar as pt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblTovar] = {
    withSQL {
      select.from(PblTovar as pt).where.append(where)
    }.map(PblTovar(pt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblTovar] = {
    withSQL {
      select.from(PblTovar as pt).where.append(where)
    }.map(PblTovar(pt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblTovar as pt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    tovarcode: Option[Int] = None,
    count: Option[Int] = None,
    summa: Option[BigDecimal] = None,
    prodav: Option[Int] = None,
    sn: Option[String] = None,
    inv: Option[String] = None,
    shtrih: Option[String] = None,
    dop: Option[String] = None,
    operation: Option[String] = None,
    scet: Option[Long] = None,
    param: Option[String] = None,
    documentNumber: Option[String] = None,
    documentDate: Option[LocalDate] = None)(implicit session: DBSession = autoSession): PblTovar = {
    val generatedKey = withSQL {
      insert.into(PblTovar).namedValues(
        column.tovarcode -> tovarcode,
        column.count -> count,
        column.summa -> summa,
        column.prodav -> prodav,
        column.sn -> sn,
        column.inv -> inv,
        column.shtrih -> shtrih,
        column.dop -> dop,
        column.operation -> operation,
        column.scet -> scet,
        column.param -> param,
        column.documentNumber -> documentNumber,
        column.documentDate -> documentDate
      )
    }.updateAndReturnGeneratedKey.apply()

    PblTovar(
      code = generatedKey.toInt,
      tovarcode = tovarcode,
      count = count,
      summa = summa,
      prodav = prodav,
      sn = sn,
      inv = inv,
      shtrih = shtrih,
      dop = dop,
      operation = operation,
      scet = scet,
      param = param,
      documentNumber = documentNumber,
      documentDate = documentDate)
  }

  def batchInsert(entities: collection.Seq[PblTovar])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'tovarcode -> entity.tovarcode,
        'count -> entity.count,
        'summa -> entity.summa,
        'prodav -> entity.prodav,
        'sn -> entity.sn,
        'inv -> entity.inv,
        'shtrih -> entity.shtrih,
        'dop -> entity.dop,
        'operation -> entity.operation,
        'scet -> entity.scet,
        'param -> entity.param,
        'documentNumber -> entity.documentNumber,
        'documentDate -> entity.documentDate))
    SQL("""insert into pbl_tovar(
      tovarcode,
      count,
      summa,
      prodav,
      sn,
      inv,
      shtrih,
      dop,
      operation,
      scet,
      param,
      document_number,
      document_date
    ) values (
      {tovarcode},
      {count},
      {summa},
      {prodav},
      {sn},
      {inv},
      {shtrih},
      {dop},
      {operation},
      {scet},
      {param},
      {documentNumber},
      {documentDate}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblTovar)(implicit session: DBSession = autoSession): PblTovar = {
    withSQL {
      update(PblTovar).set(
        column.code -> entity.code,
        column.tovarcode -> entity.tovarcode,
        column.count -> entity.count,
        column.summa -> entity.summa,
        column.prodav -> entity.prodav,
        column.sn -> entity.sn,
        column.inv -> entity.inv,
        column.shtrih -> entity.shtrih,
        column.dop -> entity.dop,
        column.operation -> entity.operation,
        column.scet -> entity.scet,
        column.param -> entity.param,
        column.documentNumber -> entity.documentNumber,
        column.documentDate -> entity.documentDate
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblTovar)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblTovar).where.eq(column.code, entity.code) }.update.apply()
  }

}
