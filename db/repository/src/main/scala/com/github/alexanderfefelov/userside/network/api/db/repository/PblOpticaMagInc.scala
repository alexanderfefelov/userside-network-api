package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblOpticaMagInc(
  code: Int,
  magcode: Option[Int] = None,
  volscode: Option[Int] = None) {

  def save()(implicit session: DBSession = PblOpticaMagInc.autoSession): PblOpticaMagInc = PblOpticaMagInc.save(this)(session)

  def destroy()(implicit session: DBSession = PblOpticaMagInc.autoSession): Int = PblOpticaMagInc.destroy(this)(session)

}


object PblOpticaMagInc extends SQLSyntaxSupport[PblOpticaMagInc] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_optica_mag_inc"

  override val columns = Seq("code", "magcode", "volscode")

  def apply(pomi: SyntaxProvider[PblOpticaMagInc])(rs: WrappedResultSet): PblOpticaMagInc = autoConstruct(rs, pomi)
  def apply(pomi: ResultName[PblOpticaMagInc])(rs: WrappedResultSet): PblOpticaMagInc = autoConstruct(rs, pomi)

  val pomi = PblOpticaMagInc.syntax("pomi")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblOpticaMagInc] = {
    withSQL {
      select.from(PblOpticaMagInc as pomi).where.eq(pomi.code, code)
    }.map(PblOpticaMagInc(pomi.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblOpticaMagInc] = {
    withSQL(select.from(PblOpticaMagInc as pomi)).map(PblOpticaMagInc(pomi.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblOpticaMagInc as pomi)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblOpticaMagInc] = {
    withSQL {
      select.from(PblOpticaMagInc as pomi).where.append(where)
    }.map(PblOpticaMagInc(pomi.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblOpticaMagInc] = {
    withSQL {
      select.from(PblOpticaMagInc as pomi).where.append(where)
    }.map(PblOpticaMagInc(pomi.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblOpticaMagInc as pomi).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    magcode: Option[Int] = None,
    volscode: Option[Int] = None)(implicit session: DBSession = autoSession): PblOpticaMagInc = {
    val generatedKey = withSQL {
      insert.into(PblOpticaMagInc).namedValues(
        column.magcode -> magcode,
        column.volscode -> volscode
      )
    }.updateAndReturnGeneratedKey.apply()

    PblOpticaMagInc(
      code = generatedKey.toInt,
      magcode = magcode,
      volscode = volscode)
  }

  def batchInsert(entities: collection.Seq[PblOpticaMagInc])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'magcode -> entity.magcode,
        'volscode -> entity.volscode))
    SQL("""insert into pbl_optica_mag_inc(
      magcode,
      volscode
    ) values (
      {magcode},
      {volscode}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblOpticaMagInc)(implicit session: DBSession = autoSession): PblOpticaMagInc = {
    withSQL {
      update(PblOpticaMagInc).set(
        column.code -> entity.code,
        column.magcode -> entity.magcode,
        column.volscode -> entity.volscode
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblOpticaMagInc)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblOpticaMagInc).where.eq(column.code, entity.code) }.update.apply()
  }

}
