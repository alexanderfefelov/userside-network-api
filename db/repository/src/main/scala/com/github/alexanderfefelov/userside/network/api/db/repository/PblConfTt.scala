package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfTt(
  code: Int,
  nazv: Option[String] = None,
  typer: Option[Int] = None,
  isShowOnMap: Option[Short] = None,
  shapeType: Option[Short] = None,
  parentId: Option[Int] = None,
  additionalFieldList: Option[String] = None) {

  def save()(implicit session: DBSession = PblConfTt.autoSession): PblConfTt = PblConfTt.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfTt.autoSession): Int = PblConfTt.destroy(this)(session)

}


object PblConfTt extends SQLSyntaxSupport[PblConfTt] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_tt"

  override val columns = Seq("code", "nazv", "typer", "is_show_on_map", "shape_type", "parent_id", "additional_field_list")

  def apply(pct: SyntaxProvider[PblConfTt])(rs: WrappedResultSet): PblConfTt = autoConstruct(rs, pct)
  def apply(pct: ResultName[PblConfTt])(rs: WrappedResultSet): PblConfTt = autoConstruct(rs, pct)

  val pct = PblConfTt.syntax("pct")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfTt] = {
    withSQL {
      select.from(PblConfTt as pct).where.eq(pct.code, code)
    }.map(PblConfTt(pct.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfTt] = {
    withSQL(select.from(PblConfTt as pct)).map(PblConfTt(pct.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfTt as pct)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfTt] = {
    withSQL {
      select.from(PblConfTt as pct).where.append(where)
    }.map(PblConfTt(pct.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfTt] = {
    withSQL {
      select.from(PblConfTt as pct).where.append(where)
    }.map(PblConfTt(pct.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfTt as pct).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    nazv: Option[String] = None,
    typer: Option[Int] = None,
    isShowOnMap: Option[Short] = None,
    shapeType: Option[Short] = None,
    parentId: Option[Int] = None,
    additionalFieldList: Option[String] = None)(implicit session: DBSession = autoSession): PblConfTt = {
    val generatedKey = withSQL {
      insert.into(PblConfTt).namedValues(
        column.nazv -> nazv,
        column.typer -> typer,
        column.isShowOnMap -> isShowOnMap,
        column.shapeType -> shapeType,
        column.parentId -> parentId,
        column.additionalFieldList -> additionalFieldList
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfTt(
      code = generatedKey.toInt,
      nazv = nazv,
      typer = typer,
      isShowOnMap = isShowOnMap,
      shapeType = shapeType,
      parentId = parentId,
      additionalFieldList = additionalFieldList)
  }

  def batchInsert(entities: collection.Seq[PblConfTt])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'nazv -> entity.nazv,
        'typer -> entity.typer,
        'isShowOnMap -> entity.isShowOnMap,
        'shapeType -> entity.shapeType,
        'parentId -> entity.parentId,
        'additionalFieldList -> entity.additionalFieldList))
    SQL("""insert into pbl_conf_tt(
      nazv,
      typer,
      is_show_on_map,
      shape_type,
      parent_id,
      additional_field_list
    ) values (
      {nazv},
      {typer},
      {isShowOnMap},
      {shapeType},
      {parentId},
      {additionalFieldList}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfTt)(implicit session: DBSession = autoSession): PblConfTt = {
    withSQL {
      update(PblConfTt).set(
        column.code -> entity.code,
        column.nazv -> entity.nazv,
        column.typer -> entity.typer,
        column.isShowOnMap -> entity.isShowOnMap,
        column.shapeType -> entity.shapeType,
        column.parentId -> entity.parentId,
        column.additionalFieldList -> entity.additionalFieldList
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfTt)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfTt).where.eq(column.code, entity.code) }.update.apply()
  }

}
