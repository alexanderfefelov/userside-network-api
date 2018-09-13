package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblOperCashe(
  code: Int,
  opercode: Option[Int] = None,
  datedo: Option[DateTime] = None,
  param1: Option[Int] = None,
  param2: Option[Int] = None,
  value: Option[String] = None) {

  def save()(implicit session: DBSession = PblOperCashe.autoSession): PblOperCashe = PblOperCashe.save(this)(session)

  def destroy()(implicit session: DBSession = PblOperCashe.autoSession): Int = PblOperCashe.destroy(this)(session)

}


object PblOperCashe extends SQLSyntaxSupport[PblOperCashe] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_oper_cashe"

  override val columns = Seq("code", "opercode", "datedo", "param1", "param2", "value")

  def apply(poc: SyntaxProvider[PblOperCashe])(rs: WrappedResultSet): PblOperCashe = autoConstruct(rs, poc)
  def apply(poc: ResultName[PblOperCashe])(rs: WrappedResultSet): PblOperCashe = autoConstruct(rs, poc)

  val poc = PblOperCashe.syntax("poc")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblOperCashe] = {
    withSQL {
      select.from(PblOperCashe as poc).where.eq(poc.code, code)
    }.map(PblOperCashe(poc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblOperCashe] = {
    withSQL(select.from(PblOperCashe as poc)).map(PblOperCashe(poc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblOperCashe as poc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblOperCashe] = {
    withSQL {
      select.from(PblOperCashe as poc).where.append(where)
    }.map(PblOperCashe(poc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblOperCashe] = {
    withSQL {
      select.from(PblOperCashe as poc).where.append(where)
    }.map(PblOperCashe(poc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblOperCashe as poc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    opercode: Option[Int] = None,
    datedo: Option[DateTime] = None,
    param1: Option[Int] = None,
    param2: Option[Int] = None,
    value: Option[String] = None)(implicit session: DBSession = autoSession): PblOperCashe = {
    val generatedKey = withSQL {
      insert.into(PblOperCashe).namedValues(
        column.opercode -> opercode,
        column.datedo -> datedo,
        column.param1 -> param1,
        column.param2 -> param2,
        column.value -> value
      )
    }.updateAndReturnGeneratedKey.apply()

    PblOperCashe(
      code = generatedKey.toInt,
      opercode = opercode,
      datedo = datedo,
      param1 = param1,
      param2 = param2,
      value = value)
  }

  def batchInsert(entities: collection.Seq[PblOperCashe])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'opercode -> entity.opercode,
        'datedo -> entity.datedo,
        'param1 -> entity.param1,
        'param2 -> entity.param2,
        'value -> entity.value))
    SQL("""insert into pbl_oper_cashe(
      opercode,
      datedo,
      param1,
      param2,
      value
    ) values (
      {opercode},
      {datedo},
      {param1},
      {param2},
      {value}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblOperCashe)(implicit session: DBSession = autoSession): PblOperCashe = {
    withSQL {
      update(PblOperCashe).set(
        column.code -> entity.code,
        column.opercode -> entity.opercode,
        column.datedo -> entity.datedo,
        column.param1 -> entity.param1,
        column.param2 -> entity.param2,
        column.value -> entity.value
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblOperCashe)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblOperCashe).where.eq(column.code, entity.code) }.update.apply()
  }

}
