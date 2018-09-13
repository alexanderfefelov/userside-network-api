package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblPers(
  code: Int,
  fio: Option[String] = None,
  dolg: Option[String] = None,
  datein: Option[LocalDate] = None,
  dateout: Option[LocalDate] = None,
  opis: Option[String] = None,
  iswork: Option[Short] = None,
  mainphone: Option[Int] = None,
  isdel: Option[Short] = None,
  smsuvedom: Option[Short] = None,
  mainmail: Option[Int] = None,
  mailuvedom: Option[Short] = None,
  mailuvedom2: Option[Short] = None,
  fioshort: Option[String] = None,
  geoAcc: Option[String] = None,
  pictcode: Option[Int] = None,
  actualPhone: Option[String] = None,
  actualMail: Option[String] = None,
  gpsId: Option[String] = None,
  isJabberMissTaskNotify: Option[Short] = None) {

  def save()(implicit session: DBSession = PblPers.autoSession): PblPers = PblPers.save(this)(session)

  def destroy()(implicit session: DBSession = PblPers.autoSession): Int = PblPers.destroy(this)(session)

}


object PblPers extends SQLSyntaxSupport[PblPers] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_pers"

  override val columns = Seq("code", "fio", "dolg", "datein", "dateout", "opis", "iswork", "mainphone", "isdel", "smsuvedom", "mainmail", "mailuvedom", "mailuvedom2", "fioshort", "geo_acc", "pictcode", "actual_phone", "actual_mail", "gps_id", "is_jabber_miss_task_notify")

  def apply(pp: SyntaxProvider[PblPers])(rs: WrappedResultSet): PblPers = autoConstruct(rs, pp)
  def apply(pp: ResultName[PblPers])(rs: WrappedResultSet): PblPers = autoConstruct(rs, pp)

  val pp = PblPers.syntax("pp")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblPers] = {
    withSQL {
      select.from(PblPers as pp).where.eq(pp.code, code)
    }.map(PblPers(pp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblPers] = {
    withSQL(select.from(PblPers as pp)).map(PblPers(pp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblPers as pp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblPers] = {
    withSQL {
      select.from(PblPers as pp).where.append(where)
    }.map(PblPers(pp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblPers] = {
    withSQL {
      select.from(PblPers as pp).where.append(where)
    }.map(PblPers(pp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblPers as pp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    fio: Option[String] = None,
    dolg: Option[String] = None,
    datein: Option[LocalDate] = None,
    dateout: Option[LocalDate] = None,
    opis: Option[String] = None,
    iswork: Option[Short] = None,
    mainphone: Option[Int] = None,
    isdel: Option[Short] = None,
    smsuvedom: Option[Short] = None,
    mainmail: Option[Int] = None,
    mailuvedom: Option[Short] = None,
    mailuvedom2: Option[Short] = None,
    fioshort: Option[String] = None,
    geoAcc: Option[String] = None,
    pictcode: Option[Int] = None,
    actualPhone: Option[String] = None,
    actualMail: Option[String] = None,
    gpsId: Option[String] = None,
    isJabberMissTaskNotify: Option[Short] = None)(implicit session: DBSession = autoSession): PblPers = {
    val generatedKey = withSQL {
      insert.into(PblPers).namedValues(
        column.fio -> fio,
        column.dolg -> dolg,
        column.datein -> datein,
        column.dateout -> dateout,
        column.opis -> opis,
        column.iswork -> iswork,
        column.mainphone -> mainphone,
        column.isdel -> isdel,
        column.smsuvedom -> smsuvedom,
        column.mainmail -> mainmail,
        column.mailuvedom -> mailuvedom,
        column.mailuvedom2 -> mailuvedom2,
        column.fioshort -> fioshort,
        column.geoAcc -> geoAcc,
        column.pictcode -> pictcode,
        column.actualPhone -> actualPhone,
        column.actualMail -> actualMail,
        column.gpsId -> gpsId,
        column.isJabberMissTaskNotify -> isJabberMissTaskNotify
      )
    }.updateAndReturnGeneratedKey.apply()

    PblPers(
      code = generatedKey.toInt,
      fio = fio,
      dolg = dolg,
      datein = datein,
      dateout = dateout,
      opis = opis,
      iswork = iswork,
      mainphone = mainphone,
      isdel = isdel,
      smsuvedom = smsuvedom,
      mainmail = mainmail,
      mailuvedom = mailuvedom,
      mailuvedom2 = mailuvedom2,
      fioshort = fioshort,
      geoAcc = geoAcc,
      pictcode = pictcode,
      actualPhone = actualPhone,
      actualMail = actualMail,
      gpsId = gpsId,
      isJabberMissTaskNotify = isJabberMissTaskNotify)
  }

  def batchInsert(entities: collection.Seq[PblPers])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'fio -> entity.fio,
        'dolg -> entity.dolg,
        'datein -> entity.datein,
        'dateout -> entity.dateout,
        'opis -> entity.opis,
        'iswork -> entity.iswork,
        'mainphone -> entity.mainphone,
        'isdel -> entity.isdel,
        'smsuvedom -> entity.smsuvedom,
        'mainmail -> entity.mainmail,
        'mailuvedom -> entity.mailuvedom,
        'mailuvedom2 -> entity.mailuvedom2,
        'fioshort -> entity.fioshort,
        'geoAcc -> entity.geoAcc,
        'pictcode -> entity.pictcode,
        'actualPhone -> entity.actualPhone,
        'actualMail -> entity.actualMail,
        'gpsId -> entity.gpsId,
        'isJabberMissTaskNotify -> entity.isJabberMissTaskNotify))
    SQL("""insert into pbl_pers(
      fio,
      dolg,
      datein,
      dateout,
      opis,
      iswork,
      mainphone,
      isdel,
      smsuvedom,
      mainmail,
      mailuvedom,
      mailuvedom2,
      fioshort,
      geo_acc,
      pictcode,
      actual_phone,
      actual_mail,
      gps_id,
      is_jabber_miss_task_notify
    ) values (
      {fio},
      {dolg},
      {datein},
      {dateout},
      {opis},
      {iswork},
      {mainphone},
      {isdel},
      {smsuvedom},
      {mainmail},
      {mailuvedom},
      {mailuvedom2},
      {fioshort},
      {geoAcc},
      {pictcode},
      {actualPhone},
      {actualMail},
      {gpsId},
      {isJabberMissTaskNotify}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblPers)(implicit session: DBSession = autoSession): PblPers = {
    withSQL {
      update(PblPers).set(
        column.code -> entity.code,
        column.fio -> entity.fio,
        column.dolg -> entity.dolg,
        column.datein -> entity.datein,
        column.dateout -> entity.dateout,
        column.opis -> entity.opis,
        column.iswork -> entity.iswork,
        column.mainphone -> entity.mainphone,
        column.isdel -> entity.isdel,
        column.smsuvedom -> entity.smsuvedom,
        column.mainmail -> entity.mainmail,
        column.mailuvedom -> entity.mailuvedom,
        column.mailuvedom2 -> entity.mailuvedom2,
        column.fioshort -> entity.fioshort,
        column.geoAcc -> entity.geoAcc,
        column.pictcode -> entity.pictcode,
        column.actualPhone -> entity.actualPhone,
        column.actualMail -> entity.actualMail,
        column.gpsId -> entity.gpsId,
        column.isJabberMissTaskNotify -> entity.isJabberMissTaskNotify
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblPers)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblPers).where.eq(column.code, entity.code) }.update.apply()
  }

}
