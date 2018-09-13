package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblTovarsklad(
  code: Int,
  nazv: Option[String] = None,
  opis: Option[String] = None,
  operAcc: Option[String] = None,
  operAccRead: Option[String] = None) {

  def save()(implicit session: DBSession = PblTovarsklad.autoSession): PblTovarsklad = PblTovarsklad.save(this)(session)

  def destroy()(implicit session: DBSession = PblTovarsklad.autoSession): Int = PblTovarsklad.destroy(this)(session)

}


object PblTovarsklad extends SQLSyntaxSupport[PblTovarsklad] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_tovarsklad"

  override val columns = Seq("code", "nazv", "opis", "oper_acc", "oper_acc_read")

  def apply(pt: SyntaxProvider[PblTovarsklad])(rs: WrappedResultSet): PblTovarsklad = autoConstruct(rs, pt)
  def apply(pt: ResultName[PblTovarsklad])(rs: WrappedResultSet): PblTovarsklad = autoConstruct(rs, pt)

  val pt = PblTovarsklad.syntax("pt")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblTovarsklad] = {
    withSQL {
      select.from(PblTovarsklad as pt).where.eq(pt.code, code)
    }.map(PblTovarsklad(pt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblTovarsklad] = {
    withSQL(select.from(PblTovarsklad as pt)).map(PblTovarsklad(pt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblTovarsklad as pt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblTovarsklad] = {
    withSQL {
      select.from(PblTovarsklad as pt).where.append(where)
    }.map(PblTovarsklad(pt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblTovarsklad] = {
    withSQL {
      select.from(PblTovarsklad as pt).where.append(where)
    }.map(PblTovarsklad(pt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblTovarsklad as pt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    nazv: Option[String] = None,
    opis: Option[String] = None,
    operAcc: Option[String] = None,
    operAccRead: Option[String] = None)(implicit session: DBSession = autoSession): PblTovarsklad = {
    val generatedKey = withSQL {
      insert.into(PblTovarsklad).namedValues(
        column.nazv -> nazv,
        column.opis -> opis,
        column.operAcc -> operAcc,
        column.operAccRead -> operAccRead
      )
    }.updateAndReturnGeneratedKey.apply()

    PblTovarsklad(
      code = generatedKey.toInt,
      nazv = nazv,
      opis = opis,
      operAcc = operAcc,
      operAccRead = operAccRead)
  }

  def batchInsert(entities: collection.Seq[PblTovarsklad])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'nazv -> entity.nazv,
        'opis -> entity.opis,
        'operAcc -> entity.operAcc,
        'operAccRead -> entity.operAccRead))
    SQL("""insert into pbl_tovarsklad(
      nazv,
      opis,
      oper_acc,
      oper_acc_read
    ) values (
      {nazv},
      {opis},
      {operAcc},
      {operAccRead}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblTovarsklad)(implicit session: DBSession = autoSession): PblTovarsklad = {
    withSQL {
      update(PblTovarsklad).set(
        column.code -> entity.code,
        column.nazv -> entity.nazv,
        column.opis -> entity.opis,
        column.operAcc -> entity.operAcc,
        column.operAccRead -> entity.operAccRead
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblTovarsklad)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblTovarsklad).where.eq(column.code, entity.code) }.update.apply()
  }

}
