package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblOpticaVol(
  code: Int,
  opticacode: Option[Int] = None,
  volno: Option[Short] = None,
  volmod: Option[Short] = None,
  volcol: Option[Int] = None,
  position: Option[Int] = None,
  position2: Option[Int] = None,
  zatuh: Option[BigDecimal] = None,
  mark1: Option[String] = None,
  mark2: Option[String] = None) {

  def save()(implicit session: DBSession = PblOpticaVol.autoSession): PblOpticaVol = PblOpticaVol.save(this)(session)

  def destroy()(implicit session: DBSession = PblOpticaVol.autoSession): Int = PblOpticaVol.destroy(this)(session)

}


object PblOpticaVol extends SQLSyntaxSupport[PblOpticaVol] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_optica_vol"

  override val columns = Seq("code", "opticacode", "volno", "volmod", "volcol", "position", "position2", "zatuh", "mark1", "mark2")

  def apply(pov: SyntaxProvider[PblOpticaVol])(rs: WrappedResultSet): PblOpticaVol = autoConstruct(rs, pov)
  def apply(pov: ResultName[PblOpticaVol])(rs: WrappedResultSet): PblOpticaVol = autoConstruct(rs, pov)

  val pov = PblOpticaVol.syntax("pov")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblOpticaVol] = {
    withSQL {
      select.from(PblOpticaVol as pov).where.eq(pov.code, code)
    }.map(PblOpticaVol(pov.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblOpticaVol] = {
    withSQL(select.from(PblOpticaVol as pov)).map(PblOpticaVol(pov.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblOpticaVol as pov)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblOpticaVol] = {
    withSQL {
      select.from(PblOpticaVol as pov).where.append(where)
    }.map(PblOpticaVol(pov.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblOpticaVol] = {
    withSQL {
      select.from(PblOpticaVol as pov).where.append(where)
    }.map(PblOpticaVol(pov.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblOpticaVol as pov).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    opticacode: Option[Int] = None,
    volno: Option[Short] = None,
    volmod: Option[Short] = None,
    volcol: Option[Int] = None,
    position: Option[Int] = None,
    position2: Option[Int] = None,
    zatuh: Option[BigDecimal] = None,
    mark1: Option[String] = None,
    mark2: Option[String] = None)(implicit session: DBSession = autoSession): PblOpticaVol = {
    val generatedKey = withSQL {
      insert.into(PblOpticaVol).namedValues(
        column.opticacode -> opticacode,
        column.volno -> volno,
        column.volmod -> volmod,
        column.volcol -> volcol,
        column.position -> position,
        column.position2 -> position2,
        column.zatuh -> zatuh,
        column.mark1 -> mark1,
        column.mark2 -> mark2
      )
    }.updateAndReturnGeneratedKey.apply()

    PblOpticaVol(
      code = generatedKey.toInt,
      opticacode = opticacode,
      volno = volno,
      volmod = volmod,
      volcol = volcol,
      position = position,
      position2 = position2,
      zatuh = zatuh,
      mark1 = mark1,
      mark2 = mark2)
  }

  def batchInsert(entities: collection.Seq[PblOpticaVol])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'opticacode -> entity.opticacode,
        'volno -> entity.volno,
        'volmod -> entity.volmod,
        'volcol -> entity.volcol,
        'position -> entity.position,
        'position2 -> entity.position2,
        'zatuh -> entity.zatuh,
        'mark1 -> entity.mark1,
        'mark2 -> entity.mark2))
    SQL("""insert into pbl_optica_vol(
      opticacode,
      volno,
      volmod,
      volcol,
      position,
      position2,
      zatuh,
      mark1,
      mark2
    ) values (
      {opticacode},
      {volno},
      {volmod},
      {volcol},
      {position},
      {position2},
      {zatuh},
      {mark1},
      {mark2}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblOpticaVol)(implicit session: DBSession = autoSession): PblOpticaVol = {
    withSQL {
      update(PblOpticaVol).set(
        column.code -> entity.code,
        column.opticacode -> entity.opticacode,
        column.volno -> entity.volno,
        column.volmod -> entity.volmod,
        column.volcol -> entity.volcol,
        column.position -> entity.position,
        column.position2 -> entity.position2,
        column.zatuh -> entity.zatuh,
        column.mark1 -> entity.mark1,
        column.mark2 -> entity.mark2
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblOpticaVol)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblOpticaVol).where.eq(column.code, entity.code) }.update.apply()
  }

}
