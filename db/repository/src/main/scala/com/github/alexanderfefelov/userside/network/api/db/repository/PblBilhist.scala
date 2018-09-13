package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblBilhist(
  code: Int,
  usercode: Option[Int] = None,
  datedo: Option[DateTime] = None,
  oper: Option[Int] = None,
  summa: Option[BigDecimal] = None,
  doing: Option[String] = None,
  pko: Option[Int] = None,
  oldbalans: Option[BigDecimal] = None,
  billdata: Option[String] = None,
  paidtyper: Option[Int] = None,
  billingId: Option[Int] = None) {

  def save()(implicit session: DBSession = PblBilhist.autoSession): PblBilhist = PblBilhist.save(this)(session)

  def destroy()(implicit session: DBSession = PblBilhist.autoSession): Int = PblBilhist.destroy(this)(session)

}


object PblBilhist extends SQLSyntaxSupport[PblBilhist] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_bilhist"

  override val columns = Seq("code", "usercode", "datedo", "oper", "summa", "doing", "pko", "oldbalans", "billdata", "paidtyper", "billing_id")

  def apply(pb: SyntaxProvider[PblBilhist])(rs: WrappedResultSet): PblBilhist = autoConstruct(rs, pb)
  def apply(pb: ResultName[PblBilhist])(rs: WrappedResultSet): PblBilhist = autoConstruct(rs, pb)

  val pb = PblBilhist.syntax("pb")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblBilhist] = {
    withSQL {
      select.from(PblBilhist as pb).where.eq(pb.code, code)
    }.map(PblBilhist(pb.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblBilhist] = {
    withSQL(select.from(PblBilhist as pb)).map(PblBilhist(pb.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblBilhist as pb)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblBilhist] = {
    withSQL {
      select.from(PblBilhist as pb).where.append(where)
    }.map(PblBilhist(pb.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblBilhist] = {
    withSQL {
      select.from(PblBilhist as pb).where.append(where)
    }.map(PblBilhist(pb.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblBilhist as pb).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    usercode: Option[Int] = None,
    datedo: Option[DateTime] = None,
    oper: Option[Int] = None,
    summa: Option[BigDecimal] = None,
    doing: Option[String] = None,
    pko: Option[Int] = None,
    oldbalans: Option[BigDecimal] = None,
    billdata: Option[String] = None,
    paidtyper: Option[Int] = None,
    billingId: Option[Int] = None)(implicit session: DBSession = autoSession): PblBilhist = {
    val generatedKey = withSQL {
      insert.into(PblBilhist).namedValues(
        column.usercode -> usercode,
        column.datedo -> datedo,
        column.oper -> oper,
        column.summa -> summa,
        column.doing -> doing,
        column.pko -> pko,
        column.oldbalans -> oldbalans,
        column.billdata -> billdata,
        column.paidtyper -> paidtyper,
        column.billingId -> billingId
      )
    }.updateAndReturnGeneratedKey.apply()

    PblBilhist(
      code = generatedKey.toInt,
      usercode = usercode,
      datedo = datedo,
      oper = oper,
      summa = summa,
      doing = doing,
      pko = pko,
      oldbalans = oldbalans,
      billdata = billdata,
      paidtyper = paidtyper,
      billingId = billingId)
  }

  def batchInsert(entities: collection.Seq[PblBilhist])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'usercode -> entity.usercode,
        'datedo -> entity.datedo,
        'oper -> entity.oper,
        'summa -> entity.summa,
        'doing -> entity.doing,
        'pko -> entity.pko,
        'oldbalans -> entity.oldbalans,
        'billdata -> entity.billdata,
        'paidtyper -> entity.paidtyper,
        'billingId -> entity.billingId))
    SQL("""insert into pbl_bilhist(
      usercode,
      datedo,
      oper,
      summa,
      doing,
      pko,
      oldbalans,
      billdata,
      paidtyper,
      billing_id
    ) values (
      {usercode},
      {datedo},
      {oper},
      {summa},
      {doing},
      {pko},
      {oldbalans},
      {billdata},
      {paidtyper},
      {billingId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblBilhist)(implicit session: DBSession = autoSession): PblBilhist = {
    withSQL {
      update(PblBilhist).set(
        column.code -> entity.code,
        column.usercode -> entity.usercode,
        column.datedo -> entity.datedo,
        column.oper -> entity.oper,
        column.summa -> entity.summa,
        column.doing -> entity.doing,
        column.pko -> entity.pko,
        column.oldbalans -> entity.oldbalans,
        column.billdata -> entity.billdata,
        column.paidtyper -> entity.paidtyper,
        column.billingId -> entity.billingId
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblBilhist)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblBilhist).where.eq(column.code, entity.code) }.update.apply()
  }

}
