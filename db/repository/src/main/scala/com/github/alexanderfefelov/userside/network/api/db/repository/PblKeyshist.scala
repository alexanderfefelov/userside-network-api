package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblKeyshist(
  code: Int,
  keycode: Option[Int] = None,
  datedo: Option[DateTime] = None,
  keyfrom: Option[Int] = None,
  keyto: Option[Int] = None,
  dop: Option[String] = None,
  opercode: Option[Int] = None) {

  def save()(implicit session: DBSession = PblKeyshist.autoSession): PblKeyshist = PblKeyshist.save(this)(session)

  def destroy()(implicit session: DBSession = PblKeyshist.autoSession): Int = PblKeyshist.destroy(this)(session)

}


object PblKeyshist extends SQLSyntaxSupport[PblKeyshist] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_keyshist"

  override val columns = Seq("code", "keycode", "datedo", "keyfrom", "keyto", "dop", "opercode")

  def apply(pk: SyntaxProvider[PblKeyshist])(rs: WrappedResultSet): PblKeyshist = autoConstruct(rs, pk)
  def apply(pk: ResultName[PblKeyshist])(rs: WrappedResultSet): PblKeyshist = autoConstruct(rs, pk)

  val pk = PblKeyshist.syntax("pk")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblKeyshist] = {
    withSQL {
      select.from(PblKeyshist as pk).where.eq(pk.code, code)
    }.map(PblKeyshist(pk.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblKeyshist] = {
    withSQL(select.from(PblKeyshist as pk)).map(PblKeyshist(pk.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblKeyshist as pk)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblKeyshist] = {
    withSQL {
      select.from(PblKeyshist as pk).where.append(where)
    }.map(PblKeyshist(pk.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblKeyshist] = {
    withSQL {
      select.from(PblKeyshist as pk).where.append(where)
    }.map(PblKeyshist(pk.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblKeyshist as pk).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    keycode: Option[Int] = None,
    datedo: Option[DateTime] = None,
    keyfrom: Option[Int] = None,
    keyto: Option[Int] = None,
    dop: Option[String] = None,
    opercode: Option[Int] = None)(implicit session: DBSession = autoSession): PblKeyshist = {
    val generatedKey = withSQL {
      insert.into(PblKeyshist).namedValues(
        column.keycode -> keycode,
        column.datedo -> datedo,
        column.keyfrom -> keyfrom,
        column.keyto -> keyto,
        column.dop -> dop,
        column.opercode -> opercode
      )
    }.updateAndReturnGeneratedKey.apply()

    PblKeyshist(
      code = generatedKey.toInt,
      keycode = keycode,
      datedo = datedo,
      keyfrom = keyfrom,
      keyto = keyto,
      dop = dop,
      opercode = opercode)
  }

  def batchInsert(entities: collection.Seq[PblKeyshist])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'keycode -> entity.keycode,
        'datedo -> entity.datedo,
        'keyfrom -> entity.keyfrom,
        'keyto -> entity.keyto,
        'dop -> entity.dop,
        'opercode -> entity.opercode))
    SQL("""insert into pbl_keyshist(
      keycode,
      datedo,
      keyfrom,
      keyto,
      dop,
      opercode
    ) values (
      {keycode},
      {datedo},
      {keyfrom},
      {keyto},
      {dop},
      {opercode}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblKeyshist)(implicit session: DBSession = autoSession): PblKeyshist = {
    withSQL {
      update(PblKeyshist).set(
        column.code -> entity.code,
        column.keycode -> entity.keycode,
        column.datedo -> entity.datedo,
        column.keyfrom -> entity.keyfrom,
        column.keyto -> entity.keyto,
        column.dop -> entity.dop,
        column.opercode -> entity.opercode
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblKeyshist)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblKeyshist).where.eq(column.code, entity.code) }.update.apply()
  }

}
