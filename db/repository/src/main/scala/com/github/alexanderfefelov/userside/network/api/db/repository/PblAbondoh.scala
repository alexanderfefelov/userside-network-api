package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblAbondoh(
  code: Int,
  datestat: Option[LocalDate] = None,
  doh: Option[BigDecimal] = None,
  users: Option[Int] = None) {

  def save()(implicit session: DBSession = PblAbondoh.autoSession): PblAbondoh = PblAbondoh.save(this)(session)

  def destroy()(implicit session: DBSession = PblAbondoh.autoSession): Int = PblAbondoh.destroy(this)(session)

}


object PblAbondoh extends SQLSyntaxSupport[PblAbondoh] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_abondoh"

  override val columns = Seq("code", "datestat", "doh", "users")

  def apply(pa: SyntaxProvider[PblAbondoh])(rs: WrappedResultSet): PblAbondoh = autoConstruct(rs, pa)
  def apply(pa: ResultName[PblAbondoh])(rs: WrappedResultSet): PblAbondoh = autoConstruct(rs, pa)

  val pa = PblAbondoh.syntax("pa")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblAbondoh] = {
    withSQL {
      select.from(PblAbondoh as pa).where.eq(pa.code, code)
    }.map(PblAbondoh(pa.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblAbondoh] = {
    withSQL(select.from(PblAbondoh as pa)).map(PblAbondoh(pa.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblAbondoh as pa)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblAbondoh] = {
    withSQL {
      select.from(PblAbondoh as pa).where.append(where)
    }.map(PblAbondoh(pa.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblAbondoh] = {
    withSQL {
      select.from(PblAbondoh as pa).where.append(where)
    }.map(PblAbondoh(pa.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblAbondoh as pa).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    datestat: Option[LocalDate] = None,
    doh: Option[BigDecimal] = None,
    users: Option[Int] = None)(implicit session: DBSession = autoSession): PblAbondoh = {
    val generatedKey = withSQL {
      insert.into(PblAbondoh).namedValues(
        column.datestat -> datestat,
        column.doh -> doh,
        column.users -> users
      )
    }.updateAndReturnGeneratedKey.apply()

    PblAbondoh(
      code = generatedKey.toInt,
      datestat = datestat,
      doh = doh,
      users = users)
  }

  def batchInsert(entities: collection.Seq[PblAbondoh])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'datestat -> entity.datestat,
        'doh -> entity.doh,
        'users -> entity.users))
    SQL("""insert into pbl_abondoh(
      datestat,
      doh,
      users
    ) values (
      {datestat},
      {doh},
      {users}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblAbondoh)(implicit session: DBSession = autoSession): PblAbondoh = {
    withSQL {
      update(PblAbondoh).set(
        column.code -> entity.code,
        column.datestat -> entity.datestat,
        column.doh -> entity.doh,
        column.users -> entity.users
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblAbondoh)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblAbondoh).where.eq(column.code, entity.code) }.update.apply()
  }

}
