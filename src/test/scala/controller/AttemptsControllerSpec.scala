package controller

import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import model._

// NOTICE before/after filters won't be executed by default
class AttemptsControllerSpec extends FunSpec with Matchers with BeforeAndAfterAll with DBSettings {

  override def afterAll() {
    super.afterAll()
    Attempt.deleteAll()
    Training.deleteAll()
    Exercise.deleteAll()
    Shooter.deleteAll()
  }

  def createMockController = new AttemptsController with MockController
  def shooterId = FactoryGirl(Shooter).create().id
  def trainingId = FactoryGirl(Training).create().id
  def exerciseId = FactoryGirl(Exercise).create().id
  def newAttempt = {
    FactoryGirl(Attempt)
      .withVariables(
        'trainingId -> trainingId,
        'shooterId -> shooterId,
        'exerciseId -> exerciseId
      ).create()
  }

  describe("AttemptsController") {

    describe("shows attempts") {
      it("shows HTML response") {
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/attempts/index"))
        controller.contentType should equal("text/html; charset=utf-8")
      }

      it("shows JSON response") {
        implicit val format = Format.JSON
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/attempts/index"))
        controller.contentType should equal("application/json; charset=utf-8")
      }
    }

    describe("shows a attempt") {
      it("shows HTML response") {
        val attempt = newAttempt
        val controller = createMockController
        controller.showResource(attempt.id)
        controller.status should equal(200)
        controller.getFromRequestScope[Attempt]("item") should equal(Some(attempt))
        controller.renderCall.map(_.path) should equal(Some("/attempts/show"))
      }
    }

    describe("shows new resource input form") {
      it("shows HTML response") {
        val controller = createMockController
        controller.newResource()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/attempts/new"))
      }
    }

    describe("creates a attempt") {
      it("succeeds with valid parameters") {
        val controller = createMockController
        controller.prepareParams(
          "training_id" -> trainingId.toString,
          "exercise_id" -> exerciseId.toString,
          "shooter_id" -> shooterId.toString,
          "result" -> 54.44.toString
        )
        controller.createResource()
        controller.status should equal(200)
      }

      it("fails with invalid parameters") {
        val controller = createMockController
        controller.prepareParams() // no parameters
        controller.createResource()
        controller.status should equal(400)
        controller.errorMessages.size should be > (0)
      }
    }

    it("shows a resource edit input form") {
      val attempt = newAttempt
      val controller = createMockController
      controller.editResource(attempt.id)
      controller.status should equal(200)
      controller.renderCall.map(_.path) should equal(Some("/attempts/edit"))
    }

    it("updates a attempt") {
      val attempt = newAttempt
      val controller = createMockController
      controller.prepareParams(
        "training_id" -> trainingId.toString,
        "exercise_id" -> exerciseId.toString,
        "shooter_id" -> shooterId.toString,
        "result" -> 34.44.toString
      )
      controller.updateResource(attempt.id)
      controller.status should equal(200)
    }

    it("destroys a attempt") {
      val attempt = newAttempt
      val controller = createMockController
      controller.destroyResource(attempt.id)
      controller.status should equal(200)
    }

  }

}
