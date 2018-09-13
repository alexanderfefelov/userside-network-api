package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblDeviceDopdata(
  code: Int,
  devtyper: Option[Int] = None,
  devcode: Option[Int] = None,
  typer: Option[Int] = None,
  typer2: Option[Int] = None,
  objId: Option[Int] = None,
  dataName: Option[String] = None,
  dateadd: Option[DateTime] = None,
  valuestr: Option[String] = None,
  valueint: Option[Int] = None,
  valuememo: Option[String] = None) {

  def save()(implicit session: DBSession = PblDeviceDopdata.autoSession): PblDeviceDopdata = PblDeviceDopdata.save(this)(session)

  def destroy()(implicit session: DBSession = PblDeviceDopdata.autoSession): Int = PblDeviceDopdata.destroy(this)(session)

}


object PblDeviceDopdata extends SQLSyntaxSupport[PblDeviceDopdata] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_device_dopdata"

  override val columns = Seq("code", "devtyper", "devcode", "typer", "typer2", "obj_id", "data_name", "dateadd", "valuestr", "valueint", "valuememo")

  def apply(pdd: SyntaxProvider[PblDeviceDopdata])(rs: WrappedResultSet): PblDeviceDopdata = autoConstruct(rs, pdd)
  def apply(pdd: ResultName[PblDeviceDopdata])(rs: WrappedResultSet): PblDeviceDopdata = autoConstruct(rs, pdd)

  val pdd = PblDeviceDopdata.syntax("pdd")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblDeviceDopdata] = {
    withSQL {
      select.from(PblDeviceDopdata as pdd).where.eq(pdd.code, code)
    }.map(PblDeviceDopdata(pdd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblDeviceDopdata] = {
    withSQL(select.from(PblDeviceDopdata as pdd)).map(PblDeviceDopdata(pdd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblDeviceDopdata as pdd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblDeviceDopdata] = {
    withSQL {
      select.from(PblDeviceDopdata as pdd).where.append(where)
    }.map(PblDeviceDopdata(pdd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblDeviceDopdata] = {
    withSQL {
      select.from(PblDeviceDopdata as pdd).where.append(where)
    }.map(PblDeviceDopdata(pdd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblDeviceDopdata as pdd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    devtyper: Option[Int] = None,
    devcode: Option[Int] = None,
    typer: Option[Int] = None,
    typer2: Option[Int] = None,
    objId: Option[Int] = None,
    dataName: Option[String] = None,
    dateadd: Option[DateTime] = None,
    valuestr: Option[String] = None,
    valueint: Option[Int] = None,
    valuememo: Option[String] = None)(implicit session: DBSession = autoSession): PblDeviceDopdata = {
    val generatedKey = withSQL {
      insert.into(PblDeviceDopdata).namedValues(
        column.devtyper -> devtyper,
        column.devcode -> devcode,
        column.typer -> typer,
        column.typer2 -> typer2,
        column.objId -> objId,
        column.dataName -> dataName,
        column.dateadd -> dateadd,
        column.valuestr -> valuestr,
        column.valueint -> valueint,
        column.valuememo -> valuememo
      )
    }.updateAndReturnGeneratedKey.apply()

    PblDeviceDopdata(
      code = generatedKey.toInt,
      devtyper = devtyper,
      devcode = devcode,
      typer = typer,
      typer2 = typer2,
      objId = objId,
      dataName = dataName,
      dateadd = dateadd,
      valuestr = valuestr,
      valueint = valueint,
      valuememo = valuememo)
  }

  def batchInsert(entities: collection.Seq[PblDeviceDopdata])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'devtyper -> entity.devtyper,
        'devcode -> entity.devcode,
        'typer -> entity.typer,
        'typer2 -> entity.typer2,
        'objId -> entity.objId,
        'dataName -> entity.dataName,
        'dateadd -> entity.dateadd,
        'valuestr -> entity.valuestr,
        'valueint -> entity.valueint,
        'valuememo -> entity.valuememo))
    SQL("""insert into pbl_device_dopdata(
      devtyper,
      devcode,
      typer,
      typer2,
      obj_id,
      data_name,
      dateadd,
      valuestr,
      valueint,
      valuememo
    ) values (
      {devtyper},
      {devcode},
      {typer},
      {typer2},
      {objId},
      {dataName},
      {dateadd},
      {valuestr},
      {valueint},
      {valuememo}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblDeviceDopdata)(implicit session: DBSession = autoSession): PblDeviceDopdata = {
    withSQL {
      update(PblDeviceDopdata).set(
        column.code -> entity.code,
        column.devtyper -> entity.devtyper,
        column.devcode -> entity.devcode,
        column.typer -> entity.typer,
        column.typer2 -> entity.typer2,
        column.objId -> entity.objId,
        column.dataName -> entity.dataName,
        column.dateadd -> entity.dateadd,
        column.valuestr -> entity.valuestr,
        column.valueint -> entity.valueint,
        column.valuememo -> entity.valuememo
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblDeviceDopdata)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblDeviceDopdata).where.eq(column.code, entity.code) }.update.apply()
  }

}
