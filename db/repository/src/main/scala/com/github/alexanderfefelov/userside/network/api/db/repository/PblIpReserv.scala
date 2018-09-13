package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblIpReserv(
  code: Int,
  userip: Option[BigDecimal] = None,
  opis: Option[String] = None) {

  def save()(implicit session: DBSession = PblIpReserv.autoSession): PblIpReserv = PblIpReserv.save(this)(session)

  def destroy()(implicit session: DBSession = PblIpReserv.autoSession): Int = PblIpReserv.destroy(this)(session)

}


object PblIpReserv extends SQLSyntaxSupport[PblIpReserv] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_ip_reserv"

  override val columns = Seq("code", "userip", "opis")

  def apply(pir: SyntaxProvider[PblIpReserv])(rs: WrappedResultSet): PblIpReserv = autoConstruct(rs, pir)
  def apply(pir: ResultName[PblIpReserv])(rs: WrappedResultSet): PblIpReserv = autoConstruct(rs, pir)

  val pir = PblIpReserv.syntax("pir")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblIpReserv] = {
    withSQL {
      select.from(PblIpReserv as pir).where.eq(pir.code, code)
    }.map(PblIpReserv(pir.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblIpReserv] = {
    withSQL(select.from(PblIpReserv as pir)).map(PblIpReserv(pir.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblIpReserv as pir)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblIpReserv] = {
    withSQL {
      select.from(PblIpReserv as pir).where.append(where)
    }.map(PblIpReserv(pir.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblIpReserv] = {
    withSQL {
      select.from(PblIpReserv as pir).where.append(where)
    }.map(PblIpReserv(pir.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblIpReserv as pir).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    userip: Option[BigDecimal] = None,
    opis: Option[String] = None)(implicit session: DBSession = autoSession): PblIpReserv = {
    val generatedKey = withSQL {
      insert.into(PblIpReserv).namedValues(
        column.userip -> userip,
        column.opis -> opis
      )
    }.updateAndReturnGeneratedKey.apply()

    PblIpReserv(
      code = generatedKey.toInt,
      userip = userip,
      opis = opis)
  }

  def batchInsert(entities: collection.Seq[PblIpReserv])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'userip -> entity.userip,
        'opis -> entity.opis))
    SQL("""insert into pbl_ip_reserv(
      userip,
      opis
    ) values (
      {userip},
      {opis}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblIpReserv)(implicit session: DBSession = autoSession): PblIpReserv = {
    withSQL {
      update(PblIpReserv).set(
        column.code -> entity.code,
        column.userip -> entity.userip,
        column.opis -> entity.opis
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblIpReserv)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblIpReserv).where.eq(column.code, entity.code) }.update.apply()
  }

}
