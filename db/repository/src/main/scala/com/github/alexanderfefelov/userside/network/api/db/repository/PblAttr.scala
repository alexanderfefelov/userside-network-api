package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblAttr(
  code: Int,
  attrcode: Option[Int] = None,
  usercode: Option[Int] = None,
  valuestr: Option[String] = None,
  valueint: Option[Int] = None,
  valuedate: Option[LocalDate] = None,
  valuememo: Option[String] = None) {

  def save()(implicit session: DBSession = PblAttr.autoSession): PblAttr = PblAttr.save(this)(session)

  def destroy()(implicit session: DBSession = PblAttr.autoSession): Int = PblAttr.destroy(this)(session)

}


object PblAttr extends SQLSyntaxSupport[PblAttr] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_attr"

  override val columns = Seq("code", "attrcode", "usercode", "valuestr", "valueint", "valuedate", "valuememo")

  def apply(pa: SyntaxProvider[PblAttr])(rs: WrappedResultSet): PblAttr = autoConstruct(rs, pa)
  def apply(pa: ResultName[PblAttr])(rs: WrappedResultSet): PblAttr = autoConstruct(rs, pa)

  val pa = PblAttr.syntax("pa")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblAttr] = {
    withSQL {
      select.from(PblAttr as pa).where.eq(pa.code, code)
    }.map(PblAttr(pa.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblAttr] = {
    withSQL(select.from(PblAttr as pa)).map(PblAttr(pa.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblAttr as pa)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblAttr] = {
    withSQL {
      select.from(PblAttr as pa).where.append(where)
    }.map(PblAttr(pa.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblAttr] = {
    withSQL {
      select.from(PblAttr as pa).where.append(where)
    }.map(PblAttr(pa.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblAttr as pa).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    attrcode: Option[Int] = None,
    usercode: Option[Int] = None,
    valuestr: Option[String] = None,
    valueint: Option[Int] = None,
    valuedate: Option[LocalDate] = None,
    valuememo: Option[String] = None)(implicit session: DBSession = autoSession): PblAttr = {
    val generatedKey = withSQL {
      insert.into(PblAttr).namedValues(
        column.attrcode -> attrcode,
        column.usercode -> usercode,
        column.valuestr -> valuestr,
        column.valueint -> valueint,
        column.valuedate -> valuedate,
        column.valuememo -> valuememo
      )
    }.updateAndReturnGeneratedKey.apply()

    PblAttr(
      code = generatedKey.toInt,
      attrcode = attrcode,
      usercode = usercode,
      valuestr = valuestr,
      valueint = valueint,
      valuedate = valuedate,
      valuememo = valuememo)
  }

  def batchInsert(entities: collection.Seq[PblAttr])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'attrcode -> entity.attrcode,
        'usercode -> entity.usercode,
        'valuestr -> entity.valuestr,
        'valueint -> entity.valueint,
        'valuedate -> entity.valuedate,
        'valuememo -> entity.valuememo))
    SQL("""insert into pbl_attr(
      attrcode,
      usercode,
      valuestr,
      valueint,
      valuedate,
      valuememo
    ) values (
      {attrcode},
      {usercode},
      {valuestr},
      {valueint},
      {valuedate},
      {valuememo}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblAttr)(implicit session: DBSession = autoSession): PblAttr = {
    withSQL {
      update(PblAttr).set(
        column.code -> entity.code,
        column.attrcode -> entity.attrcode,
        column.usercode -> entity.usercode,
        column.valuestr -> entity.valuestr,
        column.valueint -> entity.valueint,
        column.valuedate -> entity.valuedate,
        column.valuememo -> entity.valuememo
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblAttr)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblAttr).where.eq(column.code, entity.code) }.update.apply()
  }

}
