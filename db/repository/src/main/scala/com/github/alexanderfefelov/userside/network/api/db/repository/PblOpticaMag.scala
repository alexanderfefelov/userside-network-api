package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblOpticaMag(
  code: Int,
  nazv: Option[String] = None,
  dateadd: Option[DateTime] = None,
  nodeId: Option[Int] = None) {

  def save()(implicit session: DBSession = PblOpticaMag.autoSession): PblOpticaMag = PblOpticaMag.save(this)(session)

  def destroy()(implicit session: DBSession = PblOpticaMag.autoSession): Int = PblOpticaMag.destroy(this)(session)

}


object PblOpticaMag extends SQLSyntaxSupport[PblOpticaMag] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_optica_mag"

  override val columns = Seq("code", "nazv", "dateadd", "node_id")

  def apply(pom: SyntaxProvider[PblOpticaMag])(rs: WrappedResultSet): PblOpticaMag = autoConstruct(rs, pom)
  def apply(pom: ResultName[PblOpticaMag])(rs: WrappedResultSet): PblOpticaMag = autoConstruct(rs, pom)

  val pom = PblOpticaMag.syntax("pom")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblOpticaMag] = {
    withSQL {
      select.from(PblOpticaMag as pom).where.eq(pom.code, code)
    }.map(PblOpticaMag(pom.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblOpticaMag] = {
    withSQL(select.from(PblOpticaMag as pom)).map(PblOpticaMag(pom.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblOpticaMag as pom)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblOpticaMag] = {
    withSQL {
      select.from(PblOpticaMag as pom).where.append(where)
    }.map(PblOpticaMag(pom.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblOpticaMag] = {
    withSQL {
      select.from(PblOpticaMag as pom).where.append(where)
    }.map(PblOpticaMag(pom.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblOpticaMag as pom).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    nazv: Option[String] = None,
    dateadd: Option[DateTime] = None,
    nodeId: Option[Int] = None)(implicit session: DBSession = autoSession): PblOpticaMag = {
    val generatedKey = withSQL {
      insert.into(PblOpticaMag).namedValues(
        column.nazv -> nazv,
        column.dateadd -> dateadd,
        column.nodeId -> nodeId
      )
    }.updateAndReturnGeneratedKey.apply()

    PblOpticaMag(
      code = generatedKey.toInt,
      nazv = nazv,
      dateadd = dateadd,
      nodeId = nodeId)
  }

  def batchInsert(entities: collection.Seq[PblOpticaMag])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'nazv -> entity.nazv,
        'dateadd -> entity.dateadd,
        'nodeId -> entity.nodeId))
    SQL("""insert into pbl_optica_mag(
      nazv,
      dateadd,
      node_id
    ) values (
      {nazv},
      {dateadd},
      {nodeId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblOpticaMag)(implicit session: DBSession = autoSession): PblOpticaMag = {
    withSQL {
      update(PblOpticaMag).set(
        column.code -> entity.code,
        column.nazv -> entity.nazv,
        column.dateadd -> entity.dateadd,
        column.nodeId -> entity.nodeId
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblOpticaMag)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblOpticaMag).where.eq(column.code, entity.code) }.update.apply()
  }

}
