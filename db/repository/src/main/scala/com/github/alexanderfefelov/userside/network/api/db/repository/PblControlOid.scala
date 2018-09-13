package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblControlOid(
  code: Int,
  oid: Option[String] = None,
  nazv: Option[String] = None,
  alertthen: Option[Short] = None,
  alertparam1: Option[String] = None,
  alertparam2: Option[String] = None,
  ispolling: Option[Short] = None) {

  def save()(implicit session: DBSession = PblControlOid.autoSession): PblControlOid = PblControlOid.save(this)(session)

  def destroy()(implicit session: DBSession = PblControlOid.autoSession): Int = PblControlOid.destroy(this)(session)

}


object PblControlOid extends SQLSyntaxSupport[PblControlOid] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_control_oid"

  override val columns = Seq("code", "oid", "nazv", "alertthen", "alertparam1", "alertparam2", "ispolling")

  def apply(pco: SyntaxProvider[PblControlOid])(rs: WrappedResultSet): PblControlOid = autoConstruct(rs, pco)
  def apply(pco: ResultName[PblControlOid])(rs: WrappedResultSet): PblControlOid = autoConstruct(rs, pco)

  val pco = PblControlOid.syntax("pco")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblControlOid] = {
    withSQL {
      select.from(PblControlOid as pco).where.eq(pco.code, code)
    }.map(PblControlOid(pco.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblControlOid] = {
    withSQL(select.from(PblControlOid as pco)).map(PblControlOid(pco.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblControlOid as pco)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblControlOid] = {
    withSQL {
      select.from(PblControlOid as pco).where.append(where)
    }.map(PblControlOid(pco.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblControlOid] = {
    withSQL {
      select.from(PblControlOid as pco).where.append(where)
    }.map(PblControlOid(pco.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblControlOid as pco).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    oid: Option[String] = None,
    nazv: Option[String] = None,
    alertthen: Option[Short] = None,
    alertparam1: Option[String] = None,
    alertparam2: Option[String] = None,
    ispolling: Option[Short] = None)(implicit session: DBSession = autoSession): PblControlOid = {
    val generatedKey = withSQL {
      insert.into(PblControlOid).namedValues(
        column.oid -> oid,
        column.nazv -> nazv,
        column.alertthen -> alertthen,
        column.alertparam1 -> alertparam1,
        column.alertparam2 -> alertparam2,
        column.ispolling -> ispolling
      )
    }.updateAndReturnGeneratedKey.apply()

    PblControlOid(
      code = generatedKey.toInt,
      oid = oid,
      nazv = nazv,
      alertthen = alertthen,
      alertparam1 = alertparam1,
      alertparam2 = alertparam2,
      ispolling = ispolling)
  }

  def batchInsert(entities: collection.Seq[PblControlOid])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'oid -> entity.oid,
        'nazv -> entity.nazv,
        'alertthen -> entity.alertthen,
        'alertparam1 -> entity.alertparam1,
        'alertparam2 -> entity.alertparam2,
        'ispolling -> entity.ispolling))
    SQL("""insert into pbl_control_oid(
      oid,
      nazv,
      alertthen,
      alertparam1,
      alertparam2,
      ispolling
    ) values (
      {oid},
      {nazv},
      {alertthen},
      {alertparam1},
      {alertparam2},
      {ispolling}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblControlOid)(implicit session: DBSession = autoSession): PblControlOid = {
    withSQL {
      update(PblControlOid).set(
        column.code -> entity.code,
        column.oid -> entity.oid,
        column.nazv -> entity.nazv,
        column.alertthen -> entity.alertthen,
        column.alertparam1 -> entity.alertparam1,
        column.alertparam2 -> entity.alertparam2,
        column.ispolling -> entity.ispolling
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblControlOid)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblControlOid).where.eq(column.code, entity.code) }.update.apply()
  }

}
