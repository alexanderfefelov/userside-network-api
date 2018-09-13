package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblPatchPanel(
  id: Int,
  typeId: Option[Int] = None,
  nodeId: Option[Int] = None,
  comment: Option[String] = None,
  isPlan: Option[Short] = None,
  portCount: Option[Short] = None,
  inventorySectionId: Option[Int] = None,
  inventoryId: Option[Int] = None,
  rotation: Option[Short] = None,
  schemePositionX: Option[Int] = None,
  schemePositionY: Option[Int] = None) {

  def save()(implicit session: DBSession = PblPatchPanel.autoSession): PblPatchPanel = PblPatchPanel.save(this)(session)

  def destroy()(implicit session: DBSession = PblPatchPanel.autoSession): Int = PblPatchPanel.destroy(this)(session)

}


object PblPatchPanel extends SQLSyntaxSupport[PblPatchPanel] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_patch_panel"

  override val columns = Seq("id", "type_id", "node_id", "comment", "is_plan", "port_count", "inventory_section_id", "inventory_id", "rotation", "scheme_position_x", "scheme_position_y")

  def apply(ppp: SyntaxProvider[PblPatchPanel])(rs: WrappedResultSet): PblPatchPanel = autoConstruct(rs, ppp)
  def apply(ppp: ResultName[PblPatchPanel])(rs: WrappedResultSet): PblPatchPanel = autoConstruct(rs, ppp)

  val ppp = PblPatchPanel.syntax("ppp")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblPatchPanel] = {
    withSQL {
      select.from(PblPatchPanel as ppp).where.eq(ppp.id, id)
    }.map(PblPatchPanel(ppp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblPatchPanel] = {
    withSQL(select.from(PblPatchPanel as ppp)).map(PblPatchPanel(ppp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblPatchPanel as ppp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblPatchPanel] = {
    withSQL {
      select.from(PblPatchPanel as ppp).where.append(where)
    }.map(PblPatchPanel(ppp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblPatchPanel] = {
    withSQL {
      select.from(PblPatchPanel as ppp).where.append(where)
    }.map(PblPatchPanel(ppp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblPatchPanel as ppp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    typeId: Option[Int] = None,
    nodeId: Option[Int] = None,
    comment: Option[String] = None,
    isPlan: Option[Short] = None,
    portCount: Option[Short] = None,
    inventorySectionId: Option[Int] = None,
    inventoryId: Option[Int] = None,
    rotation: Option[Short] = None,
    schemePositionX: Option[Int] = None,
    schemePositionY: Option[Int] = None)(implicit session: DBSession = autoSession): PblPatchPanel = {
    val generatedKey = withSQL {
      insert.into(PblPatchPanel).namedValues(
        column.typeId -> typeId,
        column.nodeId -> nodeId,
        column.comment -> comment,
        column.isPlan -> isPlan,
        column.portCount -> portCount,
        column.inventorySectionId -> inventorySectionId,
        column.inventoryId -> inventoryId,
        column.rotation -> rotation,
        column.schemePositionX -> schemePositionX,
        column.schemePositionY -> schemePositionY
      )
    }.updateAndReturnGeneratedKey.apply()

    PblPatchPanel(
      id = generatedKey.toInt,
      typeId = typeId,
      nodeId = nodeId,
      comment = comment,
      isPlan = isPlan,
      portCount = portCount,
      inventorySectionId = inventorySectionId,
      inventoryId = inventoryId,
      rotation = rotation,
      schemePositionX = schemePositionX,
      schemePositionY = schemePositionY)
  }

  def batchInsert(entities: collection.Seq[PblPatchPanel])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'typeId -> entity.typeId,
        'nodeId -> entity.nodeId,
        'comment -> entity.comment,
        'isPlan -> entity.isPlan,
        'portCount -> entity.portCount,
        'inventorySectionId -> entity.inventorySectionId,
        'inventoryId -> entity.inventoryId,
        'rotation -> entity.rotation,
        'schemePositionX -> entity.schemePositionX,
        'schemePositionY -> entity.schemePositionY))
    SQL("""insert into pbl_patch_panel(
      type_id,
      node_id,
      comment,
      is_plan,
      port_count,
      inventory_section_id,
      inventory_id,
      rotation,
      scheme_position_x,
      scheme_position_y
    ) values (
      {typeId},
      {nodeId},
      {comment},
      {isPlan},
      {portCount},
      {inventorySectionId},
      {inventoryId},
      {rotation},
      {schemePositionX},
      {schemePositionY}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblPatchPanel)(implicit session: DBSession = autoSession): PblPatchPanel = {
    withSQL {
      update(PblPatchPanel).set(
        column.id -> entity.id,
        column.typeId -> entity.typeId,
        column.nodeId -> entity.nodeId,
        column.comment -> entity.comment,
        column.isPlan -> entity.isPlan,
        column.portCount -> entity.portCount,
        column.inventorySectionId -> entity.inventorySectionId,
        column.inventoryId -> entity.inventoryId,
        column.rotation -> entity.rotation,
        column.schemePositionX -> entity.schemePositionX,
        column.schemePositionY -> entity.schemePositionY
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblPatchPanel)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblPatchPanel).where.eq(column.id, entity.id) }.update.apply()
  }

}
