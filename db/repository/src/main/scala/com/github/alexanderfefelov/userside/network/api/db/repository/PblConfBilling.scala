package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblConfBilling(
  code: Int,
  billcode: Option[Int] = None,
  nazv: Option[String] = None,
  typer: Option[Short] = None,
  istraf: Option[Short] = None,
  isupdfio: Option[Short] = None,
  isupdtel: Option[Short] = None,
  isupdadr: Option[Short] = None,
  isupdmac: Option[Short] = None,
  ismt: Option[Short] = None,
  lastupdate: Option[DateTime] = None,
  dateTrafficUpdate: Option[DateTime] = None,
  isApiCustomerCardRead: Option[Short] = None) {

  def save()(implicit session: DBSession = PblConfBilling.autoSession): PblConfBilling = PblConfBilling.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfBilling.autoSession): Int = PblConfBilling.destroy(this)(session)

}


object PblConfBilling extends SQLSyntaxSupport[PblConfBilling] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_billing"

  override val columns = Seq("code", "billcode", "nazv", "typer", "istraf", "isupdfio", "isupdtel", "isupdadr", "isupdmac", "ismt", "lastupdate", "date_traffic_update", "is_api_customer_card_read")

  def apply(pcb: SyntaxProvider[PblConfBilling])(rs: WrappedResultSet): PblConfBilling = autoConstruct(rs, pcb)
  def apply(pcb: ResultName[PblConfBilling])(rs: WrappedResultSet): PblConfBilling = autoConstruct(rs, pcb)

  val pcb = PblConfBilling.syntax("pcb")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfBilling] = {
    withSQL {
      select.from(PblConfBilling as pcb).where.eq(pcb.code, code)
    }.map(PblConfBilling(pcb.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfBilling] = {
    withSQL(select.from(PblConfBilling as pcb)).map(PblConfBilling(pcb.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfBilling as pcb)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfBilling] = {
    withSQL {
      select.from(PblConfBilling as pcb).where.append(where)
    }.map(PblConfBilling(pcb.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfBilling] = {
    withSQL {
      select.from(PblConfBilling as pcb).where.append(where)
    }.map(PblConfBilling(pcb.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfBilling as pcb).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    billcode: Option[Int] = None,
    nazv: Option[String] = None,
    typer: Option[Short] = None,
    istraf: Option[Short] = None,
    isupdfio: Option[Short] = None,
    isupdtel: Option[Short] = None,
    isupdadr: Option[Short] = None,
    isupdmac: Option[Short] = None,
    ismt: Option[Short] = None,
    lastupdate: Option[DateTime] = None,
    dateTrafficUpdate: Option[DateTime] = None,
    isApiCustomerCardRead: Option[Short] = None)(implicit session: DBSession = autoSession): PblConfBilling = {
    val generatedKey = withSQL {
      insert.into(PblConfBilling).namedValues(
        column.billcode -> billcode,
        column.nazv -> nazv,
        column.typer -> typer,
        column.istraf -> istraf,
        column.isupdfio -> isupdfio,
        column.isupdtel -> isupdtel,
        column.isupdadr -> isupdadr,
        column.isupdmac -> isupdmac,
        column.ismt -> ismt,
        column.lastupdate -> lastupdate,
        column.dateTrafficUpdate -> dateTrafficUpdate,
        column.isApiCustomerCardRead -> isApiCustomerCardRead
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfBilling(
      code = generatedKey.toInt,
      billcode = billcode,
      nazv = nazv,
      typer = typer,
      istraf = istraf,
      isupdfio = isupdfio,
      isupdtel = isupdtel,
      isupdadr = isupdadr,
      isupdmac = isupdmac,
      ismt = ismt,
      lastupdate = lastupdate,
      dateTrafficUpdate = dateTrafficUpdate,
      isApiCustomerCardRead = isApiCustomerCardRead)
  }

  def batchInsert(entities: collection.Seq[PblConfBilling])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'billcode -> entity.billcode,
        'nazv -> entity.nazv,
        'typer -> entity.typer,
        'istraf -> entity.istraf,
        'isupdfio -> entity.isupdfio,
        'isupdtel -> entity.isupdtel,
        'isupdadr -> entity.isupdadr,
        'isupdmac -> entity.isupdmac,
        'ismt -> entity.ismt,
        'lastupdate -> entity.lastupdate,
        'dateTrafficUpdate -> entity.dateTrafficUpdate,
        'isApiCustomerCardRead -> entity.isApiCustomerCardRead))
    SQL("""insert into pbl_conf_billing(
      billcode,
      nazv,
      typer,
      istraf,
      isupdfio,
      isupdtel,
      isupdadr,
      isupdmac,
      ismt,
      lastupdate,
      date_traffic_update,
      is_api_customer_card_read
    ) values (
      {billcode},
      {nazv},
      {typer},
      {istraf},
      {isupdfio},
      {isupdtel},
      {isupdadr},
      {isupdmac},
      {ismt},
      {lastupdate},
      {dateTrafficUpdate},
      {isApiCustomerCardRead}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfBilling)(implicit session: DBSession = autoSession): PblConfBilling = {
    withSQL {
      update(PblConfBilling).set(
        column.code -> entity.code,
        column.billcode -> entity.billcode,
        column.nazv -> entity.nazv,
        column.typer -> entity.typer,
        column.istraf -> entity.istraf,
        column.isupdfio -> entity.isupdfio,
        column.isupdtel -> entity.isupdtel,
        column.isupdadr -> entity.isupdadr,
        column.isupdmac -> entity.isupdmac,
        column.ismt -> entity.ismt,
        column.lastupdate -> entity.lastupdate,
        column.dateTrafficUpdate -> entity.dateTrafficUpdate,
        column.isApiCustomerCardRead -> entity.isApiCustomerCardRead
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfBilling)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfBilling).where.eq(column.code, entity.code) }.update.apply()
  }

}
