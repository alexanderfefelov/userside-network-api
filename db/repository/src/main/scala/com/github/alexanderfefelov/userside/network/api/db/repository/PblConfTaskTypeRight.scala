package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfTaskTypeRight(
  id: Int,
  taskTypeId: Option[Int] = None,
  operProfileId: Option[Int] = None,
  rights: Option[Short] = None) {

  def save()(implicit session: DBSession = PblConfTaskTypeRight.autoSession): PblConfTaskTypeRight = PblConfTaskTypeRight.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfTaskTypeRight.autoSession): Int = PblConfTaskTypeRight.destroy(this)(session)

}


object PblConfTaskTypeRight extends SQLSyntaxSupport[PblConfTaskTypeRight] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_task_type_right"

  override val columns = Seq("id", "task_type_id", "oper_profile_id", "rights")

  def apply(pcttr: SyntaxProvider[PblConfTaskTypeRight])(rs: WrappedResultSet): PblConfTaskTypeRight = autoConstruct(rs, pcttr)
  def apply(pcttr: ResultName[PblConfTaskTypeRight])(rs: WrappedResultSet): PblConfTaskTypeRight = autoConstruct(rs, pcttr)

  val pcttr = PblConfTaskTypeRight.syntax("pcttr")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblConfTaskTypeRight] = {
    withSQL {
      select.from(PblConfTaskTypeRight as pcttr).where.eq(pcttr.id, id)
    }.map(PblConfTaskTypeRight(pcttr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfTaskTypeRight] = {
    withSQL(select.from(PblConfTaskTypeRight as pcttr)).map(PblConfTaskTypeRight(pcttr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfTaskTypeRight as pcttr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfTaskTypeRight] = {
    withSQL {
      select.from(PblConfTaskTypeRight as pcttr).where.append(where)
    }.map(PblConfTaskTypeRight(pcttr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfTaskTypeRight] = {
    withSQL {
      select.from(PblConfTaskTypeRight as pcttr).where.append(where)
    }.map(PblConfTaskTypeRight(pcttr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfTaskTypeRight as pcttr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    taskTypeId: Option[Int] = None,
    operProfileId: Option[Int] = None,
    rights: Option[Short] = None)(implicit session: DBSession = autoSession): PblConfTaskTypeRight = {
    val generatedKey = withSQL {
      insert.into(PblConfTaskTypeRight).namedValues(
        column.taskTypeId -> taskTypeId,
        column.operProfileId -> operProfileId,
        column.rights -> rights
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfTaskTypeRight(
      id = generatedKey.toInt,
      taskTypeId = taskTypeId,
      operProfileId = operProfileId,
      rights = rights)
  }

  def batchInsert(entities: collection.Seq[PblConfTaskTypeRight])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'taskTypeId -> entity.taskTypeId,
        'operProfileId -> entity.operProfileId,
        'rights -> entity.rights))
    SQL("""insert into pbl_conf_task_type_right(
      task_type_id,
      oper_profile_id,
      rights
    ) values (
      {taskTypeId},
      {operProfileId},
      {rights}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfTaskTypeRight)(implicit session: DBSession = autoSession): PblConfTaskTypeRight = {
    withSQL {
      update(PblConfTaskTypeRight).set(
        column.id -> entity.id,
        column.taskTypeId -> entity.taskTypeId,
        column.operProfileId -> entity.operProfileId,
        column.rights -> entity.rights
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfTaskTypeRight)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfTaskTypeRight).where.eq(column.id, entity.id) }.update.apply()
  }

}
