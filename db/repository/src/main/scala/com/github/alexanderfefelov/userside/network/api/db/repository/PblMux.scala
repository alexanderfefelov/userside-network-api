package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblMux(
  code: Int,
  isplan: Option[Short] = None,
  skladcode: Option[Int] = None,
  opis: Option[String] = None,
  uzelcode: Option[Int] = None,
  dateadd: Option[DateTime] = None,
  rotation: Option[Short] = None,
  x1: Option[Int] = None,
  y1: Option[Int] = None,
  layers: Option[String] = None,
  nazv: Option[String] = None,
  location: Option[String] = None,
  citycode: Option[Int] = None) {

  def save()(implicit session: DBSession = PblMux.autoSession): PblMux = PblMux.save(this)(session)

  def destroy()(implicit session: DBSession = PblMux.autoSession): Int = PblMux.destroy(this)(session)

}


object PblMux extends SQLSyntaxSupport[PblMux] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_mux"

  override val columns = Seq("code", "isplan", "skladcode", "opis", "uzelcode", "dateadd", "rotation", "x1", "y1", "layers", "nazv", "location", "citycode")

  def apply(pm: SyntaxProvider[PblMux])(rs: WrappedResultSet): PblMux = autoConstruct(rs, pm)
  def apply(pm: ResultName[PblMux])(rs: WrappedResultSet): PblMux = autoConstruct(rs, pm)

  val pm = PblMux.syntax("pm")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblMux] = {
    withSQL {
      select.from(PblMux as pm).where.eq(pm.code, code)
    }.map(PblMux(pm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblMux] = {
    withSQL(select.from(PblMux as pm)).map(PblMux(pm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblMux as pm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblMux] = {
    withSQL {
      select.from(PblMux as pm).where.append(where)
    }.map(PblMux(pm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblMux] = {
    withSQL {
      select.from(PblMux as pm).where.append(where)
    }.map(PblMux(pm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblMux as pm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    isplan: Option[Short] = None,
    skladcode: Option[Int] = None,
    opis: Option[String] = None,
    uzelcode: Option[Int] = None,
    dateadd: Option[DateTime] = None,
    rotation: Option[Short] = None,
    x1: Option[Int] = None,
    y1: Option[Int] = None,
    layers: Option[String] = None,
    nazv: Option[String] = None,
    location: Option[String] = None,
    citycode: Option[Int] = None)(implicit session: DBSession = autoSession): PblMux = {
    val generatedKey = withSQL {
      insert.into(PblMux).namedValues(
        column.isplan -> isplan,
        column.skladcode -> skladcode,
        column.opis -> opis,
        column.uzelcode -> uzelcode,
        column.dateadd -> dateadd,
        column.rotation -> rotation,
        column.x1 -> x1,
        column.y1 -> y1,
        column.layers -> layers,
        column.nazv -> nazv,
        column.location -> location,
        column.citycode -> citycode
      )
    }.updateAndReturnGeneratedKey.apply()

    PblMux(
      code = generatedKey.toInt,
      isplan = isplan,
      skladcode = skladcode,
      opis = opis,
      uzelcode = uzelcode,
      dateadd = dateadd,
      rotation = rotation,
      x1 = x1,
      y1 = y1,
      layers = layers,
      nazv = nazv,
      location = location,
      citycode = citycode)
  }

  def batchInsert(entities: collection.Seq[PblMux])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'isplan -> entity.isplan,
        'skladcode -> entity.skladcode,
        'opis -> entity.opis,
        'uzelcode -> entity.uzelcode,
        'dateadd -> entity.dateadd,
        'rotation -> entity.rotation,
        'x1 -> entity.x1,
        'y1 -> entity.y1,
        'layers -> entity.layers,
        'nazv -> entity.nazv,
        'location -> entity.location,
        'citycode -> entity.citycode))
    SQL("""insert into pbl_mux(
      isplan,
      skladcode,
      opis,
      uzelcode,
      dateadd,
      rotation,
      x1,
      y1,
      layers,
      nazv,
      location,
      citycode
    ) values (
      {isplan},
      {skladcode},
      {opis},
      {uzelcode},
      {dateadd},
      {rotation},
      {x1},
      {y1},
      {layers},
      {nazv},
      {location},
      {citycode}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblMux)(implicit session: DBSession = autoSession): PblMux = {
    withSQL {
      update(PblMux).set(
        column.code -> entity.code,
        column.isplan -> entity.isplan,
        column.skladcode -> entity.skladcode,
        column.opis -> entity.opis,
        column.uzelcode -> entity.uzelcode,
        column.dateadd -> entity.dateadd,
        column.rotation -> entity.rotation,
        column.x1 -> entity.x1,
        column.y1 -> entity.y1,
        column.layers -> entity.layers,
        column.nazv -> entity.nazv,
        column.location -> entity.location,
        column.citycode -> entity.citycode
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblMux)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblMux).where.eq(column.code, entity.code) }.update.apply()
  }

}
