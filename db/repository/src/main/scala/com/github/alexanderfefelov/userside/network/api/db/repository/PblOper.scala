package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblOper(
  code: Int,
  oper: Option[String] = None,
  pass: Option[String] = None,
  fio: Option[String] = None,
  dolgnost: Option[String] = None,
  locked: Option[Short] = None,
  lastmsg: Option[DateTime] = None,
  profilecode: Option[Int] = None,
  lastact: Option[DateTime] = None,
  email: Option[String] = None,
  perscode: Option[Int] = None,
  cashe: Option[String] = None,
  geoAcc: Option[String] = None,
  curGeoAcc: Option[String] = None,
  adminWidget: Option[String] = None,
  lang: Option[String] = None,
  journalnews: Option[String] = None,
  ipphone: Option[String] = None,
  rightsList: Option[String] = None,
  accF: Option[String] = None,
  ipPhoneList: Option[String] = None,
  dateLastChangePassword: Option[DateTime] = None,
  ipAllowList: Option[String] = None) {

  def save()(implicit session: DBSession = PblOper.autoSession): PblOper = PblOper.save(this)(session)

  def destroy()(implicit session: DBSession = PblOper.autoSession): Int = PblOper.destroy(this)(session)

}


object PblOper extends SQLSyntaxSupport[PblOper] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_oper"

  override val columns = Seq("code", "oper", "pass", "fio", "dolgnost", "locked", "lastmsg", "profilecode", "lastact", "email", "perscode", "cashe", "geo_acc", "cur_geo_acc", "admin_widget", "lang", "journalnews", "ipphone", "rights_list", "acc_f", "ip_phone_list", "date_last_change_password", "ip_allow_list")

  def apply(po: SyntaxProvider[PblOper])(rs: WrappedResultSet): PblOper = autoConstruct(rs, po)
  def apply(po: ResultName[PblOper])(rs: WrappedResultSet): PblOper = autoConstruct(rs, po)

  val po = PblOper.syntax("po")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblOper] = {
    withSQL {
      select.from(PblOper as po).where.eq(po.code, code)
    }.map(PblOper(po.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblOper] = {
    withSQL(select.from(PblOper as po)).map(PblOper(po.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblOper as po)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblOper] = {
    withSQL {
      select.from(PblOper as po).where.append(where)
    }.map(PblOper(po.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblOper] = {
    withSQL {
      select.from(PblOper as po).where.append(where)
    }.map(PblOper(po.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblOper as po).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    oper: Option[String] = None,
    pass: Option[String] = None,
    fio: Option[String] = None,
    dolgnost: Option[String] = None,
    locked: Option[Short] = None,
    lastmsg: Option[DateTime] = None,
    profilecode: Option[Int] = None,
    lastact: Option[DateTime] = None,
    email: Option[String] = None,
    perscode: Option[Int] = None,
    cashe: Option[String] = None,
    geoAcc: Option[String] = None,
    curGeoAcc: Option[String] = None,
    adminWidget: Option[String] = None,
    lang: Option[String] = None,
    journalnews: Option[String] = None,
    ipphone: Option[String] = None,
    rightsList: Option[String] = None,
    accF: Option[String] = None,
    ipPhoneList: Option[String] = None,
    dateLastChangePassword: Option[DateTime] = None,
    ipAllowList: Option[String] = None)(implicit session: DBSession = autoSession): PblOper = {
    val generatedKey = withSQL {
      insert.into(PblOper).namedValues(
        column.oper -> oper,
        column.pass -> pass,
        column.fio -> fio,
        column.dolgnost -> dolgnost,
        column.locked -> locked,
        column.lastmsg -> lastmsg,
        column.profilecode -> profilecode,
        column.lastact -> lastact,
        column.email -> email,
        column.perscode -> perscode,
        column.cashe -> cashe,
        column.geoAcc -> geoAcc,
        column.curGeoAcc -> curGeoAcc,
        column.adminWidget -> adminWidget,
        column.lang -> lang,
        column.journalnews -> journalnews,
        column.ipphone -> ipphone,
        column.rightsList -> rightsList,
        column.accF -> accF,
        column.ipPhoneList -> ipPhoneList,
        column.dateLastChangePassword -> dateLastChangePassword,
        column.ipAllowList -> ipAllowList
      )
    }.updateAndReturnGeneratedKey.apply()

    PblOper(
      code = generatedKey.toInt,
      oper = oper,
      pass = pass,
      fio = fio,
      dolgnost = dolgnost,
      locked = locked,
      lastmsg = lastmsg,
      profilecode = profilecode,
      lastact = lastact,
      email = email,
      perscode = perscode,
      cashe = cashe,
      geoAcc = geoAcc,
      curGeoAcc = curGeoAcc,
      adminWidget = adminWidget,
      lang = lang,
      journalnews = journalnews,
      ipphone = ipphone,
      rightsList = rightsList,
      accF = accF,
      ipPhoneList = ipPhoneList,
      dateLastChangePassword = dateLastChangePassword,
      ipAllowList = ipAllowList)
  }

  def batchInsert(entities: collection.Seq[PblOper])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'oper -> entity.oper,
        'pass -> entity.pass,
        'fio -> entity.fio,
        'dolgnost -> entity.dolgnost,
        'locked -> entity.locked,
        'lastmsg -> entity.lastmsg,
        'profilecode -> entity.profilecode,
        'lastact -> entity.lastact,
        'email -> entity.email,
        'perscode -> entity.perscode,
        'cashe -> entity.cashe,
        'geoAcc -> entity.geoAcc,
        'curGeoAcc -> entity.curGeoAcc,
        'adminWidget -> entity.adminWidget,
        'lang -> entity.lang,
        'journalnews -> entity.journalnews,
        'ipphone -> entity.ipphone,
        'rightsList -> entity.rightsList,
        'accF -> entity.accF,
        'ipPhoneList -> entity.ipPhoneList,
        'dateLastChangePassword -> entity.dateLastChangePassword,
        'ipAllowList -> entity.ipAllowList))
    SQL("""insert into pbl_oper(
      oper,
      pass,
      fio,
      dolgnost,
      locked,
      lastmsg,
      profilecode,
      lastact,
      email,
      perscode,
      cashe,
      geo_acc,
      cur_geo_acc,
      admin_widget,
      lang,
      journalnews,
      ipphone,
      rights_list,
      acc_f,
      ip_phone_list,
      date_last_change_password,
      ip_allow_list
    ) values (
      {oper},
      {pass},
      {fio},
      {dolgnost},
      {locked},
      {lastmsg},
      {profilecode},
      {lastact},
      {email},
      {perscode},
      {cashe},
      {geoAcc},
      {curGeoAcc},
      {adminWidget},
      {lang},
      {journalnews},
      {ipphone},
      {rightsList},
      {accF},
      {ipPhoneList},
      {dateLastChangePassword},
      {ipAllowList}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblOper)(implicit session: DBSession = autoSession): PblOper = {
    withSQL {
      update(PblOper).set(
        column.code -> entity.code,
        column.oper -> entity.oper,
        column.pass -> entity.pass,
        column.fio -> entity.fio,
        column.dolgnost -> entity.dolgnost,
        column.locked -> entity.locked,
        column.lastmsg -> entity.lastmsg,
        column.profilecode -> entity.profilecode,
        column.lastact -> entity.lastact,
        column.email -> entity.email,
        column.perscode -> entity.perscode,
        column.cashe -> entity.cashe,
        column.geoAcc -> entity.geoAcc,
        column.curGeoAcc -> entity.curGeoAcc,
        column.adminWidget -> entity.adminWidget,
        column.lang -> entity.lang,
        column.journalnews -> entity.journalnews,
        column.ipphone -> entity.ipphone,
        column.rightsList -> entity.rightsList,
        column.accF -> entity.accF,
        column.ipPhoneList -> entity.ipPhoneList,
        column.dateLastChangePassword -> entity.dateLastChangePassword,
        column.ipAllowList -> entity.ipAllowList
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblOper)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblOper).where.eq(column.code, entity.code) }.update.apply()
  }

}
