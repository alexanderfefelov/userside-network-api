package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblAvto(
  code: Int,
  brand: Option[String] = None,
  model: Option[String] = None,
  regno: Option[String] = None,
  gpsId: Option[String] = None,
  dateadd: Option[DateTime] = None) {

  def save()(implicit session: DBSession = PblAvto.autoSession): PblAvto = PblAvto.save(this)(session)

  def destroy()(implicit session: DBSession = PblAvto.autoSession): Int = PblAvto.destroy(this)(session)

}


object PblAvto extends SQLSyntaxSupport[PblAvto] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_avto"

  override val columns = Seq("code", "brand", "model", "regno", "gps_id", "dateadd")

  def apply(pa: SyntaxProvider[PblAvto])(rs: WrappedResultSet): PblAvto = autoConstruct(rs, pa)
  def apply(pa: ResultName[PblAvto])(rs: WrappedResultSet): PblAvto = autoConstruct(rs, pa)

  val pa = PblAvto.syntax("pa")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblAvto] = {
    withSQL {
      select.from(PblAvto as pa).where.eq(pa.code, code)
    }.map(PblAvto(pa.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblAvto] = {
    withSQL(select.from(PblAvto as pa)).map(PblAvto(pa.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblAvto as pa)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblAvto] = {
    withSQL {
      select.from(PblAvto as pa).where.append(where)
    }.map(PblAvto(pa.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblAvto] = {
    withSQL {
      select.from(PblAvto as pa).where.append(where)
    }.map(PblAvto(pa.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblAvto as pa).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    brand: Option[String] = None,
    model: Option[String] = None,
    regno: Option[String] = None,
    gpsId: Option[String] = None,
    dateadd: Option[DateTime] = None)(implicit session: DBSession = autoSession): PblAvto = {
    val generatedKey = withSQL {
      insert.into(PblAvto).namedValues(
        column.brand -> brand,
        column.model -> model,
        column.regno -> regno,
        column.gpsId -> gpsId,
        column.dateadd -> dateadd
      )
    }.updateAndReturnGeneratedKey.apply()

    PblAvto(
      code = generatedKey.toInt,
      brand = brand,
      model = model,
      regno = regno,
      gpsId = gpsId,
      dateadd = dateadd)
  }

  def batchInsert(entities: collection.Seq[PblAvto])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'brand -> entity.brand,
        'model -> entity.model,
        'regno -> entity.regno,
        'gpsId -> entity.gpsId,
        'dateadd -> entity.dateadd))
    SQL("""insert into pbl_avto(
      brand,
      model,
      regno,
      gps_id,
      dateadd
    ) values (
      {brand},
      {model},
      {regno},
      {gpsId},
      {dateadd}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblAvto)(implicit session: DBSession = autoSession): PblAvto = {
    withSQL {
      update(PblAvto).set(
        column.code -> entity.code,
        column.brand -> entity.brand,
        column.model -> entity.model,
        column.regno -> entity.regno,
        column.gpsId -> entity.gpsId,
        column.dateadd -> entity.dateadd
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblAvto)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblAvto).where.eq(column.code, entity.code) }.update.apply()
  }

}
