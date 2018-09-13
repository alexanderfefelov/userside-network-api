package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblCableDuct(
  id: Int,
  cableRouteId: Option[Int] = None,
  number: Option[Int] = None,
  comment: Option[String] = None,
  diameter: Option[Int] = None,
  dateAdd: Option[LocalDate] = None,
  dateInstall: Option[LocalDate] = None) {

  def save()(implicit session: DBSession = PblCableDuct.autoSession): PblCableDuct = PblCableDuct.save(this)(session)

  def destroy()(implicit session: DBSession = PblCableDuct.autoSession): Int = PblCableDuct.destroy(this)(session)

}


object PblCableDuct extends SQLSyntaxSupport[PblCableDuct] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_cable_duct"

  override val columns = Seq("id", "cable_route_id", "number", "comment", "diameter", "date_add", "date_install")

  def apply(pcd: SyntaxProvider[PblCableDuct])(rs: WrappedResultSet): PblCableDuct = autoConstruct(rs, pcd)
  def apply(pcd: ResultName[PblCableDuct])(rs: WrappedResultSet): PblCableDuct = autoConstruct(rs, pcd)

  val pcd = PblCableDuct.syntax("pcd")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblCableDuct] = {
    withSQL {
      select.from(PblCableDuct as pcd).where.eq(pcd.id, id)
    }.map(PblCableDuct(pcd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblCableDuct] = {
    withSQL(select.from(PblCableDuct as pcd)).map(PblCableDuct(pcd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblCableDuct as pcd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblCableDuct] = {
    withSQL {
      select.from(PblCableDuct as pcd).where.append(where)
    }.map(PblCableDuct(pcd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblCableDuct] = {
    withSQL {
      select.from(PblCableDuct as pcd).where.append(where)
    }.map(PblCableDuct(pcd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblCableDuct as pcd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cableRouteId: Option[Int] = None,
    number: Option[Int] = None,
    comment: Option[String] = None,
    diameter: Option[Int] = None,
    dateAdd: Option[LocalDate] = None,
    dateInstall: Option[LocalDate] = None)(implicit session: DBSession = autoSession): PblCableDuct = {
    val generatedKey = withSQL {
      insert.into(PblCableDuct).namedValues(
        column.cableRouteId -> cableRouteId,
        column.number -> number,
        column.comment -> comment,
        column.diameter -> diameter,
        column.dateAdd -> dateAdd,
        column.dateInstall -> dateInstall
      )
    }.updateAndReturnGeneratedKey.apply()

    PblCableDuct(
      id = generatedKey.toInt,
      cableRouteId = cableRouteId,
      number = number,
      comment = comment,
      diameter = diameter,
      dateAdd = dateAdd,
      dateInstall = dateInstall)
  }

  def batchInsert(entities: collection.Seq[PblCableDuct])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cableRouteId -> entity.cableRouteId,
        'number -> entity.number,
        'comment -> entity.comment,
        'diameter -> entity.diameter,
        'dateAdd -> entity.dateAdd,
        'dateInstall -> entity.dateInstall))
    SQL("""insert into pbl_cable_duct(
      cable_route_id,
      number,
      comment,
      diameter,
      date_add,
      date_install
    ) values (
      {cableRouteId},
      {number},
      {comment},
      {diameter},
      {dateAdd},
      {dateInstall}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblCableDuct)(implicit session: DBSession = autoSession): PblCableDuct = {
    withSQL {
      update(PblCableDuct).set(
        column.id -> entity.id,
        column.cableRouteId -> entity.cableRouteId,
        column.number -> entity.number,
        column.comment -> entity.comment,
        column.diameter -> entity.diameter,
        column.dateAdd -> entity.dateAdd,
        column.dateInstall -> entity.dateInstall
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblCableDuct)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblCableDuct).where.eq(column.id, entity.id) }.update.apply()
  }

}
