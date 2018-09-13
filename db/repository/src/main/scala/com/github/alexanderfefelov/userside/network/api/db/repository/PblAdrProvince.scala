package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblAdrProvince(
  code: Int,
  nazv: Option[String] = None,
  maincity: Option[Int] = None,
  newId: Option[Int] = None) {

  def save()(implicit session: DBSession = PblAdrProvince.autoSession): PblAdrProvince = PblAdrProvince.save(this)(session)

  def destroy()(implicit session: DBSession = PblAdrProvince.autoSession): Int = PblAdrProvince.destroy(this)(session)

}


object PblAdrProvince extends SQLSyntaxSupport[PblAdrProvince] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_adr_province"

  override val columns = Seq("code", "nazv", "maincity", "new_id")

  def apply(pap: SyntaxProvider[PblAdrProvince])(rs: WrappedResultSet): PblAdrProvince = autoConstruct(rs, pap)
  def apply(pap: ResultName[PblAdrProvince])(rs: WrappedResultSet): PblAdrProvince = autoConstruct(rs, pap)

  val pap = PblAdrProvince.syntax("pap")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblAdrProvince] = {
    withSQL {
      select.from(PblAdrProvince as pap).where.eq(pap.code, code)
    }.map(PblAdrProvince(pap.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblAdrProvince] = {
    withSQL(select.from(PblAdrProvince as pap)).map(PblAdrProvince(pap.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblAdrProvince as pap)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblAdrProvince] = {
    withSQL {
      select.from(PblAdrProvince as pap).where.append(where)
    }.map(PblAdrProvince(pap.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblAdrProvince] = {
    withSQL {
      select.from(PblAdrProvince as pap).where.append(where)
    }.map(PblAdrProvince(pap.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblAdrProvince as pap).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    nazv: Option[String] = None,
    maincity: Option[Int] = None,
    newId: Option[Int] = None)(implicit session: DBSession = autoSession): PblAdrProvince = {
    val generatedKey = withSQL {
      insert.into(PblAdrProvince).namedValues(
        column.nazv -> nazv,
        column.maincity -> maincity,
        column.newId -> newId
      )
    }.updateAndReturnGeneratedKey.apply()

    PblAdrProvince(
      code = generatedKey.toInt,
      nazv = nazv,
      maincity = maincity,
      newId = newId)
  }

  def batchInsert(entities: collection.Seq[PblAdrProvince])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'nazv -> entity.nazv,
        'maincity -> entity.maincity,
        'newId -> entity.newId))
    SQL("""insert into pbl_adr_province(
      nazv,
      maincity,
      new_id
    ) values (
      {nazv},
      {maincity},
      {newId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblAdrProvince)(implicit session: DBSession = autoSession): PblAdrProvince = {
    withSQL {
      update(PblAdrProvince).set(
        column.code -> entity.code,
        column.nazv -> entity.nazv,
        column.maincity -> entity.maincity,
        column.newId -> entity.newId
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblAdrProvince)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblAdrProvince).where.eq(column.code, entity.code) }.update.apply()
  }

}
