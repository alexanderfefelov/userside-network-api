package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfTable(
  code: Int,
  tbltyper: Option[Int] = None,
  property: Option[String] = None) {

  def save()(implicit session: DBSession = PblConfTable.autoSession): PblConfTable = PblConfTable.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfTable.autoSession): Int = PblConfTable.destroy(this)(session)

}


object PblConfTable extends SQLSyntaxSupport[PblConfTable] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_table"

  override val columns = Seq("code", "tbltyper", "property")

  def apply(pct: SyntaxProvider[PblConfTable])(rs: WrappedResultSet): PblConfTable = autoConstruct(rs, pct)
  def apply(pct: ResultName[PblConfTable])(rs: WrappedResultSet): PblConfTable = autoConstruct(rs, pct)

  val pct = PblConfTable.syntax("pct")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfTable] = {
    withSQL {
      select.from(PblConfTable as pct).where.eq(pct.code, code)
    }.map(PblConfTable(pct.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfTable] = {
    withSQL(select.from(PblConfTable as pct)).map(PblConfTable(pct.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfTable as pct)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfTable] = {
    withSQL {
      select.from(PblConfTable as pct).where.append(where)
    }.map(PblConfTable(pct.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfTable] = {
    withSQL {
      select.from(PblConfTable as pct).where.append(where)
    }.map(PblConfTable(pct.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfTable as pct).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    tbltyper: Option[Int] = None,
    property: Option[String] = None)(implicit session: DBSession = autoSession): PblConfTable = {
    val generatedKey = withSQL {
      insert.into(PblConfTable).namedValues(
        column.tbltyper -> tbltyper,
        column.property -> property
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfTable(
      code = generatedKey.toInt,
      tbltyper = tbltyper,
      property = property)
  }

  def batchInsert(entities: collection.Seq[PblConfTable])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'tbltyper -> entity.tbltyper,
        'property -> entity.property))
    SQL("""insert into pbl_conf_table(
      tbltyper,
      property
    ) values (
      {tbltyper},
      {property}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfTable)(implicit session: DBSession = autoSession): PblConfTable = {
    withSQL {
      update(PblConfTable).set(
        column.code -> entity.code,
        column.tbltyper -> entity.tbltyper,
        column.property -> entity.property
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfTable)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfTable).where.eq(column.code, entity.code) }.update.apply()
  }

}
