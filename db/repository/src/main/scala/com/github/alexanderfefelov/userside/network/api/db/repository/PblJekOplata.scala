package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblJekOplata(
  code: Int,
  jekcode: Option[Int] = None,
  summa: Option[BigDecimal] = None,
  datedo: Option[DateTime] = None,
  opis: Option[String] = None,
  datetoopl: Option[DateTime] = None) {

  def save()(implicit session: DBSession = PblJekOplata.autoSession): PblJekOplata = PblJekOplata.save(this)(session)

  def destroy()(implicit session: DBSession = PblJekOplata.autoSession): Int = PblJekOplata.destroy(this)(session)

}


object PblJekOplata extends SQLSyntaxSupport[PblJekOplata] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_jek_oplata"

  override val columns = Seq("code", "jekcode", "summa", "datedo", "opis", "datetoopl")

  def apply(pjo: SyntaxProvider[PblJekOplata])(rs: WrappedResultSet): PblJekOplata = autoConstruct(rs, pjo)
  def apply(pjo: ResultName[PblJekOplata])(rs: WrappedResultSet): PblJekOplata = autoConstruct(rs, pjo)

  val pjo = PblJekOplata.syntax("pjo")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblJekOplata] = {
    withSQL {
      select.from(PblJekOplata as pjo).where.eq(pjo.code, code)
    }.map(PblJekOplata(pjo.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblJekOplata] = {
    withSQL(select.from(PblJekOplata as pjo)).map(PblJekOplata(pjo.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblJekOplata as pjo)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblJekOplata] = {
    withSQL {
      select.from(PblJekOplata as pjo).where.append(where)
    }.map(PblJekOplata(pjo.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblJekOplata] = {
    withSQL {
      select.from(PblJekOplata as pjo).where.append(where)
    }.map(PblJekOplata(pjo.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblJekOplata as pjo).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    jekcode: Option[Int] = None,
    summa: Option[BigDecimal] = None,
    datedo: Option[DateTime] = None,
    opis: Option[String] = None,
    datetoopl: Option[DateTime] = None)(implicit session: DBSession = autoSession): PblJekOplata = {
    val generatedKey = withSQL {
      insert.into(PblJekOplata).namedValues(
        column.jekcode -> jekcode,
        column.summa -> summa,
        column.datedo -> datedo,
        column.opis -> opis,
        column.datetoopl -> datetoopl
      )
    }.updateAndReturnGeneratedKey.apply()

    PblJekOplata(
      code = generatedKey.toInt,
      jekcode = jekcode,
      summa = summa,
      datedo = datedo,
      opis = opis,
      datetoopl = datetoopl)
  }

  def batchInsert(entities: collection.Seq[PblJekOplata])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'jekcode -> entity.jekcode,
        'summa -> entity.summa,
        'datedo -> entity.datedo,
        'opis -> entity.opis,
        'datetoopl -> entity.datetoopl))
    SQL("""insert into pbl_jek_oplata(
      jekcode,
      summa,
      datedo,
      opis,
      datetoopl
    ) values (
      {jekcode},
      {summa},
      {datedo},
      {opis},
      {datetoopl}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblJekOplata)(implicit session: DBSession = autoSession): PblJekOplata = {
    withSQL {
      update(PblJekOplata).set(
        column.code -> entity.code,
        column.jekcode -> entity.jekcode,
        column.summa -> entity.summa,
        column.datedo -> entity.datedo,
        column.opis -> entity.opis,
        column.datetoopl -> entity.datetoopl
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblJekOplata)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblJekOplata).where.eq(column.code, entity.code) }.update.apply()
  }

}
