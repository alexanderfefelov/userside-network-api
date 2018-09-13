package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._

case class PblConfJournal(
  code: Int,
  typer: Option[Int] = None,
  nazv: Option[String] = None,
  summa: Option[BigDecimal] = None,
  timedo: Option[BigDecimal] = None,
  colorFon: Option[String] = None,
  colorText: Option[String] = None,
  colorLink: Option[String] = None,
  deadline: Option[Int] = None,
  deadlineInfo: Option[Int] = None,
  maingroup: Option[Int] = None,
  ispricemult: Option[Short] = None,
  ispriceproizv: Option[Short] = None,
  rRead: Option[String] = None,
  rDo: Option[String] = None,
  rWrite: Option[String] = None,
  selList: Option[String] = None,
  defPers: Option[String] = None,
  defLoop: Option[String] = None,
  docSh: Option[Int] = None,
  addhour: Option[Short] = None,
  timelineInfo: Option[BigDecimal] = None,
  opis: Option[String] = None,
  onmain: Option[Short] = None,
  pos: Option[Short] = None,
  isonaboncard: Option[Short] = None,
  priceAbon: Option[BigDecimal] = None,
  billcode: Option[Int] = None,
  docs: Option[String] = None,
  dopf: Option[String] = None,
  smsShWorkadd: Option[String] = None,
  smsOnadd: Option[Short] = None,
  firststate: Option[Int] = None,
  emailShWorkInfo: Option[String] = None,
  lmHide: Option[Short] = None,
  checkList: Option[String] = None) {

  def save()(implicit session: DBSession = PblConfJournal.autoSession): PblConfJournal = PblConfJournal.save(this)(session)

  def destroy()(implicit session: DBSession = PblConfJournal.autoSession): Int = PblConfJournal.destroy(this)(session)

}


object PblConfJournal extends SQLSyntaxSupport[PblConfJournal] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_conf_journal"

  override val columns = Seq("code", "typer", "nazv", "summa", "timedo", "color_fon", "color_text", "color_link", "deadline", "deadline_info", "maingroup", "ispricemult", "ispriceproizv", "r_read", "r_do", "r_write", "sel_list", "def_pers", "def_loop", "doc_sh", "addhour", "timeline_info", "opis", "onmain", "pos", "isonaboncard", "price_abon", "billcode", "docs", "dopf", "sms_sh_workadd", "sms_onadd", "firststate", "email_sh_work_info", "lm_hide", "check_list")

  def apply(pcj: SyntaxProvider[PblConfJournal])(rs: WrappedResultSet): PblConfJournal = autoConstruct(rs, pcj)
  def apply(pcj: ResultName[PblConfJournal])(rs: WrappedResultSet): PblConfJournal = autoConstruct(rs, pcj)

  val pcj = PblConfJournal.syntax("pcj")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblConfJournal] = {
    withSQL {
      select.from(PblConfJournal as pcj).where.eq(pcj.code, code)
    }.map(PblConfJournal(pcj.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblConfJournal] = {
    withSQL(select.from(PblConfJournal as pcj)).map(PblConfJournal(pcj.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblConfJournal as pcj)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblConfJournal] = {
    withSQL {
      select.from(PblConfJournal as pcj).where.append(where)
    }.map(PblConfJournal(pcj.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblConfJournal] = {
    withSQL {
      select.from(PblConfJournal as pcj).where.append(where)
    }.map(PblConfJournal(pcj.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblConfJournal as pcj).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    typer: Option[Int] = None,
    nazv: Option[String] = None,
    summa: Option[BigDecimal] = None,
    timedo: Option[BigDecimal] = None,
    colorFon: Option[String] = None,
    colorText: Option[String] = None,
    colorLink: Option[String] = None,
    deadline: Option[Int] = None,
    deadlineInfo: Option[Int] = None,
    maingroup: Option[Int] = None,
    ispricemult: Option[Short] = None,
    ispriceproizv: Option[Short] = None,
    rRead: Option[String] = None,
    rDo: Option[String] = None,
    rWrite: Option[String] = None,
    selList: Option[String] = None,
    defPers: Option[String] = None,
    defLoop: Option[String] = None,
    docSh: Option[Int] = None,
    addhour: Option[Short] = None,
    timelineInfo: Option[BigDecimal] = None,
    opis: Option[String] = None,
    onmain: Option[Short] = None,
    pos: Option[Short] = None,
    isonaboncard: Option[Short] = None,
    priceAbon: Option[BigDecimal] = None,
    billcode: Option[Int] = None,
    docs: Option[String] = None,
    dopf: Option[String] = None,
    smsShWorkadd: Option[String] = None,
    smsOnadd: Option[Short] = None,
    firststate: Option[Int] = None,
    emailShWorkInfo: Option[String] = None,
    lmHide: Option[Short] = None,
    checkList: Option[String] = None)(implicit session: DBSession = autoSession): PblConfJournal = {
    val generatedKey = withSQL {
      insert.into(PblConfJournal).namedValues(
        column.typer -> typer,
        column.nazv -> nazv,
        column.summa -> summa,
        column.timedo -> timedo,
        column.colorFon -> colorFon,
        column.colorText -> colorText,
        column.colorLink -> colorLink,
        column.deadline -> deadline,
        column.deadlineInfo -> deadlineInfo,
        column.maingroup -> maingroup,
        column.ispricemult -> ispricemult,
        column.ispriceproizv -> ispriceproizv,
        column.rRead -> rRead,
        column.rDo -> rDo,
        column.rWrite -> rWrite,
        column.selList -> selList,
        column.defPers -> defPers,
        column.defLoop -> defLoop,
        column.docSh -> docSh,
        column.addhour -> addhour,
        column.timelineInfo -> timelineInfo,
        column.opis -> opis,
        column.onmain -> onmain,
        column.pos -> pos,
        column.isonaboncard -> isonaboncard,
        column.priceAbon -> priceAbon,
        column.billcode -> billcode,
        column.docs -> docs,
        column.dopf -> dopf,
        column.smsShWorkadd -> smsShWorkadd,
        column.smsOnadd -> smsOnadd,
        column.firststate -> firststate,
        column.emailShWorkInfo -> emailShWorkInfo,
        column.lmHide -> lmHide,
        column.checkList -> checkList
      )
    }.updateAndReturnGeneratedKey.apply()

    PblConfJournal(
      code = generatedKey.toInt,
      typer = typer,
      nazv = nazv,
      summa = summa,
      timedo = timedo,
      colorFon = colorFon,
      colorText = colorText,
      colorLink = colorLink,
      deadline = deadline,
      deadlineInfo = deadlineInfo,
      maingroup = maingroup,
      ispricemult = ispricemult,
      ispriceproizv = ispriceproizv,
      rRead = rRead,
      rDo = rDo,
      rWrite = rWrite,
      selList = selList,
      defPers = defPers,
      defLoop = defLoop,
      docSh = docSh,
      addhour = addhour,
      timelineInfo = timelineInfo,
      opis = opis,
      onmain = onmain,
      pos = pos,
      isonaboncard = isonaboncard,
      priceAbon = priceAbon,
      billcode = billcode,
      docs = docs,
      dopf = dopf,
      smsShWorkadd = smsShWorkadd,
      smsOnadd = smsOnadd,
      firststate = firststate,
      emailShWorkInfo = emailShWorkInfo,
      lmHide = lmHide,
      checkList = checkList)
  }

  def batchInsert(entities: collection.Seq[PblConfJournal])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'typer -> entity.typer,
        'nazv -> entity.nazv,
        'summa -> entity.summa,
        'timedo -> entity.timedo,
        'colorFon -> entity.colorFon,
        'colorText -> entity.colorText,
        'colorLink -> entity.colorLink,
        'deadline -> entity.deadline,
        'deadlineInfo -> entity.deadlineInfo,
        'maingroup -> entity.maingroup,
        'ispricemult -> entity.ispricemult,
        'ispriceproizv -> entity.ispriceproizv,
        'rRead -> entity.rRead,
        'rDo -> entity.rDo,
        'rWrite -> entity.rWrite,
        'selList -> entity.selList,
        'defPers -> entity.defPers,
        'defLoop -> entity.defLoop,
        'docSh -> entity.docSh,
        'addhour -> entity.addhour,
        'timelineInfo -> entity.timelineInfo,
        'opis -> entity.opis,
        'onmain -> entity.onmain,
        'pos -> entity.pos,
        'isonaboncard -> entity.isonaboncard,
        'priceAbon -> entity.priceAbon,
        'billcode -> entity.billcode,
        'docs -> entity.docs,
        'dopf -> entity.dopf,
        'smsShWorkadd -> entity.smsShWorkadd,
        'smsOnadd -> entity.smsOnadd,
        'firststate -> entity.firststate,
        'emailShWorkInfo -> entity.emailShWorkInfo,
        'lmHide -> entity.lmHide,
        'checkList -> entity.checkList))
    SQL("""insert into pbl_conf_journal(
      typer,
      nazv,
      summa,
      timedo,
      color_fon,
      color_text,
      color_link,
      deadline,
      deadline_info,
      maingroup,
      ispricemult,
      ispriceproizv,
      r_read,
      r_do,
      r_write,
      sel_list,
      def_pers,
      def_loop,
      doc_sh,
      addhour,
      timeline_info,
      opis,
      onmain,
      pos,
      isonaboncard,
      price_abon,
      billcode,
      docs,
      dopf,
      sms_sh_workadd,
      sms_onadd,
      firststate,
      email_sh_work_info,
      lm_hide,
      check_list
    ) values (
      {typer},
      {nazv},
      {summa},
      {timedo},
      {colorFon},
      {colorText},
      {colorLink},
      {deadline},
      {deadlineInfo},
      {maingroup},
      {ispricemult},
      {ispriceproizv},
      {rRead},
      {rDo},
      {rWrite},
      {selList},
      {defPers},
      {defLoop},
      {docSh},
      {addhour},
      {timelineInfo},
      {opis},
      {onmain},
      {pos},
      {isonaboncard},
      {priceAbon},
      {billcode},
      {docs},
      {dopf},
      {smsShWorkadd},
      {smsOnadd},
      {firststate},
      {emailShWorkInfo},
      {lmHide},
      {checkList}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblConfJournal)(implicit session: DBSession = autoSession): PblConfJournal = {
    withSQL {
      update(PblConfJournal).set(
        column.code -> entity.code,
        column.typer -> entity.typer,
        column.nazv -> entity.nazv,
        column.summa -> entity.summa,
        column.timedo -> entity.timedo,
        column.colorFon -> entity.colorFon,
        column.colorText -> entity.colorText,
        column.colorLink -> entity.colorLink,
        column.deadline -> entity.deadline,
        column.deadlineInfo -> entity.deadlineInfo,
        column.maingroup -> entity.maingroup,
        column.ispricemult -> entity.ispricemult,
        column.ispriceproizv -> entity.ispriceproizv,
        column.rRead -> entity.rRead,
        column.rDo -> entity.rDo,
        column.rWrite -> entity.rWrite,
        column.selList -> entity.selList,
        column.defPers -> entity.defPers,
        column.defLoop -> entity.defLoop,
        column.docSh -> entity.docSh,
        column.addhour -> entity.addhour,
        column.timelineInfo -> entity.timelineInfo,
        column.opis -> entity.opis,
        column.onmain -> entity.onmain,
        column.pos -> entity.pos,
        column.isonaboncard -> entity.isonaboncard,
        column.priceAbon -> entity.priceAbon,
        column.billcode -> entity.billcode,
        column.docs -> entity.docs,
        column.dopf -> entity.dopf,
        column.smsShWorkadd -> entity.smsShWorkadd,
        column.smsOnadd -> entity.smsOnadd,
        column.firststate -> entity.firststate,
        column.emailShWorkInfo -> entity.emailShWorkInfo,
        column.lmHide -> entity.lmHide,
        column.checkList -> entity.checkList
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblConfJournal)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblConfJournal).where.eq(column.code, entity.code) }.update.apply()
  }

}
