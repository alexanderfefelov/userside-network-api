package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblPelengMac(
  code: Int,
  devcode: Option[Int] = None,
  devstack: Option[Int] = None,
  devport: Option[Int] = None,
  mac: Option[String] = None,
  datefirst: Option[DateTime] = None,
  datelast: Option[DateTime] = None,
  inbase: Option[Short] = None,
  devtype: Option[Short] = None,
  vid: Option[Int] = None,
  ifaceCaption: Option[String] = None) {

  def save()(implicit session: DBSession = PblPelengMac.autoSession): PblPelengMac = PblPelengMac.save(this)(session)

  def destroy()(implicit session: DBSession = PblPelengMac.autoSession): Int = PblPelengMac.destroy(this)(session)

}


object PblPelengMac extends SQLSyntaxSupport[PblPelengMac] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_peleng_mac"

  override val columns = Seq("code", "devcode", "devstack", "devport", "mac", "datefirst", "datelast", "inbase", "devtype", "vid", "iface_caption")

  def apply(ppm: SyntaxProvider[PblPelengMac])(rs: WrappedResultSet): PblPelengMac = autoConstruct(rs, ppm)
  def apply(ppm: ResultName[PblPelengMac])(rs: WrappedResultSet): PblPelengMac = autoConstruct(rs, ppm)

  val ppm = PblPelengMac.syntax("ppm")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblPelengMac] = {
    withSQL {
      select.from(PblPelengMac as ppm).where.eq(ppm.code, code)
    }.map(PblPelengMac(ppm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblPelengMac] = {
    withSQL(select.from(PblPelengMac as ppm)).map(PblPelengMac(ppm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblPelengMac as ppm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblPelengMac] = {
    withSQL {
      select.from(PblPelengMac as ppm).where.append(where)
    }.map(PblPelengMac(ppm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblPelengMac] = {
    withSQL {
      select.from(PblPelengMac as ppm).where.append(where)
    }.map(PblPelengMac(ppm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblPelengMac as ppm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    devcode: Option[Int] = None,
    devstack: Option[Int] = None,
    devport: Option[Int] = None,
    mac: Option[String] = None,
    datefirst: Option[DateTime] = None,
    datelast: Option[DateTime] = None,
    inbase: Option[Short] = None,
    devtype: Option[Short] = None,
    vid: Option[Int] = None,
    ifaceCaption: Option[String] = None)(implicit session: DBSession = autoSession): PblPelengMac = {
    val generatedKey = withSQL {
      insert.into(PblPelengMac).namedValues(
        column.devcode -> devcode,
        column.devstack -> devstack,
        column.devport -> devport,
        column.mac -> mac,
        column.datefirst -> datefirst,
        column.datelast -> datelast,
        column.inbase -> inbase,
        column.devtype -> devtype,
        column.vid -> vid,
        column.ifaceCaption -> ifaceCaption
      )
    }.updateAndReturnGeneratedKey.apply()

    PblPelengMac(
      code = generatedKey.toInt,
      devcode = devcode,
      devstack = devstack,
      devport = devport,
      mac = mac,
      datefirst = datefirst,
      datelast = datelast,
      inbase = inbase,
      devtype = devtype,
      vid = vid,
      ifaceCaption = ifaceCaption)
  }

  def batchInsert(entities: collection.Seq[PblPelengMac])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'devcode -> entity.devcode,
        'devstack -> entity.devstack,
        'devport -> entity.devport,
        'mac -> entity.mac,
        'datefirst -> entity.datefirst,
        'datelast -> entity.datelast,
        'inbase -> entity.inbase,
        'devtype -> entity.devtype,
        'vid -> entity.vid,
        'ifaceCaption -> entity.ifaceCaption))
    SQL("""insert into pbl_peleng_mac(
      devcode,
      devstack,
      devport,
      mac,
      datefirst,
      datelast,
      inbase,
      devtype,
      vid,
      iface_caption
    ) values (
      {devcode},
      {devstack},
      {devport},
      {mac},
      {datefirst},
      {datelast},
      {inbase},
      {devtype},
      {vid},
      {ifaceCaption}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblPelengMac)(implicit session: DBSession = autoSession): PblPelengMac = {
    withSQL {
      update(PblPelengMac).set(
        column.code -> entity.code,
        column.devcode -> entity.devcode,
        column.devstack -> entity.devstack,
        column.devport -> entity.devport,
        column.mac -> entity.mac,
        column.datefirst -> entity.datefirst,
        column.datelast -> entity.datelast,
        column.inbase -> entity.inbase,
        column.devtype -> entity.devtype,
        column.vid -> entity.vid,
        column.ifaceCaption -> entity.ifaceCaption
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblPelengMac)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblPelengMac).where.eq(column.code, entity.code) }.update.apply()
  }

}
