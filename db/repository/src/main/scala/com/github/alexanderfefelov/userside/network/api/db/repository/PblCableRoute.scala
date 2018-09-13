package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblCableRoute(
  id: Int,
  name: Option[String] = None,
  objectFirstType: Option[Int] = None,
  objectFirstId: Option[Int] = None,
  objectSecondType: Option[Int] = None,
  objectSecondId: Option[Int] = None,
  ownerId: Option[Int] = None,
  comment: Option[String] = None,
  length: Option[BigDecimal] = None,
  dateAdd: Option[LocalDate] = None,
  dateInstall: Option[LocalDate] = None,
  coord: Option[String] = None) {

  def save()(implicit session: DBSession = PblCableRoute.autoSession): PblCableRoute = PblCableRoute.save(this)(session)

  def destroy()(implicit session: DBSession = PblCableRoute.autoSession): Int = PblCableRoute.destroy(this)(session)

}


object PblCableRoute extends SQLSyntaxSupport[PblCableRoute] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_cable_route"

  override val columns = Seq("id", "name", "object_first_type", "object_first_id", "object_second_type", "object_second_id", "owner_id", "comment", "length", "date_add", "date_install", "coord")

  def apply(pcr: SyntaxProvider[PblCableRoute])(rs: WrappedResultSet): PblCableRoute = autoConstruct(rs, pcr)
  def apply(pcr: ResultName[PblCableRoute])(rs: WrappedResultSet): PblCableRoute = autoConstruct(rs, pcr)

  val pcr = PblCableRoute.syntax("pcr")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblCableRoute] = {
    withSQL {
      select.from(PblCableRoute as pcr).where.eq(pcr.id, id)
    }.map(PblCableRoute(pcr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblCableRoute] = {
    withSQL(select.from(PblCableRoute as pcr)).map(PblCableRoute(pcr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblCableRoute as pcr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblCableRoute] = {
    withSQL {
      select.from(PblCableRoute as pcr).where.append(where)
    }.map(PblCableRoute(pcr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblCableRoute] = {
    withSQL {
      select.from(PblCableRoute as pcr).where.append(where)
    }.map(PblCableRoute(pcr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblCableRoute as pcr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: Option[String] = None,
    objectFirstType: Option[Int] = None,
    objectFirstId: Option[Int] = None,
    objectSecondType: Option[Int] = None,
    objectSecondId: Option[Int] = None,
    ownerId: Option[Int] = None,
    comment: Option[String] = None,
    length: Option[BigDecimal] = None,
    dateAdd: Option[LocalDate] = None,
    dateInstall: Option[LocalDate] = None,
    coord: Option[String] = None)(implicit session: DBSession = autoSession): PblCableRoute = {
    val generatedKey = withSQL {
      insert.into(PblCableRoute).namedValues(
        column.name -> name,
        column.objectFirstType -> objectFirstType,
        column.objectFirstId -> objectFirstId,
        column.objectSecondType -> objectSecondType,
        column.objectSecondId -> objectSecondId,
        column.ownerId -> ownerId,
        column.comment -> comment,
        column.length -> length,
        column.dateAdd -> dateAdd,
        column.dateInstall -> dateInstall,
        column.coord -> coord
      )
    }.updateAndReturnGeneratedKey.apply()

    PblCableRoute(
      id = generatedKey.toInt,
      name = name,
      objectFirstType = objectFirstType,
      objectFirstId = objectFirstId,
      objectSecondType = objectSecondType,
      objectSecondId = objectSecondId,
      ownerId = ownerId,
      comment = comment,
      length = length,
      dateAdd = dateAdd,
      dateInstall = dateInstall,
      coord = coord)
  }

  def batchInsert(entities: collection.Seq[PblCableRoute])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'name -> entity.name,
        'objectFirstType -> entity.objectFirstType,
        'objectFirstId -> entity.objectFirstId,
        'objectSecondType -> entity.objectSecondType,
        'objectSecondId -> entity.objectSecondId,
        'ownerId -> entity.ownerId,
        'comment -> entity.comment,
        'length -> entity.length,
        'dateAdd -> entity.dateAdd,
        'dateInstall -> entity.dateInstall,
        'coord -> entity.coord))
    SQL("""insert into pbl_cable_route(
      name,
      object_first_type,
      object_first_id,
      object_second_type,
      object_second_id,
      owner_id,
      comment,
      length,
      date_add,
      date_install,
      coord
    ) values (
      {name},
      {objectFirstType},
      {objectFirstId},
      {objectSecondType},
      {objectSecondId},
      {ownerId},
      {comment},
      {length},
      {dateAdd},
      {dateInstall},
      {coord}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblCableRoute)(implicit session: DBSession = autoSession): PblCableRoute = {
    withSQL {
      update(PblCableRoute).set(
        column.id -> entity.id,
        column.name -> entity.name,
        column.objectFirstType -> entity.objectFirstType,
        column.objectFirstId -> entity.objectFirstId,
        column.objectSecondType -> entity.objectSecondType,
        column.objectSecondId -> entity.objectSecondId,
        column.ownerId -> entity.ownerId,
        column.comment -> entity.comment,
        column.length -> entity.length,
        column.dateAdd -> entity.dateAdd,
        column.dateInstall -> entity.dateInstall,
        column.coord -> entity.coord
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblCableRoute)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblCableRoute).where.eq(column.id, entity.id) }.update.apply()
  }

}
