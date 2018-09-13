package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblKeys(
  code: Int,
  housecode: Option[Int] = None,
  nazv: Option[String] = None,
  status: Option[Int] = None,
  dop: Option[String] = None) {

  def save()(implicit session: DBSession = PblKeys.autoSession): PblKeys = PblKeys.save(this)(session)

  def destroy()(implicit session: DBSession = PblKeys.autoSession): Int = PblKeys.destroy(this)(session)

}


object PblKeys extends SQLSyntaxSupport[PblKeys] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_keys"

  override val columns = Seq("code", "housecode", "nazv", "status", "dop")

  def apply(pk: SyntaxProvider[PblKeys])(rs: WrappedResultSet): PblKeys = autoConstruct(rs, pk)
  def apply(pk: ResultName[PblKeys])(rs: WrappedResultSet): PblKeys = autoConstruct(rs, pk)

  val pk = PblKeys.syntax("pk")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblKeys] = {
    withSQL {
      select.from(PblKeys as pk).where.eq(pk.code, code)
    }.map(PblKeys(pk.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblKeys] = {
    withSQL(select.from(PblKeys as pk)).map(PblKeys(pk.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblKeys as pk)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblKeys] = {
    withSQL {
      select.from(PblKeys as pk).where.append(where)
    }.map(PblKeys(pk.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblKeys] = {
    withSQL {
      select.from(PblKeys as pk).where.append(where)
    }.map(PblKeys(pk.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblKeys as pk).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    housecode: Option[Int] = None,
    nazv: Option[String] = None,
    status: Option[Int] = None,
    dop: Option[String] = None)(implicit session: DBSession = autoSession): PblKeys = {
    val generatedKey = withSQL {
      insert.into(PblKeys).namedValues(
        column.housecode -> housecode,
        column.nazv -> nazv,
        column.status -> status,
        column.dop -> dop
      )
    }.updateAndReturnGeneratedKey.apply()

    PblKeys(
      code = generatedKey.toInt,
      housecode = housecode,
      nazv = nazv,
      status = status,
      dop = dop)
  }

  def batchInsert(entities: collection.Seq[PblKeys])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'housecode -> entity.housecode,
        'nazv -> entity.nazv,
        'status -> entity.status,
        'dop -> entity.dop))
    SQL("""insert into pbl_keys(
      housecode,
      nazv,
      status,
      dop
    ) values (
      {housecode},
      {nazv},
      {status},
      {dop}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblKeys)(implicit session: DBSession = autoSession): PblKeys = {
    withSQL {
      update(PblKeys).set(
        column.code -> entity.code,
        column.housecode -> entity.housecode,
        column.nazv -> entity.nazv,
        column.status -> entity.status,
        column.dop -> entity.dop
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblKeys)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblKeys).where.eq(column.code, entity.code) }.update.apply()
  }

}
