package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblJek(
  code: Int,
  nazv: Option[String] = None,
  adr: Option[String] = None,
  dirfio: Option[String] = None,
  tel: Option[String] = None,
  dognumber: Option[String] = None,
  dogdata: Option[LocalDate] = None,
  rekv: Option[String] = None,
  dop: Option[String] = None,
  summa: Option[BigDecimal] = None) {

  def save()(implicit session: DBSession = PblJek.autoSession): PblJek = PblJek.save(this)(session)

  def destroy()(implicit session: DBSession = PblJek.autoSession): Int = PblJek.destroy(this)(session)

}


object PblJek extends SQLSyntaxSupport[PblJek] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_jek"

  override val columns = Seq("code", "nazv", "adr", "dirfio", "tel", "dognumber", "dogdata", "rekv", "dop", "summa")

  def apply(pj: SyntaxProvider[PblJek])(rs: WrappedResultSet): PblJek = autoConstruct(rs, pj)
  def apply(pj: ResultName[PblJek])(rs: WrappedResultSet): PblJek = autoConstruct(rs, pj)

  val pj = PblJek.syntax("pj")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblJek] = {
    withSQL {
      select.from(PblJek as pj).where.eq(pj.code, code)
    }.map(PblJek(pj.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblJek] = {
    withSQL(select.from(PblJek as pj)).map(PblJek(pj.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblJek as pj)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblJek] = {
    withSQL {
      select.from(PblJek as pj).where.append(where)
    }.map(PblJek(pj.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblJek] = {
    withSQL {
      select.from(PblJek as pj).where.append(where)
    }.map(PblJek(pj.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblJek as pj).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    nazv: Option[String] = None,
    adr: Option[String] = None,
    dirfio: Option[String] = None,
    tel: Option[String] = None,
    dognumber: Option[String] = None,
    dogdata: Option[LocalDate] = None,
    rekv: Option[String] = None,
    dop: Option[String] = None,
    summa: Option[BigDecimal] = None)(implicit session: DBSession = autoSession): PblJek = {
    val generatedKey = withSQL {
      insert.into(PblJek).namedValues(
        column.nazv -> nazv,
        column.adr -> adr,
        column.dirfio -> dirfio,
        column.tel -> tel,
        column.dognumber -> dognumber,
        column.dogdata -> dogdata,
        column.rekv -> rekv,
        column.dop -> dop,
        column.summa -> summa
      )
    }.updateAndReturnGeneratedKey.apply()

    PblJek(
      code = generatedKey.toInt,
      nazv = nazv,
      adr = adr,
      dirfio = dirfio,
      tel = tel,
      dognumber = dognumber,
      dogdata = dogdata,
      rekv = rekv,
      dop = dop,
      summa = summa)
  }

  def batchInsert(entities: collection.Seq[PblJek])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'nazv -> entity.nazv,
        'adr -> entity.adr,
        'dirfio -> entity.dirfio,
        'tel -> entity.tel,
        'dognumber -> entity.dognumber,
        'dogdata -> entity.dogdata,
        'rekv -> entity.rekv,
        'dop -> entity.dop,
        'summa -> entity.summa))
    SQL("""insert into pbl_jek(
      nazv,
      adr,
      dirfio,
      tel,
      dognumber,
      dogdata,
      rekv,
      dop,
      summa
    ) values (
      {nazv},
      {adr},
      {dirfio},
      {tel},
      {dognumber},
      {dogdata},
      {rekv},
      {dop},
      {summa}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblJek)(implicit session: DBSession = autoSession): PblJek = {
    withSQL {
      update(PblJek).set(
        column.code -> entity.code,
        column.nazv -> entity.nazv,
        column.adr -> entity.adr,
        column.dirfio -> entity.dirfio,
        column.tel -> entity.tel,
        column.dognumber -> entity.dognumber,
        column.dogdata -> entity.dogdata,
        column.rekv -> entity.rekv,
        column.dop -> entity.dop,
        column.summa -> entity.summa
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblJek)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblJek).where.eq(column.code, entity.code) }.update.apply()
  }

}
