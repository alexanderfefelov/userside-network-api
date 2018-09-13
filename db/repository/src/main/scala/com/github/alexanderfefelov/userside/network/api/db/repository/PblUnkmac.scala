package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblUnkmac(
  code: Int,
  mac: Option[String] = None,
  userip: Option[BigDecimal] = None,
  firstdate: Option[DateTime] = None,
  lastdate: Option[DateTime] = None,
  modcode: Option[Short] = None) {

  def save()(implicit session: DBSession = PblUnkmac.autoSession): PblUnkmac = PblUnkmac.save(this)(session)

  def destroy()(implicit session: DBSession = PblUnkmac.autoSession): Int = PblUnkmac.destroy(this)(session)

}


object PblUnkmac extends SQLSyntaxSupport[PblUnkmac] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_unkmac"

  override val columns = Seq("code", "mac", "userip", "firstdate", "lastdate", "modcode")

  def apply(pu: SyntaxProvider[PblUnkmac])(rs: WrappedResultSet): PblUnkmac = autoConstruct(rs, pu)
  def apply(pu: ResultName[PblUnkmac])(rs: WrappedResultSet): PblUnkmac = autoConstruct(rs, pu)

  val pu = PblUnkmac.syntax("pu")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblUnkmac] = {
    withSQL {
      select.from(PblUnkmac as pu).where.eq(pu.code, code)
    }.map(PblUnkmac(pu.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblUnkmac] = {
    withSQL(select.from(PblUnkmac as pu)).map(PblUnkmac(pu.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblUnkmac as pu)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblUnkmac] = {
    withSQL {
      select.from(PblUnkmac as pu).where.append(where)
    }.map(PblUnkmac(pu.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblUnkmac] = {
    withSQL {
      select.from(PblUnkmac as pu).where.append(where)
    }.map(PblUnkmac(pu.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblUnkmac as pu).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    mac: Option[String] = None,
    userip: Option[BigDecimal] = None,
    firstdate: Option[DateTime] = None,
    lastdate: Option[DateTime] = None,
    modcode: Option[Short] = None)(implicit session: DBSession = autoSession): PblUnkmac = {
    val generatedKey = withSQL {
      insert.into(PblUnkmac).namedValues(
        column.mac -> mac,
        column.userip -> userip,
        column.firstdate -> firstdate,
        column.lastdate -> lastdate,
        column.modcode -> modcode
      )
    }.updateAndReturnGeneratedKey.apply()

    PblUnkmac(
      code = generatedKey.toInt,
      mac = mac,
      userip = userip,
      firstdate = firstdate,
      lastdate = lastdate,
      modcode = modcode)
  }

  def batchInsert(entities: collection.Seq[PblUnkmac])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'mac -> entity.mac,
        'userip -> entity.userip,
        'firstdate -> entity.firstdate,
        'lastdate -> entity.lastdate,
        'modcode -> entity.modcode))
    SQL("""insert into pbl_unkmac(
      mac,
      userip,
      firstdate,
      lastdate,
      modcode
    ) values (
      {mac},
      {userip},
      {firstdate},
      {lastdate},
      {modcode}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblUnkmac)(implicit session: DBSession = autoSession): PblUnkmac = {
    withSQL {
      update(PblUnkmac).set(
        column.code -> entity.code,
        column.mac -> entity.mac,
        column.userip -> entity.userip,
        column.firstdate -> entity.firstdate,
        column.lastdate -> entity.lastdate,
        column.modcode -> entity.modcode
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblUnkmac)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblUnkmac).where.eq(column.code, entity.code) }.update.apply()
  }

}
