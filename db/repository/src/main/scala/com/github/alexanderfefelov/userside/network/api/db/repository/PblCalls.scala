package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblCalls(
  code: Int,
  dateadd: Option[DateTime] = None,
  phone: Option[String] = None,
  asterid: Option[String] = None,
  usercode: Option[Int] = None,
  answerphone: Option[String] = None,
  additionalData: Option[String] = None) {

  def save()(implicit session: DBSession = PblCalls.autoSession): PblCalls = PblCalls.save(this)(session)

  def destroy()(implicit session: DBSession = PblCalls.autoSession): Int = PblCalls.destroy(this)(session)

}


object PblCalls extends SQLSyntaxSupport[PblCalls] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_calls"

  override val columns = Seq("code", "dateadd", "phone", "asterid", "usercode", "answerphone", "additional_data")

  def apply(pc: SyntaxProvider[PblCalls])(rs: WrappedResultSet): PblCalls = autoConstruct(rs, pc)
  def apply(pc: ResultName[PblCalls])(rs: WrappedResultSet): PblCalls = autoConstruct(rs, pc)

  val pc = PblCalls.syntax("pc")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblCalls] = {
    withSQL {
      select.from(PblCalls as pc).where.eq(pc.code, code)
    }.map(PblCalls(pc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblCalls] = {
    withSQL(select.from(PblCalls as pc)).map(PblCalls(pc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblCalls as pc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblCalls] = {
    withSQL {
      select.from(PblCalls as pc).where.append(where)
    }.map(PblCalls(pc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblCalls] = {
    withSQL {
      select.from(PblCalls as pc).where.append(where)
    }.map(PblCalls(pc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblCalls as pc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    dateadd: Option[DateTime] = None,
    phone: Option[String] = None,
    asterid: Option[String] = None,
    usercode: Option[Int] = None,
    answerphone: Option[String] = None,
    additionalData: Option[String] = None)(implicit session: DBSession = autoSession): PblCalls = {
    val generatedKey = withSQL {
      insert.into(PblCalls).namedValues(
        column.dateadd -> dateadd,
        column.phone -> phone,
        column.asterid -> asterid,
        column.usercode -> usercode,
        column.answerphone -> answerphone,
        column.additionalData -> additionalData
      )
    }.updateAndReturnGeneratedKey.apply()

    PblCalls(
      code = generatedKey.toInt,
      dateadd = dateadd,
      phone = phone,
      asterid = asterid,
      usercode = usercode,
      answerphone = answerphone,
      additionalData = additionalData)
  }

  def batchInsert(entities: collection.Seq[PblCalls])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'dateadd -> entity.dateadd,
        'phone -> entity.phone,
        'asterid -> entity.asterid,
        'usercode -> entity.usercode,
        'answerphone -> entity.answerphone,
        'additionalData -> entity.additionalData))
    SQL("""insert into pbl_calls(
      dateadd,
      phone,
      asterid,
      usercode,
      answerphone,
      additional_data
    ) values (
      {dateadd},
      {phone},
      {asterid},
      {usercode},
      {answerphone},
      {additionalData}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblCalls)(implicit session: DBSession = autoSession): PblCalls = {
    withSQL {
      update(PblCalls).set(
        column.code -> entity.code,
        column.dateadd -> entity.dateadd,
        column.phone -> entity.phone,
        column.asterid -> entity.asterid,
        column.usercode -> entity.usercode,
        column.answerphone -> entity.answerphone,
        column.additionalData -> entity.additionalData
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblCalls)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblCalls).where.eq(column.code, entity.code) }.update.apply()
  }

}
