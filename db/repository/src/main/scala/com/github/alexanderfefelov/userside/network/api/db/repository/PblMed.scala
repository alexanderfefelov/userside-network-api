package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblMed(
  code: Int,
  uzelcode1: Option[Int] = None,
  obj2Typer: Option[Int] = None,
  uzelcode2: Option[Int] = None,
  opis: Option[String] = None,
  medlen: Option[Int] = None,
  dateadd: Option[LocalDate] = None,
  layers: Option[String] = None,
  isplan: Option[Short] = None) {

  def save()(implicit session: DBSession = PblMed.autoSession): PblMed = PblMed.save(this)(session)

  def destroy()(implicit session: DBSession = PblMed.autoSession): Int = PblMed.destroy(this)(session)

}


object PblMed extends SQLSyntaxSupport[PblMed] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_med"

  override val columns = Seq("code", "uzelcode1", "obj2_typer", "uzelcode2", "opis", "medlen", "dateadd", "layers", "isplan")

  def apply(pm: SyntaxProvider[PblMed])(rs: WrappedResultSet): PblMed = autoConstruct(rs, pm)
  def apply(pm: ResultName[PblMed])(rs: WrappedResultSet): PblMed = autoConstruct(rs, pm)

  val pm = PblMed.syntax("pm")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblMed] = {
    withSQL {
      select.from(PblMed as pm).where.eq(pm.code, code)
    }.map(PblMed(pm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblMed] = {
    withSQL(select.from(PblMed as pm)).map(PblMed(pm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblMed as pm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblMed] = {
    withSQL {
      select.from(PblMed as pm).where.append(where)
    }.map(PblMed(pm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblMed] = {
    withSQL {
      select.from(PblMed as pm).where.append(where)
    }.map(PblMed(pm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblMed as pm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    uzelcode1: Option[Int] = None,
    obj2Typer: Option[Int] = None,
    uzelcode2: Option[Int] = None,
    opis: Option[String] = None,
    medlen: Option[Int] = None,
    dateadd: Option[LocalDate] = None,
    layers: Option[String] = None,
    isplan: Option[Short] = None)(implicit session: DBSession = autoSession): PblMed = {
    val generatedKey = withSQL {
      insert.into(PblMed).namedValues(
        column.uzelcode1 -> uzelcode1,
        column.obj2Typer -> obj2Typer,
        column.uzelcode2 -> uzelcode2,
        column.opis -> opis,
        column.medlen -> medlen,
        column.dateadd -> dateadd,
        column.layers -> layers,
        column.isplan -> isplan
      )
    }.updateAndReturnGeneratedKey.apply()

    PblMed(
      code = generatedKey.toInt,
      uzelcode1 = uzelcode1,
      obj2Typer = obj2Typer,
      uzelcode2 = uzelcode2,
      opis = opis,
      medlen = medlen,
      dateadd = dateadd,
      layers = layers,
      isplan = isplan)
  }

  def batchInsert(entities: collection.Seq[PblMed])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'uzelcode1 -> entity.uzelcode1,
        'obj2Typer -> entity.obj2Typer,
        'uzelcode2 -> entity.uzelcode2,
        'opis -> entity.opis,
        'medlen -> entity.medlen,
        'dateadd -> entity.dateadd,
        'layers -> entity.layers,
        'isplan -> entity.isplan))
    SQL("""insert into pbl_med(
      uzelcode1,
      obj2_typer,
      uzelcode2,
      opis,
      medlen,
      dateadd,
      layers,
      isplan
    ) values (
      {uzelcode1},
      {obj2Typer},
      {uzelcode2},
      {opis},
      {medlen},
      {dateadd},
      {layers},
      {isplan}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblMed)(implicit session: DBSession = autoSession): PblMed = {
    withSQL {
      update(PblMed).set(
        column.code -> entity.code,
        column.uzelcode1 -> entity.uzelcode1,
        column.obj2Typer -> entity.obj2Typer,
        column.uzelcode2 -> entity.uzelcode2,
        column.opis -> entity.opis,
        column.medlen -> entity.medlen,
        column.dateadd -> entity.dateadd,
        column.layers -> entity.layers,
        column.isplan -> entity.isplan
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblMed)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblMed).where.eq(column.code, entity.code) }.update.apply()
  }

}
