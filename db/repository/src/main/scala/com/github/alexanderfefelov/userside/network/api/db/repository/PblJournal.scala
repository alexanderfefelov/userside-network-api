package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblJournal(
  code: Int,
  status: Option[Int] = None,
  datestatus: Option[DateTime] = None,
  housecode: Option[Int] = None,
  typer: Option[Int] = None,
  usercode: Option[Int] = None,
  dateadd: Option[DateTime] = None,
  datefinish: Option[DateTime] = None,
  datedo: Option[DateTime] = None,
  opis: Option[String] = None,
  newklient: Option[String] = None,
  fromaprove: Option[String] = None,
  uzelcode: Option[Int] = None,
  priority: Option[Short] = None,
  apart: Option[String] = None,
  parentcode: Option[Int] = None,
  deadline: Option[BigDecimal] = None,
  opercode: Option[Int] = None,
  citycode: Option[Int] = None,
  onmain: Option[Short] = None,
  dateupd: Option[DateTime] = None,
  datemust: Option[DateTime] = None,
  apartB: Option[String] = None,
  timeToWork: Option[Int] = None,
  commentShort: Option[String] = None,
  isDelete: Option[Short] = None,
  isPrivate: Option[Short] = None,
  dateHold: Option[DateTime] = None) {

  def save()(implicit session: DBSession = PblJournal.autoSession): PblJournal = PblJournal.save(this)(session)

  def destroy()(implicit session: DBSession = PblJournal.autoSession): Int = PblJournal.destroy(this)(session)

}


object PblJournal extends SQLSyntaxSupport[PblJournal] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_journal"

  override val columns = Seq("code", "status", "datestatus", "housecode", "typer", "usercode", "dateadd", "datefinish", "datedo", "opis", "newklient", "fromaprove", "uzelcode", "priority", "apart", "parentcode", "deadline", "opercode", "citycode", "onmain", "dateupd", "datemust", "apart_b", "time_to_work", "comment_short", "is_delete", "is_private", "date_hold")

  def apply(pj: SyntaxProvider[PblJournal])(rs: WrappedResultSet): PblJournal = autoConstruct(rs, pj)
  def apply(pj: ResultName[PblJournal])(rs: WrappedResultSet): PblJournal = autoConstruct(rs, pj)

  val pj = PblJournal.syntax("pj")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblJournal] = {
    withSQL {
      select.from(PblJournal as pj).where.eq(pj.code, code)
    }.map(PblJournal(pj.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblJournal] = {
    withSQL(select.from(PblJournal as pj)).map(PblJournal(pj.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblJournal as pj)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblJournal] = {
    withSQL {
      select.from(PblJournal as pj).where.append(where)
    }.map(PblJournal(pj.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblJournal] = {
    withSQL {
      select.from(PblJournal as pj).where.append(where)
    }.map(PblJournal(pj.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblJournal as pj).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    status: Option[Int] = None,
    datestatus: Option[DateTime] = None,
    housecode: Option[Int] = None,
    typer: Option[Int] = None,
    usercode: Option[Int] = None,
    dateadd: Option[DateTime] = None,
    datefinish: Option[DateTime] = None,
    datedo: Option[DateTime] = None,
    opis: Option[String] = None,
    newklient: Option[String] = None,
    fromaprove: Option[String] = None,
    uzelcode: Option[Int] = None,
    priority: Option[Short] = None,
    apart: Option[String] = None,
    parentcode: Option[Int] = None,
    deadline: Option[BigDecimal] = None,
    opercode: Option[Int] = None,
    citycode: Option[Int] = None,
    onmain: Option[Short] = None,
    dateupd: Option[DateTime] = None,
    datemust: Option[DateTime] = None,
    apartB: Option[String] = None,
    timeToWork: Option[Int] = None,
    commentShort: Option[String] = None,
    isDelete: Option[Short] = None,
    isPrivate: Option[Short] = None,
    dateHold: Option[DateTime] = None)(implicit session: DBSession = autoSession): PblJournal = {
    val generatedKey = withSQL {
      insert.into(PblJournal).namedValues(
        column.status -> status,
        column.datestatus -> datestatus,
        column.housecode -> housecode,
        column.typer -> typer,
        column.usercode -> usercode,
        column.dateadd -> dateadd,
        column.datefinish -> datefinish,
        column.datedo -> datedo,
        column.opis -> opis,
        column.newklient -> newklient,
        column.fromaprove -> fromaprove,
        column.uzelcode -> uzelcode,
        column.priority -> priority,
        column.apart -> apart,
        column.parentcode -> parentcode,
        column.deadline -> deadline,
        column.opercode -> opercode,
        column.citycode -> citycode,
        column.onmain -> onmain,
        column.dateupd -> dateupd,
        column.datemust -> datemust,
        column.apartB -> apartB,
        column.timeToWork -> timeToWork,
        column.commentShort -> commentShort,
        column.isDelete -> isDelete,
        column.isPrivate -> isPrivate,
        column.dateHold -> dateHold
      )
    }.updateAndReturnGeneratedKey.apply()

    PblJournal(
      code = generatedKey.toInt,
      status = status,
      datestatus = datestatus,
      housecode = housecode,
      typer = typer,
      usercode = usercode,
      dateadd = dateadd,
      datefinish = datefinish,
      datedo = datedo,
      opis = opis,
      newklient = newklient,
      fromaprove = fromaprove,
      uzelcode = uzelcode,
      priority = priority,
      apart = apart,
      parentcode = parentcode,
      deadline = deadline,
      opercode = opercode,
      citycode = citycode,
      onmain = onmain,
      dateupd = dateupd,
      datemust = datemust,
      apartB = apartB,
      timeToWork = timeToWork,
      commentShort = commentShort,
      isDelete = isDelete,
      isPrivate = isPrivate,
      dateHold = dateHold)
  }

  def batchInsert(entities: collection.Seq[PblJournal])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'status -> entity.status,
        'datestatus -> entity.datestatus,
        'housecode -> entity.housecode,
        'typer -> entity.typer,
        'usercode -> entity.usercode,
        'dateadd -> entity.dateadd,
        'datefinish -> entity.datefinish,
        'datedo -> entity.datedo,
        'opis -> entity.opis,
        'newklient -> entity.newklient,
        'fromaprove -> entity.fromaprove,
        'uzelcode -> entity.uzelcode,
        'priority -> entity.priority,
        'apart -> entity.apart,
        'parentcode -> entity.parentcode,
        'deadline -> entity.deadline,
        'opercode -> entity.opercode,
        'citycode -> entity.citycode,
        'onmain -> entity.onmain,
        'dateupd -> entity.dateupd,
        'datemust -> entity.datemust,
        'apartB -> entity.apartB,
        'timeToWork -> entity.timeToWork,
        'commentShort -> entity.commentShort,
        'isDelete -> entity.isDelete,
        'isPrivate -> entity.isPrivate,
        'dateHold -> entity.dateHold))
    SQL("""insert into pbl_journal(
      status,
      datestatus,
      housecode,
      typer,
      usercode,
      dateadd,
      datefinish,
      datedo,
      opis,
      newklient,
      fromaprove,
      uzelcode,
      priority,
      apart,
      parentcode,
      deadline,
      opercode,
      citycode,
      onmain,
      dateupd,
      datemust,
      apart_b,
      time_to_work,
      comment_short,
      is_delete,
      is_private,
      date_hold
    ) values (
      {status},
      {datestatus},
      {housecode},
      {typer},
      {usercode},
      {dateadd},
      {datefinish},
      {datedo},
      {opis},
      {newklient},
      {fromaprove},
      {uzelcode},
      {priority},
      {apart},
      {parentcode},
      {deadline},
      {opercode},
      {citycode},
      {onmain},
      {dateupd},
      {datemust},
      {apartB},
      {timeToWork},
      {commentShort},
      {isDelete},
      {isPrivate},
      {dateHold}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblJournal)(implicit session: DBSession = autoSession): PblJournal = {
    withSQL {
      update(PblJournal).set(
        column.code -> entity.code,
        column.status -> entity.status,
        column.datestatus -> entity.datestatus,
        column.housecode -> entity.housecode,
        column.typer -> entity.typer,
        column.usercode -> entity.usercode,
        column.dateadd -> entity.dateadd,
        column.datefinish -> entity.datefinish,
        column.datedo -> entity.datedo,
        column.opis -> entity.opis,
        column.newklient -> entity.newklient,
        column.fromaprove -> entity.fromaprove,
        column.uzelcode -> entity.uzelcode,
        column.priority -> entity.priority,
        column.apart -> entity.apart,
        column.parentcode -> entity.parentcode,
        column.deadline -> entity.deadline,
        column.opercode -> entity.opercode,
        column.citycode -> entity.citycode,
        column.onmain -> entity.onmain,
        column.dateupd -> entity.dateupd,
        column.datemust -> entity.datemust,
        column.apartB -> entity.apartB,
        column.timeToWork -> entity.timeToWork,
        column.commentShort -> entity.commentShort,
        column.isDelete -> entity.isDelete,
        column.isPrivate -> entity.isPrivate,
        column.dateHold -> entity.dateHold
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblJournal)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblJournal).where.eq(column.code, entity.code) }.update.apply()
  }

}
