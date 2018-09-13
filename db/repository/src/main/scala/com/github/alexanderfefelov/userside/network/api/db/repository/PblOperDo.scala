package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblOperDo(
  code: Int,
  typer: Option[Short] = None,
  objectcode: Option[Int] = None,
  opercode: Option[Int] = None,
  typerdo: Option[Short] = None,
  datedo: Option[DateTime] = None,
  opis: Option[String] = None) {

  def save()(implicit session: DBSession = PblOperDo.autoSession): PblOperDo = PblOperDo.save(this)(session)

  def destroy()(implicit session: DBSession = PblOperDo.autoSession): Int = PblOperDo.destroy(this)(session)

}


object PblOperDo extends SQLSyntaxSupport[PblOperDo] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_oper_do"

  override val columns = Seq("code", "typer", "objectcode", "opercode", "typerdo", "datedo", "opis")

  def apply(pod: SyntaxProvider[PblOperDo])(rs: WrappedResultSet): PblOperDo = autoConstruct(rs, pod)
  def apply(pod: ResultName[PblOperDo])(rs: WrappedResultSet): PblOperDo = autoConstruct(rs, pod)

  val pod = PblOperDo.syntax("pod")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblOperDo] = {
    withSQL {
      select.from(PblOperDo as pod).where.eq(pod.code, code)
    }.map(PblOperDo(pod.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblOperDo] = {
    withSQL(select.from(PblOperDo as pod)).map(PblOperDo(pod.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblOperDo as pod)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblOperDo] = {
    withSQL {
      select.from(PblOperDo as pod).where.append(where)
    }.map(PblOperDo(pod.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblOperDo] = {
    withSQL {
      select.from(PblOperDo as pod).where.append(where)
    }.map(PblOperDo(pod.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblOperDo as pod).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    typer: Option[Short] = None,
    objectcode: Option[Int] = None,
    opercode: Option[Int] = None,
    typerdo: Option[Short] = None,
    datedo: Option[DateTime] = None,
    opis: Option[String] = None)(implicit session: DBSession = autoSession): PblOperDo = {
    val generatedKey = withSQL {
      insert.into(PblOperDo).namedValues(
        column.typer -> typer,
        column.objectcode -> objectcode,
        column.opercode -> opercode,
        column.typerdo -> typerdo,
        column.datedo -> datedo,
        column.opis -> opis
      )
    }.updateAndReturnGeneratedKey.apply()

    PblOperDo(
      code = generatedKey.toInt,
      typer = typer,
      objectcode = objectcode,
      opercode = opercode,
      typerdo = typerdo,
      datedo = datedo,
      opis = opis)
  }

  def batchInsert(entities: collection.Seq[PblOperDo])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'typer -> entity.typer,
        'objectcode -> entity.objectcode,
        'opercode -> entity.opercode,
        'typerdo -> entity.typerdo,
        'datedo -> entity.datedo,
        'opis -> entity.opis))
    SQL("""insert into pbl_oper_do(
      typer,
      objectcode,
      opercode,
      typerdo,
      datedo,
      opis
    ) values (
      {typer},
      {objectcode},
      {opercode},
      {typerdo},
      {datedo},
      {opis}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblOperDo)(implicit session: DBSession = autoSession): PblOperDo = {
    withSQL {
      update(PblOperDo).set(
        column.code -> entity.code,
        column.typer -> entity.typer,
        column.objectcode -> entity.objectcode,
        column.opercode -> entity.opercode,
        column.typerdo -> entity.typerdo,
        column.datedo -> entity.datedo,
        column.opis -> entity.opis
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblOperDo)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblOperDo).where.eq(column.code, entity.code) }.update.apply()
  }

}
