package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblMap(
  code: Int,
  codenumber: Option[Int] = None,
  opis: Option[String] = None,
  dateadd: Option[DateTime] = None,
  typemap: Option[Short] = None,
  geoX: Option[BigDecimal] = None,
  geoY: Option[BigDecimal] = None,
  geoScale: Option[Short] = None,
  maptypeshow: Option[Short] = None) {

  def save()(implicit session: DBSession = PblMap.autoSession): PblMap = PblMap.save(this)(session)

  def destroy()(implicit session: DBSession = PblMap.autoSession): Int = PblMap.destroy(this)(session)

}


object PblMap extends SQLSyntaxSupport[PblMap] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_map"

  override val columns = Seq("code", "codenumber", "opis", "dateadd", "typemap", "geo_x", "geo_y", "geo_scale", "maptypeshow")

  def apply(pm: SyntaxProvider[PblMap])(rs: WrappedResultSet): PblMap = autoConstruct(rs, pm)
  def apply(pm: ResultName[PblMap])(rs: WrappedResultSet): PblMap = autoConstruct(rs, pm)

  val pm = PblMap.syntax("pm")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblMap] = {
    withSQL {
      select.from(PblMap as pm).where.eq(pm.code, code)
    }.map(PblMap(pm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblMap] = {
    withSQL(select.from(PblMap as pm)).map(PblMap(pm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblMap as pm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblMap] = {
    withSQL {
      select.from(PblMap as pm).where.append(where)
    }.map(PblMap(pm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblMap] = {
    withSQL {
      select.from(PblMap as pm).where.append(where)
    }.map(PblMap(pm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblMap as pm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    codenumber: Option[Int] = None,
    opis: Option[String] = None,
    dateadd: Option[DateTime] = None,
    typemap: Option[Short] = None,
    geoX: Option[BigDecimal] = None,
    geoY: Option[BigDecimal] = None,
    geoScale: Option[Short] = None,
    maptypeshow: Option[Short] = None)(implicit session: DBSession = autoSession): PblMap = {
    val generatedKey = withSQL {
      insert.into(PblMap).namedValues(
        column.codenumber -> codenumber,
        column.opis -> opis,
        column.dateadd -> dateadd,
        column.typemap -> typemap,
        column.geoX -> geoX,
        column.geoY -> geoY,
        column.geoScale -> geoScale,
        column.maptypeshow -> maptypeshow
      )
    }.updateAndReturnGeneratedKey.apply()

    PblMap(
      code = generatedKey.toInt,
      codenumber = codenumber,
      opis = opis,
      dateadd = dateadd,
      typemap = typemap,
      geoX = geoX,
      geoY = geoY,
      geoScale = geoScale,
      maptypeshow = maptypeshow)
  }

  def batchInsert(entities: collection.Seq[PblMap])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'codenumber -> entity.codenumber,
        'opis -> entity.opis,
        'dateadd -> entity.dateadd,
        'typemap -> entity.typemap,
        'geoX -> entity.geoX,
        'geoY -> entity.geoY,
        'geoScale -> entity.geoScale,
        'maptypeshow -> entity.maptypeshow))
    SQL("""insert into pbl_map(
      codenumber,
      opis,
      dateadd,
      typemap,
      geo_x,
      geo_y,
      geo_scale,
      maptypeshow
    ) values (
      {codenumber},
      {opis},
      {dateadd},
      {typemap},
      {geoX},
      {geoY},
      {geoScale},
      {maptypeshow}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblMap)(implicit session: DBSession = autoSession): PblMap = {
    withSQL {
      update(PblMap).set(
        column.code -> entity.code,
        column.codenumber -> entity.codenumber,
        column.opis -> entity.opis,
        column.dateadd -> entity.dateadd,
        column.typemap -> entity.typemap,
        column.geoX -> entity.geoX,
        column.geoY -> entity.geoY,
        column.geoScale -> entity.geoScale,
        column.maptypeshow -> entity.maptypeshow
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblMap)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblMap).where.eq(column.code, entity.code) }.update.apply()
  }

}
