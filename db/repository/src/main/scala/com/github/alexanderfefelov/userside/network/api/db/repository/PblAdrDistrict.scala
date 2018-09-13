package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblAdrDistrict(
  code: Int,
  nazv: Option[String] = None,
  provincecode: Option[Int] = None,
  maincity: Option[Int] = None,
  newId: Option[Int] = None) {

  def save()(implicit session: DBSession = PblAdrDistrict.autoSession): PblAdrDistrict = PblAdrDistrict.save(this)(session)

  def destroy()(implicit session: DBSession = PblAdrDistrict.autoSession): Int = PblAdrDistrict.destroy(this)(session)

}


object PblAdrDistrict extends SQLSyntaxSupport[PblAdrDistrict] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_adr_district"

  override val columns = Seq("code", "nazv", "provincecode", "maincity", "new_id")

  def apply(pad: SyntaxProvider[PblAdrDistrict])(rs: WrappedResultSet): PblAdrDistrict = autoConstruct(rs, pad)
  def apply(pad: ResultName[PblAdrDistrict])(rs: WrappedResultSet): PblAdrDistrict = autoConstruct(rs, pad)

  val pad = PblAdrDistrict.syntax("pad")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblAdrDistrict] = {
    withSQL {
      select.from(PblAdrDistrict as pad).where.eq(pad.code, code)
    }.map(PblAdrDistrict(pad.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblAdrDistrict] = {
    withSQL(select.from(PblAdrDistrict as pad)).map(PblAdrDistrict(pad.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblAdrDistrict as pad)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblAdrDistrict] = {
    withSQL {
      select.from(PblAdrDistrict as pad).where.append(where)
    }.map(PblAdrDistrict(pad.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblAdrDistrict] = {
    withSQL {
      select.from(PblAdrDistrict as pad).where.append(where)
    }.map(PblAdrDistrict(pad.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblAdrDistrict as pad).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    nazv: Option[String] = None,
    provincecode: Option[Int] = None,
    maincity: Option[Int] = None,
    newId: Option[Int] = None)(implicit session: DBSession = autoSession): PblAdrDistrict = {
    val generatedKey = withSQL {
      insert.into(PblAdrDistrict).namedValues(
        column.nazv -> nazv,
        column.provincecode -> provincecode,
        column.maincity -> maincity,
        column.newId -> newId
      )
    }.updateAndReturnGeneratedKey.apply()

    PblAdrDistrict(
      code = generatedKey.toInt,
      nazv = nazv,
      provincecode = provincecode,
      maincity = maincity,
      newId = newId)
  }

  def batchInsert(entities: collection.Seq[PblAdrDistrict])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'nazv -> entity.nazv,
        'provincecode -> entity.provincecode,
        'maincity -> entity.maincity,
        'newId -> entity.newId))
    SQL("""insert into pbl_adr_district(
      nazv,
      provincecode,
      maincity,
      new_id
    ) values (
      {nazv},
      {provincecode},
      {maincity},
      {newId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblAdrDistrict)(implicit session: DBSession = autoSession): PblAdrDistrict = {
    withSQL {
      update(PblAdrDistrict).set(
        column.code -> entity.code,
        column.nazv -> entity.nazv,
        column.provincecode -> entity.provincecode,
        column.maincity -> entity.maincity,
        column.newId -> entity.newId
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblAdrDistrict)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblAdrDistrict).where.eq(column.code, entity.code) }.update.apply()
  }

}
