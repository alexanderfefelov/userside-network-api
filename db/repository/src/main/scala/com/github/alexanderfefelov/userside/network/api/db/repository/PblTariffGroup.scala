package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblTariffGroup(
  id: Int,
  name: Option[String] = None) {

  def save()(implicit session: DBSession = PblTariffGroup.autoSession): PblTariffGroup = PblTariffGroup.save(this)(session)

  def destroy()(implicit session: DBSession = PblTariffGroup.autoSession): Int = PblTariffGroup.destroy(this)(session)

}


object PblTariffGroup extends SQLSyntaxSupport[PblTariffGroup] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_tariff_group"

  override val columns = Seq("id", "name")

  def apply(ptg: SyntaxProvider[PblTariffGroup])(rs: WrappedResultSet): PblTariffGroup = autoConstruct(rs, ptg)
  def apply(ptg: ResultName[PblTariffGroup])(rs: WrappedResultSet): PblTariffGroup = autoConstruct(rs, ptg)

  val ptg = PblTariffGroup.syntax("ptg")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblTariffGroup] = {
    withSQL {
      select.from(PblTariffGroup as ptg).where.eq(ptg.id, id)
    }.map(PblTariffGroup(ptg.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblTariffGroup] = {
    withSQL(select.from(PblTariffGroup as ptg)).map(PblTariffGroup(ptg.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblTariffGroup as ptg)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblTariffGroup] = {
    withSQL {
      select.from(PblTariffGroup as ptg).where.append(where)
    }.map(PblTariffGroup(ptg.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblTariffGroup] = {
    withSQL {
      select.from(PblTariffGroup as ptg).where.append(where)
    }.map(PblTariffGroup(ptg.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblTariffGroup as ptg).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: Option[String] = None)(implicit session: DBSession = autoSession): PblTariffGroup = {
    val generatedKey = withSQL {
      insert.into(PblTariffGroup).namedValues(
        column.name -> name
      )
    }.updateAndReturnGeneratedKey.apply()

    PblTariffGroup(
      id = generatedKey.toInt,
      name = name)
  }

  def batchInsert(entities: collection.Seq[PblTariffGroup])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'name -> entity.name))
    SQL("""insert into pbl_tariff_group(
      name
    ) values (
      {name}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblTariffGroup)(implicit session: DBSession = autoSession): PblTariffGroup = {
    withSQL {
      update(PblTariffGroup).set(
        column.id -> entity.id,
        column.name -> entity.name
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblTariffGroup)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblTariffGroup).where.eq(column.id, entity.id) }.update.apply()
  }

}
