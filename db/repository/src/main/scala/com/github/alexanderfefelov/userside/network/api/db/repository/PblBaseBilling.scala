package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblBaseBilling(
  code: Int,
  usercode: Option[Int] = None,
  billcode: Option[Int] = None,
  workstatus: Option[Short] = None,
  datepaid: Option[LocalDate] = None,
  groupn: Option[String] = None,
  balans: Option[BigDecimal] = None,
  comment: Option[String] = None) {

  def save()(implicit session: DBSession = PblBaseBilling.autoSession): PblBaseBilling = PblBaseBilling.save(this)(session)

  def destroy()(implicit session: DBSession = PblBaseBilling.autoSession): Int = PblBaseBilling.destroy(this)(session)

}


object PblBaseBilling extends SQLSyntaxSupport[PblBaseBilling] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_base_billing"

  override val columns = Seq("code", "usercode", "billcode", "workstatus", "datepaid", "groupn", "balans", "comment")

  def apply(pbb: SyntaxProvider[PblBaseBilling])(rs: WrappedResultSet): PblBaseBilling = autoConstruct(rs, pbb)
  def apply(pbb: ResultName[PblBaseBilling])(rs: WrappedResultSet): PblBaseBilling = autoConstruct(rs, pbb)

  val pbb = PblBaseBilling.syntax("pbb")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblBaseBilling] = {
    withSQL {
      select.from(PblBaseBilling as pbb).where.eq(pbb.code, code)
    }.map(PblBaseBilling(pbb.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblBaseBilling] = {
    withSQL(select.from(PblBaseBilling as pbb)).map(PblBaseBilling(pbb.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblBaseBilling as pbb)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblBaseBilling] = {
    withSQL {
      select.from(PblBaseBilling as pbb).where.append(where)
    }.map(PblBaseBilling(pbb.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblBaseBilling] = {
    withSQL {
      select.from(PblBaseBilling as pbb).where.append(where)
    }.map(PblBaseBilling(pbb.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblBaseBilling as pbb).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    usercode: Option[Int] = None,
    billcode: Option[Int] = None,
    workstatus: Option[Short] = None,
    datepaid: Option[LocalDate] = None,
    groupn: Option[String] = None,
    balans: Option[BigDecimal] = None,
    comment: Option[String] = None)(implicit session: DBSession = autoSession): PblBaseBilling = {
    val generatedKey = withSQL {
      insert.into(PblBaseBilling).namedValues(
        column.usercode -> usercode,
        column.billcode -> billcode,
        column.workstatus -> workstatus,
        column.datepaid -> datepaid,
        column.groupn -> groupn,
        column.balans -> balans,
        column.comment -> comment
      )
    }.updateAndReturnGeneratedKey.apply()

    PblBaseBilling(
      code = generatedKey.toInt,
      usercode = usercode,
      billcode = billcode,
      workstatus = workstatus,
      datepaid = datepaid,
      groupn = groupn,
      balans = balans,
      comment = comment)
  }

  def batchInsert(entities: collection.Seq[PblBaseBilling])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'usercode -> entity.usercode,
        'billcode -> entity.billcode,
        'workstatus -> entity.workstatus,
        'datepaid -> entity.datepaid,
        'groupn -> entity.groupn,
        'balans -> entity.balans,
        'comment -> entity.comment))
    SQL("""insert into pbl_base_billing(
      usercode,
      billcode,
      workstatus,
      datepaid,
      groupn,
      balans,
      comment
    ) values (
      {usercode},
      {billcode},
      {workstatus},
      {datepaid},
      {groupn},
      {balans},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblBaseBilling)(implicit session: DBSession = autoSession): PblBaseBilling = {
    withSQL {
      update(PblBaseBilling).set(
        column.code -> entity.code,
        column.usercode -> entity.usercode,
        column.billcode -> entity.billcode,
        column.workstatus -> entity.workstatus,
        column.datepaid -> entity.datepaid,
        column.groupn -> entity.groupn,
        column.balans -> entity.balans,
        column.comment -> entity.comment
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblBaseBilling)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblBaseBilling).where.eq(column.code, entity.code) }.update.apply()
  }

}
