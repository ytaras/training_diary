package model

import skinny.orm._, feature._
import scalikejdbc._
import org.joda.time._

case class Training(
  id: Long,
  date: LocalDate,
  comment: String,
  createdAt: DateTime,
  updatedAt: DateTime
)

object Training extends SkinnyCRUDMapper[Training] with TimestampsFeature[Training] {
  override lazy val tableName = "trainings"
  override lazy val defaultAlias = createAlias("t")

  /*
   * If you're familiar with ScalikeJDBC/Skinny ORM, using #autoConstruct makes your mapper simpler.
   * (e.g.)
   * override def extract(rs: WrappedResultSet, rn: ResultName[Training]) = autoConstruct(rs, rn)
   *
   * Be aware of excluding associations like this:
   * (e.g.)
   * case class Member(id: Long, companyId: Long, company: Option[Company] = None)
   * object Member extends SkinnyCRUDMapper[Member] {
   *   override def extract(rs: WrappedResultSet, rn: ResultName[Member]) =
   *     autoConstruct(rs, rn, "company") // "company" will be skipped
   * }
   */
  override def extract(rs: WrappedResultSet, rn: ResultName[Training]): Training = new Training(
    id = rs.get(rn.id),
    date = rs.get(rn.date),
    comment = rs.get(rn.comment),
    createdAt = rs.get(rn.createdAt),
    updatedAt = rs.get(rn.updatedAt)
  )
}
