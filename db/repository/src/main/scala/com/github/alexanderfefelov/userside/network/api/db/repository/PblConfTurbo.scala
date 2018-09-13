package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfTurbo(
  code: Int,
  hourtime: Option[Int] = None,
  nazv: Option[String] = None,
  price: Option[BigDecimal] = None) {

  def save()(implicit session: DBSession = PblConfTurbo.autoSession): PblConfTurbo = PblConfTurbo.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfTurbo.autoSession): Int = PblConfTurbo.destroy(this)(session)

}


object PblConfTurbo extends SQLSyntaxSupport[PblConfTurbo] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_turbo"

  override val columns = Seq("code", "hourtime", "nazv", "price")

  def apply(pct: SyntaxProvider[PblConfTurbo])(rs: WrappedResultSet): PblConfTurbo = autoConstruct(rs, pct)
  def apply(pct: ResultName[PblConfTurbo])(rs: WrappedResultSet): PblConfTurbo = autoConstruct(rs, pct)

  val pct = PblConfTurbo.syntax("pct")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfTurbo] = {
    withSQL {
      select.from(PblConfTurbo as pct).where.eq(pct.code, code)
    }.map(PblConfTurbo(pct.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfTurbo] = {
    withSQL(select.from(PblConfTurbo as pct)).map(PblConfTurbo(pct.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfTurbo as pct)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfTurbo] = {
    withSQL {
      select.from(PblConfTurbo as pct).where.append(where)
    }.map(PblConfTurbo(pct.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfTurbo] = {
    withSQL {
      select.from(PblConfTurbo as pct).where.append(where)
    }.map(PblConfTurbo(pct.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfTurbo as pct).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    hourtime: Option[Int] = None,
    nazv: Option[String] = None,
    price: Option[BigDecimal] = None)(implicit session: DBSession = autoSession): PblConfTurbo = {
    val generatedKey = withSQL {
      insert.into(PblConfTurbo).namedValues(
        column.hourtime -> hourtime,
        column.nazv -> nazv,
        column.price -> price
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfTurbo(
      code = generatedKey.toInt,
      hourtime = hourtime,
      nazv = nazv,
      price = price)
  }

  def batchInsert(entities: collection.Seq[PblConfTurbo])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'hourtime -> entity.hourtime,
        'nazv -> entity.nazv,
        'price -> entity.price))
    SQL("""insert into pbl_conf_turbo(
      hourtime,
      nazv,
      price
    ) values (
      {hourtime},
      {nazv},
      {price}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfTurbo)(implicit session: DBSession = autoSession): PblConfTurbo = {
    withSQL {
      update(PblConfTurbo).set(
        column.code -> entity.code,
        column.hourtime -> entity.hourtime,
        column.nazv -> entity.nazv,
        column.price -> entity.price
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfTurbo)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfTurbo).where.eq(column.code, entity.code) }.update.apply()
  }

}
