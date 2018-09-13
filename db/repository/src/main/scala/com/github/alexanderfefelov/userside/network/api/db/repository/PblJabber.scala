package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblJabber(
  code: Int,
  datedo: Option[DateTime] = None,
  opercode: Option[Int] = None,
  perscode: Option[Int] = None,
  receiver: Option[String] = None,
  opis: Option[String] = None,
  par1: Option[Int] = None,
  par2: Option[Int] = None,
  issend: Option[Short] = None) {

  def save()(implicit session: DBSession = PblJabber.autoSession): PblJabber = PblJabber.save(this)(session)

  def destroy()(implicit session: DBSession = PblJabber.autoSession): Int = PblJabber.destroy(this)(session)

}


object PblJabber extends SQLSyntaxSupport[PblJabber] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_jabber"

  override val columns = Seq("code", "datedo", "opercode", "perscode", "receiver", "opis", "par1", "par2", "issend")

  def apply(pj: SyntaxProvider[PblJabber])(rs: WrappedResultSet): PblJabber = autoConstruct(rs, pj)
  def apply(pj: ResultName[PblJabber])(rs: WrappedResultSet): PblJabber = autoConstruct(rs, pj)

  val pj = PblJabber.syntax("pj")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblJabber] = {
    withSQL {
      select.from(PblJabber as pj).where.eq(pj.code, code)
    }.map(PblJabber(pj.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblJabber] = {
    withSQL(select.from(PblJabber as pj)).map(PblJabber(pj.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblJabber as pj)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblJabber] = {
    withSQL {
      select.from(PblJabber as pj).where.append(where)
    }.map(PblJabber(pj.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblJabber] = {
    withSQL {
      select.from(PblJabber as pj).where.append(where)
    }.map(PblJabber(pj.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblJabber as pj).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    datedo: Option[DateTime] = None,
    opercode: Option[Int] = None,
    perscode: Option[Int] = None,
    receiver: Option[String] = None,
    opis: Option[String] = None,
    par1: Option[Int] = None,
    par2: Option[Int] = None,
    issend: Option[Short] = None)(implicit session: DBSession = autoSession): PblJabber = {
    val generatedKey = withSQL {
      insert.into(PblJabber).namedValues(
        column.datedo -> datedo,
        column.opercode -> opercode,
        column.perscode -> perscode,
        column.receiver -> receiver,
        column.opis -> opis,
        column.par1 -> par1,
        column.par2 -> par2,
        column.issend -> issend
      )
    }.updateAndReturnGeneratedKey.apply()

    PblJabber(
      code = generatedKey.toInt,
      datedo = datedo,
      opercode = opercode,
      perscode = perscode,
      receiver = receiver,
      opis = opis,
      par1 = par1,
      par2 = par2,
      issend = issend)
  }

  def batchInsert(entities: collection.Seq[PblJabber])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'datedo -> entity.datedo,
        'opercode -> entity.opercode,
        'perscode -> entity.perscode,
        'receiver -> entity.receiver,
        'opis -> entity.opis,
        'par1 -> entity.par1,
        'par2 -> entity.par2,
        'issend -> entity.issend))
    SQL("""insert into pbl_jabber(
      datedo,
      opercode,
      perscode,
      receiver,
      opis,
      par1,
      par2,
      issend
    ) values (
      {datedo},
      {opercode},
      {perscode},
      {receiver},
      {opis},
      {par1},
      {par2},
      {issend}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblJabber)(implicit session: DBSession = autoSession): PblJabber = {
    withSQL {
      update(PblJabber).set(
        column.code -> entity.code,
        column.datedo -> entity.datedo,
        column.opercode -> entity.opercode,
        column.perscode -> entity.perscode,
        column.receiver -> entity.receiver,
        column.opis -> entity.opis,
        column.par1 -> entity.par1,
        column.par2 -> entity.par2,
        column.issend -> entity.issend
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblJabber)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblJabber).where.eq(column.code, entity.code) }.update.apply()
  }

}
