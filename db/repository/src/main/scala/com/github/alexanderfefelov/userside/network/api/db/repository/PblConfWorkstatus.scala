package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfWorkstatus(
  code: Int,
  nazv: Option[String] = None) {

  def save()(implicit session: DBSession = PblConfWorkstatus.autoSession): PblConfWorkstatus = PblConfWorkstatus.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfWorkstatus.autoSession): Int = PblConfWorkstatus.destroy(this)(session)

}


object PblConfWorkstatus extends SQLSyntaxSupport[PblConfWorkstatus] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_workstatus"

  override val columns = Seq("code", "nazv")

  def apply(pcw: SyntaxProvider[PblConfWorkstatus])(rs: WrappedResultSet): PblConfWorkstatus = autoConstruct(rs, pcw)
  def apply(pcw: ResultName[PblConfWorkstatus])(rs: WrappedResultSet): PblConfWorkstatus = autoConstruct(rs, pcw)

  val pcw = PblConfWorkstatus.syntax("pcw")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfWorkstatus] = {
    withSQL {
      select.from(PblConfWorkstatus as pcw).where.eq(pcw.code, code)
    }.map(PblConfWorkstatus(pcw.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfWorkstatus] = {
    withSQL(select.from(PblConfWorkstatus as pcw)).map(PblConfWorkstatus(pcw.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfWorkstatus as pcw)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfWorkstatus] = {
    withSQL {
      select.from(PblConfWorkstatus as pcw).where.append(where)
    }.map(PblConfWorkstatus(pcw.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfWorkstatus] = {
    withSQL {
      select.from(PblConfWorkstatus as pcw).where.append(where)
    }.map(PblConfWorkstatus(pcw.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfWorkstatus as pcw).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    nazv: Option[String] = None)(implicit session: DBSession = autoSession): PblConfWorkstatus = {
    val generatedKey = withSQL {
      insert.into(PblConfWorkstatus).namedValues(
        column.nazv -> nazv
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfWorkstatus(
      code = generatedKey.toInt,
      nazv = nazv)
  }

  def batchInsert(entities: collection.Seq[PblConfWorkstatus])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'nazv -> entity.nazv))
    SQL("""insert into pbl_conf_workstatus(
      nazv
    ) values (
      {nazv}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfWorkstatus)(implicit session: DBSession = autoSession): PblConfWorkstatus = {
    withSQL {
      update(PblConfWorkstatus).set(
        column.code -> entity.code,
        column.nazv -> entity.nazv
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfWorkstatus)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfWorkstatus).where.eq(column.code, entity.code) }.update.apply()
  }

}
