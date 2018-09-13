package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblOtkl(
  code: Int,
  dateadd: Option[DateTime] = None,
  usercode: Option[Int] = None,
  whoadd: Option[Int] = None,
  status: Option[Short] = None,
  dateotkl: Option[DateTime] = None) {

  def save()(implicit session: DBSession = PblOtkl.autoSession): PblOtkl = PblOtkl.save(this)(session)

  def destroy()(implicit session: DBSession = PblOtkl.autoSession): Int = PblOtkl.destroy(this)(session)

}


object PblOtkl extends SQLSyntaxSupport[PblOtkl] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_otkl"

  override val columns = Seq("code", "dateadd", "usercode", "whoadd", "status", "dateotkl")

  def apply(po: SyntaxProvider[PblOtkl])(rs: WrappedResultSet): PblOtkl = autoConstruct(rs, po)
  def apply(po: ResultName[PblOtkl])(rs: WrappedResultSet): PblOtkl = autoConstruct(rs, po)

  val po = PblOtkl.syntax("po")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblOtkl] = {
    withSQL {
      select.from(PblOtkl as po).where.eq(po.code, code)
    }.map(PblOtkl(po.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblOtkl] = {
    withSQL(select.from(PblOtkl as po)).map(PblOtkl(po.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblOtkl as po)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblOtkl] = {
    withSQL {
      select.from(PblOtkl as po).where.append(where)
    }.map(PblOtkl(po.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblOtkl] = {
    withSQL {
      select.from(PblOtkl as po).where.append(where)
    }.map(PblOtkl(po.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblOtkl as po).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    dateadd: Option[DateTime] = None,
    usercode: Option[Int] = None,
    whoadd: Option[Int] = None,
    status: Option[Short] = None,
    dateotkl: Option[DateTime] = None)(implicit session: DBSession = autoSession): PblOtkl = {
    val generatedKey = withSQL {
      insert.into(PblOtkl).namedValues(
        column.dateadd -> dateadd,
        column.usercode -> usercode,
        column.whoadd -> whoadd,
        column.status -> status,
        column.dateotkl -> dateotkl
      )
    }.updateAndReturnGeneratedKey.apply()

    PblOtkl(
      code = generatedKey.toInt,
      dateadd = dateadd,
      usercode = usercode,
      whoadd = whoadd,
      status = status,
      dateotkl = dateotkl)
  }

  def batchInsert(entities: collection.Seq[PblOtkl])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'dateadd -> entity.dateadd,
        'usercode -> entity.usercode,
        'whoadd -> entity.whoadd,
        'status -> entity.status,
        'dateotkl -> entity.dateotkl))
    SQL("""insert into pbl_otkl(
      dateadd,
      usercode,
      whoadd,
      status,
      dateotkl
    ) values (
      {dateadd},
      {usercode},
      {whoadd},
      {status},
      {dateotkl}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblOtkl)(implicit session: DBSession = autoSession): PblOtkl = {
    withSQL {
      update(PblOtkl).set(
        column.code -> entity.code,
        column.dateadd -> entity.dateadd,
        column.usercode -> entity.usercode,
        column.whoadd -> entity.whoadd,
        column.status -> entity.status,
        column.dateotkl -> entity.dateotkl
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblOtkl)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblOtkl).where.eq(column.code, entity.code) }.update.apply()
  }

}
