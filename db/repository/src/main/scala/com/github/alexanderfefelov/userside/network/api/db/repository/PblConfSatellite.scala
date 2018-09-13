package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfSatellite(
  code: Int,
  citycode: Option[Int] = None,
  adr: Option[String] = None,
  apikey: Option[String] = None) {

  def save()(implicit session: DBSession = PblConfSatellite.autoSession): PblConfSatellite = PblConfSatellite.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfSatellite.autoSession): Int = PblConfSatellite.destroy(this)(session)

}


object PblConfSatellite extends SQLSyntaxSupport[PblConfSatellite] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_satellite"

  override val columns = Seq("code", "citycode", "adr", "apikey")

  def apply(pcs: SyntaxProvider[PblConfSatellite])(rs: WrappedResultSet): PblConfSatellite = autoConstruct(rs, pcs)
  def apply(pcs: ResultName[PblConfSatellite])(rs: WrappedResultSet): PblConfSatellite = autoConstruct(rs, pcs)

  val pcs = PblConfSatellite.syntax("pcs")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfSatellite] = {
    withSQL {
      select.from(PblConfSatellite as pcs).where.eq(pcs.code, code)
    }.map(PblConfSatellite(pcs.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfSatellite] = {
    withSQL(select.from(PblConfSatellite as pcs)).map(PblConfSatellite(pcs.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfSatellite as pcs)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfSatellite] = {
    withSQL {
      select.from(PblConfSatellite as pcs).where.append(where)
    }.map(PblConfSatellite(pcs.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfSatellite] = {
    withSQL {
      select.from(PblConfSatellite as pcs).where.append(where)
    }.map(PblConfSatellite(pcs.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfSatellite as pcs).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    citycode: Option[Int] = None,
    adr: Option[String] = None,
    apikey: Option[String] = None)(implicit session: DBSession = autoSession): PblConfSatellite = {
    val generatedKey = withSQL {
      insert.into(PblConfSatellite).namedValues(
        column.citycode -> citycode,
        column.adr -> adr,
        column.apikey -> apikey
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfSatellite(
      code = generatedKey.toInt,
      citycode = citycode,
      adr = adr,
      apikey = apikey)
  }

  def batchInsert(entities: collection.Seq[PblConfSatellite])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'citycode -> entity.citycode,
        'adr -> entity.adr,
        'apikey -> entity.apikey))
    SQL("""insert into pbl_conf_satellite(
      citycode,
      adr,
      apikey
    ) values (
      {citycode},
      {adr},
      {apikey}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfSatellite)(implicit session: DBSession = autoSession): PblConfSatellite = {
    withSQL {
      update(PblConfSatellite).set(
        column.code -> entity.code,
        column.citycode -> entity.citycode,
        column.adr -> entity.adr,
        column.apikey -> entity.apikey
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfSatellite)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfSatellite).where.eq(column.code, entity.code) }.update.apply()
  }

}
