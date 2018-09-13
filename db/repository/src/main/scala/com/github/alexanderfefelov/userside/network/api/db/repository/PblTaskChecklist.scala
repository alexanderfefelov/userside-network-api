package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblTaskChecklist(
  id: Int,
  taskId: Option[Int] = None,
  position: Option[Short] = None,
  name: Option[String] = None,
  isCheck: Option[Short] = None,
  dateCheck: Option[DateTime] = None,
  operatorCheckId: Option[Int] = None) {

  def save()(implicit session: DBSession = PblTaskChecklist.autoSession): PblTaskChecklist = PblTaskChecklist.save(this)(session)

  def destroy()(implicit session: DBSession = PblTaskChecklist.autoSession): Int = PblTaskChecklist.destroy(this)(session)

}


object PblTaskChecklist extends SQLSyntaxSupport[PblTaskChecklist] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_task_checklist"

  override val columns = Seq("id", "task_id", "position", "name", "is_check", "date_check", "operator_check_id")

  def apply(ptc: SyntaxProvider[PblTaskChecklist])(rs: WrappedResultSet): PblTaskChecklist = autoConstruct(rs, ptc)
  def apply(ptc: ResultName[PblTaskChecklist])(rs: WrappedResultSet): PblTaskChecklist = autoConstruct(rs, ptc)

  val ptc = PblTaskChecklist.syntax("ptc")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblTaskChecklist] = {
    withSQL {
      select.from(PblTaskChecklist as ptc).where.eq(ptc.id, id)
    }.map(PblTaskChecklist(ptc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblTaskChecklist] = {
    withSQL(select.from(PblTaskChecklist as ptc)).map(PblTaskChecklist(ptc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblTaskChecklist as ptc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblTaskChecklist] = {
    withSQL {
      select.from(PblTaskChecklist as ptc).where.append(where)
    }.map(PblTaskChecklist(ptc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblTaskChecklist] = {
    withSQL {
      select.from(PblTaskChecklist as ptc).where.append(where)
    }.map(PblTaskChecklist(ptc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblTaskChecklist as ptc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    taskId: Option[Int] = None,
    position: Option[Short] = None,
    name: Option[String] = None,
    isCheck: Option[Short] = None,
    dateCheck: Option[DateTime] = None,
    operatorCheckId: Option[Int] = None)(implicit session: DBSession = autoSession): PblTaskChecklist = {
    val generatedKey = withSQL {
      insert.into(PblTaskChecklist).namedValues(
        column.taskId -> taskId,
        column.position -> position,
        column.name -> name,
        column.isCheck -> isCheck,
        column.dateCheck -> dateCheck,
        column.operatorCheckId -> operatorCheckId
      )
    }.updateAndReturnGeneratedKey.apply()

    PblTaskChecklist(
      id = generatedKey.toInt,
      taskId = taskId,
      position = position,
      name = name,
      isCheck = isCheck,
      dateCheck = dateCheck,
      operatorCheckId = operatorCheckId)
  }

  def batchInsert(entities: collection.Seq[PblTaskChecklist])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'taskId -> entity.taskId,
        'position -> entity.position,
        'name -> entity.name,
        'isCheck -> entity.isCheck,
        'dateCheck -> entity.dateCheck,
        'operatorCheckId -> entity.operatorCheckId))
    SQL("""insert into pbl_task_checklist(
      task_id,
      position,
      name,
      is_check,
      date_check,
      operator_check_id
    ) values (
      {taskId},
      {position},
      {name},
      {isCheck},
      {dateCheck},
      {operatorCheckId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblTaskChecklist)(implicit session: DBSession = autoSession): PblTaskChecklist = {
    withSQL {
      update(PblTaskChecklist).set(
        column.id -> entity.id,
        column.taskId -> entity.taskId,
        column.position -> entity.position,
        column.name -> entity.name,
        column.isCheck -> entity.isCheck,
        column.dateCheck -> entity.dateCheck,
        column.operatorCheckId -> entity.operatorCheckId
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblTaskChecklist)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblTaskChecklist).where.eq(column.id, entity.id) }.update.apply()
  }

}
