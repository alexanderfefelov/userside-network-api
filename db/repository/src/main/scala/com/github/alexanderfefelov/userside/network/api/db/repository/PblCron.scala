package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime, LocalTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblCron(
  code: Int,
  stDay: Option[String] = None,
  stHour: Option[String] = None,
  stMinute: Option[String] = None,
  typercode: Option[Int] = None,
  nazv: Option[String] = None,
  lastuse: Option[DateTime] = None,
  param1: Option[String] = None,
  param2: Option[String] = None,
  iswork: Option[Short] = None,
  status: Option[Short] = None,
  lastdeliv: Option[LocalTime] = None) {

  def save()(implicit session: DBSession = PblCron.autoSession): PblCron = PblCron.save(this)(session)

  def destroy()(implicit session: DBSession = PblCron.autoSession): Int = PblCron.destroy(this)(session)

}


object PblCron extends SQLSyntaxSupport[PblCron] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_cron"

  override val columns = Seq("code", "st_day", "st_hour", "st_minute", "typercode", "nazv", "lastuse", "param1", "param2", "iswork", "status", "lastdeliv")

  def apply(pc: SyntaxProvider[PblCron])(rs: WrappedResultSet): PblCron = autoConstruct(rs, pc)
  def apply(pc: ResultName[PblCron])(rs: WrappedResultSet): PblCron = autoConstruct(rs, pc)

  val pc = PblCron.syntax("pc")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblCron] = {
    withSQL {
      select.from(PblCron as pc).where.eq(pc.code, code)
    }.map(PblCron(pc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblCron] = {
    withSQL(select.from(PblCron as pc)).map(PblCron(pc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblCron as pc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblCron] = {
    withSQL {
      select.from(PblCron as pc).where.append(where)
    }.map(PblCron(pc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblCron] = {
    withSQL {
      select.from(PblCron as pc).where.append(where)
    }.map(PblCron(pc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblCron as pc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    stDay: Option[String] = None,
    stHour: Option[String] = None,
    stMinute: Option[String] = None,
    typercode: Option[Int] = None,
    nazv: Option[String] = None,
    lastuse: Option[DateTime] = None,
    param1: Option[String] = None,
    param2: Option[String] = None,
    iswork: Option[Short] = None,
    status: Option[Short] = None,
    lastdeliv: Option[LocalTime] = None)(implicit session: DBSession = autoSession): PblCron = {
    val generatedKey = withSQL {
      insert.into(PblCron).namedValues(
        column.stDay -> stDay,
        column.stHour -> stHour,
        column.stMinute -> stMinute,
        column.typercode -> typercode,
        column.nazv -> nazv,
        column.lastuse -> lastuse,
        column.param1 -> param1,
        column.param2 -> param2,
        column.iswork -> iswork,
        column.status -> status,
        column.lastdeliv -> lastdeliv
      )
    }.updateAndReturnGeneratedKey.apply()

    PblCron(
      code = generatedKey.toInt,
      stDay = stDay,
      stHour = stHour,
      stMinute = stMinute,
      typercode = typercode,
      nazv = nazv,
      lastuse = lastuse,
      param1 = param1,
      param2 = param2,
      iswork = iswork,
      status = status,
      lastdeliv = lastdeliv)
  }

  def batchInsert(entities: collection.Seq[PblCron])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'stDay -> entity.stDay,
        'stHour -> entity.stHour,
        'stMinute -> entity.stMinute,
        'typercode -> entity.typercode,
        'nazv -> entity.nazv,
        'lastuse -> entity.lastuse,
        'param1 -> entity.param1,
        'param2 -> entity.param2,
        'iswork -> entity.iswork,
        'status -> entity.status,
        'lastdeliv -> entity.lastdeliv))
    SQL("""insert into pbl_cron(
      st_day,
      st_hour,
      st_minute,
      typercode,
      nazv,
      lastuse,
      param1,
      param2,
      iswork,
      status,
      lastdeliv
    ) values (
      {stDay},
      {stHour},
      {stMinute},
      {typercode},
      {nazv},
      {lastuse},
      {param1},
      {param2},
      {iswork},
      {status},
      {lastdeliv}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblCron)(implicit session: DBSession = autoSession): PblCron = {
    withSQL {
      update(PblCron).set(
        column.code -> entity.code,
        column.stDay -> entity.stDay,
        column.stHour -> entity.stHour,
        column.stMinute -> entity.stMinute,
        column.typercode -> entity.typercode,
        column.nazv -> entity.nazv,
        column.lastuse -> entity.lastuse,
        column.param1 -> entity.param1,
        column.param2 -> entity.param2,
        column.iswork -> entity.iswork,
        column.status -> entity.status,
        column.lastdeliv -> entity.lastdeliv
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblCron)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblCron).where.eq(column.code, entity.code) }.update.apply()
  }

}
