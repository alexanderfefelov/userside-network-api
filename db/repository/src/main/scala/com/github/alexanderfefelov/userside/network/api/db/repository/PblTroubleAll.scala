package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblTroubleAll(
  code: Int,
  usercode: Option[Int] = None,
  dateadd: Option[DateTime] = None,
  theme: Option[String] = None,
  opis: Option[String] = None) {

  def save()(implicit session: DBSession = PblTroubleAll.autoSession): PblTroubleAll = PblTroubleAll.save(this)(session)

  def destroy()(implicit session: DBSession = PblTroubleAll.autoSession): Int = PblTroubleAll.destroy(this)(session)

}


object PblTroubleAll extends SQLSyntaxSupport[PblTroubleAll] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_trouble_all"

  override val columns = Seq("code", "usercode", "dateadd", "theme", "opis")

  def apply(pta: SyntaxProvider[PblTroubleAll])(rs: WrappedResultSet): PblTroubleAll = autoConstruct(rs, pta)
  def apply(pta: ResultName[PblTroubleAll])(rs: WrappedResultSet): PblTroubleAll = autoConstruct(rs, pta)

  val pta = PblTroubleAll.syntax("pta")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblTroubleAll] = {
    withSQL {
      select.from(PblTroubleAll as pta).where.eq(pta.code, code)
    }.map(PblTroubleAll(pta.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblTroubleAll] = {
    withSQL(select.from(PblTroubleAll as pta)).map(PblTroubleAll(pta.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblTroubleAll as pta)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblTroubleAll] = {
    withSQL {
      select.from(PblTroubleAll as pta).where.append(where)
    }.map(PblTroubleAll(pta.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblTroubleAll] = {
    withSQL {
      select.from(PblTroubleAll as pta).where.append(where)
    }.map(PblTroubleAll(pta.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblTroubleAll as pta).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    usercode: Option[Int] = None,
    dateadd: Option[DateTime] = None,
    theme: Option[String] = None,
    opis: Option[String] = None)(implicit session: DBSession = autoSession): PblTroubleAll = {
    val generatedKey = withSQL {
      insert.into(PblTroubleAll).namedValues(
        column.usercode -> usercode,
        column.dateadd -> dateadd,
        column.theme -> theme,
        column.opis -> opis
      )
    }.updateAndReturnGeneratedKey.apply()

    PblTroubleAll(
      code = generatedKey.toInt,
      usercode = usercode,
      dateadd = dateadd,
      theme = theme,
      opis = opis)
  }

  def batchInsert(entities: collection.Seq[PblTroubleAll])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'usercode -> entity.usercode,
        'dateadd -> entity.dateadd,
        'theme -> entity.theme,
        'opis -> entity.opis))
    SQL("""insert into pbl_trouble_all(
      usercode,
      dateadd,
      theme,
      opis
    ) values (
      {usercode},
      {dateadd},
      {theme},
      {opis}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblTroubleAll)(implicit session: DBSession = autoSession): PblTroubleAll = {
    withSQL {
      update(PblTroubleAll).set(
        column.code -> entity.code,
        column.usercode -> entity.usercode,
        column.dateadd -> entity.dateadd,
        column.theme -> entity.theme,
        column.opis -> entity.opis
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblTroubleAll)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblTroubleAll).where.eq(column.code, entity.code) }.update.apply()
  }

}
