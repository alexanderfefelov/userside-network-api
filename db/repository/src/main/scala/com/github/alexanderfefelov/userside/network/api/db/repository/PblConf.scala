package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblConf(
  id: Int,
  param: Option[String] = None,
  valuestr: Option[String] = None,
  valuedate: Option[DateTime] = None,
  valueint: Option[Int] = None,
  valuememo: Option[String] = None) {

  def save()(implicit session: DBSession = PblConf.autoSession): PblConf = PblConf.save(this)(session)

  def destroy()(implicit session: DBSession = PblConf.autoSession): Int = PblConf.destroy(this)(session)

}


object PblConf extends SQLSyntaxSupport[PblConf] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf"

  override val columns = Seq("id", "param", "valuestr", "valuedate", "valueint", "valuememo")

  def apply(pc: SyntaxProvider[PblConf])(rs: WrappedResultSet): PblConf = autoConstruct(rs, pc)
  def apply(pc: ResultName[PblConf])(rs: WrappedResultSet): PblConf = autoConstruct(rs, pc)

  val pc = PblConf.syntax("pc")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblConf] = {
    withSQL {
      select.from(PblConf as pc).where.eq(pc.id, id)
    }.map(PblConf(pc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConf] = {
    withSQL(select.from(PblConf as pc)).map(PblConf(pc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConf as pc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConf] = {
    withSQL {
      select.from(PblConf as pc).where.append(where)
    }.map(PblConf(pc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConf] = {
    withSQL {
      select.from(PblConf as pc).where.append(where)
    }.map(PblConf(pc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConf as pc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    param: Option[String] = None,
    valuestr: Option[String] = None,
    valuedate: Option[DateTime] = None,
    valueint: Option[Int] = None,
    valuememo: Option[String] = None)(implicit session: DBSession = autoSession): PblConf = {
    val generatedKey = withSQL {
      insert.into(PblConf).namedValues(
        column.param -> param,
        column.valuestr -> valuestr,
        column.valuedate -> valuedate,
        column.valueint -> valueint,
        column.valuememo -> valuememo
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConf(
      id = generatedKey.toInt,
      param = param,
      valuestr = valuestr,
      valuedate = valuedate,
      valueint = valueint,
      valuememo = valuememo)
  }

  def batchInsert(entities: collection.Seq[PblConf])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'param -> entity.param,
        'valuestr -> entity.valuestr,
        'valuedate -> entity.valuedate,
        'valueint -> entity.valueint,
        'valuememo -> entity.valuememo))
    SQL("""insert into pbl_conf(
      param,
      valuestr,
      valuedate,
      valueint,
      valuememo
    ) values (
      {param},
      {valuestr},
      {valuedate},
      {valueint},
      {valuememo}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConf)(implicit session: DBSession = autoSession): PblConf = {
    withSQL {
      update(PblConf).set(
        column.id -> entity.id,
        column.param -> entity.param,
        column.valuestr -> entity.valuestr,
        column.valuedate -> entity.valuedate,
        column.valueint -> entity.valueint,
        column.valuememo -> entity.valuememo
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConf)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConf).where.eq(column.id, entity.id) }.update.apply()
  }

}
