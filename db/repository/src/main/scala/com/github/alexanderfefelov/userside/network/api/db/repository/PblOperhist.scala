package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblOperhist(
  code: Int,
  opercode: Option[Int] = None,
  dateoper: Option[DateTime] = None,
  typer: Option[Int] = None,
  par1: Option[String] = None,
  par2: Option[String] = None,
  valNew: Option[String] = None,
  valOld: Option[String] = None,
  userip: Option[BigDecimal] = None,
  objtyper: Option[Int] = None,
  objcode: Option[Int] = None,
  objnazv: Option[String] = None,
  userip6: Option[String] = None) {

  def save()(implicit session: DBSession = PblOperhist.autoSession): PblOperhist = PblOperhist.save(this)(session)

  def destroy()(implicit session: DBSession = PblOperhist.autoSession): Int = PblOperhist.destroy(this)(session)

}


object PblOperhist extends SQLSyntaxSupport[PblOperhist] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_operhist"

  override val columns = Seq("code", "opercode", "dateoper", "typer", "par1", "par2", "val_new", "val_old", "userip", "objtyper", "objcode", "objnazv", "userip6")

  def apply(po: SyntaxProvider[PblOperhist])(rs: WrappedResultSet): PblOperhist = autoConstruct(rs, po)
  def apply(po: ResultName[PblOperhist])(rs: WrappedResultSet): PblOperhist = autoConstruct(rs, po)

  val po = PblOperhist.syntax("po")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblOperhist] = {
    withSQL {
      select.from(PblOperhist as po).where.eq(po.code, code)
    }.map(PblOperhist(po.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblOperhist] = {
    withSQL(select.from(PblOperhist as po)).map(PblOperhist(po.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblOperhist as po)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblOperhist] = {
    withSQL {
      select.from(PblOperhist as po).where.append(where)
    }.map(PblOperhist(po.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblOperhist] = {
    withSQL {
      select.from(PblOperhist as po).where.append(where)
    }.map(PblOperhist(po.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblOperhist as po).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    opercode: Option[Int] = None,
    dateoper: Option[DateTime] = None,
    typer: Option[Int] = None,
    par1: Option[String] = None,
    par2: Option[String] = None,
    valNew: Option[String] = None,
    valOld: Option[String] = None,
    userip: Option[BigDecimal] = None,
    objtyper: Option[Int] = None,
    objcode: Option[Int] = None,
    objnazv: Option[String] = None,
    userip6: Option[String] = None)(implicit session: DBSession = autoSession): PblOperhist = {
    val generatedKey = withSQL {
      insert.into(PblOperhist).namedValues(
        column.opercode -> opercode,
        column.dateoper -> dateoper,
        column.typer -> typer,
        column.par1 -> par1,
        column.par2 -> par2,
        column.valNew -> valNew,
        column.valOld -> valOld,
        column.userip -> userip,
        column.objtyper -> objtyper,
        column.objcode -> objcode,
        column.objnazv -> objnazv,
        column.userip6 -> userip6
      )
    }.updateAndReturnGeneratedKey.apply()

    PblOperhist(
      code = generatedKey.toInt,
      opercode = opercode,
      dateoper = dateoper,
      typer = typer,
      par1 = par1,
      par2 = par2,
      valNew = valNew,
      valOld = valOld,
      userip = userip,
      objtyper = objtyper,
      objcode = objcode,
      objnazv = objnazv,
      userip6 = userip6)
  }

  def batchInsert(entities: collection.Seq[PblOperhist])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'opercode -> entity.opercode,
        'dateoper -> entity.dateoper,
        'typer -> entity.typer,
        'par1 -> entity.par1,
        'par2 -> entity.par2,
        'valNew -> entity.valNew,
        'valOld -> entity.valOld,
        'userip -> entity.userip,
        'objtyper -> entity.objtyper,
        'objcode -> entity.objcode,
        'objnazv -> entity.objnazv,
        'userip6 -> entity.userip6))
    SQL("""insert into pbl_operhist(
      opercode,
      dateoper,
      typer,
      par1,
      par2,
      val_new,
      val_old,
      userip,
      objtyper,
      objcode,
      objnazv,
      userip6
    ) values (
      {opercode},
      {dateoper},
      {typer},
      {par1},
      {par2},
      {valNew},
      {valOld},
      {userip},
      {objtyper},
      {objcode},
      {objnazv},
      {userip6}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblOperhist)(implicit session: DBSession = autoSession): PblOperhist = {
    withSQL {
      update(PblOperhist).set(
        column.code -> entity.code,
        column.opercode -> entity.opercode,
        column.dateoper -> entity.dateoper,
        column.typer -> entity.typer,
        column.par1 -> entity.par1,
        column.par2 -> entity.par2,
        column.valNew -> entity.valNew,
        column.valOld -> entity.valOld,
        column.userip -> entity.userip,
        column.objtyper -> entity.objtyper,
        column.objcode -> entity.objcode,
        column.objnazv -> entity.objnazv,
        column.userip6 -> entity.userip6
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblOperhist)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblOperhist).where.eq(column.code, entity.code) }.update.apply()
  }

}
