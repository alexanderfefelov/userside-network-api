package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblTariffCustomerComment(
  code: Int,
  usercode: Option[Int] = None,
  billcode: Option[Int] = None,
  groupn: Option[String] = None,
  comment: Option[String] = None) {

  def save()(implicit session: DBSession = PblTariffCustomerComment.autoSession): PblTariffCustomerComment = PblTariffCustomerComment.save(this)(session)

  def destroy()(implicit session: DBSession = PblTariffCustomerComment.autoSession): Int = PblTariffCustomerComment.destroy(this)(session)

}


object PblTariffCustomerComment extends SQLSyntaxSupport[PblTariffCustomerComment] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_tariff_customer_comment"

  override val columns = Seq("code", "usercode", "billcode", "groupn", "comment")

  def apply(ptcc: SyntaxProvider[PblTariffCustomerComment])(rs: WrappedResultSet): PblTariffCustomerComment = autoConstruct(rs, ptcc)
  def apply(ptcc: ResultName[PblTariffCustomerComment])(rs: WrappedResultSet): PblTariffCustomerComment = autoConstruct(rs, ptcc)

  val ptcc = PblTariffCustomerComment.syntax("ptcc")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblTariffCustomerComment] = {
    withSQL {
      select.from(PblTariffCustomerComment as ptcc).where.eq(ptcc.code, code)
    }.map(PblTariffCustomerComment(ptcc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblTariffCustomerComment] = {
    withSQL(select.from(PblTariffCustomerComment as ptcc)).map(PblTariffCustomerComment(ptcc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblTariffCustomerComment as ptcc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblTariffCustomerComment] = {
    withSQL {
      select.from(PblTariffCustomerComment as ptcc).where.append(where)
    }.map(PblTariffCustomerComment(ptcc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblTariffCustomerComment] = {
    withSQL {
      select.from(PblTariffCustomerComment as ptcc).where.append(where)
    }.map(PblTariffCustomerComment(ptcc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblTariffCustomerComment as ptcc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    usercode: Option[Int] = None,
    billcode: Option[Int] = None,
    groupn: Option[String] = None,
    comment: Option[String] = None)(implicit session: DBSession = autoSession): PblTariffCustomerComment = {
    val generatedKey = withSQL {
      insert.into(PblTariffCustomerComment).namedValues(
        column.usercode -> usercode,
        column.billcode -> billcode,
        column.groupn -> groupn,
        column.comment -> comment
      )
    }.updateAndReturnGeneratedKey.apply()

    PblTariffCustomerComment(
      code = generatedKey.toInt,
      usercode = usercode,
      billcode = billcode,
      groupn = groupn,
      comment = comment)
  }

  def batchInsert(entities: collection.Seq[PblTariffCustomerComment])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'usercode -> entity.usercode,
        'billcode -> entity.billcode,
        'groupn -> entity.groupn,
        'comment -> entity.comment))
    SQL("""insert into pbl_tariff_customer_comment(
      usercode,
      billcode,
      groupn,
      comment
    ) values (
      {usercode},
      {billcode},
      {groupn},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblTariffCustomerComment)(implicit session: DBSession = autoSession): PblTariffCustomerComment = {
    withSQL {
      update(PblTariffCustomerComment).set(
        column.code -> entity.code,
        column.usercode -> entity.usercode,
        column.billcode -> entity.billcode,
        column.groupn -> entity.groupn,
        column.comment -> entity.comment
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblTariffCustomerComment)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblTariffCustomerComment).where.eq(column.code, entity.code) }.update.apply()
  }

}
