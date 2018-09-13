package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblOperAddressAccess(
  id: Int,
  operatorId: Option[Int] = None,
  unitId: Option[Int] = None,
  isCurrent: Option[Short] = None) {

  def save()(implicit session: DBSession = PblOperAddressAccess.autoSession): PblOperAddressAccess = PblOperAddressAccess.save(this)(session)

  def destroy()(implicit session: DBSession = PblOperAddressAccess.autoSession): Int = PblOperAddressAccess.destroy(this)(session)

}


object PblOperAddressAccess extends SQLSyntaxSupport[PblOperAddressAccess] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_oper_address_access"

  override val columns = Seq("id", "operator_id", "unit_id", "is_current")

  def apply(poaa: SyntaxProvider[PblOperAddressAccess])(rs: WrappedResultSet): PblOperAddressAccess = autoConstruct(rs, poaa)
  def apply(poaa: ResultName[PblOperAddressAccess])(rs: WrappedResultSet): PblOperAddressAccess = autoConstruct(rs, poaa)

  val poaa = PblOperAddressAccess.syntax("poaa")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblOperAddressAccess] = {
    withSQL {
      select.from(PblOperAddressAccess as poaa).where.eq(poaa.id, id)
    }.map(PblOperAddressAccess(poaa.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblOperAddressAccess] = {
    withSQL(select.from(PblOperAddressAccess as poaa)).map(PblOperAddressAccess(poaa.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblOperAddressAccess as poaa)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblOperAddressAccess] = {
    withSQL {
      select.from(PblOperAddressAccess as poaa).where.append(where)
    }.map(PblOperAddressAccess(poaa.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblOperAddressAccess] = {
    withSQL {
      select.from(PblOperAddressAccess as poaa).where.append(where)
    }.map(PblOperAddressAccess(poaa.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblOperAddressAccess as poaa).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    operatorId: Option[Int] = None,
    unitId: Option[Int] = None,
    isCurrent: Option[Short] = None)(implicit session: DBSession = autoSession): PblOperAddressAccess = {
    val generatedKey = withSQL {
      insert.into(PblOperAddressAccess).namedValues(
        column.operatorId -> operatorId,
        column.unitId -> unitId,
        column.isCurrent -> isCurrent
      )
    }.updateAndReturnGeneratedKey.apply()

    PblOperAddressAccess(
      id = generatedKey.toInt,
      operatorId = operatorId,
      unitId = unitId,
      isCurrent = isCurrent)
  }

  def batchInsert(entities: collection.Seq[PblOperAddressAccess])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'operatorId -> entity.operatorId,
        'unitId -> entity.unitId,
        'isCurrent -> entity.isCurrent))
    SQL("""insert into pbl_oper_address_access(
      operator_id,
      unit_id,
      is_current
    ) values (
      {operatorId},
      {unitId},
      {isCurrent}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblOperAddressAccess)(implicit session: DBSession = autoSession): PblOperAddressAccess = {
    withSQL {
      update(PblOperAddressAccess).set(
        column.id -> entity.id,
        column.operatorId -> entity.operatorId,
        column.unitId -> entity.unitId,
        column.isCurrent -> entity.isCurrent
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblOperAddressAccess)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblOperAddressAccess).where.eq(column.id, entity.id) }.update.apply()
  }

}
