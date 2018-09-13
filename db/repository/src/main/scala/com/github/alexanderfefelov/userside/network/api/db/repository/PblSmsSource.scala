package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblSmsSource(
  id: Int,
  smsId: Option[Int] = None,
  sourceId: Option[Int] = None) {

  def save()(implicit session: DBSession = PblSmsSource.autoSession): PblSmsSource = PblSmsSource.save(this)(session)

  def destroy()(implicit session: DBSession = PblSmsSource.autoSession): Int = PblSmsSource.destroy(this)(session)

}


object PblSmsSource extends SQLSyntaxSupport[PblSmsSource] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_sms_source"

  override val columns = Seq("id", "sms_id", "source_id")

  def apply(pss: SyntaxProvider[PblSmsSource])(rs: WrappedResultSet): PblSmsSource = autoConstruct(rs, pss)
  def apply(pss: ResultName[PblSmsSource])(rs: WrappedResultSet): PblSmsSource = autoConstruct(rs, pss)

  val pss = PblSmsSource.syntax("pss")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblSmsSource] = {
    withSQL {
      select.from(PblSmsSource as pss).where.eq(pss.id, id)
    }.map(PblSmsSource(pss.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblSmsSource] = {
    withSQL(select.from(PblSmsSource as pss)).map(PblSmsSource(pss.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblSmsSource as pss)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblSmsSource] = {
    withSQL {
      select.from(PblSmsSource as pss).where.append(where)
    }.map(PblSmsSource(pss.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblSmsSource] = {
    withSQL {
      select.from(PblSmsSource as pss).where.append(where)
    }.map(PblSmsSource(pss.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblSmsSource as pss).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    smsId: Option[Int] = None,
    sourceId: Option[Int] = None)(implicit session: DBSession = autoSession): PblSmsSource = {
    val generatedKey = withSQL {
      insert.into(PblSmsSource).namedValues(
        column.smsId -> smsId,
        column.sourceId -> sourceId
      )
    }.updateAndReturnGeneratedKey.apply()

    PblSmsSource(
      id = generatedKey.toInt,
      smsId = smsId,
      sourceId = sourceId)
  }

  def batchInsert(entities: collection.Seq[PblSmsSource])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'smsId -> entity.smsId,
        'sourceId -> entity.sourceId))
    SQL("""insert into pbl_sms_source(
      sms_id,
      source_id
    ) values (
      {smsId},
      {sourceId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblSmsSource)(implicit session: DBSession = autoSession): PblSmsSource = {
    withSQL {
      update(PblSmsSource).set(
        column.id -> entity.id,
        column.smsId -> entity.smsId,
        column.sourceId -> entity.sourceId
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblSmsSource)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblSmsSource).where.eq(column.id, entity.id) }.update.apply()
  }

}
