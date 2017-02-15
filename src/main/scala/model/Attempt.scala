package model

import skinny.orm._, feature._
import scalikejdbc._
import org.joda.time._

case class Attempt(
  id: Long,
  trainingId: Long,
  training: Option[Training] = None,
  exerciseId: Long,
  exercise: Option[Exercise] = None,
  shooterId: Long,
  shooter: Option[Shooter] = None,
  result: Option[Double] = None,
  createdAt: DateTime,
  updatedAt: DateTime
)

object Attempt extends SkinnyCRUDMapper[Attempt] with TimestampsFeature[Attempt] {
  override lazy val tableName = "attempts"
  override lazy val defaultAlias = createAlias("a")

  lazy val trainingRef = belongsTo[Training](Training, (a, t) => a.copy(training = t))

  lazy val exerciseRef = belongsTo[Exercise](Exercise, (a, e) => a.copy(exercise = e))

  lazy val shooterRef = belongsTo[Shooter](Shooter, (a, s) => a.copy(shooter = s))

  /*
   * If you're familiar with ScalikeJDBC/Skinny ORM, using #autoConstruct makes your mapper simpler.
   * (e.g.)
   * override def extract(rs: WrappedResultSet, rn: ResultName[Attempt]) = autoConstruct(rs, rn)
   *
   * Be aware of excluding associations like this:
   * (e.g.)
   * case class Member(id: Long, companyId: Long, company: Option[Company] = None)
   * object Member extends SkinnyCRUDMapper[Member] {
   *   override def extract(rs: WrappedResultSet, rn: ResultName[Member]) =
   *     autoConstruct(rs, rn, "company") // "company" will be skipped
   * }
   */
  override def extract(rs: WrappedResultSet, rn: ResultName[Attempt]): Attempt = new Attempt(
    id = rs.get(rn.id),
    trainingId = rs.get(rn.trainingId),
    exerciseId = rs.get(rn.exerciseId),
    shooterId = rs.get(rn.shooterId),
    result = rs.get(rn.result),
    createdAt = rs.get(rn.createdAt),
    updatedAt = rs.get(rn.updatedAt)
  )
}
