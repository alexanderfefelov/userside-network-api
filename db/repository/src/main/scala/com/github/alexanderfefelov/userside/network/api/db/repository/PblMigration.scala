package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblMigration(
  version: String,
  applyTime: Option[Int] = None) {

  def save()(implicit session: DBSession = PblMigration.autoSession): PblMigration = PblMigration.save(this)(session)

  def destroy()(implicit session: DBSession = PblMigration.autoSession): Int = PblMigration.destroy(this)(session)

}


object PblMigration extends SQLSyntaxSupport[PblMigration] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_migration"

  override val columns = Seq("version", "apply_time")

  def apply(pm: SyntaxProvider[PblMigration])(rs: WrappedResultSet): PblMigration = autoConstruct(rs, pm)
  def apply(pm: ResultName[PblMigration])(rs: WrappedResultSet): PblMigration = autoConstruct(rs, pm)

  val pm = PblMigration.syntax("pm")

  override val autoSession = AutoSession

  def find(version: String)(implicit session: DBSession = autoSession): Option[PblMigration] = {
    withSQL {
      select.from(PblMigration as pm).where.eq(pm.version, version)
    }.map(PblMigration(pm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblMigration] = {
    withSQL(select.from(PblMigration as pm)).map(PblMigration(pm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblMigration as pm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblMigration] = {
    withSQL {
      select.from(PblMigration as pm).where.append(where)
    }.map(PblMigration(pm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblMigration] = {
    withSQL {
      select.from(PblMigration as pm).where.append(where)
    }.map(PblMigration(pm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblMigration as pm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    version: String,
    applyTime: Option[Int] = None)(implicit session: DBSession = autoSession): PblMigration = {
    withSQL {
      insert.into(PblMigration).namedValues(
        column.version -> version,
        column.applyTime -> applyTime
      )
    }.update.apply()

    PblMigration(
      version = version,
      applyTime = applyTime)
  }

  def batchInsert(entities: collection.Seq[PblMigration])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'version -> entity.version,
        'applyTime -> entity.applyTime))
    SQL("""insert into pbl_migration(
      version,
      apply_time
    ) values (
      {version},
      {applyTime}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblMigration)(implicit session: DBSession = autoSession): PblMigration = {
    withSQL {
      update(PblMigration).set(
        column.version -> entity.version,
        column.applyTime -> entity.applyTime
      ).where.eq(column.version, entity.version)
    }.update.apply()
    entity
  }

  def destroy(entity: PblMigration)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblMigration).where.eq(column.version, entity.version) }.update.apply()
  }

}
