package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblPostav(
  code: Int,
  nazv: Option[String] = None,
  adr: Option[String] = None,
  okpo: Option[String] = None,
  inn: Option[String] = None,
  svid: Option[String] = None,
  bank: Option[String] = None,
  mfo: Option[String] = None,
  scet: Option[String] = None,
  tel: Option[String] = None,
  tel2: Option[String] = None,
  fax: Option[String] = None,
  dird: Option[String] = None,
  dirfio: Option[String] = None,
  gbuhd: Option[String] = None,
  gbuhfio: Option[String] = None,
  opis: Option[String] = None,
  email: Option[String] = None,
  icq: Option[String] = None,
  site: Option[String] = None) {

  def save()(implicit session: DBSession = PblPostav.autoSession): PblPostav = PblPostav.save(this)(session)

  def destroy()(implicit session: DBSession = PblPostav.autoSession): Int = PblPostav.destroy(this)(session)

}


object PblPostav extends SQLSyntaxSupport[PblPostav] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_postav"

  override val columns = Seq("code", "nazv", "adr", "okpo", "inn", "svid", "bank", "mfo", "scet", "tel", "tel2", "fax", "dird", "dirfio", "gbuhd", "gbuhfio", "opis", "email", "icq", "site")

  def apply(pp: SyntaxProvider[PblPostav])(rs: WrappedResultSet): PblPostav = autoConstruct(rs, pp)
  def apply(pp: ResultName[PblPostav])(rs: WrappedResultSet): PblPostav = autoConstruct(rs, pp)

  val pp = PblPostav.syntax("pp")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblPostav] = {
    withSQL {
      select.from(PblPostav as pp).where.eq(pp.code, code)
    }.map(PblPostav(pp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblPostav] = {
    withSQL(select.from(PblPostav as pp)).map(PblPostav(pp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblPostav as pp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblPostav] = {
    withSQL {
      select.from(PblPostav as pp).where.append(where)
    }.map(PblPostav(pp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblPostav] = {
    withSQL {
      select.from(PblPostav as pp).where.append(where)
    }.map(PblPostav(pp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblPostav as pp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    nazv: Option[String] = None,
    adr: Option[String] = None,
    okpo: Option[String] = None,
    inn: Option[String] = None,
    svid: Option[String] = None,
    bank: Option[String] = None,
    mfo: Option[String] = None,
    scet: Option[String] = None,
    tel: Option[String] = None,
    tel2: Option[String] = None,
    fax: Option[String] = None,
    dird: Option[String] = None,
    dirfio: Option[String] = None,
    gbuhd: Option[String] = None,
    gbuhfio: Option[String] = None,
    opis: Option[String] = None,
    email: Option[String] = None,
    icq: Option[String] = None,
    site: Option[String] = None)(implicit session: DBSession = autoSession): PblPostav = {
    val generatedKey = withSQL {
      insert.into(PblPostav).namedValues(
        column.nazv -> nazv,
        column.adr -> adr,
        column.okpo -> okpo,
        column.inn -> inn,
        column.svid -> svid,
        column.bank -> bank,
        column.mfo -> mfo,
        column.scet -> scet,
        column.tel -> tel,
        column.tel2 -> tel2,
        column.fax -> fax,
        column.dird -> dird,
        column.dirfio -> dirfio,
        column.gbuhd -> gbuhd,
        column.gbuhfio -> gbuhfio,
        column.opis -> opis,
        column.email -> email,
        column.icq -> icq,
        column.site -> site
      )
    }.updateAndReturnGeneratedKey.apply()

    PblPostav(
      code = generatedKey.toInt,
      nazv = nazv,
      adr = adr,
      okpo = okpo,
      inn = inn,
      svid = svid,
      bank = bank,
      mfo = mfo,
      scet = scet,
      tel = tel,
      tel2 = tel2,
      fax = fax,
      dird = dird,
      dirfio = dirfio,
      gbuhd = gbuhd,
      gbuhfio = gbuhfio,
      opis = opis,
      email = email,
      icq = icq,
      site = site)
  }

  def batchInsert(entities: collection.Seq[PblPostav])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'nazv -> entity.nazv,
        'adr -> entity.adr,
        'okpo -> entity.okpo,
        'inn -> entity.inn,
        'svid -> entity.svid,
        'bank -> entity.bank,
        'mfo -> entity.mfo,
        'scet -> entity.scet,
        'tel -> entity.tel,
        'tel2 -> entity.tel2,
        'fax -> entity.fax,
        'dird -> entity.dird,
        'dirfio -> entity.dirfio,
        'gbuhd -> entity.gbuhd,
        'gbuhfio -> entity.gbuhfio,
        'opis -> entity.opis,
        'email -> entity.email,
        'icq -> entity.icq,
        'site -> entity.site))
    SQL("""insert into pbl_postav(
      nazv,
      adr,
      okpo,
      inn,
      svid,
      bank,
      mfo,
      scet,
      tel,
      tel2,
      fax,
      dird,
      dirfio,
      gbuhd,
      gbuhfio,
      opis,
      email,
      icq,
      site
    ) values (
      {nazv},
      {adr},
      {okpo},
      {inn},
      {svid},
      {bank},
      {mfo},
      {scet},
      {tel},
      {tel2},
      {fax},
      {dird},
      {dirfio},
      {gbuhd},
      {gbuhfio},
      {opis},
      {email},
      {icq},
      {site}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblPostav)(implicit session: DBSession = autoSession): PblPostav = {
    withSQL {
      update(PblPostav).set(
        column.code -> entity.code,
        column.nazv -> entity.nazv,
        column.adr -> entity.adr,
        column.okpo -> entity.okpo,
        column.inn -> entity.inn,
        column.svid -> entity.svid,
        column.bank -> entity.bank,
        column.mfo -> entity.mfo,
        column.scet -> entity.scet,
        column.tel -> entity.tel,
        column.tel2 -> entity.tel2,
        column.fax -> entity.fax,
        column.dird -> entity.dird,
        column.dirfio -> entity.dirfio,
        column.gbuhd -> entity.gbuhd,
        column.gbuhfio -> entity.gbuhfio,
        column.opis -> entity.opis,
        column.email -> entity.email,
        column.icq -> entity.icq,
        column.site -> entity.site
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblPostav)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblPostav).where.eq(column.code, entity.code) }.update.apply()
  }

}
