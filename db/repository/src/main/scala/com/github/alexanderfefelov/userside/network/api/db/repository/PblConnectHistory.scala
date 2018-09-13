package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblConnectHistory(
  id: Int,
  dateAdd: Option[DateTime] = None,
  operatorId: Option[Int] = None,
  objectType: Option[Int] = None,
  objectId: Option[Int] = None,
  objectSide: Option[Short] = None,
  objectPort: Option[Long] = None,
  objectSecondType: Option[Int] = None,
  objectSecondId: Option[Int] = None,
  objectSecondSide: Option[Short] = None,
  objectSecondPort: Option[Long] = None,
  comment: Option[String] = None) {

  def save()(implicit session: DBSession = PblConnectHistory.autoSession): PblConnectHistory = PblConnectHistory.save(this)(session)

  def destroy()(implicit session: DBSession = PblConnectHistory.autoSession): Int = PblConnectHistory.destroy(this)(session)

}


object PblConnectHistory extends SQLSyntaxSupport[PblConnectHistory] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_connect_history"

  override val columns = Seq("id", "date_add", "operator_id", "object_type", "object_id", "object_side", "object_port", "object_second_type", "object_second_id", "object_second_side", "object_second_port", "comment")

  def apply(pch: SyntaxProvider[PblConnectHistory])(rs: WrappedResultSet): PblConnectHistory = autoConstruct(rs, pch)
  def apply(pch: ResultName[PblConnectHistory])(rs: WrappedResultSet): PblConnectHistory = autoConstruct(rs, pch)

  val pch = PblConnectHistory.syntax("pch")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblConnectHistory] = {
    withSQL {
      select.from(PblConnectHistory as pch).where.eq(pch.id, id)
    }.map(PblConnectHistory(pch.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConnectHistory] = {
    withSQL(select.from(PblConnectHistory as pch)).map(PblConnectHistory(pch.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConnectHistory as pch)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConnectHistory] = {
    withSQL {
      select.from(PblConnectHistory as pch).where.append(where)
    }.map(PblConnectHistory(pch.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConnectHistory] = {
    withSQL {
      select.from(PblConnectHistory as pch).where.append(where)
    }.map(PblConnectHistory(pch.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConnectHistory as pch).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    dateAdd: Option[DateTime] = None,
    operatorId: Option[Int] = None,
    objectType: Option[Int] = None,
    objectId: Option[Int] = None,
    objectSide: Option[Short] = None,
    objectPort: Option[Long] = None,
    objectSecondType: Option[Int] = None,
    objectSecondId: Option[Int] = None,
    objectSecondSide: Option[Short] = None,
    objectSecondPort: Option[Long] = None,
    comment: Option[String] = None)(implicit session: DBSession = autoSession): PblConnectHistory = {
    val generatedKey = withSQL {
      insert.into(PblConnectHistory).namedValues(
        column.dateAdd -> dateAdd,
        column.operatorId -> operatorId,
        column.objectType -> objectType,
        column.objectId -> objectId,
        column.objectSide -> objectSide,
        column.objectPort -> objectPort,
        column.objectSecondType -> objectSecondType,
        column.objectSecondId -> objectSecondId,
        column.objectSecondSide -> objectSecondSide,
        column.objectSecondPort -> objectSecondPort,
        column.comment -> comment
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConnectHistory(
      id = generatedKey.toInt,
      dateAdd = dateAdd,
      operatorId = operatorId,
      objectType = objectType,
      objectId = objectId,
      objectSide = objectSide,
      objectPort = objectPort,
      objectSecondType = objectSecondType,
      objectSecondId = objectSecondId,
      objectSecondSide = objectSecondSide,
      objectSecondPort = objectSecondPort,
      comment = comment)
  }

  def batchInsert(entities: collection.Seq[PblConnectHistory])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'dateAdd -> entity.dateAdd,
        'operatorId -> entity.operatorId,
        'objectType -> entity.objectType,
        'objectId -> entity.objectId,
        'objectSide -> entity.objectSide,
        'objectPort -> entity.objectPort,
        'objectSecondType -> entity.objectSecondType,
        'objectSecondId -> entity.objectSecondId,
        'objectSecondSide -> entity.objectSecondSide,
        'objectSecondPort -> entity.objectSecondPort,
        'comment -> entity.comment))
    SQL("""insert into pbl_connect_history(
      date_add,
      operator_id,
      object_type,
      object_id,
      object_side,
      object_port,
      object_second_type,
      object_second_id,
      object_second_side,
      object_second_port,
      comment
    ) values (
      {dateAdd},
      {operatorId},
      {objectType},
      {objectId},
      {objectSide},
      {objectPort},
      {objectSecondType},
      {objectSecondId},
      {objectSecondSide},
      {objectSecondPort},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConnectHistory)(implicit session: DBSession = autoSession): PblConnectHistory = {
    withSQL {
      update(PblConnectHistory).set(
        column.id -> entity.id,
        column.dateAdd -> entity.dateAdd,
        column.operatorId -> entity.operatorId,
        column.objectType -> entity.objectType,
        column.objectId -> entity.objectId,
        column.objectSide -> entity.objectSide,
        column.objectPort -> entity.objectPort,
        column.objectSecondType -> entity.objectSecondType,
        column.objectSecondId -> entity.objectSecondId,
        column.objectSecondSide -> entity.objectSecondSide,
        column.objectSecondPort -> entity.objectSecondPort,
        column.comment -> entity.comment
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConnectHistory)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConnectHistory).where.eq(column.id, entity.id) }.update.apply()
  }

}
