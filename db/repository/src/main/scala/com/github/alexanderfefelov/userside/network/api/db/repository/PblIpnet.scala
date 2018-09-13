package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblIpnet(
  code: Int,
  userip: Option[BigDecimal] = None,
  opis: Option[String] = None,
  ipstart: Option[BigDecimal] = None,
  ipstop: Option[BigDecimal] = None,
  ishide: Option[Short] = None) {

  def save()(implicit session: DBSession = PblIpnet.autoSession): PblIpnet = PblIpnet.save(this)(session)

  def destroy()(implicit session: DBSession = PblIpnet.autoSession): Int = PblIpnet.destroy(this)(session)

}


object PblIpnet extends SQLSyntaxSupport[PblIpnet] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_ipnet"

  override val columns = Seq("code", "userip", "opis", "ipstart", "ipstop", "ishide")

  def apply(pi: SyntaxProvider[PblIpnet])(rs: WrappedResultSet): PblIpnet = autoConstruct(rs, pi)
  def apply(pi: ResultName[PblIpnet])(rs: WrappedResultSet): PblIpnet = autoConstruct(rs, pi)

  val pi = PblIpnet.syntax("pi")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblIpnet] = {
    withSQL {
      select.from(PblIpnet as pi).where.eq(pi.code, code)
    }.map(PblIpnet(pi.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblIpnet] = {
    withSQL(select.from(PblIpnet as pi)).map(PblIpnet(pi.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblIpnet as pi)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblIpnet] = {
    withSQL {
      select.from(PblIpnet as pi).where.append(where)
    }.map(PblIpnet(pi.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblIpnet] = {
    withSQL {
      select.from(PblIpnet as pi).where.append(where)
    }.map(PblIpnet(pi.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblIpnet as pi).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    userip: Option[BigDecimal] = None,
    opis: Option[String] = None,
    ipstart: Option[BigDecimal] = None,
    ipstop: Option[BigDecimal] = None,
    ishide: Option[Short] = None)(implicit session: DBSession = autoSession): PblIpnet = {
    val generatedKey = withSQL {
      insert.into(PblIpnet).namedValues(
        column.userip -> userip,
        column.opis -> opis,
        column.ipstart -> ipstart,
        column.ipstop -> ipstop,
        column.ishide -> ishide
      )
    }.updateAndReturnGeneratedKey.apply()

    PblIpnet(
      code = generatedKey.toInt,
      userip = userip,
      opis = opis,
      ipstart = ipstart,
      ipstop = ipstop,
      ishide = ishide)
  }

  def batchInsert(entities: collection.Seq[PblIpnet])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'userip -> entity.userip,
        'opis -> entity.opis,
        'ipstart -> entity.ipstart,
        'ipstop -> entity.ipstop,
        'ishide -> entity.ishide))
    SQL("""insert into pbl_ipnet(
      userip,
      opis,
      ipstart,
      ipstop,
      ishide
    ) values (
      {userip},
      {opis},
      {ipstart},
      {ipstop},
      {ishide}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblIpnet)(implicit session: DBSession = autoSession): PblIpnet = {
    withSQL {
      update(PblIpnet).set(
        column.code -> entity.code,
        column.userip -> entity.userip,
        column.opis -> entity.opis,
        column.ipstart -> entity.ipstart,
        column.ipstop -> entity.ipstop,
        column.ishide -> entity.ishide
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblIpnet)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblIpnet).where.eq(column.code, entity.code) }.update.apply()
  }

}
