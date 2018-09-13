package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblTrouble(
  code: Int,
  usercode: Option[Int] = None,
  datestart: Option[DateTime] = None,
  opis: Option[String] = None,
  oper: Option[Int] = None,
  datefinish: Option[DateTime] = None,
  opisanswer: Option[String] = None,
  dateview: Option[DateTime] = None,
  isarc: Option[Short] = None,
  billingMsgId: Option[Int] = None) {

  def save()(implicit session: DBSession = PblTrouble.autoSession): PblTrouble = PblTrouble.save(this)(session)

  def destroy()(implicit session: DBSession = PblTrouble.autoSession): Int = PblTrouble.destroy(this)(session)

}


object PblTrouble extends SQLSyntaxSupport[PblTrouble] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_trouble"

  override val columns = Seq("code", "usercode", "datestart", "opis", "oper", "datefinish", "opisanswer", "dateview", "isarc", "billing_msg_id")

  def apply(pt: SyntaxProvider[PblTrouble])(rs: WrappedResultSet): PblTrouble = autoConstruct(rs, pt)
  def apply(pt: ResultName[PblTrouble])(rs: WrappedResultSet): PblTrouble = autoConstruct(rs, pt)

  val pt = PblTrouble.syntax("pt")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblTrouble] = {
    withSQL {
      select.from(PblTrouble as pt).where.eq(pt.code, code)
    }.map(PblTrouble(pt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblTrouble] = {
    withSQL(select.from(PblTrouble as pt)).map(PblTrouble(pt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblTrouble as pt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblTrouble] = {
    withSQL {
      select.from(PblTrouble as pt).where.append(where)
    }.map(PblTrouble(pt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblTrouble] = {
    withSQL {
      select.from(PblTrouble as pt).where.append(where)
    }.map(PblTrouble(pt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblTrouble as pt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    usercode: Option[Int] = None,
    datestart: Option[DateTime] = None,
    opis: Option[String] = None,
    oper: Option[Int] = None,
    datefinish: Option[DateTime] = None,
    opisanswer: Option[String] = None,
    dateview: Option[DateTime] = None,
    isarc: Option[Short] = None,
    billingMsgId: Option[Int] = None)(implicit session: DBSession = autoSession): PblTrouble = {
    val generatedKey = withSQL {
      insert.into(PblTrouble).namedValues(
        column.usercode -> usercode,
        column.datestart -> datestart,
        column.opis -> opis,
        column.oper -> oper,
        column.datefinish -> datefinish,
        column.opisanswer -> opisanswer,
        column.dateview -> dateview,
        column.isarc -> isarc,
        column.billingMsgId -> billingMsgId
      )
    }.updateAndReturnGeneratedKey.apply()

    PblTrouble(
      code = generatedKey.toInt,
      usercode = usercode,
      datestart = datestart,
      opis = opis,
      oper = oper,
      datefinish = datefinish,
      opisanswer = opisanswer,
      dateview = dateview,
      isarc = isarc,
      billingMsgId = billingMsgId)
  }

  def batchInsert(entities: collection.Seq[PblTrouble])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'usercode -> entity.usercode,
        'datestart -> entity.datestart,
        'opis -> entity.opis,
        'oper -> entity.oper,
        'datefinish -> entity.datefinish,
        'opisanswer -> entity.opisanswer,
        'dateview -> entity.dateview,
        'isarc -> entity.isarc,
        'billingMsgId -> entity.billingMsgId))
    SQL("""insert into pbl_trouble(
      usercode,
      datestart,
      opis,
      oper,
      datefinish,
      opisanswer,
      dateview,
      isarc,
      billing_msg_id
    ) values (
      {usercode},
      {datestart},
      {opis},
      {oper},
      {datefinish},
      {opisanswer},
      {dateview},
      {isarc},
      {billingMsgId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblTrouble)(implicit session: DBSession = autoSession): PblTrouble = {
    withSQL {
      update(PblTrouble).set(
        column.code -> entity.code,
        column.usercode -> entity.usercode,
        column.datestart -> entity.datestart,
        column.opis -> entity.opis,
        column.oper -> entity.oper,
        column.datefinish -> entity.datefinish,
        column.opisanswer -> entity.opisanswer,
        column.dateview -> entity.dateview,
        column.isarc -> entity.isarc,
        column.billingMsgId -> entity.billingMsgId
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblTrouble)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblTrouble).where.eq(column.code, entity.code) }.update.apply()
  }

}
