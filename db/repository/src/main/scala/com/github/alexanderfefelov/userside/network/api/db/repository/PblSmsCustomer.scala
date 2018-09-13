package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblSmsCustomer(
  id: Int,
  smsId: Option[Int] = None,
  customerId: Option[Int] = None) {

  def save()(implicit session: DBSession = PblSmsCustomer.autoSession): PblSmsCustomer = PblSmsCustomer.save(this)(session)

  def destroy()(implicit session: DBSession = PblSmsCustomer.autoSession): Int = PblSmsCustomer.destroy(this)(session)

}


object PblSmsCustomer extends SQLSyntaxSupport[PblSmsCustomer] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_sms_customer"

  override val columns = Seq("id", "sms_id", "customer_id")

  def apply(psc: SyntaxProvider[PblSmsCustomer])(rs: WrappedResultSet): PblSmsCustomer = autoConstruct(rs, psc)
  def apply(psc: ResultName[PblSmsCustomer])(rs: WrappedResultSet): PblSmsCustomer = autoConstruct(rs, psc)

  val psc = PblSmsCustomer.syntax("psc")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblSmsCustomer] = {
    withSQL {
      select.from(PblSmsCustomer as psc).where.eq(psc.id, id)
    }.map(PblSmsCustomer(psc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblSmsCustomer] = {
    withSQL(select.from(PblSmsCustomer as psc)).map(PblSmsCustomer(psc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblSmsCustomer as psc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblSmsCustomer] = {
    withSQL {
      select.from(PblSmsCustomer as psc).where.append(where)
    }.map(PblSmsCustomer(psc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblSmsCustomer] = {
    withSQL {
      select.from(PblSmsCustomer as psc).where.append(where)
    }.map(PblSmsCustomer(psc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblSmsCustomer as psc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    smsId: Option[Int] = None,
    customerId: Option[Int] = None)(implicit session: DBSession = autoSession): PblSmsCustomer = {
    val generatedKey = withSQL {
      insert.into(PblSmsCustomer).namedValues(
        column.smsId -> smsId,
        column.customerId -> customerId
      )
    }.updateAndReturnGeneratedKey.apply()

    PblSmsCustomer(
      id = generatedKey.toInt,
      smsId = smsId,
      customerId = customerId)
  }

  def batchInsert(entities: collection.Seq[PblSmsCustomer])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'smsId -> entity.smsId,
        'customerId -> entity.customerId))
    SQL("""insert into pbl_sms_customer(
      sms_id,
      customer_id
    ) values (
      {smsId},
      {customerId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblSmsCustomer)(implicit session: DBSession = autoSession): PblSmsCustomer = {
    withSQL {
      update(PblSmsCustomer).set(
        column.id -> entity.id,
        column.smsId -> entity.smsId,
        column.customerId -> entity.customerId
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblSmsCustomer)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblSmsCustomer).where.eq(column.id, entity.id) }.update.apply()
  }

}
