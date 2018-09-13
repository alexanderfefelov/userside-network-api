package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblControlCurrent(
  code: Int,
  devtyper: Option[Short] = None,
  devcode: Option[Int] = None,
  oidcode: Option[Int] = None,
  datedo: Option[DateTime] = None,
  lastvalue: Option[String] = None,
  isalert: Option[Short] = None,
  isupd: Option[Short] = None) {

  def save()(implicit session: DBSession = PblControlCurrent.autoSession): PblControlCurrent = PblControlCurrent.save(this)(session)

  def destroy()(implicit session: DBSession = PblControlCurrent.autoSession): Int = PblControlCurrent.destroy(this)(session)

}


object PblControlCurrent extends SQLSyntaxSupport[PblControlCurrent] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_control_current"

  override val columns = Seq("code", "devtyper", "devcode", "oidcode", "datedo", "lastvalue", "isalert", "isupd")

  def apply(pcc: SyntaxProvider[PblControlCurrent])(rs: WrappedResultSet): PblControlCurrent = autoConstruct(rs, pcc)
  def apply(pcc: ResultName[PblControlCurrent])(rs: WrappedResultSet): PblControlCurrent = autoConstruct(rs, pcc)

  val pcc = PblControlCurrent.syntax("pcc")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblControlCurrent] = {
    withSQL {
      select.from(PblControlCurrent as pcc).where.eq(pcc.code, code)
    }.map(PblControlCurrent(pcc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblControlCurrent] = {
    withSQL(select.from(PblControlCurrent as pcc)).map(PblControlCurrent(pcc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblControlCurrent as pcc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblControlCurrent] = {
    withSQL {
      select.from(PblControlCurrent as pcc).where.append(where)
    }.map(PblControlCurrent(pcc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblControlCurrent] = {
    withSQL {
      select.from(PblControlCurrent as pcc).where.append(where)
    }.map(PblControlCurrent(pcc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblControlCurrent as pcc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    devtyper: Option[Short] = None,
    devcode: Option[Int] = None,
    oidcode: Option[Int] = None,
    datedo: Option[DateTime] = None,
    lastvalue: Option[String] = None,
    isalert: Option[Short] = None,
    isupd: Option[Short] = None)(implicit session: DBSession = autoSession): PblControlCurrent = {
    val generatedKey = withSQL {
      insert.into(PblControlCurrent).namedValues(
        column.devtyper -> devtyper,
        column.devcode -> devcode,
        column.oidcode -> oidcode,
        column.datedo -> datedo,
        column.lastvalue -> lastvalue,
        column.isalert -> isalert,
        column.isupd -> isupd
      )
    }.updateAndReturnGeneratedKey.apply()

    PblControlCurrent(
      code = generatedKey.toInt,
      devtyper = devtyper,
      devcode = devcode,
      oidcode = oidcode,
      datedo = datedo,
      lastvalue = lastvalue,
      isalert = isalert,
      isupd = isupd)
  }

  def batchInsert(entities: collection.Seq[PblControlCurrent])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'devtyper -> entity.devtyper,
        'devcode -> entity.devcode,
        'oidcode -> entity.oidcode,
        'datedo -> entity.datedo,
        'lastvalue -> entity.lastvalue,
        'isalert -> entity.isalert,
        'isupd -> entity.isupd))
    SQL("""insert into pbl_control_current(
      devtyper,
      devcode,
      oidcode,
      datedo,
      lastvalue,
      isalert,
      isupd
    ) values (
      {devtyper},
      {devcode},
      {oidcode},
      {datedo},
      {lastvalue},
      {isalert},
      {isupd}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblControlCurrent)(implicit session: DBSession = autoSession): PblControlCurrent = {
    withSQL {
      update(PblControlCurrent).set(
        column.code -> entity.code,
        column.devtyper -> entity.devtyper,
        column.devcode -> entity.devcode,
        column.oidcode -> entity.oidcode,
        column.datedo -> entity.datedo,
        column.lastvalue -> entity.lastvalue,
        column.isalert -> entity.isalert,
        column.isupd -> entity.isupd
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblControlCurrent)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblControlCurrent).where.eq(column.code, entity.code) }.update.apply()
  }

}
