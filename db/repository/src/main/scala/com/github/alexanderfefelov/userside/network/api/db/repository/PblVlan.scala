package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblVlan(
  code: Int,
  nazv: Option[String] = None,
  opis: Option[String] = None,
  equip: Option[String] = None,
  vlantag: Option[String] = None,
  vlantag2: Option[String] = None,
  vid: Option[Int] = None) {

  def save()(implicit session: DBSession = PblVlan.autoSession): PblVlan = PblVlan.save(this)(session)

  def destroy()(implicit session: DBSession = PblVlan.autoSession): Int = PblVlan.destroy(this)(session)

}


object PblVlan extends SQLSyntaxSupport[PblVlan] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_vlan"

  override val columns = Seq("code", "nazv", "opis", "equip", "vlantag", "vlantag2", "vid")

  def apply(pv: SyntaxProvider[PblVlan])(rs: WrappedResultSet): PblVlan = autoConstruct(rs, pv)
  def apply(pv: ResultName[PblVlan])(rs: WrappedResultSet): PblVlan = autoConstruct(rs, pv)

  val pv = PblVlan.syntax("pv")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblVlan] = {
    withSQL {
      select.from(PblVlan as pv).where.eq(pv.code, code)
    }.map(PblVlan(pv.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblVlan] = {
    withSQL(select.from(PblVlan as pv)).map(PblVlan(pv.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblVlan as pv)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblVlan] = {
    withSQL {
      select.from(PblVlan as pv).where.append(where)
    }.map(PblVlan(pv.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblVlan] = {
    withSQL {
      select.from(PblVlan as pv).where.append(where)
    }.map(PblVlan(pv.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblVlan as pv).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    nazv: Option[String] = None,
    opis: Option[String] = None,
    equip: Option[String] = None,
    vlantag: Option[String] = None,
    vlantag2: Option[String] = None,
    vid: Option[Int] = None)(implicit session: DBSession = autoSession): PblVlan = {
    val generatedKey = withSQL {
      insert.into(PblVlan).namedValues(
        column.nazv -> nazv,
        column.opis -> opis,
        column.equip -> equip,
        column.vlantag -> vlantag,
        column.vlantag2 -> vlantag2,
        column.vid -> vid
      )
    }.updateAndReturnGeneratedKey.apply()

    PblVlan(
      code = generatedKey.toInt,
      nazv = nazv,
      opis = opis,
      equip = equip,
      vlantag = vlantag,
      vlantag2 = vlantag2,
      vid = vid)
  }

  def batchInsert(entities: collection.Seq[PblVlan])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'nazv -> entity.nazv,
        'opis -> entity.opis,
        'equip -> entity.equip,
        'vlantag -> entity.vlantag,
        'vlantag2 -> entity.vlantag2,
        'vid -> entity.vid))
    SQL("""insert into pbl_vlan(
      nazv,
      opis,
      equip,
      vlantag,
      vlantag2,
      vid
    ) values (
      {nazv},
      {opis},
      {equip},
      {vlantag},
      {vlantag2},
      {vid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblVlan)(implicit session: DBSession = autoSession): PblVlan = {
    withSQL {
      update(PblVlan).set(
        column.code -> entity.code,
        column.nazv -> entity.nazv,
        column.opis -> entity.opis,
        column.equip -> entity.equip,
        column.vlantag -> entity.vlantag,
        column.vlantag2 -> entity.vlantag2,
        column.vid -> entity.vid
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblVlan)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblVlan).where.eq(column.code, entity.code) }.update.apply()
  }

}
