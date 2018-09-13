package com.github.alexanderfefelov.userside.network.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PblPersOtdel(
  code: Int,
  nazv: Option[String] = None,
  parentcode: Option[Int] = None,
  dateadd: Option[LocalDate] = None,
  opis: Option[String] = None,
  calWork: Option[String] = None,
  incal: Option[Short] = None) {

  def save()(implicit session: DBSession = PblPersOtdel.autoSession): PblPersOtdel = PblPersOtdel.save(this)(session)

  def destroy()(implicit session: DBSession = PblPersOtdel.autoSession): Int = PblPersOtdel.destroy(this)(session)

}


object PblPersOtdel extends SQLSyntaxSupport[PblPersOtdel] {

  override val schemaName = Some("userside3")

  override val tableName = "pbl_pers_otdel"

  override val columns = Seq("code", "nazv", "parentcode", "dateadd", "opis", "cal_work", "incal")

  def apply(ppo: SyntaxProvider[PblPersOtdel])(rs: WrappedResultSet): PblPersOtdel = autoConstruct(rs, ppo)
  def apply(ppo: ResultName[PblPersOtdel])(rs: WrappedResultSet): PblPersOtdel = autoConstruct(rs, ppo)

  val ppo = PblPersOtdel.syntax("ppo")

  override val autoSession = AutoSession

  def find(code: Int)(implicit session: DBSession = autoSession): Option[PblPersOtdel] = {
    withSQL {
      select.from(PblPersOtdel as ppo).where.eq(ppo.code, code)
    }.map(PblPersOtdel(ppo.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PblPersOtdel] = {
    withSQL(select.from(PblPersOtdel as ppo)).map(PblPersOtdel(ppo.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PblPersOtdel as ppo)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PblPersOtdel] = {
    withSQL {
      select.from(PblPersOtdel as ppo).where.append(where)
    }.map(PblPersOtdel(ppo.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PblPersOtdel] = {
    withSQL {
      select.from(PblPersOtdel as ppo).where.append(where)
    }.map(PblPersOtdel(ppo.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PblPersOtdel as ppo).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    nazv: Option[String] = None,
    parentcode: Option[Int] = None,
    dateadd: Option[LocalDate] = None,
    opis: Option[String] = None,
    calWork: Option[String] = None,
    incal: Option[Short] = None)(implicit session: DBSession = autoSession): PblPersOtdel = {
    val generatedKey = withSQL {
      insert.into(PblPersOtdel).namedValues(
        column.nazv -> nazv,
        column.parentcode -> parentcode,
        column.dateadd -> dateadd,
        column.opis -> opis,
        column.calWork -> calWork,
        column.incal -> incal
      )
    }.updateAndReturnGeneratedKey.apply()

    PblPersOtdel(
      code = generatedKey.toInt,
      nazv = nazv,
      parentcode = parentcode,
      dateadd = dateadd,
      opis = opis,
      calWork = calWork,
      incal = incal)
  }

  def batchInsert(entities: collection.Seq[PblPersOtdel])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'nazv -> entity.nazv,
        'parentcode -> entity.parentcode,
        'dateadd -> entity.dateadd,
        'opis -> entity.opis,
        'calWork -> entity.calWork,
        'incal -> entity.incal))
    SQL("""insert into pbl_pers_otdel(
      nazv,
      parentcode,
      dateadd,
      opis,
      cal_work,
      incal
    ) values (
      {nazv},
      {parentcode},
      {dateadd},
      {opis},
      {calWork},
      {incal}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PblPersOtdel)(implicit session: DBSession = autoSession): PblPersOtdel = {
    withSQL {
      update(PblPersOtdel).set(
        column.code -> entity.code,
        column.nazv -> entity.nazv,
        column.parentcode -> entity.parentcode,
        column.dateadd -> entity.dateadd,
        column.opis -> entity.opis,
        column.calWork -> entity.calWork,
        column.incal -> entity.incal
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: PblPersOtdel)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PblPersOtdel).where.eq(column.code, entity.code) }.update.apply()
  }

}
