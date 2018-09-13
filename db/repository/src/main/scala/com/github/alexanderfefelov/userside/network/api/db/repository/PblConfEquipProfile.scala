package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfEquipProfile(
  code: Int,
  nazv: Option[String] = None,
  param: Option[String] = None,
  isport: Option[Short] = None) {

  def save()(implicit session: DBSession = PblConfEquipProfile.autoSession): PblConfEquipProfile = PblConfEquipProfile.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfEquipProfile.autoSession): Int = PblConfEquipProfile.destroy(this)(session)

}


object PblConfEquipProfile extends SQLSyntaxSupport[PblConfEquipProfile] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_equip_profile"

  override val columns = Seq("code", "nazv", "param", "isport")

  def apply(pcep: SyntaxProvider[PblConfEquipProfile])(rs: WrappedResultSet): PblConfEquipProfile = autoConstruct(rs, pcep)
  def apply(pcep: ResultName[PblConfEquipProfile])(rs: WrappedResultSet): PblConfEquipProfile = autoConstruct(rs, pcep)

  val pcep = PblConfEquipProfile.syntax("pcep")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfEquipProfile] = {
    withSQL {
      select.from(PblConfEquipProfile as pcep).where.eq(pcep.code, code)
    }.map(PblConfEquipProfile(pcep.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfEquipProfile] = {
    withSQL(select.from(PblConfEquipProfile as pcep)).map(PblConfEquipProfile(pcep.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfEquipProfile as pcep)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfEquipProfile] = {
    withSQL {
      select.from(PblConfEquipProfile as pcep).where.append(where)
    }.map(PblConfEquipProfile(pcep.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfEquipProfile] = {
    withSQL {
      select.from(PblConfEquipProfile as pcep).where.append(where)
    }.map(PblConfEquipProfile(pcep.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfEquipProfile as pcep).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    nazv: Option[String] = None,
    param: Option[String] = None,
    isport: Option[Short] = None)(implicit session: DBSession = autoSession): PblConfEquipProfile = {
    val generatedKey = withSQL {
      insert.into(PblConfEquipProfile).namedValues(
        column.nazv -> nazv,
        column.param -> param,
        column.isport -> isport
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfEquipProfile(
      code = generatedKey.toInt,
      nazv = nazv,
      param = param,
      isport = isport)
  }

  def batchInsert(entities: collection.Seq[PblConfEquipProfile])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'nazv -> entity.nazv,
        'param -> entity.param,
        'isport -> entity.isport))
    SQL("""insert into pbl_conf_equip_profile(
      nazv,
      param,
      isport
    ) values (
      {nazv},
      {param},
      {isport}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfEquipProfile)(implicit session: DBSession = autoSession): PblConfEquipProfile = {
    withSQL {
      update(PblConfEquipProfile).set(
        column.code -> entity.code,
        column.nazv -> entity.nazv,
        column.param -> entity.param,
        column.isport -> entity.isport
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfEquipProfile)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfEquipProfile).where.eq(column.code, entity.code) }.update.apply()
  }

}
