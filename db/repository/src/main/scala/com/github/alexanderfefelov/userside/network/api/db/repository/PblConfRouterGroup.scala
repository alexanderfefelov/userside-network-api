package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfRouterGroup(
  code: Int,
  routercode: Option[Int] = None,
  groupcode: Option[String] = None,
  userip: Option[String] = None,
  whiteint: Option[String] = None,
  grayint: Option[String] = None) {

  def save()(implicit session: DBSession = PblConfRouterGroup.autoSession): PblConfRouterGroup = PblConfRouterGroup.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfRouterGroup.autoSession): Int = PblConfRouterGroup.destroy(this)(session)

}


object PblConfRouterGroup extends SQLSyntaxSupport[PblConfRouterGroup] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_router_group"

  override val columns = Seq("code", "routercode", "groupcode", "userip", "whiteint", "grayint")

  def apply(pcrg: SyntaxProvider[PblConfRouterGroup])(rs: WrappedResultSet): PblConfRouterGroup = autoConstruct(rs, pcrg)
  def apply(pcrg: ResultName[PblConfRouterGroup])(rs: WrappedResultSet): PblConfRouterGroup = autoConstruct(rs, pcrg)

  val pcrg = PblConfRouterGroup.syntax("pcrg")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfRouterGroup] = {
    withSQL {
      select.from(PblConfRouterGroup as pcrg).where.eq(pcrg.code, code)
    }.map(PblConfRouterGroup(pcrg.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfRouterGroup] = {
    withSQL(select.from(PblConfRouterGroup as pcrg)).map(PblConfRouterGroup(pcrg.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfRouterGroup as pcrg)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfRouterGroup] = {
    withSQL {
      select.from(PblConfRouterGroup as pcrg).where.append(where)
    }.map(PblConfRouterGroup(pcrg.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfRouterGroup] = {
    withSQL {
      select.from(PblConfRouterGroup as pcrg).where.append(where)
    }.map(PblConfRouterGroup(pcrg.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfRouterGroup as pcrg).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    routercode: Option[Int] = None,
    groupcode: Option[String] = None,
    userip: Option[String] = None,
    whiteint: Option[String] = None,
    grayint: Option[String] = None)(implicit session: DBSession = autoSession): PblConfRouterGroup = {
    val generatedKey = withSQL {
      insert.into(PblConfRouterGroup).namedValues(
        column.routercode -> routercode,
        column.groupcode -> groupcode,
        column.userip -> userip,
        column.whiteint -> whiteint,
        column.grayint -> grayint
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfRouterGroup(
      code = generatedKey.toInt,
      routercode = routercode,
      groupcode = groupcode,
      userip = userip,
      whiteint = whiteint,
      grayint = grayint)
  }

  def batchInsert(entities: collection.Seq[PblConfRouterGroup])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'routercode -> entity.routercode,
        'groupcode -> entity.groupcode,
        'userip -> entity.userip,
        'whiteint -> entity.whiteint,
        'grayint -> entity.grayint))
    SQL("""insert into pbl_conf_router_group(
      routercode,
      groupcode,
      userip,
      whiteint,
      grayint
    ) values (
      {routercode},
      {groupcode},
      {userip},
      {whiteint},
      {grayint}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfRouterGroup)(implicit session: DBSession = autoSession): PblConfRouterGroup = {
    withSQL {
      update(PblConfRouterGroup).set(
        column.code -> entity.code,
        column.routercode -> entity.routercode,
        column.groupcode -> entity.groupcode,
        column.userip -> entity.userip,
        column.whiteint -> entity.whiteint,
        column.grayint -> entity.grayint
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfRouterGroup)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfRouterGroup).where.eq(column.code, entity.code) }.update.apply()
  }

}
