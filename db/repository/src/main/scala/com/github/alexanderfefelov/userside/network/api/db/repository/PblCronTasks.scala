package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblCronTasks(
  id: Int,
  taskDate: Option[DateTime] = None,
  categoryId: Option[Int] = None,
  objectId: Option[Int] = None,
  additionalData: Option[String] = None) {

  def save()(implicit session: DBSession = PblCronTasks.autoSession): PblCronTasks = PblCronTasks.save(this)(session)

  def destroy()(implicit session: DBSession = PblCronTasks.autoSession): Int = PblCronTasks.destroy(this)(session)

}


object PblCronTasks extends SQLSyntaxSupport[PblCronTasks] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_cron_tasks"

  override val columns = Seq("id", "task_date", "category_id", "object_id", "additional_data")

  def apply(pct: SyntaxProvider[PblCronTasks])(rs: WrappedResultSet): PblCronTasks = autoConstruct(rs, pct)
  def apply(pct: ResultName[PblCronTasks])(rs: WrappedResultSet): PblCronTasks = autoConstruct(rs, pct)

  val pct = PblCronTasks.syntax("pct")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblCronTasks] = {
    withSQL {
      select.from(PblCronTasks as pct).where.eq(pct.id, id)
    }.map(PblCronTasks(pct.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblCronTasks] = {
    withSQL(select.from(PblCronTasks as pct)).map(PblCronTasks(pct.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblCronTasks as pct)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblCronTasks] = {
    withSQL {
      select.from(PblCronTasks as pct).where.append(where)
    }.map(PblCronTasks(pct.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblCronTasks] = {
    withSQL {
      select.from(PblCronTasks as pct).where.append(where)
    }.map(PblCronTasks(pct.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblCronTasks as pct).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    taskDate: Option[DateTime] = None,
    categoryId: Option[Int] = None,
    objectId: Option[Int] = None,
    additionalData: Option[String] = None)(implicit session: DBSession = autoSession): PblCronTasks = {
    val generatedKey = withSQL {
      insert.into(PblCronTasks).namedValues(
        column.taskDate -> taskDate,
        column.categoryId -> categoryId,
        column.objectId -> objectId,
        column.additionalData -> additionalData
      )
    }.updateAndReturnGeneratedKey.apply()

    PblCronTasks(
      id = generatedKey.toInt,
      taskDate = taskDate,
      categoryId = categoryId,
      objectId = objectId,
      additionalData = additionalData)
  }

  def batchInsert(entities: collection.Seq[PblCronTasks])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'taskDate -> entity.taskDate,
        'categoryId -> entity.categoryId,
        'objectId -> entity.objectId,
        'additionalData -> entity.additionalData))
    SQL("""insert into pbl_cron_tasks(
      task_date,
      category_id,
      object_id,
      additional_data
    ) values (
      {taskDate},
      {categoryId},
      {objectId},
      {additionalData}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblCronTasks)(implicit session: DBSession = autoSession): PblCronTasks = {
    withSQL {
      update(PblCronTasks).set(
        column.id -> entity.id,
        column.taskDate -> entity.taskDate,
        column.categoryId -> entity.categoryId,
        column.objectId -> entity.objectId,
        column.additionalData -> entity.additionalData
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblCronTasks)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblCronTasks).where.eq(column.id, entity.id) }.update.apply()
  }

}
