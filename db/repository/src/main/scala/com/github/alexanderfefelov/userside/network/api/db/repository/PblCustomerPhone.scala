package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblCustomerPhone(
  id: Int,
  customerId: Option[Int] = None,
  phone: Option[String] = None,
  isMain: Option[Short] = None,
  comment: Option[String] = None) {

  def save()(implicit session: DBSession = PblCustomerPhone.autoSession): PblCustomerPhone = PblCustomerPhone.save(this)(session)

  def destroy()(implicit session: DBSession = PblCustomerPhone.autoSession): Int = PblCustomerPhone.destroy(this)(session)

}


object PblCustomerPhone extends SQLSyntaxSupport[PblCustomerPhone] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_customer_phone"

  override val columns = Seq("id", "customer_id", "phone", "is_main", "comment")

  def apply(pcp: SyntaxProvider[PblCustomerPhone])(rs: WrappedResultSet): PblCustomerPhone = autoConstruct(rs, pcp)
  def apply(pcp: ResultName[PblCustomerPhone])(rs: WrappedResultSet): PblCustomerPhone = autoConstruct(rs, pcp)

  val pcp = PblCustomerPhone.syntax("pcp")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblCustomerPhone] = {
    withSQL {
      select.from(PblCustomerPhone as pcp).where.eq(pcp.id, id)
    }.map(PblCustomerPhone(pcp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblCustomerPhone] = {
    withSQL(select.from(PblCustomerPhone as pcp)).map(PblCustomerPhone(pcp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblCustomerPhone as pcp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblCustomerPhone] = {
    withSQL {
      select.from(PblCustomerPhone as pcp).where.append(where)
    }.map(PblCustomerPhone(pcp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblCustomerPhone] = {
    withSQL {
      select.from(PblCustomerPhone as pcp).where.append(where)
    }.map(PblCustomerPhone(pcp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblCustomerPhone as pcp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    customerId: Option[Int] = None,
    phone: Option[String] = None,
    isMain: Option[Short] = None,
    comment: Option[String] = None)(implicit session: DBSession = autoSession): PblCustomerPhone = {
    val generatedKey = withSQL {
      insert.into(PblCustomerPhone).namedValues(
        column.customerId -> customerId,
        column.phone -> phone,
        column.isMain -> isMain,
        column.comment -> comment
      )
    }.updateAndReturnGeneratedKey.apply()

    PblCustomerPhone(
      id = generatedKey.toInt,
      customerId = customerId,
      phone = phone,
      isMain = isMain,
      comment = comment)
  }

  def batchInsert(entities: collection.Seq[PblCustomerPhone])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'customerId -> entity.customerId,
        'phone -> entity.phone,
        'isMain -> entity.isMain,
        'comment -> entity.comment))
    SQL("""insert into pbl_customer_phone(
      customer_id,
      phone,
      is_main,
      comment
    ) values (
      {customerId},
      {phone},
      {isMain},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblCustomerPhone)(implicit session: DBSession = autoSession): PblCustomerPhone = {
    withSQL {
      update(PblCustomerPhone).set(
        column.id -> entity.id,
        column.customerId -> entity.customerId,
        column.phone -> entity.phone,
        column.isMain -> entity.isMain,
        column.comment -> entity.comment
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblCustomerPhone)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblCustomerPhone).where.eq(column.id, entity.id) }.update.apply()
  }

}
