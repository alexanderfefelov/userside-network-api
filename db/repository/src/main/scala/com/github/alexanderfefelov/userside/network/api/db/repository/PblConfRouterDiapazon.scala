package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfRouterDiapazon(
  code: Int,
  routercode: Option[Int] = None,
  userip1: Option[BigDecimal] = None,
  userip2: Option[BigDecimal] = None,
  typer: Option[Short] = None) {

  def save()(implicit session: DBSession = PblConfRouterDiapazon.autoSession): PblConfRouterDiapazon = PblConfRouterDiapazon.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfRouterDiapazon.autoSession): Int = PblConfRouterDiapazon.destroy(this)(session)

}


object PblConfRouterDiapazon extends SQLSyntaxSupport[PblConfRouterDiapazon] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_router_diapazon"

  override val columns = Seq("code", "routercode", "userip1", "userip2", "typer")

  def apply(pcrd: SyntaxProvider[PblConfRouterDiapazon])(rs: WrappedResultSet): PblConfRouterDiapazon = autoConstruct(rs, pcrd)
  def apply(pcrd: ResultName[PblConfRouterDiapazon])(rs: WrappedResultSet): PblConfRouterDiapazon = autoConstruct(rs, pcrd)

  val pcrd = PblConfRouterDiapazon.syntax("pcrd")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfRouterDiapazon] = {
    withSQL {
      select.from(PblConfRouterDiapazon as pcrd).where.eq(pcrd.code, code)
    }.map(PblConfRouterDiapazon(pcrd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfRouterDiapazon] = {
    withSQL(select.from(PblConfRouterDiapazon as pcrd)).map(PblConfRouterDiapazon(pcrd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfRouterDiapazon as pcrd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfRouterDiapazon] = {
    withSQL {
      select.from(PblConfRouterDiapazon as pcrd).where.append(where)
    }.map(PblConfRouterDiapazon(pcrd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfRouterDiapazon] = {
    withSQL {
      select.from(PblConfRouterDiapazon as pcrd).where.append(where)
    }.map(PblConfRouterDiapazon(pcrd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfRouterDiapazon as pcrd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    routercode: Option[Int] = None,
    userip1: Option[BigDecimal] = None,
    userip2: Option[BigDecimal] = None,
    typer: Option[Short] = None)(implicit session: DBSession = autoSession): PblConfRouterDiapazon = {
    val generatedKey = withSQL {
      insert.into(PblConfRouterDiapazon).namedValues(
        column.routercode -> routercode,
        column.userip1 -> userip1,
        column.userip2 -> userip2,
        column.typer -> typer
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfRouterDiapazon(
      code = generatedKey.toInt,
      routercode = routercode,
      userip1 = userip1,
      userip2 = userip2,
      typer = typer)
  }

  def batchInsert(entities: collection.Seq[PblConfRouterDiapazon])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'routercode -> entity.routercode,
        'userip1 -> entity.userip1,
        'userip2 -> entity.userip2,
        'typer -> entity.typer))
    SQL("""insert into pbl_conf_router_diapazon(
      routercode,
      userip1,
      userip2,
      typer
    ) values (
      {routercode},
      {userip1},
      {userip2},
      {typer}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfRouterDiapazon)(implicit session: DBSession = autoSession): PblConfRouterDiapazon = {
    withSQL {
      update(PblConfRouterDiapazon).set(
        column.code -> entity.code,
        column.routercode -> entity.routercode,
        column.userip1 -> entity.userip1,
        column.userip2 -> entity.userip2,
        column.typer -> entity.typer
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfRouterDiapazon)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfRouterDiapazon).where.eq(column.code, entity.code) }.update.apply()
  }

}
