package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblCableDuctContent(
  id: Int,
  cableDuctId: Option[Int] = None,
  cableType: Option[Int] = None,
  cableId: Option[Int] = None,
  position: Option[Int] = None,
  direction: Option[Short] = None) {

  def save()(implicit session: DBSession = PblCableDuctContent.autoSession): PblCableDuctContent = PblCableDuctContent.save(this)(session)

  def destroy()(implicit session: DBSession = PblCableDuctContent.autoSession): Int = PblCableDuctContent.destroy(this)(session)

}


object PblCableDuctContent extends SQLSyntaxSupport[PblCableDuctContent] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_cable_duct_content"

  override val columns = Seq("id", "cable_duct_id", "cable_type", "cable_id", "position", "direction")

  def apply(pcdc: SyntaxProvider[PblCableDuctContent])(rs: WrappedResultSet): PblCableDuctContent = autoConstruct(rs, pcdc)
  def apply(pcdc: ResultName[PblCableDuctContent])(rs: WrappedResultSet): PblCableDuctContent = autoConstruct(rs, pcdc)

  val pcdc = PblCableDuctContent.syntax("pcdc")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblCableDuctContent] = {
    withSQL {
      select.from(PblCableDuctContent as pcdc).where.eq(pcdc.id, id)
    }.map(PblCableDuctContent(pcdc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblCableDuctContent] = {
    withSQL(select.from(PblCableDuctContent as pcdc)).map(PblCableDuctContent(pcdc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblCableDuctContent as pcdc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblCableDuctContent] = {
    withSQL {
      select.from(PblCableDuctContent as pcdc).where.append(where)
    }.map(PblCableDuctContent(pcdc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblCableDuctContent] = {
    withSQL {
      select.from(PblCableDuctContent as pcdc).where.append(where)
    }.map(PblCableDuctContent(pcdc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblCableDuctContent as pcdc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cableDuctId: Option[Int] = None,
    cableType: Option[Int] = None,
    cableId: Option[Int] = None,
    position: Option[Int] = None,
    direction: Option[Short] = None)(implicit session: DBSession = autoSession): PblCableDuctContent = {
    val generatedKey = withSQL {
      insert.into(PblCableDuctContent).namedValues(
        column.cableDuctId -> cableDuctId,
        column.cableType -> cableType,
        column.cableId -> cableId,
        column.position -> position,
        column.direction -> direction
      )
    }.updateAndReturnGeneratedKey.apply()

    PblCableDuctContent(
      id = generatedKey.toInt,
      cableDuctId = cableDuctId,
      cableType = cableType,
      cableId = cableId,
      position = position,
      direction = direction)
  }

  def batchInsert(entities: collection.Seq[PblCableDuctContent])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cableDuctId -> entity.cableDuctId,
        'cableType -> entity.cableType,
        'cableId -> entity.cableId,
        'position -> entity.position,
        'direction -> entity.direction))
    SQL("""insert into pbl_cable_duct_content(
      cable_duct_id,
      cable_type,
      cable_id,
      position,
      direction
    ) values (
      {cableDuctId},
      {cableType},
      {cableId},
      {position},
      {direction}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblCableDuctContent)(implicit session: DBSession = autoSession): PblCableDuctContent = {
    withSQL {
      update(PblCableDuctContent).set(
        column.id -> entity.id,
        column.cableDuctId -> entity.cableDuctId,
        column.cableType -> entity.cableType,
        column.cableId -> entity.cableId,
        column.position -> entity.position,
        column.direction -> entity.direction
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblCableDuctContent)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblCableDuctContent).where.eq(column.id, entity.id) }.update.apply()
  }

}
