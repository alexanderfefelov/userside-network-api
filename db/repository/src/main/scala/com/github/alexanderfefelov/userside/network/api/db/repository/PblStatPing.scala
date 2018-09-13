package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblStatPing(
  code: Int,
  usercode: Option[Int] = None,
  datedo: Option[DateTime] = None) {

  def save()(implicit session: DBSession = PblStatPing.autoSession): PblStatPing = PblStatPing.save(this)(session)

  def destroy()(implicit session: DBSession = PblStatPing.autoSession): Int = PblStatPing.destroy(this)(session)

}


object PblStatPing extends SQLSyntaxSupport[PblStatPing] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_stat_ping"

  override val columns = Seq("code", "usercode", "datedo")

  def apply(psp: SyntaxProvider[PblStatPing])(rs: WrappedResultSet): PblStatPing = autoConstruct(rs, psp)
  def apply(psp: ResultName[PblStatPing])(rs: WrappedResultSet): PblStatPing = autoConstruct(rs, psp)

  val psp = PblStatPing.syntax("psp")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblStatPing] = {
    withSQL {
      select.from(PblStatPing as psp).where.eq(psp.code, code)
    }.map(PblStatPing(psp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblStatPing] = {
    withSQL(select.from(PblStatPing as psp)).map(PblStatPing(psp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblStatPing as psp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblStatPing] = {
    withSQL {
      select.from(PblStatPing as psp).where.append(where)
    }.map(PblStatPing(psp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblStatPing] = {
    withSQL {
      select.from(PblStatPing as psp).where.append(where)
    }.map(PblStatPing(psp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblStatPing as psp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    usercode: Option[Int] = None,
    datedo: Option[DateTime] = None)(implicit session: DBSession = autoSession): PblStatPing = {
    val generatedKey = withSQL {
      insert.into(PblStatPing).namedValues(
        column.usercode -> usercode,
        column.datedo -> datedo
      )
    }.updateAndReturnGeneratedKey.apply()

    PblStatPing(
      code = generatedKey.toInt,
      usercode = usercode,
      datedo = datedo)
  }

  def batchInsert(entities: collection.Seq[PblStatPing])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'usercode -> entity.usercode,
        'datedo -> entity.datedo))
    SQL("""insert into pbl_stat_ping(
      usercode,
      datedo
    ) values (
      {usercode},
      {datedo}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblStatPing)(implicit session: DBSession = autoSession): PblStatPing = {
    withSQL {
      update(PblStatPing).set(
        column.code -> entity.code,
        column.usercode -> entity.usercode,
        column.datedo -> entity.datedo
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblStatPing)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblStatPing).where.eq(column.code, entity.code) }.update.apply()
  }

}
