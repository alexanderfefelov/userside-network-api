package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblAdvertCustomer(
  id: Int,
  advertId: Option[Int] = None,
  customerId: Option[Int] = None,
  dateAdd: Option[DateTime] = None) {

  def save()(implicit session: DBSession = PblAdvertCustomer.autoSession): PblAdvertCustomer = PblAdvertCustomer.save(this)(session)

  def destroy()(implicit session: DBSession = PblAdvertCustomer.autoSession): Int = PblAdvertCustomer.destroy(this)(session)

}


object PblAdvertCustomer extends SQLSyntaxSupport[PblAdvertCustomer] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_advert_customer"

  override val columns = Seq("id", "advert_id", "customer_id", "date_add")

  def apply(pac: SyntaxProvider[PblAdvertCustomer])(rs: WrappedResultSet): PblAdvertCustomer = autoConstruct(rs, pac)
  def apply(pac: ResultName[PblAdvertCustomer])(rs: WrappedResultSet): PblAdvertCustomer = autoConstruct(rs, pac)

  val pac = PblAdvertCustomer.syntax("pac")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblAdvertCustomer] = {
    withSQL {
      select.from(PblAdvertCustomer as pac).where.eq(pac.id, id)
    }.map(PblAdvertCustomer(pac.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblAdvertCustomer] = {
    withSQL(select.from(PblAdvertCustomer as pac)).map(PblAdvertCustomer(pac.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblAdvertCustomer as pac)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblAdvertCustomer] = {
    withSQL {
      select.from(PblAdvertCustomer as pac).where.append(where)
    }.map(PblAdvertCustomer(pac.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblAdvertCustomer] = {
    withSQL {
      select.from(PblAdvertCustomer as pac).where.append(where)
    }.map(PblAdvertCustomer(pac.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblAdvertCustomer as pac).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    advertId: Option[Int] = None,
    customerId: Option[Int] = None,
    dateAdd: Option[DateTime] = None)(implicit session: DBSession = autoSession): PblAdvertCustomer = {
    val generatedKey = withSQL {
      insert.into(PblAdvertCustomer).namedValues(
        column.advertId -> advertId,
        column.customerId -> customerId,
        column.dateAdd -> dateAdd
      )
    }.updateAndReturnGeneratedKey.apply()

    PblAdvertCustomer(
      id = generatedKey.toInt,
      advertId = advertId,
      customerId = customerId,
      dateAdd = dateAdd)
  }

  def batchInsert(entities: collection.Seq[PblAdvertCustomer])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'advertId -> entity.advertId,
        'customerId -> entity.customerId,
        'dateAdd -> entity.dateAdd))
    SQL("""insert into pbl_advert_customer(
      advert_id,
      customer_id,
      date_add
    ) values (
      {advertId},
      {customerId},
      {dateAdd}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblAdvertCustomer)(implicit session: DBSession = autoSession): PblAdvertCustomer = {
    withSQL {
      update(PblAdvertCustomer).set(
        column.id -> entity.id,
        column.advertId -> entity.advertId,
        column.customerId -> entity.customerId,
        column.dateAdd -> entity.dateAdd
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblAdvertCustomer)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblAdvertCustomer).where.eq(column.id, entity.id) }.update.apply()
  }

}
