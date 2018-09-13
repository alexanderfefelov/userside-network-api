package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblDocs(
  code: Int,
  nazv: Option[String] = None,
  isenabled: Option[Short] = None,
  opis: Option[String] = None) {

  def save()(implicit session: DBSession = PblDocs.autoSession): PblDocs = PblDocs.save(this)(session)

  def destroy()(implicit session: DBSession = PblDocs.autoSession): Int = PblDocs.destroy(this)(session)

}


object PblDocs extends SQLSyntaxSupport[PblDocs] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_docs"

  override val columns = Seq("code", "nazv", "isenabled", "opis")

  def apply(pd: SyntaxProvider[PblDocs])(rs: WrappedResultSet): PblDocs = autoConstruct(rs, pd)
  def apply(pd: ResultName[PblDocs])(rs: WrappedResultSet): PblDocs = autoConstruct(rs, pd)

  val pd = PblDocs.syntax("pd")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblDocs] = {
    withSQL {
      select.from(PblDocs as pd).where.eq(pd.code, code)
    }.map(PblDocs(pd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblDocs] = {
    withSQL(select.from(PblDocs as pd)).map(PblDocs(pd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblDocs as pd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblDocs] = {
    withSQL {
      select.from(PblDocs as pd).where.append(where)
    }.map(PblDocs(pd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblDocs] = {
    withSQL {
      select.from(PblDocs as pd).where.append(where)
    }.map(PblDocs(pd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblDocs as pd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    nazv: Option[String] = None,
    isenabled: Option[Short] = None,
    opis: Option[String] = None)(implicit session: DBSession = autoSession): PblDocs = {
    val generatedKey = withSQL {
      insert.into(PblDocs).namedValues(
        column.nazv -> nazv,
        column.isenabled -> isenabled,
        column.opis -> opis
      )
    }.updateAndReturnGeneratedKey.apply()

    PblDocs(
      code = generatedKey.toInt,
      nazv = nazv,
      isenabled = isenabled,
      opis = opis)
  }

  def batchInsert(entities: collection.Seq[PblDocs])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'nazv -> entity.nazv,
        'isenabled -> entity.isenabled,
        'opis -> entity.opis))
    SQL("""insert into pbl_docs(
      nazv,
      isenabled,
      opis
    ) values (
      {nazv},
      {isenabled},
      {opis}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblDocs)(implicit session: DBSession = autoSession): PblDocs = {
    withSQL {
      update(PblDocs).set(
        column.code -> entity.code,
        column.nazv -> entity.nazv,
        column.isenabled -> entity.isenabled,
        column.opis -> entity.opis
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblDocs)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblDocs).where.eq(column.code, entity.code) }.update.apply()
  }

}
