package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfSwitchPass(
  code: Int,
  basecode: Option[Int] = None,
  comLogin: Option[String] = None,
  comPass: Option[String] = None) {

  def save()(implicit session: DBSession = PblConfSwitchPass.autoSession): PblConfSwitchPass = PblConfSwitchPass.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfSwitchPass.autoSession): Int = PblConfSwitchPass.destroy(this)(session)

}


object PblConfSwitchPass extends SQLSyntaxSupport[PblConfSwitchPass] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_switch_pass"

  override val columns = Seq("code", "basecode", "com_login", "com_pass")

  def apply(pcsp: SyntaxProvider[PblConfSwitchPass])(rs: WrappedResultSet): PblConfSwitchPass = autoConstruct(rs, pcsp)
  def apply(pcsp: ResultName[PblConfSwitchPass])(rs: WrappedResultSet): PblConfSwitchPass = autoConstruct(rs, pcsp)

  val pcsp = PblConfSwitchPass.syntax("pcsp")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfSwitchPass] = {
    withSQL {
      select.from(PblConfSwitchPass as pcsp).where.eq(pcsp.code, code)
    }.map(PblConfSwitchPass(pcsp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfSwitchPass] = {
    withSQL(select.from(PblConfSwitchPass as pcsp)).map(PblConfSwitchPass(pcsp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfSwitchPass as pcsp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfSwitchPass] = {
    withSQL {
      select.from(PblConfSwitchPass as pcsp).where.append(where)
    }.map(PblConfSwitchPass(pcsp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfSwitchPass] = {
    withSQL {
      select.from(PblConfSwitchPass as pcsp).where.append(where)
    }.map(PblConfSwitchPass(pcsp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfSwitchPass as pcsp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    basecode: Option[Int] = None,
    comLogin: Option[String] = None,
    comPass: Option[String] = None)(implicit session: DBSession = autoSession): PblConfSwitchPass = {
    val generatedKey = withSQL {
      insert.into(PblConfSwitchPass).namedValues(
        column.basecode -> basecode,
        column.comLogin -> comLogin,
        column.comPass -> comPass
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfSwitchPass(
      code = generatedKey.toInt,
      basecode = basecode,
      comLogin = comLogin,
      comPass = comPass)
  }

  def batchInsert(entities: collection.Seq[PblConfSwitchPass])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'basecode -> entity.basecode,
        'comLogin -> entity.comLogin,
        'comPass -> entity.comPass))
    SQL("""insert into pbl_conf_switch_pass(
      basecode,
      com_login,
      com_pass
    ) values (
      {basecode},
      {comLogin},
      {comPass}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfSwitchPass)(implicit session: DBSession = autoSession): PblConfSwitchPass = {
    withSQL {
      update(PblConfSwitchPass).set(
        column.code -> entity.code,
        column.basecode -> entity.basecode,
        column.comLogin -> entity.comLogin,
        column.comPass -> entity.comPass
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfSwitchPass)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfSwitchPass).where.eq(column.code, entity.code) }.update.apply()
  }

}
