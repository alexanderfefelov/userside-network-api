package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfLayer(
  code: Int,
  devtyper: Option[String] = None,
  nazv: Option[String] = None,
  property: Option[String] = None,
  onmapdef: Option[Short] = None) {

  def save()(implicit session: DBSession = PblConfLayer.autoSession): PblConfLayer = PblConfLayer.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfLayer.autoSession): Int = PblConfLayer.destroy(this)(session)

}


object PblConfLayer extends SQLSyntaxSupport[PblConfLayer] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_layer"

  override val columns = Seq("code", "devtyper", "nazv", "property", "onmapdef")

  def apply(pcl: SyntaxProvider[PblConfLayer])(rs: WrappedResultSet): PblConfLayer = autoConstruct(rs, pcl)
  def apply(pcl: ResultName[PblConfLayer])(rs: WrappedResultSet): PblConfLayer = autoConstruct(rs, pcl)

  val pcl = PblConfLayer.syntax("pcl")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfLayer] = {
    withSQL {
      select.from(PblConfLayer as pcl).where.eq(pcl.code, code)
    }.map(PblConfLayer(pcl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfLayer] = {
    withSQL(select.from(PblConfLayer as pcl)).map(PblConfLayer(pcl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfLayer as pcl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfLayer] = {
    withSQL {
      select.from(PblConfLayer as pcl).where.append(where)
    }.map(PblConfLayer(pcl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfLayer] = {
    withSQL {
      select.from(PblConfLayer as pcl).where.append(where)
    }.map(PblConfLayer(pcl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfLayer as pcl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    devtyper: Option[String] = None,
    nazv: Option[String] = None,
    property: Option[String] = None,
    onmapdef: Option[Short] = None)(implicit session: DBSession = autoSession): PblConfLayer = {
    val generatedKey = withSQL {
      insert.into(PblConfLayer).namedValues(
        column.devtyper -> devtyper,
        column.nazv -> nazv,
        column.property -> property,
        column.onmapdef -> onmapdef
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfLayer(
      code = generatedKey.toInt,
      devtyper = devtyper,
      nazv = nazv,
      property = property,
      onmapdef = onmapdef)
  }

  def batchInsert(entities: collection.Seq[PblConfLayer])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'devtyper -> entity.devtyper,
        'nazv -> entity.nazv,
        'property -> entity.property,
        'onmapdef -> entity.onmapdef))
    SQL("""insert into pbl_conf_layer(
      devtyper,
      nazv,
      property,
      onmapdef
    ) values (
      {devtyper},
      {nazv},
      {property},
      {onmapdef}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfLayer)(implicit session: DBSession = autoSession): PblConfLayer = {
    withSQL {
      update(PblConfLayer).set(
        column.code -> entity.code,
        column.devtyper -> entity.devtyper,
        column.nazv -> entity.nazv,
        column.property -> entity.property,
        column.onmapdef -> entity.onmapdef
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfLayer)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfLayer).where.eq(column.code, entity.code) }.update.apply()
  }

}
