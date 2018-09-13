package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblControlStat(
  code: Int,
  devtyper: Option[Short] = None,
  devcode: Option[Int] = None,
  oidcode: Option[Int] = None,
  datechange: Option[DateTime] = None,
  lastvalue: Option[String] = None,
  issend: Option[Short] = None,
  whoreceive: Option[String] = None) {

  def save()(implicit session: DBSession = PblControlStat.autoSession): PblControlStat = PblControlStat.save(this)(session)

  def destroy()(implicit session: DBSession = PblControlStat.autoSession): Int = PblControlStat.destroy(this)(session)

}


object PblControlStat extends SQLSyntaxSupport[PblControlStat] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_control_stat"

  override val columns = Seq("code", "devtyper", "devcode", "oidcode", "datechange", "lastvalue", "issend", "whoreceive")

  def apply(pcs: SyntaxProvider[PblControlStat])(rs: WrappedResultSet): PblControlStat = autoConstruct(rs, pcs)
  def apply(pcs: ResultName[PblControlStat])(rs: WrappedResultSet): PblControlStat = autoConstruct(rs, pcs)

  val pcs = PblControlStat.syntax("pcs")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblControlStat] = {
    withSQL {
      select.from(PblControlStat as pcs).where.eq(pcs.code, code)
    }.map(PblControlStat(pcs.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblControlStat] = {
    withSQL(select.from(PblControlStat as pcs)).map(PblControlStat(pcs.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblControlStat as pcs)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblControlStat] = {
    withSQL {
      select.from(PblControlStat as pcs).where.append(where)
    }.map(PblControlStat(pcs.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblControlStat] = {
    withSQL {
      select.from(PblControlStat as pcs).where.append(where)
    }.map(PblControlStat(pcs.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblControlStat as pcs).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    devtyper: Option[Short] = None,
    devcode: Option[Int] = None,
    oidcode: Option[Int] = None,
    datechange: Option[DateTime] = None,
    lastvalue: Option[String] = None,
    issend: Option[Short] = None,
    whoreceive: Option[String] = None)(implicit session: DBSession = autoSession): PblControlStat = {
    val generatedKey = withSQL {
      insert.into(PblControlStat).namedValues(
        column.devtyper -> devtyper,
        column.devcode -> devcode,
        column.oidcode -> oidcode,
        column.datechange -> datechange,
        column.lastvalue -> lastvalue,
        column.issend -> issend,
        column.whoreceive -> whoreceive
      )
    }.updateAndReturnGeneratedKey.apply()

    PblControlStat(
      code = generatedKey.toInt,
      devtyper = devtyper,
      devcode = devcode,
      oidcode = oidcode,
      datechange = datechange,
      lastvalue = lastvalue,
      issend = issend,
      whoreceive = whoreceive)
  }

  def batchInsert(entities: collection.Seq[PblControlStat])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'devtyper -> entity.devtyper,
        'devcode -> entity.devcode,
        'oidcode -> entity.oidcode,
        'datechange -> entity.datechange,
        'lastvalue -> entity.lastvalue,
        'issend -> entity.issend,
        'whoreceive -> entity.whoreceive))
    SQL("""insert into pbl_control_stat(
      devtyper,
      devcode,
      oidcode,
      datechange,
      lastvalue,
      issend,
      whoreceive
    ) values (
      {devtyper},
      {devcode},
      {oidcode},
      {datechange},
      {lastvalue},
      {issend},
      {whoreceive}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblControlStat)(implicit session: DBSession = autoSession): PblControlStat = {
    withSQL {
      update(PblControlStat).set(
        column.code -> entity.code,
        column.devtyper -> entity.devtyper,
        column.devcode -> entity.devcode,
        column.oidcode -> entity.oidcode,
        column.datechange -> entity.datechange,
        column.lastvalue -> entity.lastvalue,
        column.issend -> entity.issend,
        column.whoreceive -> entity.whoreceive
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblControlStat)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblControlStat).where.eq(column.code, entity.code) }.update.apply()
  }

}
