package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblObjInt(
  code: Int,
  obj1: Option[Short] = None,
  usercode1: Option[Int] = None,
  obj2: Option[Short] = None,
  usercode2: Option[Int] = None,
  pos: Option[Short] = None,
  dop1: Option[Int] = None,
  additionalDataString: Option[String] = None) {

  def save()(implicit session: DBSession = PblObjInt.autoSession): PblObjInt = PblObjInt.save(this)(session)

  def destroy()(implicit session: DBSession = PblObjInt.autoSession): Int = PblObjInt.destroy(this)(session)

}


object PblObjInt extends SQLSyntaxSupport[PblObjInt] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_obj_int"

  override val columns = Seq("code", "obj1", "usercode1", "obj2", "usercode2", "pos", "dop1", "additional_data_string")

  def apply(poi: SyntaxProvider[PblObjInt])(rs: WrappedResultSet): PblObjInt = autoConstruct(rs, poi)
  def apply(poi: ResultName[PblObjInt])(rs: WrappedResultSet): PblObjInt = autoConstruct(rs, poi)

  val poi = PblObjInt.syntax("poi")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblObjInt] = {
    withSQL {
      select.from(PblObjInt as poi).where.eq(poi.code, code)
    }.map(PblObjInt(poi.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblObjInt] = {
    withSQL(select.from(PblObjInt as poi)).map(PblObjInt(poi.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblObjInt as poi)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblObjInt] = {
    withSQL {
      select.from(PblObjInt as poi).where.append(where)
    }.map(PblObjInt(poi.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblObjInt] = {
    withSQL {
      select.from(PblObjInt as poi).where.append(where)
    }.map(PblObjInt(poi.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblObjInt as poi).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    obj1: Option[Short] = None,
    usercode1: Option[Int] = None,
    obj2: Option[Short] = None,
    usercode2: Option[Int] = None,
    pos: Option[Short] = None,
    dop1: Option[Int] = None,
    additionalDataString: Option[String] = None)(implicit session: DBSession = autoSession): PblObjInt = {
    val generatedKey = withSQL {
      insert.into(PblObjInt).namedValues(
        column.obj1 -> obj1,
        column.usercode1 -> usercode1,
        column.obj2 -> obj2,
        column.usercode2 -> usercode2,
        column.pos -> pos,
        column.dop1 -> dop1,
        column.additionalDataString -> additionalDataString
      )
    }.updateAndReturnGeneratedKey.apply()

    PblObjInt(
      code = generatedKey.toInt,
      obj1 = obj1,
      usercode1 = usercode1,
      obj2 = obj2,
      usercode2 = usercode2,
      pos = pos,
      dop1 = dop1,
      additionalDataString = additionalDataString)
  }

  def batchInsert(entities: collection.Seq[PblObjInt])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'obj1 -> entity.obj1,
        'usercode1 -> entity.usercode1,
        'obj2 -> entity.obj2,
        'usercode2 -> entity.usercode2,
        'pos -> entity.pos,
        'dop1 -> entity.dop1,
        'additionalDataString -> entity.additionalDataString))
    SQL("""insert into pbl_obj_int(
      obj1,
      usercode1,
      obj2,
      usercode2,
      pos,
      dop1,
      additional_data_string
    ) values (
      {obj1},
      {usercode1},
      {obj2},
      {usercode2},
      {pos},
      {dop1},
      {additionalDataString}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblObjInt)(implicit session: DBSession = autoSession): PblObjInt = {
    withSQL {
      update(PblObjInt).set(
        column.code -> entity.code,
        column.obj1 -> entity.obj1,
        column.usercode1 -> entity.usercode1,
        column.obj2 -> entity.obj2,
        column.usercode2 -> entity.usercode2,
        column.pos -> entity.pos,
        column.dop1 -> entity.dop1,
        column.additionalDataString -> entity.additionalDataString
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblObjInt)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblObjInt).where.eq(column.code, entity.code) }.update.apply()
  }

}
