package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblCeleb(
  code: Int,
  nazv: Option[String] = None,
  datestart: Option[LocalDate] = None,
  datestop: Option[LocalDate] = None,
  active: Option[Short] = None) {

  def save()(implicit session: DBSession = PblCeleb.autoSession): PblCeleb = PblCeleb.save(this)(session)

  def destroy()(implicit session: DBSession = PblCeleb.autoSession): Int = PblCeleb.destroy(this)(session)

}


object PblCeleb extends SQLSyntaxSupport[PblCeleb] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_celeb"

  override val columns = Seq("code", "nazv", "datestart", "datestop", "active")

  def apply(pc: SyntaxProvider[PblCeleb])(rs: WrappedResultSet): PblCeleb = autoConstruct(rs, pc)
  def apply(pc: ResultName[PblCeleb])(rs: WrappedResultSet): PblCeleb = autoConstruct(rs, pc)

  val pc = PblCeleb.syntax("pc")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblCeleb] = {
    withSQL {
      select.from(PblCeleb as pc).where.eq(pc.code, code)
    }.map(PblCeleb(pc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblCeleb] = {
    withSQL(select.from(PblCeleb as pc)).map(PblCeleb(pc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblCeleb as pc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblCeleb] = {
    withSQL {
      select.from(PblCeleb as pc).where.append(where)
    }.map(PblCeleb(pc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblCeleb] = {
    withSQL {
      select.from(PblCeleb as pc).where.append(where)
    }.map(PblCeleb(pc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblCeleb as pc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    nazv: Option[String] = None,
    datestart: Option[LocalDate] = None,
    datestop: Option[LocalDate] = None,
    active: Option[Short] = None)(implicit session: DBSession = autoSession): PblCeleb = {
    val generatedKey = withSQL {
      insert.into(PblCeleb).namedValues(
        column.nazv -> nazv,
        column.datestart -> datestart,
        column.datestop -> datestop,
        column.active -> active
      )
    }.updateAndReturnGeneratedKey.apply()

    PblCeleb(
      code = generatedKey.toInt,
      nazv = nazv,
      datestart = datestart,
      datestop = datestop,
      active = active)
  }

  def batchInsert(entities: collection.Seq[PblCeleb])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'nazv -> entity.nazv,
        'datestart -> entity.datestart,
        'datestop -> entity.datestop,
        'active -> entity.active))
    SQL("""insert into pbl_celeb(
      nazv,
      datestart,
      datestop,
      active
    ) values (
      {nazv},
      {datestart},
      {datestop},
      {active}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblCeleb)(implicit session: DBSession = autoSession): PblCeleb = {
    withSQL {
      update(PblCeleb).set(
        column.code -> entity.code,
        column.nazv -> entity.nazv,
        column.datestart -> entity.datestart,
        column.datestop -> entity.datestop,
        column.active -> entity.active
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblCeleb)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblCeleb).where.eq(column.code, entity.code) }.update.apply()
  }

}
