package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblPortLearn(
  code: Int,
  typer: Option[Short] = None,
  devcode: Option[Int] = None,
  portcode: Option[Int] = None) {

  def save()(implicit session: DBSession = PblPortLearn.autoSession): PblPortLearn = PblPortLearn.save(this)(session)

  def destroy()(implicit session: DBSession = PblPortLearn.autoSession): Int = PblPortLearn.destroy(this)(session)

}


object PblPortLearn extends SQLSyntaxSupport[PblPortLearn] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_port_learn"

  override val columns = Seq("code", "typer", "devcode", "portcode")

  def apply(ppl: SyntaxProvider[PblPortLearn])(rs: WrappedResultSet): PblPortLearn = autoConstruct(rs, ppl)
  def apply(ppl: ResultName[PblPortLearn])(rs: WrappedResultSet): PblPortLearn = autoConstruct(rs, ppl)

  val ppl = PblPortLearn.syntax("ppl")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblPortLearn] = {
    withSQL {
      select.from(PblPortLearn as ppl).where.eq(ppl.code, code)
    }.map(PblPortLearn(ppl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblPortLearn] = {
    withSQL(select.from(PblPortLearn as ppl)).map(PblPortLearn(ppl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblPortLearn as ppl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblPortLearn] = {
    withSQL {
      select.from(PblPortLearn as ppl).where.append(where)
    }.map(PblPortLearn(ppl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblPortLearn] = {
    withSQL {
      select.from(PblPortLearn as ppl).where.append(where)
    }.map(PblPortLearn(ppl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblPortLearn as ppl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    typer: Option[Short] = None,
    devcode: Option[Int] = None,
    portcode: Option[Int] = None)(implicit session: DBSession = autoSession): PblPortLearn = {
    val generatedKey = withSQL {
      insert.into(PblPortLearn).namedValues(
        column.typer -> typer,
        column.devcode -> devcode,
        column.portcode -> portcode
      )
    }.updateAndReturnGeneratedKey.apply()

    PblPortLearn(
      code = generatedKey.toInt,
      typer = typer,
      devcode = devcode,
      portcode = portcode)
  }

  def batchInsert(entities: collection.Seq[PblPortLearn])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'typer -> entity.typer,
        'devcode -> entity.devcode,
        'portcode -> entity.portcode))
    SQL("""insert into pbl_port_learn(
      typer,
      devcode,
      portcode
    ) values (
      {typer},
      {devcode},
      {portcode}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblPortLearn)(implicit session: DBSession = autoSession): PblPortLearn = {
    withSQL {
      update(PblPortLearn).set(
        column.code -> entity.code,
        column.typer -> entity.typer,
        column.devcode -> entity.devcode,
        column.portcode -> entity.portcode
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblPortLearn)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblPortLearn).where.eq(column.code, entity.code) }.update.apply()
  }

}
