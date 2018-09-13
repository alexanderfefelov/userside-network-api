package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblGroup(
  id: Int,
  code: Option[String] = None,
  groupname: Option[String] = None,
  price: Option[BigDecimal] = None,
  trafex: Option[Int] = None,
  users: Option[Int] = None,
  trafbuh: Option[Int] = None,
  abon: Option[BigDecimal] = None,
  abonday: Option[Short] = None,
  trafrx1: Option[String] = None,
  traftx1: Option[String] = None,
  trafrx2: Option[String] = None,
  traftx2: Option[String] = None,
  rxkbps: Option[String] = None,
  txkbps: Option[String] = None,
  rulewhite: Option[String] = None,
  rulegray: Option[String] = None,
  hidename: Option[String] = None,
  priznak: Option[Short] = None,
  akciyaDay: Option[Int] = None,
  akciyaMany: Option[Int] = None,
  notsms: Option[Short] = None,
  isturbo: Option[Short] = None,
  speedtx: Option[Int] = None,
  speedrx: Option[Int] = None,
  dogroup: Option[Short] = None,
  billcode: Option[Int] = None,
  notinbilling: Option[Short] = None,
  akciyaDayOut: Option[String] = None,
  akciyaDayOutTarif: Option[String] = None,
  abonFull: Option[BigDecimal] = None,
  serviceType: Option[Short] = None) {

  def save()(implicit session: DBSession = PblGroup.autoSession): PblGroup = PblGroup.save(this)(session)

  def destroy()(implicit session: DBSession = PblGroup.autoSession): Int = PblGroup.destroy(this)(session)

}


object PblGroup extends SQLSyntaxSupport[PblGroup] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_group"

  override val columns = Seq("id", "code", "groupname", "price", "trafex", "users", "trafbuh", "abon", "abonday", "trafrx1", "traftx1", "trafrx2", "traftx2", "rxkbps", "txkbps", "rulewhite", "rulegray", "hidename", "priznak", "akciya_day", "akciya_many", "notsms", "isturbo", "speedtx", "speedrx", "dogroup", "billcode", "notinbilling", "akciya_day_out", "akciya_day_out_tarif", "abon_full", "service_type")

  def apply(pg: SyntaxProvider[PblGroup])(rs: WrappedResultSet): PblGroup = autoConstruct(rs, pg)
  def apply(pg: ResultName[PblGroup])(rs: WrappedResultSet): PblGroup = autoConstruct(rs, pg)

  val pg = PblGroup.syntax("pg")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblGroup] = {
    withSQL {
      select.from(PblGroup as pg).where.eq(pg.id, id)
    }.map(PblGroup(pg.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblGroup] = {
    withSQL(select.from(PblGroup as pg)).map(PblGroup(pg.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblGroup as pg)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblGroup] = {
    withSQL {
      select.from(PblGroup as pg).where.append(where)
    }.map(PblGroup(pg.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblGroup] = {
    withSQL {
      select.from(PblGroup as pg).where.append(where)
    }.map(PblGroup(pg.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblGroup as pg).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: Option[String] = None,
    groupname: Option[String] = None,
    price: Option[BigDecimal] = None,
    trafex: Option[Int] = None,
    users: Option[Int] = None,
    trafbuh: Option[Int] = None,
    abon: Option[BigDecimal] = None,
    abonday: Option[Short] = None,
    trafrx1: Option[String] = None,
    traftx1: Option[String] = None,
    trafrx2: Option[String] = None,
    traftx2: Option[String] = None,
    rxkbps: Option[String] = None,
    txkbps: Option[String] = None,
    rulewhite: Option[String] = None,
    rulegray: Option[String] = None,
    hidename: Option[String] = None,
    priznak: Option[Short] = None,
    akciyaDay: Option[Int] = None,
    akciyaMany: Option[Int] = None,
    notsms: Option[Short] = None,
    isturbo: Option[Short] = None,
    speedtx: Option[Int] = None,
    speedrx: Option[Int] = None,
    dogroup: Option[Short] = None,
    billcode: Option[Int] = None,
    notinbilling: Option[Short] = None,
    akciyaDayOut: Option[String] = None,
    akciyaDayOutTarif: Option[String] = None,
    abonFull: Option[BigDecimal] = None,
    serviceType: Option[Short] = None)(implicit session: DBSession = autoSession): PblGroup = {
    val generatedKey = withSQL {
      insert.into(PblGroup).namedValues(
        column.code -> code,
        column.groupname -> groupname,
        column.price -> price,
        column.trafex -> trafex,
        column.users -> users,
        column.trafbuh -> trafbuh,
        column.abon -> abon,
        column.abonday -> abonday,
        column.trafrx1 -> trafrx1,
        column.traftx1 -> traftx1,
        column.trafrx2 -> trafrx2,
        column.traftx2 -> traftx2,
        column.rxkbps -> rxkbps,
        column.txkbps -> txkbps,
        column.rulewhite -> rulewhite,
        column.rulegray -> rulegray,
        column.hidename -> hidename,
        column.priznak -> priznak,
        column.akciyaDay -> akciyaDay,
        column.akciyaMany -> akciyaMany,
        column.notsms -> notsms,
        column.isturbo -> isturbo,
        column.speedtx -> speedtx,
        column.speedrx -> speedrx,
        column.dogroup -> dogroup,
        column.billcode -> billcode,
        column.notinbilling -> notinbilling,
        column.akciyaDayOut -> akciyaDayOut,
        column.akciyaDayOutTarif -> akciyaDayOutTarif,
        column.abonFull -> abonFull,
        column.serviceType -> serviceType
      )
    }.updateAndReturnGeneratedKey.apply()

    PblGroup(
      id = generatedKey.toInt,
      code = code,
      groupname = groupname,
      price = price,
      trafex = trafex,
      users = users,
      trafbuh = trafbuh,
      abon = abon,
      abonday = abonday,
      trafrx1 = trafrx1,
      traftx1 = traftx1,
      trafrx2 = trafrx2,
      traftx2 = traftx2,
      rxkbps = rxkbps,
      txkbps = txkbps,
      rulewhite = rulewhite,
      rulegray = rulegray,
      hidename = hidename,
      priznak = priznak,
      akciyaDay = akciyaDay,
      akciyaMany = akciyaMany,
      notsms = notsms,
      isturbo = isturbo,
      speedtx = speedtx,
      speedrx = speedrx,
      dogroup = dogroup,
      billcode = billcode,
      notinbilling = notinbilling,
      akciyaDayOut = akciyaDayOut,
      akciyaDayOutTarif = akciyaDayOutTarif,
      abonFull = abonFull,
      serviceType = serviceType)
  }

  def batchInsert(entities: collection.Seq[PblGroup])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'code -> entity.code,
        'groupname -> entity.groupname,
        'price -> entity.price,
        'trafex -> entity.trafex,
        'users -> entity.users,
        'trafbuh -> entity.trafbuh,
        'abon -> entity.abon,
        'abonday -> entity.abonday,
        'trafrx1 -> entity.trafrx1,
        'traftx1 -> entity.traftx1,
        'trafrx2 -> entity.trafrx2,
        'traftx2 -> entity.traftx2,
        'rxkbps -> entity.rxkbps,
        'txkbps -> entity.txkbps,
        'rulewhite -> entity.rulewhite,
        'rulegray -> entity.rulegray,
        'hidename -> entity.hidename,
        'priznak -> entity.priznak,
        'akciyaDay -> entity.akciyaDay,
        'akciyaMany -> entity.akciyaMany,
        'notsms -> entity.notsms,
        'isturbo -> entity.isturbo,
        'speedtx -> entity.speedtx,
        'speedrx -> entity.speedrx,
        'dogroup -> entity.dogroup,
        'billcode -> entity.billcode,
        'notinbilling -> entity.notinbilling,
        'akciyaDayOut -> entity.akciyaDayOut,
        'akciyaDayOutTarif -> entity.akciyaDayOutTarif,
        'abonFull -> entity.abonFull,
        'serviceType -> entity.serviceType))
    SQL("""insert into pbl_group(
      code,
      groupname,
      price,
      trafex,
      users,
      trafbuh,
      abon,
      abonday,
      trafrx1,
      traftx1,
      trafrx2,
      traftx2,
      rxkbps,
      txkbps,
      rulewhite,
      rulegray,
      hidename,
      priznak,
      akciya_day,
      akciya_many,
      notsms,
      isturbo,
      speedtx,
      speedrx,
      dogroup,
      billcode,
      notinbilling,
      akciya_day_out,
      akciya_day_out_tarif,
      abon_full,
      service_type
    ) values (
      {code},
      {groupname},
      {price},
      {trafex},
      {users},
      {trafbuh},
      {abon},
      {abonday},
      {trafrx1},
      {traftx1},
      {trafrx2},
      {traftx2},
      {rxkbps},
      {txkbps},
      {rulewhite},
      {rulegray},
      {hidename},
      {priznak},
      {akciyaDay},
      {akciyaMany},
      {notsms},
      {isturbo},
      {speedtx},
      {speedrx},
      {dogroup},
      {billcode},
      {notinbilling},
      {akciyaDayOut},
      {akciyaDayOutTarif},
      {abonFull},
      {serviceType}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblGroup)(implicit session: DBSession = autoSession): PblGroup = {
    withSQL {
      update(PblGroup).set(
        column.id -> entity.id,
        column.code -> entity.code,
        column.groupname -> entity.groupname,
        column.price -> entity.price,
        column.trafex -> entity.trafex,
        column.users -> entity.users,
        column.trafbuh -> entity.trafbuh,
        column.abon -> entity.abon,
        column.abonday -> entity.abonday,
        column.trafrx1 -> entity.trafrx1,
        column.traftx1 -> entity.traftx1,
        column.trafrx2 -> entity.trafrx2,
        column.traftx2 -> entity.traftx2,
        column.rxkbps -> entity.rxkbps,
        column.txkbps -> entity.txkbps,
        column.rulewhite -> entity.rulewhite,
        column.rulegray -> entity.rulegray,
        column.hidename -> entity.hidename,
        column.priznak -> entity.priznak,
        column.akciyaDay -> entity.akciyaDay,
        column.akciyaMany -> entity.akciyaMany,
        column.notsms -> entity.notsms,
        column.isturbo -> entity.isturbo,
        column.speedtx -> entity.speedtx,
        column.speedrx -> entity.speedrx,
        column.dogroup -> entity.dogroup,
        column.billcode -> entity.billcode,
        column.notinbilling -> entity.notinbilling,
        column.akciyaDayOut -> entity.akciyaDayOut,
        column.akciyaDayOutTarif -> entity.akciyaDayOutTarif,
        column.abonFull -> entity.abonFull,
        column.serviceType -> entity.serviceType
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblGroup)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblGroup).where.eq(column.id, entity.id) }.update.apply()
  }

}
