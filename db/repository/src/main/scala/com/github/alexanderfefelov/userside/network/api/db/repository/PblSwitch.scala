package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime, LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblSwitch(
  code: Int,
  devtyper: Option[Short] = None,
  skladcode: Option[Int] = None,
  opis: Option[String] = None,
  port: Option[Short] = None,
  citycode: Option[Int] = None,
  uzelcode: Option[Int] = None,
  usercode: Option[Int] = None,
  location: Option[String] = None,
  nazv: Option[String] = None,
  lastact: Option[DateTime] = None,
  dateadd: Option[LocalDate] = None,
  comPublic: Option[String] = None,
  comPrivate: Option[String] = None,
  comLogin: Option[String] = None,
  comPass: Option[String] = None,
  pelengcode: Option[Int] = None,
  valuememo: Option[String] = None,
  snmpver: Option[Short] = None,
  fdpver: Option[Short] = None,
  x1: Option[Int] = None,
  y1: Option[Int] = None,
  upport: Option[String] = None,
  onsms: Option[Short] = None,
  issmssend: Option[Short] = None,
  hashPort2: Option[String] = None,
  onmail: Option[Short] = None,
  ismailsend: Option[Short] = None,
  proshivka: Option[String] = None,
  proshivkaDate: Option[DateTime] = None,
  cablelen: Option[String] = None,
  hostname: Option[String] = None,
  layers: Option[String] = None,
  basecode: Option[Int] = None,
  param: Option[String] = None,
  rotation: Option[Short] = None,
  ipabon: Option[BigDecimal] = None,
  snmpDontask: Option[Short] = None,
  logStatus: Option[Short] = None,
  dnport: Option[String] = None,
  datepeleng: Option[DateTime] = None,
  ipport: Option[String] = None,
  profile: Option[Int] = None,
  azimut: Option[Short] = None,
  sectcoord: Option[String] = None,
  colcol: Option[String] = None,
  mainmapGuid: Option[Int] = None,
  flagCustomCoord: Option[Short] = None,
  levelMax: Option[Short] = None,
  levelMin: Option[Short] = None,
  latitude: Option[BigDecimal] = None,
  longitude: Option[BigDecimal] = None,
  ip: Option[BigDecimal] = None,
  mac: Option[String] = None,
  snmpPort: Option[Int] = None,
  dateCabletest: Option[DateTime] = None) {

  def save()(implicit session: DBSession = PblSwitch.autoSession): PblSwitch = PblSwitch.save(this)(session)

  def destroy()(implicit session: DBSession = PblSwitch.autoSession): Int = PblSwitch.destroy(this)(session)

}


object PblSwitch extends SQLSyntaxSupport[PblSwitch] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_switch"

  override val columns = Seq("code", "devtyper", "skladcode", "opis", "port", "citycode", "uzelcode", "usercode", "location", "nazv", "lastact", "dateadd", "com_public", "com_private", "com_login", "com_pass", "pelengcode", "valuememo", "snmpver", "fdpver", "x1", "y1", "upport", "onsms", "issmssend", "hash_port2", "onmail", "ismailsend", "proshivka", "proshivka_date", "cablelen", "hostname", "layers", "basecode", "param", "rotation", "ipabon", "snmp_dontask", "log_status", "dnport", "datepeleng", "ipport", "profile", "azimut", "sectcoord", "colcol", "mainmap_guid", "flag_custom_coord", "level_max", "level_min", "latitude", "longitude", "ip", "mac", "snmp_port", "date_cabletest")

  def apply(ps: SyntaxProvider[PblSwitch])(rs: WrappedResultSet): PblSwitch = autoConstruct(rs, ps)
  def apply(ps: ResultName[PblSwitch])(rs: WrappedResultSet): PblSwitch = autoConstruct(rs, ps)

  val ps = PblSwitch.syntax("ps")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblSwitch] = {
    withSQL {
      select.from(PblSwitch as ps).where.eq(ps.code, code)
    }.map(PblSwitch(ps.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblSwitch] = {
    withSQL(select.from(PblSwitch as ps)).map(PblSwitch(ps.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblSwitch as ps)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblSwitch] = {
    withSQL {
      select.from(PblSwitch as ps).where.append(where)
    }.map(PblSwitch(ps.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblSwitch] = {
    withSQL {
      select.from(PblSwitch as ps).where.append(where)
    }.map(PblSwitch(ps.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblSwitch as ps).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    devtyper: Option[Short] = None,
    skladcode: Option[Int] = None,
    opis: Option[String] = None,
    port: Option[Short] = None,
    citycode: Option[Int] = None,
    uzelcode: Option[Int] = None,
    usercode: Option[Int] = None,
    location: Option[String] = None,
    nazv: Option[String] = None,
    lastact: Option[DateTime] = None,
    dateadd: Option[LocalDate] = None,
    comPublic: Option[String] = None,
    comPrivate: Option[String] = None,
    comLogin: Option[String] = None,
    comPass: Option[String] = None,
    pelengcode: Option[Int] = None,
    valuememo: Option[String] = None,
    snmpver: Option[Short] = None,
    fdpver: Option[Short] = None,
    x1: Option[Int] = None,
    y1: Option[Int] = None,
    upport: Option[String] = None,
    onsms: Option[Short] = None,
    issmssend: Option[Short] = None,
    hashPort2: Option[String] = None,
    onmail: Option[Short] = None,
    ismailsend: Option[Short] = None,
    proshivka: Option[String] = None,
    proshivkaDate: Option[DateTime] = None,
    cablelen: Option[String] = None,
    hostname: Option[String] = None,
    layers: Option[String] = None,
    basecode: Option[Int] = None,
    param: Option[String] = None,
    rotation: Option[Short] = None,
    ipabon: Option[BigDecimal] = None,
    snmpDontask: Option[Short] = None,
    logStatus: Option[Short] = None,
    dnport: Option[String] = None,
    datepeleng: Option[DateTime] = None,
    ipport: Option[String] = None,
    profile: Option[Int] = None,
    azimut: Option[Short] = None,
    sectcoord: Option[String] = None,
    colcol: Option[String] = None,
    mainmapGuid: Option[Int] = None,
    flagCustomCoord: Option[Short] = None,
    levelMax: Option[Short] = None,
    levelMin: Option[Short] = None,
    latitude: Option[BigDecimal] = None,
    longitude: Option[BigDecimal] = None,
    ip: Option[BigDecimal] = None,
    mac: Option[String] = None,
    snmpPort: Option[Int] = None,
    dateCabletest: Option[DateTime] = None)(implicit session: DBSession = autoSession): PblSwitch = {
    val generatedKey = withSQL {
      insert.into(PblSwitch).namedValues(
        column.devtyper -> devtyper,
        column.skladcode -> skladcode,
        column.opis -> opis,
        column.port -> port,
        column.citycode -> citycode,
        column.uzelcode -> uzelcode,
        column.usercode -> usercode,
        column.location -> location,
        column.nazv -> nazv,
        column.lastact -> lastact,
        column.dateadd -> dateadd,
        column.comPublic -> comPublic,
        column.comPrivate -> comPrivate,
        column.comLogin -> comLogin,
        column.comPass -> comPass,
        column.pelengcode -> pelengcode,
        column.valuememo -> valuememo,
        column.snmpver -> snmpver,
        column.fdpver -> fdpver,
        column.x1 -> x1,
        column.y1 -> y1,
        column.upport -> upport,
        column.onsms -> onsms,
        column.issmssend -> issmssend,
        column.hashPort2 -> hashPort2,
        column.onmail -> onmail,
        column.ismailsend -> ismailsend,
        column.proshivka -> proshivka,
        column.proshivkaDate -> proshivkaDate,
        column.cablelen -> cablelen,
        column.hostname -> hostname,
        column.layers -> layers,
        column.basecode -> basecode,
        column.param -> param,
        column.rotation -> rotation,
        column.ipabon -> ipabon,
        column.snmpDontask -> snmpDontask,
        column.logStatus -> logStatus,
        column.dnport -> dnport,
        column.datepeleng -> datepeleng,
        column.ipport -> ipport,
        column.profile -> profile,
        column.azimut -> azimut,
        column.sectcoord -> sectcoord,
        column.colcol -> colcol,
        column.mainmapGuid -> mainmapGuid,
        column.flagCustomCoord -> flagCustomCoord,
        column.levelMax -> levelMax,
        column.levelMin -> levelMin,
        column.latitude -> latitude,
        column.longitude -> longitude,
        column.ip -> ip,
        column.mac -> mac,
        column.snmpPort -> snmpPort,
        column.dateCabletest -> dateCabletest
      )
    }.updateAndReturnGeneratedKey.apply()

    PblSwitch(
      code = generatedKey.toInt,
      devtyper = devtyper,
      skladcode = skladcode,
      opis = opis,
      port = port,
      citycode = citycode,
      uzelcode = uzelcode,
      usercode = usercode,
      location = location,
      nazv = nazv,
      lastact = lastact,
      dateadd = dateadd,
      comPublic = comPublic,
      comPrivate = comPrivate,
      comLogin = comLogin,
      comPass = comPass,
      pelengcode = pelengcode,
      valuememo = valuememo,
      snmpver = snmpver,
      fdpver = fdpver,
      x1 = x1,
      y1 = y1,
      upport = upport,
      onsms = onsms,
      issmssend = issmssend,
      hashPort2 = hashPort2,
      onmail = onmail,
      ismailsend = ismailsend,
      proshivka = proshivka,
      proshivkaDate = proshivkaDate,
      cablelen = cablelen,
      hostname = hostname,
      layers = layers,
      basecode = basecode,
      param = param,
      rotation = rotation,
      ipabon = ipabon,
      snmpDontask = snmpDontask,
      logStatus = logStatus,
      dnport = dnport,
      datepeleng = datepeleng,
      ipport = ipport,
      profile = profile,
      azimut = azimut,
      sectcoord = sectcoord,
      colcol = colcol,
      mainmapGuid = mainmapGuid,
      flagCustomCoord = flagCustomCoord,
      levelMax = levelMax,
      levelMin = levelMin,
      latitude = latitude,
      longitude = longitude,
      ip = ip,
      mac = mac,
      snmpPort = snmpPort,
      dateCabletest = dateCabletest)
  }

  def batchInsert(entities: collection.Seq[PblSwitch])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'devtyper -> entity.devtyper,
        'skladcode -> entity.skladcode,
        'opis -> entity.opis,
        'port -> entity.port,
        'citycode -> entity.citycode,
        'uzelcode -> entity.uzelcode,
        'usercode -> entity.usercode,
        'location -> entity.location,
        'nazv -> entity.nazv,
        'lastact -> entity.lastact,
        'dateadd -> entity.dateadd,
        'comPublic -> entity.comPublic,
        'comPrivate -> entity.comPrivate,
        'comLogin -> entity.comLogin,
        'comPass -> entity.comPass,
        'pelengcode -> entity.pelengcode,
        'valuememo -> entity.valuememo,
        'snmpver -> entity.snmpver,
        'fdpver -> entity.fdpver,
        'x1 -> entity.x1,
        'y1 -> entity.y1,
        'upport -> entity.upport,
        'onsms -> entity.onsms,
        'issmssend -> entity.issmssend,
        'hashPort2 -> entity.hashPort2,
        'onmail -> entity.onmail,
        'ismailsend -> entity.ismailsend,
        'proshivka -> entity.proshivka,
        'proshivkaDate -> entity.proshivkaDate,
        'cablelen -> entity.cablelen,
        'hostname -> entity.hostname,
        'layers -> entity.layers,
        'basecode -> entity.basecode,
        'param -> entity.param,
        'rotation -> entity.rotation,
        'ipabon -> entity.ipabon,
        'snmpDontask -> entity.snmpDontask,
        'logStatus -> entity.logStatus,
        'dnport -> entity.dnport,
        'datepeleng -> entity.datepeleng,
        'ipport -> entity.ipport,
        'profile -> entity.profile,
        'azimut -> entity.azimut,
        'sectcoord -> entity.sectcoord,
        'colcol -> entity.colcol,
        'mainmapGuid -> entity.mainmapGuid,
        'flagCustomCoord -> entity.flagCustomCoord,
        'levelMax -> entity.levelMax,
        'levelMin -> entity.levelMin,
        'latitude -> entity.latitude,
        'longitude -> entity.longitude,
        'ip -> entity.ip,
        'mac -> entity.mac,
        'snmpPort -> entity.snmpPort,
        'dateCabletest -> entity.dateCabletest))
    SQL("""insert into pbl_switch(
      devtyper,
      skladcode,
      opis,
      port,
      citycode,
      uzelcode,
      usercode,
      location,
      nazv,
      lastact,
      dateadd,
      com_public,
      com_private,
      com_login,
      com_pass,
      pelengcode,
      valuememo,
      snmpver,
      fdpver,
      x1,
      y1,
      upport,
      onsms,
      issmssend,
      hash_port2,
      onmail,
      ismailsend,
      proshivka,
      proshivka_date,
      cablelen,
      hostname,
      layers,
      basecode,
      param,
      rotation,
      ipabon,
      snmp_dontask,
      log_status,
      dnport,
      datepeleng,
      ipport,
      profile,
      azimut,
      sectcoord,
      colcol,
      mainmap_guid,
      flag_custom_coord,
      level_max,
      level_min,
      latitude,
      longitude,
      ip,
      mac,
      snmp_port,
      date_cabletest
    ) values (
      {devtyper},
      {skladcode},
      {opis},
      {port},
      {citycode},
      {uzelcode},
      {usercode},
      {location},
      {nazv},
      {lastact},
      {dateadd},
      {comPublic},
      {comPrivate},
      {comLogin},
      {comPass},
      {pelengcode},
      {valuememo},
      {snmpver},
      {fdpver},
      {x1},
      {y1},
      {upport},
      {onsms},
      {issmssend},
      {hashPort2},
      {onmail},
      {ismailsend},
      {proshivka},
      {proshivkaDate},
      {cablelen},
      {hostname},
      {layers},
      {basecode},
      {param},
      {rotation},
      {ipabon},
      {snmpDontask},
      {logStatus},
      {dnport},
      {datepeleng},
      {ipport},
      {profile},
      {azimut},
      {sectcoord},
      {colcol},
      {mainmapGuid},
      {flagCustomCoord},
      {levelMax},
      {levelMin},
      {latitude},
      {longitude},
      {ip},
      {mac},
      {snmpPort},
      {dateCabletest}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblSwitch)(implicit session: DBSession = autoSession): PblSwitch = {
    withSQL {
      update(PblSwitch).set(
        column.code -> entity.code,
        column.devtyper -> entity.devtyper,
        column.skladcode -> entity.skladcode,
        column.opis -> entity.opis,
        column.port -> entity.port,
        column.citycode -> entity.citycode,
        column.uzelcode -> entity.uzelcode,
        column.usercode -> entity.usercode,
        column.location -> entity.location,
        column.nazv -> entity.nazv,
        column.lastact -> entity.lastact,
        column.dateadd -> entity.dateadd,
        column.comPublic -> entity.comPublic,
        column.comPrivate -> entity.comPrivate,
        column.comLogin -> entity.comLogin,
        column.comPass -> entity.comPass,
        column.pelengcode -> entity.pelengcode,
        column.valuememo -> entity.valuememo,
        column.snmpver -> entity.snmpver,
        column.fdpver -> entity.fdpver,
        column.x1 -> entity.x1,
        column.y1 -> entity.y1,
        column.upport -> entity.upport,
        column.onsms -> entity.onsms,
        column.issmssend -> entity.issmssend,
        column.hashPort2 -> entity.hashPort2,
        column.onmail -> entity.onmail,
        column.ismailsend -> entity.ismailsend,
        column.proshivka -> entity.proshivka,
        column.proshivkaDate -> entity.proshivkaDate,
        column.cablelen -> entity.cablelen,
        column.hostname -> entity.hostname,
        column.layers -> entity.layers,
        column.basecode -> entity.basecode,
        column.param -> entity.param,
        column.rotation -> entity.rotation,
        column.ipabon -> entity.ipabon,
        column.snmpDontask -> entity.snmpDontask,
        column.logStatus -> entity.logStatus,
        column.dnport -> entity.dnport,
        column.datepeleng -> entity.datepeleng,
        column.ipport -> entity.ipport,
        column.profile -> entity.profile,
        column.azimut -> entity.azimut,
        column.sectcoord -> entity.sectcoord,
        column.colcol -> entity.colcol,
        column.mainmapGuid -> entity.mainmapGuid,
        column.flagCustomCoord -> entity.flagCustomCoord,
        column.levelMax -> entity.levelMax,
        column.levelMin -> entity.levelMin,
        column.latitude -> entity.latitude,
        column.longitude -> entity.longitude,
        column.ip -> entity.ip,
        column.mac -> entity.mac,
        column.snmpPort -> entity.snmpPort,
        column.dateCabletest -> entity.dateCabletest
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblSwitch)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblSwitch).where.eq(column.code, entity.code) }.update.apply()
  }

}
