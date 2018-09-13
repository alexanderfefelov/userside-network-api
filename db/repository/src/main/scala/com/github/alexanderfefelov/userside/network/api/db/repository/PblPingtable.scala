package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblPingtable(
  code: Int,
  datestat: Option[DateTime] = None,
  count: Option[Int] = None) {

  def save()(implicit session: DBSession = PblPingtable.autoSession): PblPingtable = PblPingtable.save(this)(session)

  def destroy()(implicit session: DBSession = PblPingtable.autoSession): Int = PblPingtable.destroy(this)(session)

}


object PblPingtable extends SQLSyntaxSupport[PblPingtable] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_pingtable"

  override val columns = Seq("code", "datestat", "count")

  def apply(pp: SyntaxProvider[PblPingtable])(rs: WrappedResultSet): PblPingtable = autoConstruct(rs, pp)
  def apply(pp: ResultName[PblPingtable])(rs: WrappedResultSet): PblPingtable = autoConstruct(rs, pp)

  val pp = PblPingtable.syntax("pp")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblPingtable] = {
    withSQL {
      select.from(PblPingtable as pp).where.eq(pp.code, code)
    }.map(PblPingtable(pp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblPingtable] = {
    withSQL(select.from(PblPingtable as pp)).map(PblPingtable(pp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblPingtable as pp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblPingtable] = {
    withSQL {
      select.from(PblPingtable as pp).where.append(where)
    }.map(PblPingtable(pp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblPingtable] = {
    withSQL {
      select.from(PblPingtable as pp).where.append(where)
    }.map(PblPingtable(pp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblPingtable as pp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    datestat: Option[DateTime] = None,
    count: Option[Int] = None)(implicit session: DBSession = autoSession): PblPingtable = {
    val generatedKey = withSQL {
      insert.into(PblPingtable).namedValues(
        column.datestat -> datestat,
        column.count -> count
      )
    }.updateAndReturnGeneratedKey.apply()

    PblPingtable(
      code = generatedKey.toInt,
      datestat = datestat,
      count = count)
  }

  def batchInsert(entities: collection.Seq[PblPingtable])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'datestat -> entity.datestat,
        'count -> entity.count))
    SQL("""insert into pbl_pingtable(
      datestat,
      count
    ) values (
      {datestat},
      {count}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblPingtable)(implicit session: DBSession = autoSession): PblPingtable = {
    withSQL {
      update(PblPingtable).set(
        column.code -> entity.code,
        column.datestat -> entity.datestat,
        column.count -> entity.count
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblPingtable)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblPingtable).where.eq(column.code, entity.code) }.update.apply()
  }

}
