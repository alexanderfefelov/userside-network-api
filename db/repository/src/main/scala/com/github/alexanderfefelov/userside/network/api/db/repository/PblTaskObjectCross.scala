package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblTaskObjectCross(
  id: Int,
  taskId: Option[Int] = None,
  objectType: Option[Int] = None,
  objectId: Option[Int] = None) {

  def save()(implicit session: DBSession = PblTaskObjectCross.autoSession): PblTaskObjectCross = PblTaskObjectCross.save(this)(session)

  def destroy()(implicit session: DBSession = PblTaskObjectCross.autoSession): Int = PblTaskObjectCross.destroy(this)(session)

}


object PblTaskObjectCross extends SQLSyntaxSupport[PblTaskObjectCross] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_task_object_cross"

  override val columns = Seq("id", "task_id", "object_type", "object_id")

  def apply(ptoc: SyntaxProvider[PblTaskObjectCross])(rs: WrappedResultSet): PblTaskObjectCross = autoConstruct(rs, ptoc)
  def apply(ptoc: ResultName[PblTaskObjectCross])(rs: WrappedResultSet): PblTaskObjectCross = autoConstruct(rs, ptoc)

  val ptoc = PblTaskObjectCross.syntax("ptoc")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblTaskObjectCross] = {
    withSQL {
      select.from(PblTaskObjectCross as ptoc).where.eq(ptoc.id, id)
    }.map(PblTaskObjectCross(ptoc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblTaskObjectCross] = {
    withSQL(select.from(PblTaskObjectCross as ptoc)).map(PblTaskObjectCross(ptoc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblTaskObjectCross as ptoc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblTaskObjectCross] = {
    withSQL {
      select.from(PblTaskObjectCross as ptoc).where.append(where)
    }.map(PblTaskObjectCross(ptoc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblTaskObjectCross] = {
    withSQL {
      select.from(PblTaskObjectCross as ptoc).where.append(where)
    }.map(PblTaskObjectCross(ptoc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblTaskObjectCross as ptoc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    taskId: Option[Int] = None,
    objectType: Option[Int] = None,
    objectId: Option[Int] = None)(implicit session: DBSession = autoSession): PblTaskObjectCross = {
    val generatedKey = withSQL {
      insert.into(PblTaskObjectCross).namedValues(
        column.taskId -> taskId,
        column.objectType -> objectType,
        column.objectId -> objectId
      )
    }.updateAndReturnGeneratedKey.apply()

    PblTaskObjectCross(
      id = generatedKey.toInt,
      taskId = taskId,
      objectType = objectType,
      objectId = objectId)
  }

  def batchInsert(entities: collection.Seq[PblTaskObjectCross])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'taskId -> entity.taskId,
        'objectType -> entity.objectType,
        'objectId -> entity.objectId))
    SQL("""insert into pbl_task_object_cross(
      task_id,
      object_type,
      object_id
    ) values (
      {taskId},
      {objectType},
      {objectId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblTaskObjectCross)(implicit session: DBSession = autoSession): PblTaskObjectCross = {
    withSQL {
      update(PblTaskObjectCross).set(
        column.id -> entity.id,
        column.taskId -> entity.taskId,
        column.objectType -> entity.objectType,
        column.objectId -> entity.objectId
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblTaskObjectCross)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblTaskObjectCross).where.eq(column.id, entity.id) }.update.apply()
  }

}
