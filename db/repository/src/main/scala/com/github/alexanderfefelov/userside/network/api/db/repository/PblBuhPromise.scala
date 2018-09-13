package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblBuhPromise(
  code: Int,
  usercode: Option[Int] = None,
  datestop: Option[DateTime] = None,
  isact: Option[Short] = None,
  oldkredit: Option[BigDecimal] = None) {

  def save()(implicit session: DBSession = PblBuhPromise.autoSession): PblBuhPromise = PblBuhPromise.save(this)(session)

  def destroy()(implicit session: DBSession = PblBuhPromise.autoSession): Int = PblBuhPromise.destroy(this)(session)

}


object PblBuhPromise extends SQLSyntaxSupport[PblBuhPromise] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_buh_promise"

  override val columns = Seq("code", "usercode", "datestop", "isact", "oldkredit")

  def apply(pbp: SyntaxProvider[PblBuhPromise])(rs: WrappedResultSet): PblBuhPromise = autoConstruct(rs, pbp)
  def apply(pbp: ResultName[PblBuhPromise])(rs: WrappedResultSet): PblBuhPromise = autoConstruct(rs, pbp)

  val pbp = PblBuhPromise.syntax("pbp")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblBuhPromise] = {
    withSQL {
      select.from(PblBuhPromise as pbp).where.eq(pbp.code, code)
    }.map(PblBuhPromise(pbp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblBuhPromise] = {
    withSQL(select.from(PblBuhPromise as pbp)).map(PblBuhPromise(pbp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblBuhPromise as pbp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblBuhPromise] = {
    withSQL {
      select.from(PblBuhPromise as pbp).where.append(where)
    }.map(PblBuhPromise(pbp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblBuhPromise] = {
    withSQL {
      select.from(PblBuhPromise as pbp).where.append(where)
    }.map(PblBuhPromise(pbp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblBuhPromise as pbp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    usercode: Option[Int] = None,
    datestop: Option[DateTime] = None,
    isact: Option[Short] = None,
    oldkredit: Option[BigDecimal] = None)(implicit session: DBSession = autoSession): PblBuhPromise = {
    val generatedKey = withSQL {
      insert.into(PblBuhPromise).namedValues(
        column.usercode -> usercode,
        column.datestop -> datestop,
        column.isact -> isact,
        column.oldkredit -> oldkredit
      )
    }.updateAndReturnGeneratedKey.apply()

    PblBuhPromise(
      code = generatedKey.toInt,
      usercode = usercode,
      datestop = datestop,
      isact = isact,
      oldkredit = oldkredit)
  }

  def batchInsert(entities: collection.Seq[PblBuhPromise])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'usercode -> entity.usercode,
        'datestop -> entity.datestop,
        'isact -> entity.isact,
        'oldkredit -> entity.oldkredit))
    SQL("""insert into pbl_buh_promise(
      usercode,
      datestop,
      isact,
      oldkredit
    ) values (
      {usercode},
      {datestop},
      {isact},
      {oldkredit}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblBuhPromise)(implicit session: DBSession = autoSession): PblBuhPromise = {
    withSQL {
      update(PblBuhPromise).set(
        column.code -> entity.code,
        column.usercode -> entity.usercode,
        column.datestop -> entity.datestop,
        column.isact -> entity.isact,
        column.oldkredit -> entity.oldkredit
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblBuhPromise)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblBuhPromise).where.eq(column.code, entity.code) }.update.apply()
  }

}
