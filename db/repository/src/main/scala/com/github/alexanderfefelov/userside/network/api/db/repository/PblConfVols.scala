package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfVols(
  code: Int,
  proizv: Option[String] = None,
  nazv: Option[String] = None,
  kn: Option[BigDecimal] = None,
  port: Option[Int] = None,
  portcolor: Option[String] = None,
  opis: Option[String] = None,
  att1550: Option[BigDecimal] = None) {

  def save()(implicit session: DBSession = PblConfVols.autoSession): PblConfVols = PblConfVols.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfVols.autoSession): Int = PblConfVols.destroy(this)(session)

}


object PblConfVols extends SQLSyntaxSupport[PblConfVols] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_vols"

  override val columns = Seq("code", "proizv", "nazv", "kn", "port", "portcolor", "opis", "att1550")

  def apply(pcv: SyntaxProvider[PblConfVols])(rs: WrappedResultSet): PblConfVols = autoConstruct(rs, pcv)
  def apply(pcv: ResultName[PblConfVols])(rs: WrappedResultSet): PblConfVols = autoConstruct(rs, pcv)

  val pcv = PblConfVols.syntax("pcv")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfVols] = {
    withSQL {
      select.from(PblConfVols as pcv).where.eq(pcv.code, code)
    }.map(PblConfVols(pcv.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfVols] = {
    withSQL(select.from(PblConfVols as pcv)).map(PblConfVols(pcv.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfVols as pcv)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfVols] = {
    withSQL {
      select.from(PblConfVols as pcv).where.append(where)
    }.map(PblConfVols(pcv.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfVols] = {
    withSQL {
      select.from(PblConfVols as pcv).where.append(where)
    }.map(PblConfVols(pcv.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfVols as pcv).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    proizv: Option[String] = None,
    nazv: Option[String] = None,
    kn: Option[BigDecimal] = None,
    port: Option[Int] = None,
    portcolor: Option[String] = None,
    opis: Option[String] = None,
    att1550: Option[BigDecimal] = None)(implicit session: DBSession = autoSession): PblConfVols = {
    val generatedKey = withSQL {
      insert.into(PblConfVols).namedValues(
        column.proizv -> proizv,
        column.nazv -> nazv,
        column.kn -> kn,
        column.port -> port,
        column.portcolor -> portcolor,
        column.opis -> opis,
        column.att1550 -> att1550
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfVols(
      code = generatedKey.toInt,
      proizv = proizv,
      nazv = nazv,
      kn = kn,
      port = port,
      portcolor = portcolor,
      opis = opis,
      att1550 = att1550)
  }

  def batchInsert(entities: collection.Seq[PblConfVols])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'proizv -> entity.proizv,
        'nazv -> entity.nazv,
        'kn -> entity.kn,
        'port -> entity.port,
        'portcolor -> entity.portcolor,
        'opis -> entity.opis,
        'att1550 -> entity.att1550))
    SQL("""insert into pbl_conf_vols(
      proizv,
      nazv,
      kn,
      port,
      portcolor,
      opis,
      att1550
    ) values (
      {proizv},
      {nazv},
      {kn},
      {port},
      {portcolor},
      {opis},
      {att1550}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfVols)(implicit session: DBSession = autoSession): PblConfVols = {
    withSQL {
      update(PblConfVols).set(
        column.code -> entity.code,
        column.proizv -> entity.proizv,
        column.nazv -> entity.nazv,
        column.kn -> entity.kn,
        column.port -> entity.port,
        column.portcolor -> entity.portcolor,
        column.opis -> entity.opis,
        column.att1550 -> entity.att1550
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfVols)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfVols).where.eq(column.code, entity.code) }.update.apply()
  }

}
