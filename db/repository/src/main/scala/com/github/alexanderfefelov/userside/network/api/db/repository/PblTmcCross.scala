package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblTmcCross(
  code: Int,
  typer: Option[Short] = None,
  tmcparent: Option[Int] = None,
  tmcchild: Option[Int] = None,
  param1: Option[Int] = None) {

  def save()(implicit session: DBSession = PblTmcCross.autoSession): PblTmcCross = PblTmcCross.save(this)(session)

  def destroy()(implicit session: DBSession = PblTmcCross.autoSession): Int = PblTmcCross.destroy(this)(session)

}


object PblTmcCross extends SQLSyntaxSupport[PblTmcCross] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_tmc_cross"

  override val columns = Seq("code", "typer", "tmcparent", "tmcchild", "param1")

  def apply(ptc: SyntaxProvider[PblTmcCross])(rs: WrappedResultSet): PblTmcCross = autoConstruct(rs, ptc)
  def apply(ptc: ResultName[PblTmcCross])(rs: WrappedResultSet): PblTmcCross = autoConstruct(rs, ptc)

  val ptc = PblTmcCross.syntax("ptc")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblTmcCross] = {
    withSQL {
      select.from(PblTmcCross as ptc).where.eq(ptc.code, code)
    }.map(PblTmcCross(ptc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblTmcCross] = {
    withSQL(select.from(PblTmcCross as ptc)).map(PblTmcCross(ptc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblTmcCross as ptc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblTmcCross] = {
    withSQL {
      select.from(PblTmcCross as ptc).where.append(where)
    }.map(PblTmcCross(ptc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblTmcCross] = {
    withSQL {
      select.from(PblTmcCross as ptc).where.append(where)
    }.map(PblTmcCross(ptc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblTmcCross as ptc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    typer: Option[Short] = None,
    tmcparent: Option[Int] = None,
    tmcchild: Option[Int] = None,
    param1: Option[Int] = None)(implicit session: DBSession = autoSession): PblTmcCross = {
    val generatedKey = withSQL {
      insert.into(PblTmcCross).namedValues(
        column.typer -> typer,
        column.tmcparent -> tmcparent,
        column.tmcchild -> tmcchild,
        column.param1 -> param1
      )
    }.updateAndReturnGeneratedKey.apply()

    PblTmcCross(
      code = generatedKey.toInt,
      typer = typer,
      tmcparent = tmcparent,
      tmcchild = tmcchild,
      param1 = param1)
  }

  def batchInsert(entities: collection.Seq[PblTmcCross])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'typer -> entity.typer,
        'tmcparent -> entity.tmcparent,
        'tmcchild -> entity.tmcchild,
        'param1 -> entity.param1))
    SQL("""insert into pbl_tmc_cross(
      typer,
      tmcparent,
      tmcchild,
      param1
    ) values (
      {typer},
      {tmcparent},
      {tmcchild},
      {param1}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblTmcCross)(implicit session: DBSession = autoSession): PblTmcCross = {
    withSQL {
      update(PblTmcCross).set(
        column.code -> entity.code,
        column.typer -> entity.typer,
        column.tmcparent -> entity.tmcparent,
        column.tmcchild -> entity.tmcchild,
        column.param1 -> entity.param1
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblTmcCross)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblTmcCross).where.eq(column.code, entity.code) }.update.apply()
  }

}
