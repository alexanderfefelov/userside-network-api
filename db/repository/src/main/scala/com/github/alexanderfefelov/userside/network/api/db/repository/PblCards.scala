package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblCards(
  code: Int,
  series: Option[Int] = None,
  cardno: Option[Int] = None,
  pin: Option[String] = None,
  nominal: Option[Int] = None,
  status: Option[Short] = None,
  dateadd: Option[DateTime] = None,
  dateact: Option[DateTime] = None,
  usercode: Option[Int] = None) {

  def save()(implicit session: DBSession = PblCards.autoSession): PblCards = PblCards.save(this)(session)

  def destroy()(implicit session: DBSession = PblCards.autoSession): Int = PblCards.destroy(this)(session)

}


object PblCards extends SQLSyntaxSupport[PblCards] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_cards"

  override val columns = Seq("code", "series", "cardno", "pin", "nominal", "status", "dateadd", "dateact", "usercode")

  def apply(pc: SyntaxProvider[PblCards])(rs: WrappedResultSet): PblCards = autoConstruct(rs, pc)
  def apply(pc: ResultName[PblCards])(rs: WrappedResultSet): PblCards = autoConstruct(rs, pc)

  val pc = PblCards.syntax("pc")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblCards] = {
    withSQL {
      select.from(PblCards as pc).where.eq(pc.code, code)
    }.map(PblCards(pc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblCards] = {
    withSQL(select.from(PblCards as pc)).map(PblCards(pc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblCards as pc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblCards] = {
    withSQL {
      select.from(PblCards as pc).where.append(where)
    }.map(PblCards(pc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblCards] = {
    withSQL {
      select.from(PblCards as pc).where.append(where)
    }.map(PblCards(pc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblCards as pc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    series: Option[Int] = None,
    cardno: Option[Int] = None,
    pin: Option[String] = None,
    nominal: Option[Int] = None,
    status: Option[Short] = None,
    dateadd: Option[DateTime] = None,
    dateact: Option[DateTime] = None,
    usercode: Option[Int] = None)(implicit session: DBSession = autoSession): PblCards = {
    val generatedKey = withSQL {
      insert.into(PblCards).namedValues(
        column.series -> series,
        column.cardno -> cardno,
        column.pin -> pin,
        column.nominal -> nominal,
        column.status -> status,
        column.dateadd -> dateadd,
        column.dateact -> dateact,
        column.usercode -> usercode
      )
    }.updateAndReturnGeneratedKey.apply()

    PblCards(
      code = generatedKey.toInt,
      series = series,
      cardno = cardno,
      pin = pin,
      nominal = nominal,
      status = status,
      dateadd = dateadd,
      dateact = dateact,
      usercode = usercode)
  }

  def batchInsert(entities: collection.Seq[PblCards])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'series -> entity.series,
        'cardno -> entity.cardno,
        'pin -> entity.pin,
        'nominal -> entity.nominal,
        'status -> entity.status,
        'dateadd -> entity.dateadd,
        'dateact -> entity.dateact,
        'usercode -> entity.usercode))
    SQL("""insert into pbl_cards(
      series,
      cardno,
      pin,
      nominal,
      status,
      dateadd,
      dateact,
      usercode
    ) values (
      {series},
      {cardno},
      {pin},
      {nominal},
      {status},
      {dateadd},
      {dateact},
      {usercode}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblCards)(implicit session: DBSession = autoSession): PblCards = {
    withSQL {
      update(PblCards).set(
        column.code -> entity.code,
        column.series -> entity.series,
        column.cardno -> entity.cardno,
        column.pin -> entity.pin,
        column.nominal -> entity.nominal,
        column.status -> entity.status,
        column.dateadd -> entity.dateadd,
        column.dateact -> entity.dateact,
        column.usercode -> entity.usercode
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblCards)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblCards).where.eq(column.code, entity.code) }.update.apply()
  }

}
