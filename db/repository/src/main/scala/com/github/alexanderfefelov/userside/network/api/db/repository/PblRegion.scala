package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblRegion(
  code: Int,
  nazv: Option[String] = None,
  geoAcc: Option[String] = None) {

  def save()(implicit session: DBSession = PblRegion.autoSession): PblRegion = PblRegion.save(this)(session)

  def destroy()(implicit session: DBSession = PblRegion.autoSession): Int = PblRegion.destroy(this)(session)

}


object PblRegion extends SQLSyntaxSupport[PblRegion] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_region"

  override val columns = Seq("code", "nazv", "geo_acc")

  def apply(pr: SyntaxProvider[PblRegion])(rs: WrappedResultSet): PblRegion = autoConstruct(rs, pr)
  def apply(pr: ResultName[PblRegion])(rs: WrappedResultSet): PblRegion = autoConstruct(rs, pr)

  val pr = PblRegion.syntax("pr")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblRegion] = {
    withSQL {
      select.from(PblRegion as pr).where.eq(pr.code, code)
    }.map(PblRegion(pr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblRegion] = {
    withSQL(select.from(PblRegion as pr)).map(PblRegion(pr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblRegion as pr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblRegion] = {
    withSQL {
      select.from(PblRegion as pr).where.append(where)
    }.map(PblRegion(pr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblRegion] = {
    withSQL {
      select.from(PblRegion as pr).where.append(where)
    }.map(PblRegion(pr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblRegion as pr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    nazv: Option[String] = None,
    geoAcc: Option[String] = None)(implicit session: DBSession = autoSession): PblRegion = {
    val generatedKey = withSQL {
      insert.into(PblRegion).namedValues(
        column.nazv -> nazv,
        column.geoAcc -> geoAcc
      )
    }.updateAndReturnGeneratedKey.apply()

    PblRegion(
      code = generatedKey.toInt,
      nazv = nazv,
      geoAcc = geoAcc)
  }

  def batchInsert(entities: collection.Seq[PblRegion])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'nazv -> entity.nazv,
        'geoAcc -> entity.geoAcc))
    SQL("""insert into pbl_region(
      nazv,
      geo_acc
    ) values (
      {nazv},
      {geoAcc}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblRegion)(implicit session: DBSession = autoSession): PblRegion = {
    withSQL {
      update(PblRegion).set(
        column.code -> entity.code,
        column.nazv -> entity.nazv,
        column.geoAcc -> entity.geoAcc
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblRegion)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblRegion).where.eq(column.code, entity.code) }.update.apply()
  }

}
