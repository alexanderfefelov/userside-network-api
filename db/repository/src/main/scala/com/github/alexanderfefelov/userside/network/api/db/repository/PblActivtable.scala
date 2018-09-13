package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblActivtable(
  code: Int,
  datestat: Option[DateTime] = None,
  count: Option[Int] = None) {

  def save()(implicit session: DBSession = PblActivtable.autoSession): PblActivtable = PblActivtable.save(this)(session)

  def destroy()(implicit session: DBSession = PblActivtable.autoSession): Int = PblActivtable.destroy(this)(session)

}


object PblActivtable extends SQLSyntaxSupport[PblActivtable] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_activtable"

  override val columns = Seq("code", "datestat", "count")

  def apply(pa: SyntaxProvider[PblActivtable])(rs: WrappedResultSet): PblActivtable = autoConstruct(rs, pa)
  def apply(pa: ResultName[PblActivtable])(rs: WrappedResultSet): PblActivtable = autoConstruct(rs, pa)

  val pa = PblActivtable.syntax("pa")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblActivtable] = {
    withSQL {
      select.from(PblActivtable as pa).where.eq(pa.code, code)
    }.map(PblActivtable(pa.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblActivtable] = {
    withSQL(select.from(PblActivtable as pa)).map(PblActivtable(pa.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblActivtable as pa)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblActivtable] = {
    withSQL {
      select.from(PblActivtable as pa).where.append(where)
    }.map(PblActivtable(pa.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblActivtable] = {
    withSQL {
      select.from(PblActivtable as pa).where.append(where)
    }.map(PblActivtable(pa.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblActivtable as pa).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    datestat: Option[DateTime] = None,
    count: Option[Int] = None)(implicit session: DBSession = autoSession): PblActivtable = {
    val generatedKey = withSQL {
      insert.into(PblActivtable).namedValues(
        column.datestat -> datestat,
        column.count -> count
      )
    }.updateAndReturnGeneratedKey.apply()

    PblActivtable(
      code = generatedKey.toInt,
      datestat = datestat,
      count = count)
  }

  def batchInsert(entities: collection.Seq[PblActivtable])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'datestat -> entity.datestat,
        'count -> entity.count))
    SQL("""insert into pbl_activtable(
      datestat,
      count
    ) values (
      {datestat},
      {count}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblActivtable)(implicit session: DBSession = autoSession): PblActivtable = {
    withSQL {
      update(PblActivtable).set(
        column.code -> entity.code,
        column.datestat -> entity.datestat,
        column.count -> entity.count
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblActivtable)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblActivtable).where.eq(column.code, entity.code) }.update.apply()
  }

}
