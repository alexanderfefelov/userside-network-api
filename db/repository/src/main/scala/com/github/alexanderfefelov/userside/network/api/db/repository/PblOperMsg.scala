package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblOperMsg(
  code: Int,
  answercode: Option[Int] = None,
  dateadd: Option[DateTime] = None,
  operfrom: Option[Int] = None,
  operto: Option[String] = None,
  opis: Option[String] = None,
  dateview: Option[String] = None,
  hidemsg: Option[String] = None,
  delmsg: Option[String] = None,
  isedit: Option[Short] = None) {

  def save()(implicit session: DBSession = PblOperMsg.autoSession): PblOperMsg = PblOperMsg.save(this)(session)

  def destroy()(implicit session: DBSession = PblOperMsg.autoSession): Int = PblOperMsg.destroy(this)(session)

}


object PblOperMsg extends SQLSyntaxSupport[PblOperMsg] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_oper_msg"

  override val columns = Seq("code", "answercode", "dateadd", "operfrom", "operto", "opis", "dateview", "hidemsg", "delmsg", "isedit")

  def apply(pom: SyntaxProvider[PblOperMsg])(rs: WrappedResultSet): PblOperMsg = autoConstruct(rs, pom)
  def apply(pom: ResultName[PblOperMsg])(rs: WrappedResultSet): PblOperMsg = autoConstruct(rs, pom)

  val pom = PblOperMsg.syntax("pom")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblOperMsg] = {
    withSQL {
      select.from(PblOperMsg as pom).where.eq(pom.code, code)
    }.map(PblOperMsg(pom.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblOperMsg] = {
    withSQL(select.from(PblOperMsg as pom)).map(PblOperMsg(pom.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblOperMsg as pom)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblOperMsg] = {
    withSQL {
      select.from(PblOperMsg as pom).where.append(where)
    }.map(PblOperMsg(pom.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblOperMsg] = {
    withSQL {
      select.from(PblOperMsg as pom).where.append(where)
    }.map(PblOperMsg(pom.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblOperMsg as pom).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    answercode: Option[Int] = None,
    dateadd: Option[DateTime] = None,
    operfrom: Option[Int] = None,
    operto: Option[String] = None,
    opis: Option[String] = None,
    dateview: Option[String] = None,
    hidemsg: Option[String] = None,
    delmsg: Option[String] = None,
    isedit: Option[Short] = None)(implicit session: DBSession = autoSession): PblOperMsg = {
    val generatedKey = withSQL {
      insert.into(PblOperMsg).namedValues(
        column.answercode -> answercode,
        column.dateadd -> dateadd,
        column.operfrom -> operfrom,
        column.operto -> operto,
        column.opis -> opis,
        column.dateview -> dateview,
        column.hidemsg -> hidemsg,
        column.delmsg -> delmsg,
        column.isedit -> isedit
      )
    }.updateAndReturnGeneratedKey.apply()

    PblOperMsg(
      code = generatedKey.toInt,
      answercode = answercode,
      dateadd = dateadd,
      operfrom = operfrom,
      operto = operto,
      opis = opis,
      dateview = dateview,
      hidemsg = hidemsg,
      delmsg = delmsg,
      isedit = isedit)
  }

  def batchInsert(entities: collection.Seq[PblOperMsg])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'answercode -> entity.answercode,
        'dateadd -> entity.dateadd,
        'operfrom -> entity.operfrom,
        'operto -> entity.operto,
        'opis -> entity.opis,
        'dateview -> entity.dateview,
        'hidemsg -> entity.hidemsg,
        'delmsg -> entity.delmsg,
        'isedit -> entity.isedit))
    SQL("""insert into pbl_oper_msg(
      answercode,
      dateadd,
      operfrom,
      operto,
      opis,
      dateview,
      hidemsg,
      delmsg,
      isedit
    ) values (
      {answercode},
      {dateadd},
      {operfrom},
      {operto},
      {opis},
      {dateview},
      {hidemsg},
      {delmsg},
      {isedit}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblOperMsg)(implicit session: DBSession = autoSession): PblOperMsg = {
    withSQL {
      update(PblOperMsg).set(
        column.code -> entity.code,
        column.answercode -> entity.answercode,
        column.dateadd -> entity.dateadd,
        column.operfrom -> entity.operfrom,
        column.operto -> entity.operto,
        column.opis -> entity.opis,
        column.dateview -> entity.dateview,
        column.hidemsg -> entity.hidemsg,
        column.delmsg -> entity.delmsg,
        column.isedit -> entity.isedit
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblOperMsg)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblOperMsg).where.eq(column.code, entity.code) }.update.apply()
  }

}
