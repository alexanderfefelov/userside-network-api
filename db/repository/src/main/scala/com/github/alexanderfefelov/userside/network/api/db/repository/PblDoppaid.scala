package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblDoppaid(
  code: Int,
  usercode: Option[Int] = None,
  paidcode: Option[Int] = None,
  dateadd: Option[DateTime] = None,
  summa: Option[BigDecimal] = None,
  opis: Option[String] = None) {

  def save()(implicit session: DBSession = PblDoppaid.autoSession): PblDoppaid = PblDoppaid.save(this)(session)

  def destroy()(implicit session: DBSession = PblDoppaid.autoSession): Int = PblDoppaid.destroy(this)(session)

}


object PblDoppaid extends SQLSyntaxSupport[PblDoppaid] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_doppaid"

  override val columns = Seq("code", "usercode", "paidcode", "dateadd", "summa", "opis")

  def apply(pd: SyntaxProvider[PblDoppaid])(rs: WrappedResultSet): PblDoppaid = autoConstruct(rs, pd)
  def apply(pd: ResultName[PblDoppaid])(rs: WrappedResultSet): PblDoppaid = autoConstruct(rs, pd)

  val pd = PblDoppaid.syntax("pd")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblDoppaid] = {
    withSQL {
      select.from(PblDoppaid as pd).where.eq(pd.code, code)
    }.map(PblDoppaid(pd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblDoppaid] = {
    withSQL(select.from(PblDoppaid as pd)).map(PblDoppaid(pd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblDoppaid as pd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblDoppaid] = {
    withSQL {
      select.from(PblDoppaid as pd).where.append(where)
    }.map(PblDoppaid(pd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblDoppaid] = {
    withSQL {
      select.from(PblDoppaid as pd).where.append(where)
    }.map(PblDoppaid(pd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblDoppaid as pd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    usercode: Option[Int] = None,
    paidcode: Option[Int] = None,
    dateadd: Option[DateTime] = None,
    summa: Option[BigDecimal] = None,
    opis: Option[String] = None)(implicit session: DBSession = autoSession): PblDoppaid = {
    val generatedKey = withSQL {
      insert.into(PblDoppaid).namedValues(
        column.usercode -> usercode,
        column.paidcode -> paidcode,
        column.dateadd -> dateadd,
        column.summa -> summa,
        column.opis -> opis
      )
    }.updateAndReturnGeneratedKey.apply()

    PblDoppaid(
      code = generatedKey.toInt,
      usercode = usercode,
      paidcode = paidcode,
      dateadd = dateadd,
      summa = summa,
      opis = opis)
  }

  def batchInsert(entities: collection.Seq[PblDoppaid])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'usercode -> entity.usercode,
        'paidcode -> entity.paidcode,
        'dateadd -> entity.dateadd,
        'summa -> entity.summa,
        'opis -> entity.opis))
    SQL("""insert into pbl_doppaid(
      usercode,
      paidcode,
      dateadd,
      summa,
      opis
    ) values (
      {usercode},
      {paidcode},
      {dateadd},
      {summa},
      {opis}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblDoppaid)(implicit session: DBSession = autoSession): PblDoppaid = {
    withSQL {
      update(PblDoppaid).set(
        column.code -> entity.code,
        column.usercode -> entity.usercode,
        column.paidcode -> entity.paidcode,
        column.dateadd -> entity.dateadd,
        column.summa -> entity.summa,
        column.opis -> entity.opis
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblDoppaid)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblDoppaid).where.eq(column.code, entity.code) }.update.apply()
  }

}
