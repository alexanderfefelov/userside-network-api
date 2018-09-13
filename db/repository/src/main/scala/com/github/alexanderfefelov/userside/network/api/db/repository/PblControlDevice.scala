package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblControlDevice(
  code: Int,
  devtyper: Option[Short] = None,
  devcode: Option[Int] = None,
  profilecode: Option[Int] = None,
  whoreceive: Option[String] = None,
  ispolling: Option[Short] = None,
  customOidcode: Option[String] = None,
  isalert: Option[Short] = None,
  downcount: Option[Int] = None) {

  def save()(implicit session: DBSession = PblControlDevice.autoSession): PblControlDevice = PblControlDevice.save(this)(session)

  def destroy()(implicit session: DBSession = PblControlDevice.autoSession): Int = PblControlDevice.destroy(this)(session)

}


object PblControlDevice extends SQLSyntaxSupport[PblControlDevice] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_control_device"

  override val columns = Seq("code", "devtyper", "devcode", "profilecode", "whoreceive", "ispolling", "custom_oidcode", "isalert", "downcount")

  def apply(pcd: SyntaxProvider[PblControlDevice])(rs: WrappedResultSet): PblControlDevice = autoConstruct(rs, pcd)
  def apply(pcd: ResultName[PblControlDevice])(rs: WrappedResultSet): PblControlDevice = autoConstruct(rs, pcd)

  val pcd = PblControlDevice.syntax("pcd")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblControlDevice] = {
    withSQL {
      select.from(PblControlDevice as pcd).where.eq(pcd.code, code)
    }.map(PblControlDevice(pcd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblControlDevice] = {
    withSQL(select.from(PblControlDevice as pcd)).map(PblControlDevice(pcd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblControlDevice as pcd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblControlDevice] = {
    withSQL {
      select.from(PblControlDevice as pcd).where.append(where)
    }.map(PblControlDevice(pcd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblControlDevice] = {
    withSQL {
      select.from(PblControlDevice as pcd).where.append(where)
    }.map(PblControlDevice(pcd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblControlDevice as pcd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    devtyper: Option[Short] = None,
    devcode: Option[Int] = None,
    profilecode: Option[Int] = None,
    whoreceive: Option[String] = None,
    ispolling: Option[Short] = None,
    customOidcode: Option[String] = None,
    isalert: Option[Short] = None,
    downcount: Option[Int] = None)(implicit session: DBSession = autoSession): PblControlDevice = {
    val generatedKey = withSQL {
      insert.into(PblControlDevice).namedValues(
        column.devtyper -> devtyper,
        column.devcode -> devcode,
        column.profilecode -> profilecode,
        column.whoreceive -> whoreceive,
        column.ispolling -> ispolling,
        column.customOidcode -> customOidcode,
        column.isalert -> isalert,
        column.downcount -> downcount
      )
    }.updateAndReturnGeneratedKey.apply()

    PblControlDevice(
      code = generatedKey.toInt,
      devtyper = devtyper,
      devcode = devcode,
      profilecode = profilecode,
      whoreceive = whoreceive,
      ispolling = ispolling,
      customOidcode = customOidcode,
      isalert = isalert,
      downcount = downcount)
  }

  def batchInsert(entities: collection.Seq[PblControlDevice])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'devtyper -> entity.devtyper,
        'devcode -> entity.devcode,
        'profilecode -> entity.profilecode,
        'whoreceive -> entity.whoreceive,
        'ispolling -> entity.ispolling,
        'customOidcode -> entity.customOidcode,
        'isalert -> entity.isalert,
        'downcount -> entity.downcount))
    SQL("""insert into pbl_control_device(
      devtyper,
      devcode,
      profilecode,
      whoreceive,
      ispolling,
      custom_oidcode,
      isalert,
      downcount
    ) values (
      {devtyper},
      {devcode},
      {profilecode},
      {whoreceive},
      {ispolling},
      {customOidcode},
      {isalert},
      {downcount}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblControlDevice)(implicit session: DBSession = autoSession): PblControlDevice = {
    withSQL {
      update(PblControlDevice).set(
        column.code -> entity.code,
        column.devtyper -> entity.devtyper,
        column.devcode -> entity.devcode,
        column.profilecode -> entity.profilecode,
        column.whoreceive -> entity.whoreceive,
        column.ispolling -> entity.ispolling,
        column.customOidcode -> entity.customOidcode,
        column.isalert -> entity.isalert,
        column.downcount -> entity.downcount
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblControlDevice)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblControlDevice).where.eq(column.code, entity.code) }.update.apply()
  }

}
