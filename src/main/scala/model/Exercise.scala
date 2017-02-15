package model

import skinny.orm._, feature._
import scalikejdbc._
import org.joda.time._

case class Exercise(
  id: Long,
  name: String,
  description: String,
  createdAt: DateTime,
  updatedAt: DateTime
)

object Exercise extends SkinnyCRUDMapper[Exercise] with TimestampsFeature[Exercise] {
  override lazy val tableName = "exercises"
  override lazy val defaultAlias = createAlias("e")

  /*
   * If you're familiar with ScalikeJDBC/Skinny ORM, using #autoConstruct makes your mapper simpler.
   * (e.g.)
   * override def extract(rs: WrappedResultSet, rn: ResultName[Exercise]) = autoConstruct(rs, rn)
   *
   * Be aware of excluding associations like this:
   * (e.g.)
   * case class Member(id: Long, companyId: Long, company: Option[Company] = None)
   * object Member extends SkinnyCRUDMapper[Member] {
   *   override def extract(rs: WrappedResultSet, rn: ResultName[Member]) =
   *     autoConstruct(rs, rn, "company") // "company" will be skipped
   * }
   */
  override def extract(rs: WrappedResultSet, rn: ResultName[Exercise]): Exercise = new Exercise(
    id = rs.get(rn.id),
    name = rs.get(rn.name),
    description = rs.get(rn.description),
    createdAt = rs.get(rn.createdAt),
    updatedAt = rs.get(rn.updatedAt)
  )
}
