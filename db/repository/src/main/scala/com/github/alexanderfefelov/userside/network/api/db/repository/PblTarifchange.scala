package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime, LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblTarifchange(
  code: Int,
  datedo: Option[DateTime] = None,
  usercode: Option[Int] = None,
  groupn: Option[String] = None,
  datetochange: Option[LocalDate] = None) {

  def save()(implicit session: DBSession = PblTarifchange.autoSession): PblTarifchange = PblTarifchange.save(this)(session)

  def destroy()(implicit session: DBSession = PblTarifchange.autoSession): Int = PblTarifchange.destroy(this)(session)

}


object PblTarifchange extends SQLSyntaxSupport[PblTarifchange] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_tarifchange"

  override val columns = Seq("code", "datedo", "usercode", "groupn", "datetochange")

  def apply(pt: SyntaxProvider[PblTarifchange])(rs: WrappedResultSet): PblTarifchange = autoConstruct(rs, pt)
  def apply(pt: ResultName[PblTarifchange])(rs: WrappedResultSet): PblTarifchange = autoConstruct(rs, pt)

  val pt = PblTarifchange.syntax("pt")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblTarifchange] = {
    withSQL {
      select.from(PblTarifchange as pt).where.eq(pt.code, code)
    }.map(PblTarifchange(pt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblTarifchange] = {
    withSQL(select.from(PblTarifchange as pt)).map(PblTarifchange(pt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblTarifchange as pt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblTarifchange] = {
    withSQL {
      select.from(PblTarifchange as pt).where.append(where)
    }.map(PblTarifchange(pt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblTarifchange] = {
    withSQL {
      select.from(PblTarifchange as pt).where.append(where)
    }.map(PblTarifchange(pt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblTarifchange as pt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    datedo: Option[DateTime] = None,
    usercode: Option[Int] = None,
    groupn: Option[String] = None,
    datetochange: Option[LocalDate] = None)(implicit session: DBSession = autoSession): PblTarifchange = {
    val generatedKey = withSQL {
      insert.into(PblTarifchange).namedValues(
        column.datedo -> datedo,
        column.usercode -> usercode,
        column.groupn -> groupn,
        column.datetochange -> datetochange
      )
    }.updateAndReturnGeneratedKey.apply()

    PblTarifchange(
      code = generatedKey.toInt,
      datedo = datedo,
      usercode = usercode,
      groupn = groupn,
      datetochange = datetochange)
  }

  def batchInsert(entities: collection.Seq[PblTarifchange])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'datedo -> entity.datedo,
        'usercode -> entity.usercode,
        'groupn -> entity.groupn,
        'datetochange -> entity.datetochange))
    SQL("""insert into pbl_tarifchange(
      datedo,
      usercode,
      groupn,
      datetochange
    ) values (
      {datedo},
      {usercode},
      {groupn},
      {datetochange}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblTarifchange)(implicit session: DBSession = autoSession): PblTarifchange = {
    withSQL {
      update(PblTarifchange).set(
        column.code -> entity.code,
        column.datedo -> entity.datedo,
        column.usercode -> entity.usercode,
        column.groupn -> entity.groupn,
        column.datetochange -> entity.datetochange
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblTarifchange)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblTarifchange).where.eq(column.code, entity.code) }.update.apply()
  }

}
