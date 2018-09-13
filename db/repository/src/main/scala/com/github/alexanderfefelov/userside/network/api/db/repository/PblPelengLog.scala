package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblPelengLog(
  code: Int,
  datestart: Option[DateTime] = None,
  devcode: Option[Int] = None,
  isdo: Option[Short] = None,
  allmac: Option[Int] = None,
  unkmac: Option[Int] = None,
  devtype: Option[Short] = None) {

  def save()(implicit session: DBSession = PblPelengLog.autoSession): PblPelengLog = PblPelengLog.save(this)(session)

  def destroy()(implicit session: DBSession = PblPelengLog.autoSession): Int = PblPelengLog.destroy(this)(session)

}


object PblPelengLog extends SQLSyntaxSupport[PblPelengLog] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_peleng_log"

  override val columns = Seq("code", "datestart", "devcode", "isdo", "allmac", "unkmac", "devtype")

  def apply(ppl: SyntaxProvider[PblPelengLog])(rs: WrappedResultSet): PblPelengLog = autoConstruct(rs, ppl)
  def apply(ppl: ResultName[PblPelengLog])(rs: WrappedResultSet): PblPelengLog = autoConstruct(rs, ppl)

  val ppl = PblPelengLog.syntax("ppl")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblPelengLog] = {
    withSQL {
      select.from(PblPelengLog as ppl).where.eq(ppl.code, code)
    }.map(PblPelengLog(ppl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblPelengLog] = {
    withSQL(select.from(PblPelengLog as ppl)).map(PblPelengLog(ppl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblPelengLog as ppl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblPelengLog] = {
    withSQL {
      select.from(PblPelengLog as ppl).where.append(where)
    }.map(PblPelengLog(ppl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblPelengLog] = {
    withSQL {
      select.from(PblPelengLog as ppl).where.append(where)
    }.map(PblPelengLog(ppl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblPelengLog as ppl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    datestart: Option[DateTime] = None,
    devcode: Option[Int] = None,
    isdo: Option[Short] = None,
    allmac: Option[Int] = None,
    unkmac: Option[Int] = None,
    devtype: Option[Short] = None)(implicit session: DBSession = autoSession): PblPelengLog = {
    val generatedKey = withSQL {
      insert.into(PblPelengLog).namedValues(
        column.datestart -> datestart,
        column.devcode -> devcode,
        column.isdo -> isdo,
        column.allmac -> allmac,
        column.unkmac -> unkmac,
        column.devtype -> devtype
      )
    }.updateAndReturnGeneratedKey.apply()

    PblPelengLog(
      code = generatedKey.toInt,
      datestart = datestart,
      devcode = devcode,
      isdo = isdo,
      allmac = allmac,
      unkmac = unkmac,
      devtype = devtype)
  }

  def batchInsert(entities: collection.Seq[PblPelengLog])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'datestart -> entity.datestart,
        'devcode -> entity.devcode,
        'isdo -> entity.isdo,
        'allmac -> entity.allmac,
        'unkmac -> entity.unkmac,
        'devtype -> entity.devtype))
    SQL("""insert into pbl_peleng_log(
      datestart,
      devcode,
      isdo,
      allmac,
      unkmac,
      devtype
    ) values (
      {datestart},
      {devcode},
      {isdo},
      {allmac},
      {unkmac},
      {devtype}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblPelengLog)(implicit session: DBSession = autoSession): PblPelengLog = {
    withSQL {
      update(PblPelengLog).set(
        column.code -> entity.code,
        column.datestart -> entity.datestart,
        column.devcode -> entity.devcode,
        column.isdo -> entity.isdo,
        column.allmac -> entity.allmac,
        column.unkmac -> entity.unkmac,
        column.devtype -> entity.devtype
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblPelengLog)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblPelengLog).where.eq(column.code, entity.code) }.update.apply()
  }

}
