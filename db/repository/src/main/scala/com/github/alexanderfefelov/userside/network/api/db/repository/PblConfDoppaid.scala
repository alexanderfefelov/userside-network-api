package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfDoppaid(
  code: Int,
  nazv: Option[String] = None,
  opis: Option[String] = None,
  isenabled: Option[Short] = None,
  summa: Option[BigDecimal] = None,
  billcode: Option[Int] = None) {

  def save()(implicit session: DBSession = PblConfDoppaid.autoSession): PblConfDoppaid = PblConfDoppaid.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfDoppaid.autoSession): Int = PblConfDoppaid.destroy(this)(session)

}


object PblConfDoppaid extends SQLSyntaxSupport[PblConfDoppaid] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_doppaid"

  override val columns = Seq("code", "nazv", "opis", "isenabled", "summa", "billcode")

  def apply(pcd: SyntaxProvider[PblConfDoppaid])(rs: WrappedResultSet): PblConfDoppaid = autoConstruct(rs, pcd)
  def apply(pcd: ResultName[PblConfDoppaid])(rs: WrappedResultSet): PblConfDoppaid = autoConstruct(rs, pcd)

  val pcd = PblConfDoppaid.syntax("pcd")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfDoppaid] = {
    withSQL {
      select.from(PblConfDoppaid as pcd).where.eq(pcd.code, code)
    }.map(PblConfDoppaid(pcd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfDoppaid] = {
    withSQL(select.from(PblConfDoppaid as pcd)).map(PblConfDoppaid(pcd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfDoppaid as pcd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfDoppaid] = {
    withSQL {
      select.from(PblConfDoppaid as pcd).where.append(where)
    }.map(PblConfDoppaid(pcd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfDoppaid] = {
    withSQL {
      select.from(PblConfDoppaid as pcd).where.append(where)
    }.map(PblConfDoppaid(pcd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfDoppaid as pcd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    nazv: Option[String] = None,
    opis: Option[String] = None,
    isenabled: Option[Short] = None,
    summa: Option[BigDecimal] = None,
    billcode: Option[Int] = None)(implicit session: DBSession = autoSession): PblConfDoppaid = {
    val generatedKey = withSQL {
      insert.into(PblConfDoppaid).namedValues(
        column.nazv -> nazv,
        column.opis -> opis,
        column.isenabled -> isenabled,
        column.summa -> summa,
        column.billcode -> billcode
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfDoppaid(
      code = generatedKey.toInt,
      nazv = nazv,
      opis = opis,
      isenabled = isenabled,
      summa = summa,
      billcode = billcode)
  }

  def batchInsert(entities: collection.Seq[PblConfDoppaid])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'nazv -> entity.nazv,
        'opis -> entity.opis,
        'isenabled -> entity.isenabled,
        'summa -> entity.summa,
        'billcode -> entity.billcode))
    SQL("""insert into pbl_conf_doppaid(
      nazv,
      opis,
      isenabled,
      summa,
      billcode
    ) values (
      {nazv},
      {opis},
      {isenabled},
      {summa},
      {billcode}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfDoppaid)(implicit session: DBSession = autoSession): PblConfDoppaid = {
    withSQL {
      update(PblConfDoppaid).set(
        column.code -> entity.code,
        column.nazv -> entity.nazv,
        column.opis -> entity.opis,
        column.isenabled -> entity.isenabled,
        column.summa -> entity.summa,
        column.billcode -> entity.billcode
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfDoppaid)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfDoppaid).where.eq(column.code, entity.code) }.update.apply()
  }

}
