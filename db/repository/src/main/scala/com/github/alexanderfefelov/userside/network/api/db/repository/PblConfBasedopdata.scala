package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfBasedopdata(
  code: Int,
  nazv: Option[String] = None,
  typer: Option[Short] = None,
  lenfield: Option[Short] = None,
  maxlenfield: Option[Short] = None,
  position: Option[Short] = None,
  inuser: Option[Short] = None,
  valuemas: Option[String] = None) {

  def save()(implicit session: DBSession = PblConfBasedopdata.autoSession): PblConfBasedopdata = PblConfBasedopdata.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfBasedopdata.autoSession): Int = PblConfBasedopdata.destroy(this)(session)

}


object PblConfBasedopdata extends SQLSyntaxSupport[PblConfBasedopdata] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_basedopdata"

  override val columns = Seq("code", "nazv", "typer", "lenfield", "maxlenfield", "position", "inuser", "valuemas")

  def apply(pcb: SyntaxProvider[PblConfBasedopdata])(rs: WrappedResultSet): PblConfBasedopdata = autoConstruct(rs, pcb)
  def apply(pcb: ResultName[PblConfBasedopdata])(rs: WrappedResultSet): PblConfBasedopdata = autoConstruct(rs, pcb)

  val pcb = PblConfBasedopdata.syntax("pcb")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfBasedopdata] = {
    withSQL {
      select.from(PblConfBasedopdata as pcb).where.eq(pcb.code, code)
    }.map(PblConfBasedopdata(pcb.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfBasedopdata] = {
    withSQL(select.from(PblConfBasedopdata as pcb)).map(PblConfBasedopdata(pcb.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfBasedopdata as pcb)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfBasedopdata] = {
    withSQL {
      select.from(PblConfBasedopdata as pcb).where.append(where)
    }.map(PblConfBasedopdata(pcb.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfBasedopdata] = {
    withSQL {
      select.from(PblConfBasedopdata as pcb).where.append(where)
    }.map(PblConfBasedopdata(pcb.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfBasedopdata as pcb).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    nazv: Option[String] = None,
    typer: Option[Short] = None,
    lenfield: Option[Short] = None,
    maxlenfield: Option[Short] = None,
    position: Option[Short] = None,
    inuser: Option[Short] = None,
    valuemas: Option[String] = None)(implicit session: DBSession = autoSession): PblConfBasedopdata = {
    val generatedKey = withSQL {
      insert.into(PblConfBasedopdata).namedValues(
        column.nazv -> nazv,
        column.typer -> typer,
        column.lenfield -> lenfield,
        column.maxlenfield -> maxlenfield,
        column.position -> position,
        column.inuser -> inuser,
        column.valuemas -> valuemas
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfBasedopdata(
      code = generatedKey.toInt,
      nazv = nazv,
      typer = typer,
      lenfield = lenfield,
      maxlenfield = maxlenfield,
      position = position,
      inuser = inuser,
      valuemas = valuemas)
  }

  def batchInsert(entities: collection.Seq[PblConfBasedopdata])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'nazv -> entity.nazv,
        'typer -> entity.typer,
        'lenfield -> entity.lenfield,
        'maxlenfield -> entity.maxlenfield,
        'position -> entity.position,
        'inuser -> entity.inuser,
        'valuemas -> entity.valuemas))
    SQL("""insert into pbl_conf_basedopdata(
      nazv,
      typer,
      lenfield,
      maxlenfield,
      position,
      inuser,
      valuemas
    ) values (
      {nazv},
      {typer},
      {lenfield},
      {maxlenfield},
      {position},
      {inuser},
      {valuemas}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfBasedopdata)(implicit session: DBSession = autoSession): PblConfBasedopdata = {
    withSQL {
      update(PblConfBasedopdata).set(
        column.code -> entity.code,
        column.nazv -> entity.nazv,
        column.typer -> entity.typer,
        column.lenfield -> entity.lenfield,
        column.maxlenfield -> entity.maxlenfield,
        column.position -> entity.position,
        column.inuser -> entity.inuser,
        column.valuemas -> entity.valuemas
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfBasedopdata)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfBasedopdata).where.eq(column.code, entity.code) }.update.apply()
  }

}
