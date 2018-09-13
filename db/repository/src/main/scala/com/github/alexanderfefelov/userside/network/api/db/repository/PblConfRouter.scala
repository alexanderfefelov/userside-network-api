package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfRouter(
  code: Int,
  routerip: Option[String] = None,
  nazv: Option[String] = None,
  rport: Option[Int] = None,
  ruser: Option[String] = None,
  rpass: Option[String] = None,
  dhcpname: Option[String] = None,
  dhcprate: Option[Short] = None,
  dhcpaddrlist: Option[Short] = None) {

  def save()(implicit session: DBSession = PblConfRouter.autoSession): PblConfRouter = PblConfRouter.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfRouter.autoSession): Int = PblConfRouter.destroy(this)(session)

}


object PblConfRouter extends SQLSyntaxSupport[PblConfRouter] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_router"

  override val columns = Seq("code", "routerip", "nazv", "rport", "ruser", "rpass", "dhcpname", "dhcprate", "dhcpaddrlist")

  def apply(pcr: SyntaxProvider[PblConfRouter])(rs: WrappedResultSet): PblConfRouter = autoConstruct(rs, pcr)
  def apply(pcr: ResultName[PblConfRouter])(rs: WrappedResultSet): PblConfRouter = autoConstruct(rs, pcr)

  val pcr = PblConfRouter.syntax("pcr")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfRouter] = {
    withSQL {
      select.from(PblConfRouter as pcr).where.eq(pcr.code, code)
    }.map(PblConfRouter(pcr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfRouter] = {
    withSQL(select.from(PblConfRouter as pcr)).map(PblConfRouter(pcr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfRouter as pcr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfRouter] = {
    withSQL {
      select.from(PblConfRouter as pcr).where.append(where)
    }.map(PblConfRouter(pcr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfRouter] = {
    withSQL {
      select.from(PblConfRouter as pcr).where.append(where)
    }.map(PblConfRouter(pcr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfRouter as pcr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    routerip: Option[String] = None,
    nazv: Option[String] = None,
    rport: Option[Int] = None,
    ruser: Option[String] = None,
    rpass: Option[String] = None,
    dhcpname: Option[String] = None,
    dhcprate: Option[Short] = None,
    dhcpaddrlist: Option[Short] = None)(implicit session: DBSession = autoSession): PblConfRouter = {
    val generatedKey = withSQL {
      insert.into(PblConfRouter).namedValues(
        column.routerip -> routerip,
        column.nazv -> nazv,
        column.rport -> rport,
        column.ruser -> ruser,
        column.rpass -> rpass,
        column.dhcpname -> dhcpname,
        column.dhcprate -> dhcprate,
        column.dhcpaddrlist -> dhcpaddrlist
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfRouter(
      code = generatedKey.toInt,
      routerip = routerip,
      nazv = nazv,
      rport = rport,
      ruser = ruser,
      rpass = rpass,
      dhcpname = dhcpname,
      dhcprate = dhcprate,
      dhcpaddrlist = dhcpaddrlist)
  }

  def batchInsert(entities: collection.Seq[PblConfRouter])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'routerip -> entity.routerip,
        'nazv -> entity.nazv,
        'rport -> entity.rport,
        'ruser -> entity.ruser,
        'rpass -> entity.rpass,
        'dhcpname -> entity.dhcpname,
        'dhcprate -> entity.dhcprate,
        'dhcpaddrlist -> entity.dhcpaddrlist))
    SQL("""insert into pbl_conf_router(
      routerip,
      nazv,
      rport,
      ruser,
      rpass,
      dhcpname,
      dhcprate,
      dhcpaddrlist
    ) values (
      {routerip},
      {nazv},
      {rport},
      {ruser},
      {rpass},
      {dhcpname},
      {dhcprate},
      {dhcpaddrlist}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfRouter)(implicit session: DBSession = autoSession): PblConfRouter = {
    withSQL {
      update(PblConfRouter).set(
        column.code -> entity.code,
        column.routerip -> entity.routerip,
        column.nazv -> entity.nazv,
        column.rport -> entity.rport,
        column.ruser -> entity.ruser,
        column.rpass -> entity.rpass,
        column.dhcpname -> entity.dhcpname,
        column.dhcprate -> entity.dhcprate,
        column.dhcpaddrlist -> entity.dhcpaddrlist
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfRouter)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfRouter).where.eq(column.code, entity.code) }.update.apply()
  }

}
