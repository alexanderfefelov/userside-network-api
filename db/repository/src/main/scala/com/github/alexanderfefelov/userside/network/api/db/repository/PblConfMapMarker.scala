package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfMapMarker(
  code: Int,
  nazv: Option[String] = None) {

  def save()(implicit session: DBSession = PblConfMapMarker.autoSession): PblConfMapMarker = PblConfMapMarker.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfMapMarker.autoSession): Int = PblConfMapMarker.destroy(this)(session)

}


object PblConfMapMarker extends SQLSyntaxSupport[PblConfMapMarker] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_map_marker"

  override val columns = Seq("code", "nazv")

  def apply(pcmm: SyntaxProvider[PblConfMapMarker])(rs: WrappedResultSet): PblConfMapMarker = autoConstruct(rs, pcmm)
  def apply(pcmm: ResultName[PblConfMapMarker])(rs: WrappedResultSet): PblConfMapMarker = autoConstruct(rs, pcmm)

  val pcmm = PblConfMapMarker.syntax("pcmm")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfMapMarker] = {
    withSQL {
      select.from(PblConfMapMarker as pcmm).where.eq(pcmm.code, code)
    }.map(PblConfMapMarker(pcmm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfMapMarker] = {
    withSQL(select.from(PblConfMapMarker as pcmm)).map(PblConfMapMarker(pcmm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfMapMarker as pcmm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfMapMarker] = {
    withSQL {
      select.from(PblConfMapMarker as pcmm).where.append(where)
    }.map(PblConfMapMarker(pcmm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfMapMarker] = {
    withSQL {
      select.from(PblConfMapMarker as pcmm).where.append(where)
    }.map(PblConfMapMarker(pcmm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfMapMarker as pcmm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    nazv: Option[String] = None)(implicit session: DBSession = autoSession): PblConfMapMarker = {
    val generatedKey = withSQL {
      insert.into(PblConfMapMarker).namedValues(
        column.nazv -> nazv
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfMapMarker(
      code = generatedKey.toInt,
      nazv = nazv)
  }

  def batchInsert(entities: collection.Seq[PblConfMapMarker])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'nazv -> entity.nazv))
    SQL("""insert into pbl_conf_map_marker(
      nazv
    ) values (
      {nazv}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfMapMarker)(implicit session: DBSession = autoSession): PblConfMapMarker = {
    withSQL {
      update(PblConfMapMarker).set(
        column.code -> entity.code,
        column.nazv -> entity.nazv
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfMapMarker)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfMapMarker).where.eq(column.code, entity.code) }.update.apply()
  }

}
