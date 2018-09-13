package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfAttr(
  code: Int,
  katcode: Option[Int] = None,
  subkatcode: Option[Int] = None,
  pos: Option[Short] = None,
  nazv: Option[String] = None,
  isactive: Option[Short] = None,
  fsize: Option[Short] = None,
  fmaxsize: Option[Short] = None,
  ftyper: Option[Short] = None,
  valuemas: Option[String] = None,
  isreq: Option[Short] = None,
  ftyper2: Option[Short] = None) {

  def save()(implicit session: DBSession = PblConfAttr.autoSession): PblConfAttr = PblConfAttr.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfAttr.autoSession): Int = PblConfAttr.destroy(this)(session)

}


object PblConfAttr extends SQLSyntaxSupport[PblConfAttr] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_attr"

  override val columns = Seq("code", "katcode", "subkatcode", "pos", "nazv", "isactive", "fsize", "fmaxsize", "ftyper", "valuemas", "isreq", "ftyper2")

  def apply(pca: SyntaxProvider[PblConfAttr])(rs: WrappedResultSet): PblConfAttr = autoConstruct(rs, pca)
  def apply(pca: ResultName[PblConfAttr])(rs: WrappedResultSet): PblConfAttr = autoConstruct(rs, pca)

  val pca = PblConfAttr.syntax("pca")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfAttr] = {
    withSQL {
      select.from(PblConfAttr as pca).where.eq(pca.code, code)
    }.map(PblConfAttr(pca.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfAttr] = {
    withSQL(select.from(PblConfAttr as pca)).map(PblConfAttr(pca.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfAttr as pca)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfAttr] = {
    withSQL {
      select.from(PblConfAttr as pca).where.append(where)
    }.map(PblConfAttr(pca.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfAttr] = {
    withSQL {
      select.from(PblConfAttr as pca).where.append(where)
    }.map(PblConfAttr(pca.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfAttr as pca).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    katcode: Option[Int] = None,
    subkatcode: Option[Int] = None,
    pos: Option[Short] = None,
    nazv: Option[String] = None,
    isactive: Option[Short] = None,
    fsize: Option[Short] = None,
    fmaxsize: Option[Short] = None,
    ftyper: Option[Short] = None,
    valuemas: Option[String] = None,
    isreq: Option[Short] = None,
    ftyper2: Option[Short] = None)(implicit session: DBSession = autoSession): PblConfAttr = {
    val generatedKey = withSQL {
      insert.into(PblConfAttr).namedValues(
        column.katcode -> katcode,
        column.subkatcode -> subkatcode,
        column.pos -> pos,
        column.nazv -> nazv,
        column.isactive -> isactive,
        column.fsize -> fsize,
        column.fmaxsize -> fmaxsize,
        column.ftyper -> ftyper,
        column.valuemas -> valuemas,
        column.isreq -> isreq,
        column.ftyper2 -> ftyper2
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfAttr(
      code = generatedKey.toInt,
      katcode = katcode,
      subkatcode = subkatcode,
      pos = pos,
      nazv = nazv,
      isactive = isactive,
      fsize = fsize,
      fmaxsize = fmaxsize,
      ftyper = ftyper,
      valuemas = valuemas,
      isreq = isreq,
      ftyper2 = ftyper2)
  }

  def batchInsert(entities: collection.Seq[PblConfAttr])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'katcode -> entity.katcode,
        'subkatcode -> entity.subkatcode,
        'pos -> entity.pos,
        'nazv -> entity.nazv,
        'isactive -> entity.isactive,
        'fsize -> entity.fsize,
        'fmaxsize -> entity.fmaxsize,
        'ftyper -> entity.ftyper,
        'valuemas -> entity.valuemas,
        'isreq -> entity.isreq,
        'ftyper2 -> entity.ftyper2))
    SQL("""insert into pbl_conf_attr(
      katcode,
      subkatcode,
      pos,
      nazv,
      isactive,
      fsize,
      fmaxsize,
      ftyper,
      valuemas,
      isreq,
      ftyper2
    ) values (
      {katcode},
      {subkatcode},
      {pos},
      {nazv},
      {isactive},
      {fsize},
      {fmaxsize},
      {ftyper},
      {valuemas},
      {isreq},
      {ftyper2}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfAttr)(implicit session: DBSession = autoSession): PblConfAttr = {
    withSQL {
      update(PblConfAttr).set(
        column.code -> entity.code,
        column.katcode -> entity.katcode,
        column.subkatcode -> entity.subkatcode,
        column.pos -> entity.pos,
        column.nazv -> entity.nazv,
        column.isactive -> entity.isactive,
        column.fsize -> entity.fsize,
        column.fmaxsize -> entity.fmaxsize,
        column.ftyper -> entity.ftyper,
        column.valuemas -> entity.valuemas,
        column.isreq -> entity.isreq,
        column.ftyper2 -> entity.ftyper2
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfAttr)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfAttr).where.eq(column.code, entity.code) }.update.apply()
  }

}
