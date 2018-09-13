package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblIpUsernet(
  code: Int,
  usercode: Option[Int] = None,
  userip1: Option[BigDecimal] = None,
  userip2: Option[BigDecimal] = None,
  subnet: Option[BigDecimal] = None,
  opis: Option[String] = None,
  color: Option[String] = None) {

  def save()(implicit session: DBSession = PblIpUsernet.autoSession): PblIpUsernet = PblIpUsernet.save(this)(session)

  def destroy()(implicit session: DBSession = PblIpUsernet.autoSession): Int = PblIpUsernet.destroy(this)(session)

}


object PblIpUsernet extends SQLSyntaxSupport[PblIpUsernet] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_ip_usernet"

  override val columns = Seq("code", "usercode", "userip1", "userip2", "subnet", "opis", "color")

  def apply(piu: SyntaxProvider[PblIpUsernet])(rs: WrappedResultSet): PblIpUsernet = autoConstruct(rs, piu)
  def apply(piu: ResultName[PblIpUsernet])(rs: WrappedResultSet): PblIpUsernet = autoConstruct(rs, piu)

  val piu = PblIpUsernet.syntax("piu")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblIpUsernet] = {
    withSQL {
      select.from(PblIpUsernet as piu).where.eq(piu.code, code)
    }.map(PblIpUsernet(piu.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblIpUsernet] = {
    withSQL(select.from(PblIpUsernet as piu)).map(PblIpUsernet(piu.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblIpUsernet as piu)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblIpUsernet] = {
    withSQL {
      select.from(PblIpUsernet as piu).where.append(where)
    }.map(PblIpUsernet(piu.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblIpUsernet] = {
    withSQL {
      select.from(PblIpUsernet as piu).where.append(where)
    }.map(PblIpUsernet(piu.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblIpUsernet as piu).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    usercode: Option[Int] = None,
    userip1: Option[BigDecimal] = None,
    userip2: Option[BigDecimal] = None,
    subnet: Option[BigDecimal] = None,
    opis: Option[String] = None,
    color: Option[String] = None)(implicit session: DBSession = autoSession): PblIpUsernet = {
    val generatedKey = withSQL {
      insert.into(PblIpUsernet).namedValues(
        column.usercode -> usercode,
        column.userip1 -> userip1,
        column.userip2 -> userip2,
        column.subnet -> subnet,
        column.opis -> opis,
        column.color -> color
      )
    }.updateAndReturnGeneratedKey.apply()

    PblIpUsernet(
      code = generatedKey.toInt,
      usercode = usercode,
      userip1 = userip1,
      userip2 = userip2,
      subnet = subnet,
      opis = opis,
      color = color)
  }

  def batchInsert(entities: collection.Seq[PblIpUsernet])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'usercode -> entity.usercode,
        'userip1 -> entity.userip1,
        'userip2 -> entity.userip2,
        'subnet -> entity.subnet,
        'opis -> entity.opis,
        'color -> entity.color))
    SQL("""insert into pbl_ip_usernet(
      usercode,
      userip1,
      userip2,
      subnet,
      opis,
      color
    ) values (
      {usercode},
      {userip1},
      {userip2},
      {subnet},
      {opis},
      {color}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblIpUsernet)(implicit session: DBSession = autoSession): PblIpUsernet = {
    withSQL {
      update(PblIpUsernet).set(
        column.code -> entity.code,
        column.usercode -> entity.usercode,
        column.userip1 -> entity.userip1,
        column.userip2 -> entity.userip2,
        column.subnet -> entity.subnet,
        column.opis -> entity.opis,
        column.color -> entity.color
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblIpUsernet)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblIpUsernet).where.eq(column.code, entity.code) }.update.apply()
  }

}
