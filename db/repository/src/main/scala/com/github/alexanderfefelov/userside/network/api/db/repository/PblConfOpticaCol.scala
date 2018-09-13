package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfOpticaCol(
  code: Int,
  colnazv: Option[String] = None,
  colcol: Option[String] = None,
  coltype: Option[Short] = None,
  markcol: Option[Short] = None) {

  def save()(implicit session: DBSession = PblConfOpticaCol.autoSession): PblConfOpticaCol = PblConfOpticaCol.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfOpticaCol.autoSession): Int = PblConfOpticaCol.destroy(this)(session)

}


object PblConfOpticaCol extends SQLSyntaxSupport[PblConfOpticaCol] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_optica_col"

  override val columns = Seq("code", "colnazv", "colcol", "coltype", "markcol")

  def apply(pcoc: SyntaxProvider[PblConfOpticaCol])(rs: WrappedResultSet): PblConfOpticaCol = autoConstruct(rs, pcoc)
  def apply(pcoc: ResultName[PblConfOpticaCol])(rs: WrappedResultSet): PblConfOpticaCol = autoConstruct(rs, pcoc)

  val pcoc = PblConfOpticaCol.syntax("pcoc")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfOpticaCol] = {
    withSQL {
      select.from(PblConfOpticaCol as pcoc).where.eq(pcoc.code, code)
    }.map(PblConfOpticaCol(pcoc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfOpticaCol] = {
    withSQL(select.from(PblConfOpticaCol as pcoc)).map(PblConfOpticaCol(pcoc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfOpticaCol as pcoc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfOpticaCol] = {
    withSQL {
      select.from(PblConfOpticaCol as pcoc).where.append(where)
    }.map(PblConfOpticaCol(pcoc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfOpticaCol] = {
    withSQL {
      select.from(PblConfOpticaCol as pcoc).where.append(where)
    }.map(PblConfOpticaCol(pcoc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfOpticaCol as pcoc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    colnazv: Option[String] = None,
    colcol: Option[String] = None,
    coltype: Option[Short] = None,
    markcol: Option[Short] = None)(implicit session: DBSession = autoSession): PblConfOpticaCol = {
    val generatedKey = withSQL {
      insert.into(PblConfOpticaCol).namedValues(
        column.colnazv -> colnazv,
        column.colcol -> colcol,
        column.coltype -> coltype,
        column.markcol -> markcol
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfOpticaCol(
      code = generatedKey.toInt,
      colnazv = colnazv,
      colcol = colcol,
      coltype = coltype,
      markcol = markcol)
  }

  def batchInsert(entities: collection.Seq[PblConfOpticaCol])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'colnazv -> entity.colnazv,
        'colcol -> entity.colcol,
        'coltype -> entity.coltype,
        'markcol -> entity.markcol))
    SQL("""insert into pbl_conf_optica_col(
      colnazv,
      colcol,
      coltype,
      markcol
    ) values (
      {colnazv},
      {colcol},
      {coltype},
      {markcol}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfOpticaCol)(implicit session: DBSession = autoSession): PblConfOpticaCol = {
    withSQL {
      update(PblConfOpticaCol).set(
        column.code -> entity.code,
        column.colnazv -> entity.colnazv,
        column.colcol -> entity.colcol,
        column.coltype -> entity.coltype,
        column.markcol -> entity.markcol
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfOpticaCol)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfOpticaCol).where.eq(column.code, entity.code) }.update.apply()
  }

}
