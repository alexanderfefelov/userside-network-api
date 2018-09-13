package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblTrash(
  code: Int,
  typer: Option[Short] = None,
  opercode: Option[Int] = None,
  datedel: Option[DateTime] = None,
  usercode: Option[Int] = None,
  opis: Option[String] = None) {

  def save()(implicit session: DBSession = PblTrash.autoSession): PblTrash = PblTrash.save(this)(session)

  def destroy()(implicit session: DBSession = PblTrash.autoSession): Int = PblTrash.destroy(this)(session)

}


object PblTrash extends SQLSyntaxSupport[PblTrash] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_trash"

  override val columns = Seq("code", "typer", "opercode", "datedel", "usercode", "opis")

  def apply(pt: SyntaxProvider[PblTrash])(rs: WrappedResultSet): PblTrash = autoConstruct(rs, pt)
  def apply(pt: ResultName[PblTrash])(rs: WrappedResultSet): PblTrash = autoConstruct(rs, pt)

  val pt = PblTrash.syntax("pt")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblTrash] = {
    withSQL {
      select.from(PblTrash as pt).where.eq(pt.code, code)
    }.map(PblTrash(pt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblTrash] = {
    withSQL(select.from(PblTrash as pt)).map(PblTrash(pt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblTrash as pt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblTrash] = {
    withSQL {
      select.from(PblTrash as pt).where.append(where)
    }.map(PblTrash(pt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblTrash] = {
    withSQL {
      select.from(PblTrash as pt).where.append(where)
    }.map(PblTrash(pt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblTrash as pt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    typer: Option[Short] = None,
    opercode: Option[Int] = None,
    datedel: Option[DateTime] = None,
    usercode: Option[Int] = None,
    opis: Option[String] = None)(implicit session: DBSession = autoSession): PblTrash = {
    val generatedKey = withSQL {
      insert.into(PblTrash).namedValues(
        column.typer -> typer,
        column.opercode -> opercode,
        column.datedel -> datedel,
        column.usercode -> usercode,
        column.opis -> opis
      )
    }.updateAndReturnGeneratedKey.apply()

    PblTrash(
      code = generatedKey.toInt,
      typer = typer,
      opercode = opercode,
      datedel = datedel,
      usercode = usercode,
      opis = opis)
  }

  def batchInsert(entities: collection.Seq[PblTrash])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'typer -> entity.typer,
        'opercode -> entity.opercode,
        'datedel -> entity.datedel,
        'usercode -> entity.usercode,
        'opis -> entity.opis))
    SQL("""insert into pbl_trash(
      typer,
      opercode,
      datedel,
      usercode,
      opis
    ) values (
      {typer},
      {opercode},
      {datedel},
      {usercode},
      {opis}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblTrash)(implicit session: DBSession = autoSession): PblTrash = {
    withSQL {
      update(PblTrash).set(
        column.code -> entity.code,
        column.typer -> entity.typer,
        column.opercode -> entity.opercode,
        column.datedel -> entity.datedel,
        column.usercode -> entity.usercode,
        column.opis -> entity.opis
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblTrash)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblTrash).where.eq(column.code, entity.code) }.update.apply()
  }

}
