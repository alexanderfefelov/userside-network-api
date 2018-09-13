package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblSmsOperator(
  id: Int,
  smsId: Option[Int] = None,
  operatorId: Option[Int] = None) {

  def save()(implicit session: DBSession = PblSmsOperator.autoSession): PblSmsOperator = PblSmsOperator.save(this)(session)

  def destroy()(implicit session: DBSession = PblSmsOperator.autoSession): Int = PblSmsOperator.destroy(this)(session)

}


object PblSmsOperator extends SQLSyntaxSupport[PblSmsOperator] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_sms_operator"

  override val columns = Seq("id", "sms_id", "operator_id")

  def apply(pso: SyntaxProvider[PblSmsOperator])(rs: WrappedResultSet): PblSmsOperator = autoConstruct(rs, pso)
  def apply(pso: ResultName[PblSmsOperator])(rs: WrappedResultSet): PblSmsOperator = autoConstruct(rs, pso)

  val pso = PblSmsOperator.syntax("pso")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblSmsOperator] = {
    withSQL {
      select.from(PblSmsOperator as pso).where.eq(pso.id, id)
    }.map(PblSmsOperator(pso.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblSmsOperator] = {
    withSQL(select.from(PblSmsOperator as pso)).map(PblSmsOperator(pso.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblSmsOperator as pso)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblSmsOperator] = {
    withSQL {
      select.from(PblSmsOperator as pso).where.append(where)
    }.map(PblSmsOperator(pso.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblSmsOperator] = {
    withSQL {
      select.from(PblSmsOperator as pso).where.append(where)
    }.map(PblSmsOperator(pso.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblSmsOperator as pso).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    smsId: Option[Int] = None,
    operatorId: Option[Int] = None)(implicit session: DBSession = autoSession): PblSmsOperator = {
    val generatedKey = withSQL {
      insert.into(PblSmsOperator).namedValues(
        column.smsId -> smsId,
        column.operatorId -> operatorId
      )
    }.updateAndReturnGeneratedKey.apply()

    PblSmsOperator(
      id = generatedKey.toInt,
      smsId = smsId,
      operatorId = operatorId)
  }

  def batchInsert(entities: collection.Seq[PblSmsOperator])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'smsId -> entity.smsId,
        'operatorId -> entity.operatorId))
    SQL("""insert into pbl_sms_operator(
      sms_id,
      operator_id
    ) values (
      {smsId},
      {operatorId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblSmsOperator)(implicit session: DBSession = autoSession): PblSmsOperator = {
    withSQL {
      update(PblSmsOperator).set(
        column.id -> entity.id,
        column.smsId -> entity.smsId,
        column.operatorId -> entity.operatorId
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblSmsOperator)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblSmsOperator).where.eq(column.id, entity.id) }.update.apply()
  }

}
