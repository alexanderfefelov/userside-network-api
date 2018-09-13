package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblIp(
  id: Int,
  typer: Option[Short] = None,
  usercode: Option[Int] = None,
  userip: Option[BigDecimal] = None,
  mac: Option[String] = None,
  isupd: Option[Short] = None,
  modifer: Option[Short] = None,
  ismain: Option[Short] = None) {

  def save()(implicit session: DBSession = PblIp.autoSession): PblIp = PblIp.save(this)(session)

  def destroy()(implicit session: DBSession = PblIp.autoSession): Int = PblIp.destroy(this)(session)

}


object PblIp extends SQLSyntaxSupport[PblIp] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_ip"

  override val columns = Seq("id", "typer", "usercode", "userip", "mac", "isupd", "modifer", "ismain")

  def apply(pi: SyntaxProvider[PblIp])(rs: WrappedResultSet): PblIp = autoConstruct(rs, pi)
  def apply(pi: ResultName[PblIp])(rs: WrappedResultSet): PblIp = autoConstruct(rs, pi)

  val pi = PblIp.syntax("pi")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblIp] = {
    withSQL {
      select.from(PblIp as pi).where.eq(pi.id, id)
    }.map(PblIp(pi.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblIp] = {
    withSQL(select.from(PblIp as pi)).map(PblIp(pi.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblIp as pi)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblIp] = {
    withSQL {
      select.from(PblIp as pi).where.append(where)
    }.map(PblIp(pi.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblIp] = {
    withSQL {
      select.from(PblIp as pi).where.append(where)
    }.map(PblIp(pi.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblIp as pi).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    typer: Option[Short] = None,
    usercode: Option[Int] = None,
    userip: Option[BigDecimal] = None,
    mac: Option[String] = None,
    isupd: Option[Short] = None,
    modifer: Option[Short] = None,
    ismain: Option[Short] = None)(implicit session: DBSession = autoSession): PblIp = {
    val generatedKey = withSQL {
      insert.into(PblIp).namedValues(
        column.typer -> typer,
        column.usercode -> usercode,
        column.userip -> userip,
        column.mac -> mac,
        column.isupd -> isupd,
        column.modifer -> modifer,
        column.ismain -> ismain
      )
    }.updateAndReturnGeneratedKey.apply()

    PblIp(
      id = generatedKey.toInt,
      typer = typer,
      usercode = usercode,
      userip = userip,
      mac = mac,
      isupd = isupd,
      modifer = modifer,
      ismain = ismain)
  }

  def batchInsert(entities: collection.Seq[PblIp])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'typer -> entity.typer,
        'usercode -> entity.usercode,
        'userip -> entity.userip,
        'mac -> entity.mac,
        'isupd -> entity.isupd,
        'modifer -> entity.modifer,
        'ismain -> entity.ismain))
    SQL("""insert into pbl_ip(
      typer,
      usercode,
      userip,
      mac,
      isupd,
      modifer,
      ismain
    ) values (
      {typer},
      {usercode},
      {userip},
      {mac},
      {isupd},
      {modifer},
      {ismain}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblIp)(implicit session: DBSession = autoSession): PblIp = {
    withSQL {
      update(PblIp).set(
        column.id -> entity.id,
        column.typer -> entity.typer,
        column.usercode -> entity.usercode,
        column.userip -> entity.userip,
        column.mac -> entity.mac,
        column.isupd -> entity.isupd,
        column.modifer -> entity.modifer,
        column.ismain -> entity.ismain
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblIp)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblIp).where.eq(column.id, entity.id) }.update.apply()
  }

}
