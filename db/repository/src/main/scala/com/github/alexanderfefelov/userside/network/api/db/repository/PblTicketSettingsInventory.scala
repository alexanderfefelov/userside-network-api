package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblTicketSettingsInventory(
  id: Int,
  ticketTypeId: Option[Int] = None,
  data: Option[String] = None) {

  def save()(implicit session: DBSession = PblTicketSettingsInventory.autoSession): PblTicketSettingsInventory = PblTicketSettingsInventory.save(this)(session)

  def destroy()(implicit session: DBSession = PblTicketSettingsInventory.autoSession): Int = PblTicketSettingsInventory.destroy(this)(session)

}


object PblTicketSettingsInventory extends SQLSyntaxSupport[PblTicketSettingsInventory] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_ticket_settings_inventory"

  override val columns = Seq("id", "ticket_type_id", "data")

  def apply(ptsi: SyntaxProvider[PblTicketSettingsInventory])(rs: WrappedResultSet): PblTicketSettingsInventory = autoConstruct(rs, ptsi)
  def apply(ptsi: ResultName[PblTicketSettingsInventory])(rs: WrappedResultSet): PblTicketSettingsInventory = autoConstruct(rs, ptsi)

  val ptsi = PblTicketSettingsInventory.syntax("ptsi")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PblTicketSettingsInventory] = {
    withSQL {
      select.from(PblTicketSettingsInventory as ptsi).where.eq(ptsi.id, id)
    }.map(PblTicketSettingsInventory(ptsi.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblTicketSettingsInventory] = {
    withSQL(select.from(PblTicketSettingsInventory as ptsi)).map(PblTicketSettingsInventory(ptsi.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblTicketSettingsInventory as ptsi)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblTicketSettingsInventory] = {
    withSQL {
      select.from(PblTicketSettingsInventory as ptsi).where.append(where)
    }.map(PblTicketSettingsInventory(ptsi.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblTicketSettingsInventory] = {
    withSQL {
      select.from(PblTicketSettingsInventory as ptsi).where.append(where)
    }.map(PblTicketSettingsInventory(ptsi.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblTicketSettingsInventory as ptsi).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    ticketTypeId: Option[Int] = None,
    data: Option[String] = None)(implicit session: DBSession = autoSession): PblTicketSettingsInventory = {
    val generatedKey = withSQL {
      insert.into(PblTicketSettingsInventory).namedValues(
        column.ticketTypeId -> ticketTypeId,
        column.data -> data
      )
    }.updateAndReturnGeneratedKey.apply()

    PblTicketSettingsInventory(
      id = generatedKey.toInt,
      ticketTypeId = ticketTypeId,
      data = data)
  }

  def batchInsert(entities: collection.Seq[PblTicketSettingsInventory])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'ticketTypeId -> entity.ticketTypeId,
        'data -> entity.data))
    SQL("""insert into pbl_ticket_settings_inventory(
      ticket_type_id,
      data
    ) values (
      {ticketTypeId},
      {data}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblTicketSettingsInventory)(implicit session: DBSession = autoSession): PblTicketSettingsInventory = {
    withSQL {
      update(PblTicketSettingsInventory).set(
        column.id -> entity.id,
        column.ticketTypeId -> entity.ticketTypeId,
        column.data -> entity.data
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PblTicketSettingsInventory)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblTicketSettingsInventory).where.eq(column.id, entity.id) }.update.apply()
  }

}
