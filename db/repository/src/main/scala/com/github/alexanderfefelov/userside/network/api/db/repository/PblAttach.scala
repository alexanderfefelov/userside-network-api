package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblAttach(
  code: Int,
  kat: Option[String] = None,
  code1: Option[Int] = None,
  code2: Option[Int] = None,
  filepath: Option[String] = None,
  realname: Option[String] = None,
  opis: Option[String] = None,
  dateadd: Option[DateTime] = None,
  operatorId: Option[Int] = None,
  srcId: Option[Short] = None) {

  def save()(implicit session: DBSession = PblAttach.autoSession): PblAttach = PblAttach.save(this)(session)

  def destroy()(implicit session: DBSession = PblAttach.autoSession): Int = PblAttach.destroy(this)(session)

}


object PblAttach extends SQLSyntaxSupport[PblAttach] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_attach"

  override val columns = Seq("code", "kat", "code1", "code2", "filepath", "realname", "opis", "dateadd", "operator_id", "src_id")

  def apply(pa: SyntaxProvider[PblAttach])(rs: WrappedResultSet): PblAttach = autoConstruct(rs, pa)
  def apply(pa: ResultName[PblAttach])(rs: WrappedResultSet): PblAttach = autoConstruct(rs, pa)

  val pa = PblAttach.syntax("pa")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblAttach] = {
    withSQL {
      select.from(PblAttach as pa).where.eq(pa.code, code)
    }.map(PblAttach(pa.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblAttach] = {
    withSQL(select.from(PblAttach as pa)).map(PblAttach(pa.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblAttach as pa)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblAttach] = {
    withSQL {
      select.from(PblAttach as pa).where.append(where)
    }.map(PblAttach(pa.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblAttach] = {
    withSQL {
      select.from(PblAttach as pa).where.append(where)
    }.map(PblAttach(pa.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblAttach as pa).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    kat: Option[String] = None,
    code1: Option[Int] = None,
    code2: Option[Int] = None,
    filepath: Option[String] = None,
    realname: Option[String] = None,
    opis: Option[String] = None,
    dateadd: Option[DateTime] = None,
    operatorId: Option[Int] = None,
    srcId: Option[Short] = None)(implicit session: DBSession = autoSession): PblAttach = {
    val generatedKey = withSQL {
      insert.into(PblAttach).namedValues(
        column.kat -> kat,
        column.code1 -> code1,
        column.code2 -> code2,
        column.filepath -> filepath,
        column.realname -> realname,
        column.opis -> opis,
        column.dateadd -> dateadd,
        column.operatorId -> operatorId,
        column.srcId -> srcId
      )
    }.updateAndReturnGeneratedKey.apply()

    PblAttach(
      code = generatedKey.toInt,
      kat = kat,
      code1 = code1,
      code2 = code2,
      filepath = filepath,
      realname = realname,
      opis = opis,
      dateadd = dateadd,
      operatorId = operatorId,
      srcId = srcId)
  }

  def batchInsert(entities: collection.Seq[PblAttach])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'kat -> entity.kat,
        'code1 -> entity.code1,
        'code2 -> entity.code2,
        'filepath -> entity.filepath,
        'realname -> entity.realname,
        'opis -> entity.opis,
        'dateadd -> entity.dateadd,
        'operatorId -> entity.operatorId,
        'srcId -> entity.srcId))
    SQL("""insert into pbl_attach(
      kat,
      code1,
      code2,
      filepath,
      realname,
      opis,
      dateadd,
      operator_id,
      src_id
    ) values (
      {kat},
      {code1},
      {code2},
      {filepath},
      {realname},
      {opis},
      {dateadd},
      {operatorId},
      {srcId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblAttach)(implicit session: DBSession = autoSession): PblAttach = {
    withSQL {
      update(PblAttach).set(
        column.code -> entity.code,
        column.kat -> entity.kat,
        column.code1 -> entity.code1,
        column.code2 -> entity.code2,
        column.filepath -> entity.filepath,
        column.realname -> entity.realname,
        column.opis -> entity.opis,
        column.dateadd -> entity.dateadd,
        column.operatorId -> entity.operatorId,
        column.srcId -> entity.srcId
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblAttach)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblAttach).where.eq(column.code, entity.code) }.update.apply()
  }

}
