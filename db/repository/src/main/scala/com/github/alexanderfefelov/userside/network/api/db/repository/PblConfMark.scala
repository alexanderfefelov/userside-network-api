package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfMark(
  code: Int,
  nazv: Option[String] = None,
  color: Option[String] = None) {

  def save()(implicit session: DBSession = PblConfMark.autoSession): PblConfMark = PblConfMark.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfMark.autoSession): Int = PblConfMark.destroy(this)(session)

}


object PblConfMark extends SQLSyntaxSupport[PblConfMark] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_mark"

  override val columns = Seq("code", "nazv", "color")

  def apply(pcm: SyntaxProvider[PblConfMark])(rs: WrappedResultSet): PblConfMark = autoConstruct(rs, pcm)
  def apply(pcm: ResultName[PblConfMark])(rs: WrappedResultSet): PblConfMark = autoConstruct(rs, pcm)

  val pcm = PblConfMark.syntax("pcm")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfMark] = {
    withSQL {
      select.from(PblConfMark as pcm).where.eq(pcm.code, code)
    }.map(PblConfMark(pcm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfMark] = {
    withSQL(select.from(PblConfMark as pcm)).map(PblConfMark(pcm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfMark as pcm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfMark] = {
    withSQL {
      select.from(PblConfMark as pcm).where.append(where)
    }.map(PblConfMark(pcm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfMark] = {
    withSQL {
      select.from(PblConfMark as pcm).where.append(where)
    }.map(PblConfMark(pcm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfMark as pcm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    nazv: Option[String] = None,
    color: Option[String] = None)(implicit session: DBSession = autoSession): PblConfMark = {
    val generatedKey = withSQL {
      insert.into(PblConfMark).namedValues(
        column.nazv -> nazv,
        column.color -> color
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfMark(
      code = generatedKey.toInt,
      nazv = nazv,
      color = color)
  }

  def batchInsert(entities: collection.Seq[PblConfMark])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'nazv -> entity.nazv,
        'color -> entity.color))
    SQL("""insert into pbl_conf_mark(
      nazv,
      color
    ) values (
      {nazv},
      {color}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfMark)(implicit session: DBSession = autoSession): PblConfMark = {
    withSQL {
      update(PblConfMark).set(
        column.code -> entity.code,
        column.nazv -> entity.nazv,
        column.color -> entity.color
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfMark)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfMark).where.eq(column.code, entity.code) }.update.apply()
  }

}
