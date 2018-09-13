package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblObjLayer(
  code: Int,
  typer: Option[Short] = None,
  usercode: Option[Int] = None,
  typer2: Option[Short] = None,
  usercode2: Option[Int] = None,
  issecond: Option[Short] = None,
  l0: Option[Int] = None,
  l1: Option[Int] = None,
  l2: Option[Int] = None,
  l3: Option[Int] = None,
  l4: Option[Int] = None,
  l5: Option[Int] = None,
  l6: Option[Int] = None,
  l7: Option[Int] = None) {

  def save()(implicit session: DBSession = PblObjLayer.autoSession): PblObjLayer = PblObjLayer.save(this)(session)

  def destroy()(implicit session: DBSession = PblObjLayer.autoSession): Int = PblObjLayer.destroy(this)(session)

}


object PblObjLayer extends SQLSyntaxSupport[PblObjLayer] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_obj_layer"

  override val columns = Seq("code", "typer", "usercode", "typer2", "usercode2", "issecond", "l0", "l1", "l2", "l3", "l4", "l5", "l6", "l7")

  def apply(pol: SyntaxProvider[PblObjLayer])(rs: WrappedResultSet): PblObjLayer = autoConstruct(rs, pol)
  def apply(pol: ResultName[PblObjLayer])(rs: WrappedResultSet): PblObjLayer = autoConstruct(rs, pol)

  val pol = PblObjLayer.syntax("pol")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblObjLayer] = {
    withSQL {
      select.from(PblObjLayer as pol).where.eq(pol.code, code)
    }.map(PblObjLayer(pol.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblObjLayer] = {
    withSQL(select.from(PblObjLayer as pol)).map(PblObjLayer(pol.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblObjLayer as pol)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblObjLayer] = {
    withSQL {
      select.from(PblObjLayer as pol).where.append(where)
    }.map(PblObjLayer(pol.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblObjLayer] = {
    withSQL {
      select.from(PblObjLayer as pol).where.append(where)
    }.map(PblObjLayer(pol.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblObjLayer as pol).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    typer: Option[Short] = None,
    usercode: Option[Int] = None,
    typer2: Option[Short] = None,
    usercode2: Option[Int] = None,
    issecond: Option[Short] = None,
    l0: Option[Int] = None,
    l1: Option[Int] = None,
    l2: Option[Int] = None,
    l3: Option[Int] = None,
    l4: Option[Int] = None,
    l5: Option[Int] = None,
    l6: Option[Int] = None,
    l7: Option[Int] = None)(implicit session: DBSession = autoSession): PblObjLayer = {
    val generatedKey = withSQL {
      insert.into(PblObjLayer).namedValues(
        column.typer -> typer,
        column.usercode -> usercode,
        column.typer2 -> typer2,
        column.usercode2 -> usercode2,
        column.issecond -> issecond,
        column.l0 -> l0,
        column.l1 -> l1,
        column.l2 -> l2,
        column.l3 -> l3,
        column.l4 -> l4,
        column.l5 -> l5,
        column.l6 -> l6,
        column.l7 -> l7
      )
    }.updateAndReturnGeneratedKey.apply()

    PblObjLayer(
      code = generatedKey.toInt,
      typer = typer,
      usercode = usercode,
      typer2 = typer2,
      usercode2 = usercode2,
      issecond = issecond,
      l0 = l0,
      l1 = l1,
      l2 = l2,
      l3 = l3,
      l4 = l4,
      l5 = l5,
      l6 = l6,
      l7 = l7)
  }

  def batchInsert(entities: collection.Seq[PblObjLayer])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'typer -> entity.typer,
        'usercode -> entity.usercode,
        'typer2 -> entity.typer2,
        'usercode2 -> entity.usercode2,
        'issecond -> entity.issecond,
        'l0 -> entity.l0,
        'l1 -> entity.l1,
        'l2 -> entity.l2,
        'l3 -> entity.l3,
        'l4 -> entity.l4,
        'l5 -> entity.l5,
        'l6 -> entity.l6,
        'l7 -> entity.l7))
    SQL("""insert into pbl_obj_layer(
      typer,
      usercode,
      typer2,
      usercode2,
      issecond,
      l0,
      l1,
      l2,
      l3,
      l4,
      l5,
      l6,
      l7
    ) values (
      {typer},
      {usercode},
      {typer2},
      {usercode2},
      {issecond},
      {l0},
      {l1},
      {l2},
      {l3},
      {l4},
      {l5},
      {l6},
      {l7}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblObjLayer)(implicit session: DBSession = autoSession): PblObjLayer = {
    withSQL {
      update(PblObjLayer).set(
        column.code -> entity.code,
        column.typer -> entity.typer,
        column.usercode -> entity.usercode,
        column.typer2 -> entity.typer2,
        column.usercode2 -> entity.usercode2,
        column.issecond -> entity.issecond,
        column.l0 -> entity.l0,
        column.l1 -> entity.l1,
        column.l2 -> entity.l2,
        column.l3 -> entity.l3,
        column.l4 -> entity.l4,
        column.l5 -> entity.l5,
        column.l6 -> entity.l6,
        column.l7 -> entity.l7
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblObjLayer)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblObjLayer).where.eq(column.code, entity.code) }.update.apply()
  }

}
