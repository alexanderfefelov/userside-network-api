package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblTrap(
  id: Int,
  timeTrap: Option[DateTime] = None,
  ipFrom: Option[BigDecimal] = None,
  hostname: Option[String] = None,
  mainOid: Option[String] = None,
  mainOidValue: Option[String] = None,
  raw: Option[String] = None,
  `type`: Option[Short] = None) {

  def save()(implicit session: DBSession = PblTrap.autoSession): PblTrap = PblTrap.save(this)(session)

  def destroy()(implicit session: DBSession = PblTrap.autoSession): Int = PblTrap.destroy(this)(session)

}


object PblTrap extends SQLSyntaxSupport[PblTrap] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_trap"

  override val columns = Seq("id", "time_trap", "ip_from", "hostname", "main_oid", "main_oid_value", "raw", "type")

  def apply(pt: SyntaxProvider[PblTrap])(rs: WrappedResultSet): PblTrap = autoConstruct(rs, pt)
  def apply(pt: ResultName[PblTrap])(rs: WrappedResultSet): PblTrap = autoConstruct(rs, pt)

  val pt = PblTrap.syntax("pt")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblTrap] = {
    withSQL {
      select.from(PblTrap as pt).where.eq(pt.id, id)
    }.map(PblTrap(pt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblTrap] = {
    withSQL(select.from(PblTrap as pt)).map(PblTrap(pt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblTrap as pt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblTrap] = {
    withSQL {
      select.from(PblTrap as pt).where.append(where)
    }.map(PblTrap(pt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblTrap] = {
    withSQL {
      select.from(PblTrap as pt).where.append(where)
    }.map(PblTrap(pt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblTrap as pt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    timeTrap: Option[DateTime] = None,
    ipFrom: Option[BigDecimal] = None,
    hostname: Option[String] = None,
    mainOid: Option[String] = None,
    mainOidValue: Option[String] = None,
    raw: Option[String] = None,
    `type`: Option[Short] = None)(implicit session: DBSession = autoSession): PblTrap = {
    val generatedKey = withSQL {
      insert.into(PblTrap).namedValues(
        column.timeTrap -> timeTrap,
        column.ipFrom -> ipFrom,
        column.hostname -> hostname,
        column.mainOid -> mainOid,
        column.mainOidValue -> mainOidValue,
        column.raw -> raw,
        column.`type` -> `type`
      )
    }.updateAndReturnGeneratedKey.apply()

    PblTrap(
      id = generatedKey.toInt,
      timeTrap = timeTrap,
      ipFrom = ipFrom,
      hostname = hostname,
      mainOid = mainOid,
      mainOidValue = mainOidValue,
      raw = raw,
      `type` = `type`)
  }

  def batchInsert(entities: collection.Seq[PblTrap])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'timeTrap -> entity.timeTrap,
        'ipFrom -> entity.ipFrom,
        'hostname -> entity.hostname,
        'mainOid -> entity.mainOid,
        'mainOidValue -> entity.mainOidValue,
        'raw -> entity.raw,
        'type -> entity.`type`))
    SQL("""insert into pbl_trap(
      time_trap,
      ip_from,
      hostname,
      main_oid,
      main_oid_value,
      raw,
      type
    ) values (
      {timeTrap},
      {ipFrom},
      {hostname},
      {mainOid},
      {mainOidValue},
      {raw},
      {type}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblTrap)(implicit session: DBSession = autoSession): PblTrap = {
    withSQL {
      update(PblTrap).set(
        column.id -> entity.id,
        column.timeTrap -> entity.timeTrap,
        column.ipFrom -> entity.ipFrom,
        column.hostname -> entity.hostname,
        column.mainOid -> entity.mainOid,
        column.mainOidValue -> entity.mainOidValue,
        column.raw -> entity.raw,
        column.`type` -> entity.`type`
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblTrap)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblTrap).where.eq(column.id, entity.id) }.update.apply()
  }

}
