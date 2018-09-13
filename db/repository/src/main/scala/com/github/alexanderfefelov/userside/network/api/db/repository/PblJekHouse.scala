package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblJekHouse(
  code: Int,
  jekcode: Option[Int] = None,
  housecode: Option[Int] = None) {

  def save()(implicit session: DBSession = PblJekHouse.autoSession): PblJekHouse = PblJekHouse.save(this)(session)

  def destroy()(implicit session: DBSession = PblJekHouse.autoSession): Int = PblJekHouse.destroy(this)(session)

}


object PblJekHouse extends SQLSyntaxSupport[PblJekHouse] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_jek_house"

  override val columns = Seq("code", "jekcode", "housecode")

  def apply(pjh: SyntaxProvider[PblJekHouse])(rs: WrappedResultSet): PblJekHouse = autoConstruct(rs, pjh)
  def apply(pjh: ResultName[PblJekHouse])(rs: WrappedResultSet): PblJekHouse = autoConstruct(rs, pjh)

  val pjh = PblJekHouse.syntax("pjh")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblJekHouse] = {
    withSQL {
      select.from(PblJekHouse as pjh).where.eq(pjh.code, code)
    }.map(PblJekHouse(pjh.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblJekHouse] = {
    withSQL(select.from(PblJekHouse as pjh)).map(PblJekHouse(pjh.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblJekHouse as pjh)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblJekHouse] = {
    withSQL {
      select.from(PblJekHouse as pjh).where.append(where)
    }.map(PblJekHouse(pjh.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblJekHouse] = {
    withSQL {
      select.from(PblJekHouse as pjh).where.append(where)
    }.map(PblJekHouse(pjh.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblJekHouse as pjh).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    jekcode: Option[Int] = None,
    housecode: Option[Int] = None)(implicit session: DBSession = autoSession): PblJekHouse = {
    val generatedKey = withSQL {
      insert.into(PblJekHouse).namedValues(
        column.jekcode -> jekcode,
        column.housecode -> housecode
      )
    }.updateAndReturnGeneratedKey.apply()

    PblJekHouse(
      code = generatedKey.toInt,
      jekcode = jekcode,
      housecode = housecode)
  }

  def batchInsert(entities: collection.Seq[PblJekHouse])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'jekcode -> entity.jekcode,
        'housecode -> entity.housecode))
    SQL("""insert into pbl_jek_house(
      jekcode,
      housecode
    ) values (
      {jekcode},
      {housecode}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblJekHouse)(implicit session: DBSession = autoSession): PblJekHouse = {
    withSQL {
      update(PblJekHouse).set(
        column.code -> entity.code,
        column.jekcode -> entity.jekcode,
        column.housecode -> entity.housecode
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblJekHouse)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblJekHouse).where.eq(column.code, entity.code) }.update.apply()
  }

}
