package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfTtResponsible(
  id: Int,
  sectionId: Option[Int] = None,
  operatorId: Option[Int] = None) {

  def save()(implicit session: DBSession = PblConfTtResponsible.autoSession): PblConfTtResponsible = PblConfTtResponsible.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfTtResponsible.autoSession): Int = PblConfTtResponsible.destroy(this)(session)

}


object PblConfTtResponsible extends SQLSyntaxSupport[PblConfTtResponsible] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_tt_responsible"

  override val columns = Seq("id", "section_id", "operator_id")

  def apply(pctr: SyntaxProvider[PblConfTtResponsible])(rs: WrappedResultSet): PblConfTtResponsible = autoConstruct(rs, pctr)
  def apply(pctr: ResultName[PblConfTtResponsible])(rs: WrappedResultSet): PblConfTtResponsible = autoConstruct(rs, pctr)

  val pctr = PblConfTtResponsible.syntax("pctr")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblConfTtResponsible] = {
    withSQL {
      select.from(PblConfTtResponsible as pctr).where.eq(pctr.id, id)
    }.map(PblConfTtResponsible(pctr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfTtResponsible] = {
    withSQL(select.from(PblConfTtResponsible as pctr)).map(PblConfTtResponsible(pctr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfTtResponsible as pctr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfTtResponsible] = {
    withSQL {
      select.from(PblConfTtResponsible as pctr).where.append(where)
    }.map(PblConfTtResponsible(pctr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfTtResponsible] = {
    withSQL {
      select.from(PblConfTtResponsible as pctr).where.append(where)
    }.map(PblConfTtResponsible(pctr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfTtResponsible as pctr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    sectionId: Option[Int] = None,
    operatorId: Option[Int] = None)(implicit session: DBSession = autoSession): PblConfTtResponsible = {
    val generatedKey = withSQL {
      insert.into(PblConfTtResponsible).namedValues(
        column.sectionId -> sectionId,
        column.operatorId -> operatorId
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfTtResponsible(
      id = generatedKey.toInt,
      sectionId = sectionId,
      operatorId = operatorId)
  }

  def batchInsert(entities: collection.Seq[PblConfTtResponsible])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'sectionId -> entity.sectionId,
        'operatorId -> entity.operatorId))
    SQL("""insert into pbl_conf_tt_responsible(
      section_id,
      operator_id
    ) values (
      {sectionId},
      {operatorId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfTtResponsible)(implicit session: DBSession = autoSession): PblConfTtResponsible = {
    withSQL {
      update(PblConfTtResponsible).set(
        column.id -> entity.id,
        column.sectionId -> entity.sectionId,
        column.operatorId -> entity.operatorId
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfTtResponsible)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfTtResponsible).where.eq(column.id, entity.id) }.update.apply()
  }

}
