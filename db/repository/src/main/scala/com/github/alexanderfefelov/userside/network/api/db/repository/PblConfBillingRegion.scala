package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfBillingRegion(
  code: Int,
  billcode: Option[Int] = None,
  regioncode: Option[Int] = None) {

  def save()(implicit session: DBSession = PblConfBillingRegion.autoSession): PblConfBillingRegion = PblConfBillingRegion.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfBillingRegion.autoSession): Int = PblConfBillingRegion.destroy(this)(session)

}


object PblConfBillingRegion extends SQLSyntaxSupport[PblConfBillingRegion] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_billing_region"

  override val columns = Seq("code", "billcode", "regioncode")

  def apply(pcbr: SyntaxProvider[PblConfBillingRegion])(rs: WrappedResultSet): PblConfBillingRegion = autoConstruct(rs, pcbr)
  def apply(pcbr: ResultName[PblConfBillingRegion])(rs: WrappedResultSet): PblConfBillingRegion = autoConstruct(rs, pcbr)

  val pcbr = PblConfBillingRegion.syntax("pcbr")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfBillingRegion] = {
    withSQL {
      select.from(PblConfBillingRegion as pcbr).where.eq(pcbr.code, code)
    }.map(PblConfBillingRegion(pcbr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfBillingRegion] = {
    withSQL(select.from(PblConfBillingRegion as pcbr)).map(PblConfBillingRegion(pcbr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfBillingRegion as pcbr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfBillingRegion] = {
    withSQL {
      select.from(PblConfBillingRegion as pcbr).where.append(where)
    }.map(PblConfBillingRegion(pcbr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfBillingRegion] = {
    withSQL {
      select.from(PblConfBillingRegion as pcbr).where.append(where)
    }.map(PblConfBillingRegion(pcbr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfBillingRegion as pcbr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    billcode: Option[Int] = None,
    regioncode: Option[Int] = None)(implicit session: DBSession = autoSession): PblConfBillingRegion = {
    val generatedKey = withSQL {
      insert.into(PblConfBillingRegion).namedValues(
        column.billcode -> billcode,
        column.regioncode -> regioncode
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfBillingRegion(
      code = generatedKey.toInt,
      billcode = billcode,
      regioncode = regioncode)
  }

  def batchInsert(entities: collection.Seq[PblConfBillingRegion])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'billcode -> entity.billcode,
        'regioncode -> entity.regioncode))
    SQL("""insert into pbl_conf_billing_region(
      billcode,
      regioncode
    ) values (
      {billcode},
      {regioncode}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfBillingRegion)(implicit session: DBSession = autoSession): PblConfBillingRegion = {
    withSQL {
      update(PblConfBillingRegion).set(
        column.code -> entity.code,
        column.billcode -> entity.billcode,
        column.regioncode -> entity.regioncode
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfBillingRegion)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfBillingRegion).where.eq(column.code, entity.code) }.update.apply()
  }

}
