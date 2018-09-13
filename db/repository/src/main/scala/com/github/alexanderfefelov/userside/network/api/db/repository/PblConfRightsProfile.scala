package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfRightsProfile(
  code: Int,
  nazv: Option[String] = None,
  accF: Option[String] = None) {

  def save()(implicit session: DBSession = PblConfRightsProfile.autoSession): PblConfRightsProfile = PblConfRightsProfile.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfRightsProfile.autoSession): Int = PblConfRightsProfile.destroy(this)(session)

}


object PblConfRightsProfile extends SQLSyntaxSupport[PblConfRightsProfile] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_rights_profile"

  override val columns = Seq("code", "nazv", "acc_f")

  def apply(pcrp: SyntaxProvider[PblConfRightsProfile])(rs: WrappedResultSet): PblConfRightsProfile = autoConstruct(rs, pcrp)
  def apply(pcrp: ResultName[PblConfRightsProfile])(rs: WrappedResultSet): PblConfRightsProfile = autoConstruct(rs, pcrp)

  val pcrp = PblConfRightsProfile.syntax("pcrp")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfRightsProfile] = {
    withSQL {
      select.from(PblConfRightsProfile as pcrp).where.eq(pcrp.code, code)
    }.map(PblConfRightsProfile(pcrp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfRightsProfile] = {
    withSQL(select.from(PblConfRightsProfile as pcrp)).map(PblConfRightsProfile(pcrp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfRightsProfile as pcrp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfRightsProfile] = {
    withSQL {
      select.from(PblConfRightsProfile as pcrp).where.append(where)
    }.map(PblConfRightsProfile(pcrp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfRightsProfile] = {
    withSQL {
      select.from(PblConfRightsProfile as pcrp).where.append(where)
    }.map(PblConfRightsProfile(pcrp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfRightsProfile as pcrp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    nazv: Option[String] = None,
    accF: Option[String] = None)(implicit session: DBSession = autoSession): PblConfRightsProfile = {
    val generatedKey = withSQL {
      insert.into(PblConfRightsProfile).namedValues(
        column.nazv -> nazv,
        column.accF -> accF
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfRightsProfile(
      code = generatedKey.toInt,
      nazv = nazv,
      accF = accF)
  }

  def batchInsert(entities: collection.Seq[PblConfRightsProfile])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'nazv -> entity.nazv,
        'accF -> entity.accF))
    SQL("""insert into pbl_conf_rights_profile(
      nazv,
      acc_f
    ) values (
      {nazv},
      {accF}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfRightsProfile)(implicit session: DBSession = autoSession): PblConfRightsProfile = {
    withSQL {
      update(PblConfRightsProfile).set(
        column.code -> entity.code,
        column.nazv -> entity.nazv,
        column.accF -> entity.accF
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfRightsProfile)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfRightsProfile).where.eq(column.code, entity.code) }.update.apply()
  }

}
