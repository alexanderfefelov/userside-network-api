package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblTariffGroupInc(
  id: Int,
  tariffGroupId: Option[Int] = None,
  tariffId: Option[String] = None) {

  def save()(implicit session: DBSession = PblTariffGroupInc.autoSession): PblTariffGroupInc = PblTariffGroupInc.save(this)(session)

  def destroy()(implicit session: DBSession = PblTariffGroupInc.autoSession): Int = PblTariffGroupInc.destroy(this)(session)

}


object PblTariffGroupInc extends SQLSyntaxSupport[PblTariffGroupInc] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_tariff_group_inc"

  override val columns = Seq("id", "tariff_group_id", "tariff_id")

  def apply(ptgi: SyntaxProvider[PblTariffGroupInc])(rs: WrappedResultSet): PblTariffGroupInc = autoConstruct(rs, ptgi)
  def apply(ptgi: ResultName[PblTariffGroupInc])(rs: WrappedResultSet): PblTariffGroupInc = autoConstruct(rs, ptgi)

  val ptgi = PblTariffGroupInc.syntax("ptgi")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblTariffGroupInc] = {
    withSQL {
      select.from(PblTariffGroupInc as ptgi).where.eq(ptgi.id, id)
    }.map(PblTariffGroupInc(ptgi.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblTariffGroupInc] = {
    withSQL(select.from(PblTariffGroupInc as ptgi)).map(PblTariffGroupInc(ptgi.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblTariffGroupInc as ptgi)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblTariffGroupInc] = {
    withSQL {
      select.from(PblTariffGroupInc as ptgi).where.append(where)
    }.map(PblTariffGroupInc(ptgi.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblTariffGroupInc] = {
    withSQL {
      select.from(PblTariffGroupInc as ptgi).where.append(where)
    }.map(PblTariffGroupInc(ptgi.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblTariffGroupInc as ptgi).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    tariffGroupId: Option[Int] = None,
    tariffId: Option[String] = None)(implicit session: DBSession = autoSession): PblTariffGroupInc = {
    val generatedKey = withSQL {
      insert.into(PblTariffGroupInc).namedValues(
        column.tariffGroupId -> tariffGroupId,
        column.tariffId -> tariffId
      )
    }.updateAndReturnGeneratedKey.apply()

    PblTariffGroupInc(
      id = generatedKey.toInt,
      tariffGroupId = tariffGroupId,
      tariffId = tariffId)
  }

  def batchInsert(entities: collection.Seq[PblTariffGroupInc])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'tariffGroupId -> entity.tariffGroupId,
        'tariffId -> entity.tariffId))
    SQL("""insert into pbl_tariff_group_inc(
      tariff_group_id,
      tariff_id
    ) values (
      {tariffGroupId},
      {tariffId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblTariffGroupInc)(implicit session: DBSession = autoSession): PblTariffGroupInc = {
    withSQL {
      update(PblTariffGroupInc).set(
        column.id -> entity.id,
        column.tariffGroupId -> entity.tariffGroupId,
        column.tariffId -> entity.tariffId
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblTariffGroupInc)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblTariffGroupInc).where.eq(column.id, entity.id) }.update.apply()
  }

}
