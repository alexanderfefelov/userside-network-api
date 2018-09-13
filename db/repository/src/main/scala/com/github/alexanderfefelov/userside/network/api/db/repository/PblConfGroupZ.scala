package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfGroupZ(
  code: Int,
  groupcode1: Option[String] = None,
  groupcode2: Option[String] = None) {

  def save()(implicit session: DBSession = PblConfGroupZ.autoSession): PblConfGroupZ = PblConfGroupZ.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfGroupZ.autoSession): Int = PblConfGroupZ.destroy(this)(session)

}


object PblConfGroupZ extends SQLSyntaxSupport[PblConfGroupZ] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_group_z"

  override val columns = Seq("code", "groupcode1", "groupcode2")

  def apply(pcgz: SyntaxProvider[PblConfGroupZ])(rs: WrappedResultSet): PblConfGroupZ = autoConstruct(rs, pcgz)
  def apply(pcgz: ResultName[PblConfGroupZ])(rs: WrappedResultSet): PblConfGroupZ = autoConstruct(rs, pcgz)

  val pcgz = PblConfGroupZ.syntax("pcgz")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfGroupZ] = {
    withSQL {
      select.from(PblConfGroupZ as pcgz).where.eq(pcgz.code, code)
    }.map(PblConfGroupZ(pcgz.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfGroupZ] = {
    withSQL(select.from(PblConfGroupZ as pcgz)).map(PblConfGroupZ(pcgz.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfGroupZ as pcgz)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfGroupZ] = {
    withSQL {
      select.from(PblConfGroupZ as pcgz).where.append(where)
    }.map(PblConfGroupZ(pcgz.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfGroupZ] = {
    withSQL {
      select.from(PblConfGroupZ as pcgz).where.append(where)
    }.map(PblConfGroupZ(pcgz.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfGroupZ as pcgz).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    groupcode1: Option[String] = None,
    groupcode2: Option[String] = None)(implicit session: DBSession = autoSession): PblConfGroupZ = {
    val generatedKey = withSQL {
      insert.into(PblConfGroupZ).namedValues(
        column.groupcode1 -> groupcode1,
        column.groupcode2 -> groupcode2
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfGroupZ(
      code = generatedKey.toInt,
      groupcode1 = groupcode1,
      groupcode2 = groupcode2)
  }

  def batchInsert(entities: collection.Seq[PblConfGroupZ])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'groupcode1 -> entity.groupcode1,
        'groupcode2 -> entity.groupcode2))
    SQL("""insert into pbl_conf_group_z(
      groupcode1,
      groupcode2
    ) values (
      {groupcode1},
      {groupcode2}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfGroupZ)(implicit session: DBSession = autoSession): PblConfGroupZ = {
    withSQL {
      update(PblConfGroupZ).set(
        column.code -> entity.code,
        column.groupcode1 -> entity.groupcode1,
        column.groupcode2 -> entity.groupcode2
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfGroupZ)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfGroupZ).where.eq(column.code, entity.code) }.update.apply()
  }

}
