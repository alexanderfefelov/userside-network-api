package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblSmslist(
  code: Int,
  nazv: Option[String] = None,
  smstext: Option[String] = None,
  dateadd: Option[DateTime] = None,
  ifopis: Option[String] = None,
  datedo: Option[DateTime] = None) {

  def save()(implicit session: DBSession = PblSmslist.autoSession): PblSmslist = PblSmslist.save(this)(session)

  def destroy()(implicit session: DBSession = PblSmslist.autoSession): Int = PblSmslist.destroy(this)(session)

}


object PblSmslist extends SQLSyntaxSupport[PblSmslist] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_smslist"

  override val columns = Seq("code", "nazv", "smstext", "dateadd", "ifopis", "datedo")

  def apply(ps: SyntaxProvider[PblSmslist])(rs: WrappedResultSet): PblSmslist = autoConstruct(rs, ps)
  def apply(ps: ResultName[PblSmslist])(rs: WrappedResultSet): PblSmslist = autoConstruct(rs, ps)

  val ps = PblSmslist.syntax("ps")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblSmslist] = {
    withSQL {
      select.from(PblSmslist as ps).where.eq(ps.code, code)
    }.map(PblSmslist(ps.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblSmslist] = {
    withSQL(select.from(PblSmslist as ps)).map(PblSmslist(ps.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblSmslist as ps)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblSmslist] = {
    withSQL {
      select.from(PblSmslist as ps).where.append(where)
    }.map(PblSmslist(ps.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblSmslist] = {
    withSQL {
      select.from(PblSmslist as ps).where.append(where)
    }.map(PblSmslist(ps.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblSmslist as ps).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    nazv: Option[String] = None,
    smstext: Option[String] = None,
    dateadd: Option[DateTime] = None,
    ifopis: Option[String] = None,
    datedo: Option[DateTime] = None)(implicit session: DBSession = autoSession): PblSmslist = {
    val generatedKey = withSQL {
      insert.into(PblSmslist).namedValues(
        column.nazv -> nazv,
        column.smstext -> smstext,
        column.dateadd -> dateadd,
        column.ifopis -> ifopis,
        column.datedo -> datedo
      )
    }.updateAndReturnGeneratedKey.apply()

    PblSmslist(
      code = generatedKey.toInt,
      nazv = nazv,
      smstext = smstext,
      dateadd = dateadd,
      ifopis = ifopis,
      datedo = datedo)
  }

  def batchInsert(entities: collection.Seq[PblSmslist])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'nazv -> entity.nazv,
        'smstext -> entity.smstext,
        'dateadd -> entity.dateadd,
        'ifopis -> entity.ifopis,
        'datedo -> entity.datedo))
    SQL("""insert into pbl_smslist(
      nazv,
      smstext,
      dateadd,
      ifopis,
      datedo
    ) values (
      {nazv},
      {smstext},
      {dateadd},
      {ifopis},
      {datedo}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblSmslist)(implicit session: DBSession = autoSession): PblSmslist = {
    withSQL {
      update(PblSmslist).set(
        column.code -> entity.code,
        column.nazv -> entity.nazv,
        column.smstext -> entity.smstext,
        column.dateadd -> entity.dateadd,
        column.ifopis -> entity.ifopis,
        column.datedo -> entity.datedo
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblSmslist)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblSmslist).where.eq(column.code, entity.code) }.update.apply()
  }

}
