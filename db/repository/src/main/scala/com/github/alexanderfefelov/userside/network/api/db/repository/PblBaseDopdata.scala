package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblBaseDopdata(
  code: Int,
  usercode: Option[Int] = None,
  datacode: Option[Int] = None,
  valuestr: Option[String] = None) {

  def save()(implicit session: DBSession = PblBaseDopdata.autoSession): PblBaseDopdata = PblBaseDopdata.save(this)(session)

  def destroy()(implicit session: DBSession = PblBaseDopdata.autoSession): Int = PblBaseDopdata.destroy(this)(session)

}


object PblBaseDopdata extends SQLSyntaxSupport[PblBaseDopdata] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_base_dopdata"

  override val columns = Seq("code", "usercode", "datacode", "valuestr")

  def apply(pbd: SyntaxProvider[PblBaseDopdata])(rs: WrappedResultSet): PblBaseDopdata = autoConstruct(rs, pbd)
  def apply(pbd: ResultName[PblBaseDopdata])(rs: WrappedResultSet): PblBaseDopdata = autoConstruct(rs, pbd)

  val pbd = PblBaseDopdata.syntax("pbd")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblBaseDopdata] = {
    withSQL {
      select.from(PblBaseDopdata as pbd).where.eq(pbd.code, code)
    }.map(PblBaseDopdata(pbd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblBaseDopdata] = {
    withSQL(select.from(PblBaseDopdata as pbd)).map(PblBaseDopdata(pbd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblBaseDopdata as pbd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblBaseDopdata] = {
    withSQL {
      select.from(PblBaseDopdata as pbd).where.append(where)
    }.map(PblBaseDopdata(pbd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblBaseDopdata] = {
    withSQL {
      select.from(PblBaseDopdata as pbd).where.append(where)
    }.map(PblBaseDopdata(pbd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblBaseDopdata as pbd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    usercode: Option[Int] = None,
    datacode: Option[Int] = None,
    valuestr: Option[String] = None)(implicit session: DBSession = autoSession): PblBaseDopdata = {
    val generatedKey = withSQL {
      insert.into(PblBaseDopdata).namedValues(
        column.usercode -> usercode,
        column.datacode -> datacode,
        column.valuestr -> valuestr
      )
    }.updateAndReturnGeneratedKey.apply()

    PblBaseDopdata(
      code = generatedKey.toInt,
      usercode = usercode,
      datacode = datacode,
      valuestr = valuestr)
  }

  def batchInsert(entities: collection.Seq[PblBaseDopdata])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'usercode -> entity.usercode,
        'datacode -> entity.datacode,
        'valuestr -> entity.valuestr))
    SQL("""insert into pbl_base_dopdata(
      usercode,
      datacode,
      valuestr
    ) values (
      {usercode},
      {datacode},
      {valuestr}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblBaseDopdata)(implicit session: DBSession = autoSession): PblBaseDopdata = {
    withSQL {
      update(PblBaseDopdata).set(
        column.code -> entity.code,
        column.usercode -> entity.usercode,
        column.datacode -> entity.datacode,
        column.valuestr -> entity.valuestr
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblBaseDopdata)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblBaseDopdata).where.eq(column.code, entity.code) }.update.apply()
  }

}
