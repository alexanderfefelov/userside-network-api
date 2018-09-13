package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblSwitchStack(
  code: Int,
  devcode: Option[Int] = None,
  portcode: Option[Long] = None,
  stackno: Option[Short] = None,
  portno: Option[Short] = None,
  devtyper: Option[Short] = None) {

  def save()(implicit session: DBSession = PblSwitchStack.autoSession): PblSwitchStack = PblSwitchStack.save(this)(session)

  def destroy()(implicit session: DBSession = PblSwitchStack.autoSession): Int = PblSwitchStack.destroy(this)(session)

}


object PblSwitchStack extends SQLSyntaxSupport[PblSwitchStack] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_switch_stack"

  override val columns = Seq("code", "devcode", "portcode", "stackno", "portno", "devtyper")

  def apply(pss: SyntaxProvider[PblSwitchStack])(rs: WrappedResultSet): PblSwitchStack = autoConstruct(rs, pss)
  def apply(pss: ResultName[PblSwitchStack])(rs: WrappedResultSet): PblSwitchStack = autoConstruct(rs, pss)

  val pss = PblSwitchStack.syntax("pss")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblSwitchStack] = {
    withSQL {
      select.from(PblSwitchStack as pss).where.eq(pss.code, code)
    }.map(PblSwitchStack(pss.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblSwitchStack] = {
    withSQL(select.from(PblSwitchStack as pss)).map(PblSwitchStack(pss.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblSwitchStack as pss)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblSwitchStack] = {
    withSQL {
      select.from(PblSwitchStack as pss).where.append(where)
    }.map(PblSwitchStack(pss.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblSwitchStack] = {
    withSQL {
      select.from(PblSwitchStack as pss).where.append(where)
    }.map(PblSwitchStack(pss.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblSwitchStack as pss).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    devcode: Option[Int] = None,
    portcode: Option[Long] = None,
    stackno: Option[Short] = None,
    portno: Option[Short] = None,
    devtyper: Option[Short] = None)(implicit session: DBSession = autoSession): PblSwitchStack = {
    val generatedKey = withSQL {
      insert.into(PblSwitchStack).namedValues(
        column.devcode -> devcode,
        column.portcode -> portcode,
        column.stackno -> stackno,
        column.portno -> portno,
        column.devtyper -> devtyper
      )
    }.updateAndReturnGeneratedKey.apply()

    PblSwitchStack(
      code = generatedKey.toInt,
      devcode = devcode,
      portcode = portcode,
      stackno = stackno,
      portno = portno,
      devtyper = devtyper)
  }

  def batchInsert(entities: collection.Seq[PblSwitchStack])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'devcode -> entity.devcode,
        'portcode -> entity.portcode,
        'stackno -> entity.stackno,
        'portno -> entity.portno,
        'devtyper -> entity.devtyper))
    SQL("""insert into pbl_switch_stack(
      devcode,
      portcode,
      stackno,
      portno,
      devtyper
    ) values (
      {devcode},
      {portcode},
      {stackno},
      {portno},
      {devtyper}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblSwitchStack)(implicit session: DBSession = autoSession): PblSwitchStack = {
    withSQL {
      update(PblSwitchStack).set(
        column.code -> entity.code,
        column.devcode -> entity.devcode,
        column.portcode -> entity.portcode,
        column.stackno -> entity.stackno,
        column.portno -> entity.portno,
        column.devtyper -> entity.devtyper
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblSwitchStack)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblSwitchStack).where.eq(column.code, entity.code) }.update.apply()
  }

}
