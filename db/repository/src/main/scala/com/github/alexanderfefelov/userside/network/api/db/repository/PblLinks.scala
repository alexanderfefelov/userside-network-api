package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblLinks(
  code: Int,
  kat: Option[Int] = None,
  nazv: Option[String] = None,
  links: Option[String] = None,
  opis: Option[String] = None,
  isframe: Option[Short] = None,
  operAcc: Option[String] = None,
  sectionId: Option[Int] = None) {

  def save()(implicit session: DBSession = PblLinks.autoSession): PblLinks = PblLinks.save(this)(session)

  def destroy()(implicit session: DBSession = PblLinks.autoSession): Int = PblLinks.destroy(this)(session)

}


object PblLinks extends SQLSyntaxSupport[PblLinks] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_links"

  override val columns = Seq("code", "kat", "nazv", "links", "opis", "isframe", "oper_acc", "section_id")

  def apply(pl: SyntaxProvider[PblLinks])(rs: WrappedResultSet): PblLinks = autoConstruct(rs, pl)
  def apply(pl: ResultName[PblLinks])(rs: WrappedResultSet): PblLinks = autoConstruct(rs, pl)

  val pl = PblLinks.syntax("pl")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblLinks] = {
    withSQL {
      select.from(PblLinks as pl).where.eq(pl.code, code)
    }.map(PblLinks(pl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblLinks] = {
    withSQL(select.from(PblLinks as pl)).map(PblLinks(pl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblLinks as pl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblLinks] = {
    withSQL {
      select.from(PblLinks as pl).where.append(where)
    }.map(PblLinks(pl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblLinks] = {
    withSQL {
      select.from(PblLinks as pl).where.append(where)
    }.map(PblLinks(pl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblLinks as pl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    kat: Option[Int] = None,
    nazv: Option[String] = None,
    links: Option[String] = None,
    opis: Option[String] = None,
    isframe: Option[Short] = None,
    operAcc: Option[String] = None,
    sectionId: Option[Int] = None)(implicit session: DBSession = autoSession): PblLinks = {
    val generatedKey = withSQL {
      insert.into(PblLinks).namedValues(
        column.kat -> kat,
        column.nazv -> nazv,
        column.links -> links,
        column.opis -> opis,
        column.isframe -> isframe,
        column.operAcc -> operAcc,
        column.sectionId -> sectionId
      )
    }.updateAndReturnGeneratedKey.apply()

    PblLinks(
      code = generatedKey.toInt,
      kat = kat,
      nazv = nazv,
      links = links,
      opis = opis,
      isframe = isframe,
      operAcc = operAcc,
      sectionId = sectionId)
  }

  def batchInsert(entities: collection.Seq[PblLinks])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'kat -> entity.kat,
        'nazv -> entity.nazv,
        'links -> entity.links,
        'opis -> entity.opis,
        'isframe -> entity.isframe,
        'operAcc -> entity.operAcc,
        'sectionId -> entity.sectionId))
    SQL("""insert into pbl_links(
      kat,
      nazv,
      links,
      opis,
      isframe,
      oper_acc,
      section_id
    ) values (
      {kat},
      {nazv},
      {links},
      {opis},
      {isframe},
      {operAcc},
      {sectionId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblLinks)(implicit session: DBSession = autoSession): PblLinks = {
    withSQL {
      update(PblLinks).set(
        column.code -> entity.code,
        column.kat -> entity.kat,
        column.nazv -> entity.nazv,
        column.links -> entity.links,
        column.opis -> entity.opis,
        column.isframe -> entity.isframe,
        column.operAcc -> entity.operAcc,
        column.sectionId -> entity.sectionId
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblLinks)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblLinks).where.eq(column.code, entity.code) }.update.apply()
  }

}
