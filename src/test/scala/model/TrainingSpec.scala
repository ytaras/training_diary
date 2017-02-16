package model

import skinny.DBSettings
import skinny.test._
import org.scalatest.fixture.FunSpec
import org.scalatest._
import scalikejdbc._
import scalikejdbc.scalatest._
import org.joda.time._

class TrainingSpec extends FunSpec with Matchers with DBSettings with AutoRollback {

  describe("Trainigs") {
    it("should load attempts") { implicit session =>
      val attempt = FactoryGirl(Attempt).withVariables(
        'shooterId -> FactoryGirl(Shooter).create().id,
        'trainingId -> FactoryGirl(Training).create().id,
        'exerciseId -> FactoryGirl(Exercise).create().id
      ).create()
      Training.joins(Training.attemptsRef).findById(attempt.trainingId).get.attempts should contain(attempt)
    }
  }
}
