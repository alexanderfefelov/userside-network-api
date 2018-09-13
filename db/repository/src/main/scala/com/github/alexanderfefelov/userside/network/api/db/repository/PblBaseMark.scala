package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblBaseMark(
  code: Int,
  usercode: Option[Int] = None,
  markcode: Option[Int] = None,
  dateadd: Option[DateTime] = None) {

  def save()(implicit session: DBSession = PblBaseMark.autoSession): PblBaseMark = PblBaseMark.save(this)(session)

  def destroy()(implicit session: DBSession = PblBaseMark.autoSession): Int = PblBaseMark.destroy(this)(session)

}


object PblBaseMark extends SQLSyntaxSupport[PblBaseMark] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_base_mark"

  override val columns = Seq("code", "usercode", "markcode", "dateadd")

  def apply(pbm: SyntaxProvider[PblBaseMark])(rs: WrappedResultSet): PblBaseMark = autoConstruct(rs, pbm)
  def apply(pbm: ResultName[PblBaseMark])(rs: WrappedResultSet): PblBaseMark = autoConstruct(rs, pbm)

  val pbm = PblBaseMark.syntax("pbm")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblBaseMark] = {
    withSQL {
      select.from(PblBaseMark as pbm).where.eq(pbm.code, code)
    }.map(PblBaseMark(pbm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblBaseMark] = {
    withSQL(select.from(PblBaseMark as pbm)).map(PblBaseMark(pbm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblBaseMark as pbm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblBaseMark] = {
    withSQL {
      select.from(PblBaseMark as pbm).where.append(where)
    }.map(PblBaseMark(pbm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblBaseMark] = {
    withSQL {
      select.from(PblBaseMark as pbm).where.append(where)
    }.map(PblBaseMark(pbm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblBaseMark as pbm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    usercode: Option[Int] = None,
    markcode: Option[Int] = None,
    dateadd: Option[DateTime] = None)(implicit session: DBSession = autoSession): PblBaseMark = {
    val generatedKey = withSQL {
      insert.into(PblBaseMark).namedValues(
        column.usercode -> usercode,
        column.markcode -> markcode,
        column.dateadd -> dateadd
      )
    }.updateAndReturnGeneratedKey.apply()

    PblBaseMark(
      code = generatedKey.toInt,
      usercode = usercode,
      markcode = markcode,
      dateadd = dateadd)
  }

  def batchInsert(entities: collection.Seq[PblBaseMark])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'usercode -> entity.usercode,
        'markcode -> entity.markcode,
        'dateadd -> entity.dateadd))
    SQL("""insert into pbl_base_mark(
      usercode,
      markcode,
      dateadd
    ) values (
      {usercode},
      {markcode},
      {dateadd}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblBaseMark)(implicit session: DBSession = autoSession): PblBaseMark = {
    withSQL {
      update(PblBaseMark).set(
        column.code -> entity.code,
        column.usercode -> entity.usercode,
        column.markcode -> entity.markcode,
        column.dateadd -> entity.dateadd
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblBaseMark)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblBaseMark).where.eq(column.code, entity.code) }.update.apply()
  }

}
