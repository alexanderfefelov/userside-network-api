package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblHouseUnit(
  id: Int,
  houseId: Option[Int] = None,
  unitId: Option[Int] = None,
  isMain: Option[Short] = None) {

  def save()(implicit session: DBSession = PblHouseUnit.autoSession): PblHouseUnit = PblHouseUnit.save(this)(session)

  def destroy()(implicit session: DBSession = PblHouseUnit.autoSession): Int = PblHouseUnit.destroy(this)(session)

}


object PblHouseUnit extends SQLSyntaxSupport[PblHouseUnit] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_house_unit"

  override val columns = Seq("id", "house_id", "unit_id", "is_main")

  def apply(phu: SyntaxProvider[PblHouseUnit])(rs: WrappedResultSet): PblHouseUnit = autoConstruct(rs, phu)
  def apply(phu: ResultName[PblHouseUnit])(rs: WrappedResultSet): PblHouseUnit = autoConstruct(rs, phu)

  val phu = PblHouseUnit.syntax("phu")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblHouseUnit] = {
    withSQL {
      select.from(PblHouseUnit as phu).where.eq(phu.id, id)
    }.map(PblHouseUnit(phu.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblHouseUnit] = {
    withSQL(select.from(PblHouseUnit as phu)).map(PblHouseUnit(phu.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblHouseUnit as phu)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblHouseUnit] = {
    withSQL {
      select.from(PblHouseUnit as phu).where.append(where)
    }.map(PblHouseUnit(phu.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblHouseUnit] = {
    withSQL {
      select.from(PblHouseUnit as phu).where.append(where)
    }.map(PblHouseUnit(phu.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblHouseUnit as phu).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    houseId: Option[Int] = None,
    unitId: Option[Int] = None,
    isMain: Option[Short] = None)(implicit session: DBSession = autoSession): PblHouseUnit = {
    val generatedKey = withSQL {
      insert.into(PblHouseUnit).namedValues(
        column.houseId -> houseId,
        column.unitId -> unitId,
        column.isMain -> isMain
      )
    }.updateAndReturnGeneratedKey.apply()

    PblHouseUnit(
      id = generatedKey.toInt,
      houseId = houseId,
      unitId = unitId,
      isMain = isMain)
  }

  def batchInsert(entities: collection.Seq[PblHouseUnit])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'houseId -> entity.houseId,
        'unitId -> entity.unitId,
        'isMain -> entity.isMain))
    SQL("""insert into pbl_house_unit(
      house_id,
      unit_id,
      is_main
    ) values (
      {houseId},
      {unitId},
      {isMain}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblHouseUnit)(implicit session: DBSession = autoSession): PblHouseUnit = {
    withSQL {
      update(PblHouseUnit).set(
        column.id -> entity.id,
        column.houseId -> entity.houseId,
        column.unitId -> entity.unitId,
        column.isMain -> entity.isMain
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblHouseUnit)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblHouseUnit).where.eq(column.id, entity.id) }.update.apply()
  }

}
