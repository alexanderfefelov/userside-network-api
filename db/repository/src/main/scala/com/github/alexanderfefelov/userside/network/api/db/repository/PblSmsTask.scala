package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblSmsTask(
  id: Int,
  smsId: Option[Int] = None,
  taskId: Option[Int] = None) {

  def save()(implicit session: DBSession = PblSmsTask.autoSession): PblSmsTask = PblSmsTask.save(this)(session)

  def destroy()(implicit session: DBSession = PblSmsTask.autoSession): Int = PblSmsTask.destroy(this)(session)

}


object PblSmsTask extends SQLSyntaxSupport[PblSmsTask] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_sms_task"

  override val columns = Seq("id", "sms_id", "task_id")

  def apply(pst: SyntaxProvider[PblSmsTask])(rs: WrappedResultSet): PblSmsTask = autoConstruct(rs, pst)
  def apply(pst: ResultName[PblSmsTask])(rs: WrappedResultSet): PblSmsTask = autoConstruct(rs, pst)

  val pst = PblSmsTask.syntax("pst")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblSmsTask] = {
    withSQL {
      select.from(PblSmsTask as pst).where.eq(pst.id, id)
    }.map(PblSmsTask(pst.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblSmsTask] = {
    withSQL(select.from(PblSmsTask as pst)).map(PblSmsTask(pst.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblSmsTask as pst)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblSmsTask] = {
    withSQL {
      select.from(PblSmsTask as pst).where.append(where)
    }.map(PblSmsTask(pst.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblSmsTask] = {
    withSQL {
      select.from(PblSmsTask as pst).where.append(where)
    }.map(PblSmsTask(pst.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblSmsTask as pst).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    smsId: Option[Int] = None,
    taskId: Option[Int] = None)(implicit session: DBSession = autoSession): PblSmsTask = {
    val generatedKey = withSQL {
      insert.into(PblSmsTask).namedValues(
        column.smsId -> smsId,
        column.taskId -> taskId
      )
    }.updateAndReturnGeneratedKey.apply()

    PblSmsTask(
      id = generatedKey.toInt,
      smsId = smsId,
      taskId = taskId)
  }

  def batchInsert(entities: collection.Seq[PblSmsTask])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'smsId -> entity.smsId,
        'taskId -> entity.taskId))
    SQL("""insert into pbl_sms_task(
      sms_id,
      task_id
    ) values (
      {smsId},
      {taskId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblSmsTask)(implicit session: DBSession = autoSession): PblSmsTask = {
    withSQL {
      update(PblSmsTask).set(
        column.id -> entity.id,
        column.smsId -> entity.smsId,
        column.taskId -> entity.taskId
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblSmsTask)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblSmsTask).where.eq(column.id, entity.id) }.update.apply()
  }

}
