package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblJur(
  code: Int,
  usercode: Option[Int] = None) {

  def save()(implicit session: DBSession = PblJur.autoSession): PblJur = PblJur.save(this)(session)

  def destroy()(implicit session: DBSession = PblJur.autoSession): Int = PblJur.destroy(this)(session)

}


object PblJur extends SQLSyntaxSupport[PblJur] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_jur"

  override val columns = Seq("code", "usercode")

  def apply(pj: SyntaxProvider[PblJur])(rs: WrappedResultSet): PblJur = autoConstruct(rs, pj)
  def apply(pj: ResultName[PblJur])(rs: WrappedResultSet): PblJur = autoConstruct(rs, pj)

  val pj = PblJur.syntax("pj")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblJur] = {
    withSQL {
      select.from(PblJur as pj).where.eq(pj.code, code)
    }.map(PblJur(pj.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblJur] = {
    withSQL(select.from(PblJur as pj)).map(PblJur(pj.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblJur as pj)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblJur] = {
    withSQL {
      select.from(PblJur as pj).where.append(where)
    }.map(PblJur(pj.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblJur] = {
    withSQL {
      select.from(PblJur as pj).where.append(where)
    }.map(PblJur(pj.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblJur as pj).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    usercode: Option[Int] = None)(implicit session: DBSession = autoSession): PblJur = {
    val generatedKey = withSQL {
      insert.into(PblJur).namedValues(
        column.usercode -> usercode
      )
    }.updateAndReturnGeneratedKey.apply()

    PblJur(
      code = generatedKey.toInt,
      usercode = usercode)
  }

  def batchInsert(entities: collection.Seq[PblJur])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'usercode -> entity.usercode))
    SQL("""insert into pbl_jur(
      usercode
    ) values (
      {usercode}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblJur)(implicit session: DBSession = autoSession): PblJur = {
    withSQL {
      update(PblJur).set(
        column.code -> entity.code,
        column.usercode -> entity.usercode
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblJur)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblJur).where.eq(column.code, entity.code) }.update.apply()
  }

}
