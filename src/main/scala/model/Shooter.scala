package model

import skinny.orm._, feature._
import scalikejdbc._
import org.joda.time._

case class Shooter(
  id: Long,
  name: String,
  createdAt: DateTime,
  updatedAt: DateTime
)

object Shooter extends SkinnyCRUDMapper[Shooter] with TimestampsFeature[Shooter] {
  override lazy val tableName = "shooters"
  override lazy val defaultAlias = createAlias("s")

  /*
   * If you're familiar with ScalikeJDBC/Skinny ORM, using #autoConstruct makes your mapper simpler.
   * (e.g.)
   * override def extract(rs: WrappedResultSet, rn: ResultName[Shooter]) = autoConstruct(rs, rn)
   *
   * Be aware of excluding associations like this:
   * (e.g.)
   * case class Member(id: Long, companyId: Long, company: Option[Company] = None)
   * object Member extends SkinnyCRUDMapper[Member] {
   *   override def extract(rs: WrappedResultSet, rn: ResultName[Member]) =
   *     autoConstruct(rs, rn, "company") // "company" will be skipped
   * }
   */
  override def extract(rs: WrappedResultSet, rn: ResultName[Shooter]): Shooter = new Shooter(
    id = rs.get(rn.id),
    name = rs.get(rn.name),
    createdAt = rs.get(rn.createdAt),
    updatedAt = rs.get(rn.updatedAt)
  )
}
