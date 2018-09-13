package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfAccf(
  code: Int,
  nazv: Option[String] = None) {

  def save()(implicit session: DBSession = PblConfAccf.autoSession): PblConfAccf = PblConfAccf.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfAccf.autoSession): Int = PblConfAccf.destroy(this)(session)

}


object PblConfAccf extends SQLSyntaxSupport[PblConfAccf] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_accf"

  override val columns = Seq("code", "nazv")

  def apply(pca: SyntaxProvider[PblConfAccf])(rs: WrappedResultSet): PblConfAccf = autoConstruct(rs, pca)
  def apply(pca: ResultName[PblConfAccf])(rs: WrappedResultSet): PblConfAccf = autoConstruct(rs, pca)

  val pca = PblConfAccf.syntax("pca")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfAccf] = {
    withSQL {
      select.from(PblConfAccf as pca).where.eq(pca.code, code)
    }.map(PblConfAccf(pca.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfAccf] = {
    withSQL(select.from(PblConfAccf as pca)).map(PblConfAccf(pca.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfAccf as pca)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfAccf] = {
    withSQL {
      select.from(PblConfAccf as pca).where.append(where)
    }.map(PblConfAccf(pca.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfAccf] = {
    withSQL {
      select.from(PblConfAccf as pca).where.append(where)
    }.map(PblConfAccf(pca.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfAccf as pca).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    nazv: Option[String] = None)(implicit session: DBSession = autoSession): PblConfAccf = {
    val generatedKey = withSQL {
      insert.into(PblConfAccf).namedValues(
        column.nazv -> nazv
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfAccf(
      code = generatedKey.toInt,
      nazv = nazv)
  }

  def batchInsert(entities: collection.Seq[PblConfAccf])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'nazv -> entity.nazv))
    SQL("""insert into pbl_conf_accf(
      nazv
    ) values (
      {nazv}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfAccf)(implicit session: DBSession = autoSession): PblConfAccf = {
    withSQL {
      update(PblConfAccf).set(
        column.code -> entity.code,
        column.nazv -> entity.nazv
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfAccf)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfAccf).where.eq(column.code, entity.code) }.update.apply()
  }

}
