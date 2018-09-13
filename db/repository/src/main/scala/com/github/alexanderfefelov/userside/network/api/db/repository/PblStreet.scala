package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblStreet(
  code: Int,
  street: Option[String] = None,
  isdel: Option[Short] = None,
  citycode: Option[Int] = None,
  areacode: Option[Int] = None,
  newId: Option[Int] = None) {

  def save()(implicit session: DBSession = PblStreet.autoSession): PblStreet = PblStreet.save(this)(session)

  def destroy()(implicit session: DBSession = PblStreet.autoSession): Int = PblStreet.destroy(this)(session)

}


object PblStreet extends SQLSyntaxSupport[PblStreet] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_street"

  override val columns = Seq("code", "street", "isdel", "citycode", "areacode", "new_id")

  def apply(ps: SyntaxProvider[PblStreet])(rs: WrappedResultSet): PblStreet = autoConstruct(rs, ps)
  def apply(ps: ResultName[PblStreet])(rs: WrappedResultSet): PblStreet = autoConstruct(rs, ps)

  val ps = PblStreet.syntax("ps")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblStreet] = {
    withSQL {
      select.from(PblStreet as ps).where.eq(ps.code, code)
    }.map(PblStreet(ps.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblStreet] = {
    withSQL(select.from(PblStreet as ps)).map(PblStreet(ps.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblStreet as ps)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblStreet] = {
    withSQL {
      select.from(PblStreet as ps).where.append(where)
    }.map(PblStreet(ps.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblStreet] = {
    withSQL {
      select.from(PblStreet as ps).where.append(where)
    }.map(PblStreet(ps.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblStreet as ps).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    street: Option[String] = None,
    isdel: Option[Short] = None,
    citycode: Option[Int] = None,
    areacode: Option[Int] = None,
    newId: Option[Int] = None)(implicit session: DBSession = autoSession): PblStreet = {
    val generatedKey = withSQL {
      insert.into(PblStreet).namedValues(
        column.street -> street,
        column.isdel -> isdel,
        column.citycode -> citycode,
        column.areacode -> areacode,
        column.newId -> newId
      )
    }.updateAndReturnGeneratedKey.apply()

    PblStreet(
      code = generatedKey.toInt,
      street = street,
      isdel = isdel,
      citycode = citycode,
      areacode = areacode,
      newId = newId)
  }

  def batchInsert(entities: collection.Seq[PblStreet])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'street -> entity.street,
        'isdel -> entity.isdel,
        'citycode -> entity.citycode,
        'areacode -> entity.areacode,
        'newId -> entity.newId))
    SQL("""insert into pbl_street(
      street,
      isdel,
      citycode,
      areacode,
      new_id
    ) values (
      {street},
      {isdel},
      {citycode},
      {areacode},
      {newId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblStreet)(implicit session: DBSession = autoSession): PblStreet = {
    withSQL {
      update(PblStreet).set(
        column.code -> entity.code,
        column.street -> entity.street,
        column.isdel -> entity.isdel,
        column.citycode -> entity.citycode,
        column.areacode -> entity.areacode,
        column.newId -> entity.newId
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblStreet)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblStreet).where.eq(column.code, entity.code) }.update.apply()
  }

}
