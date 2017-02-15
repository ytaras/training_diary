package controller

import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import model._

// NOTICE before/after filters won't be executed by default
class ExercisesControllerSpec extends FunSpec with Matchers with BeforeAndAfterAll with DBSettings {

  override def afterAll() {
    super.afterAll()
    Exercise.deleteAll()
  }

  def createMockController = new ExercisesController with MockController
  def newExercise = FactoryGirl(Exercise).create()

  describe("ExercisesController") {

    describe("shows exercises") {
      it("shows HTML response") {
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/exercises/index"))
        controller.contentType should equal("text/html; charset=utf-8")
      }

      it("shows JSON response") {
        implicit val format = Format.JSON
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/exercises/index"))
        controller.contentType should equal("application/json; charset=utf-8")
      }
    }

    describe("shows a exercise") {
      it("shows HTML response") {
        val exercise = newExercise
        val controller = createMockController
        controller.showResource(exercise.id)
        controller.status should equal(200)
        controller.getFromRequestScope[Exercise]("item") should equal(Some(exercise))
        controller.renderCall.map(_.path) should equal(Some("/exercises/show"))
      }
    }

    describe("shows new resource input form") {
      it("shows HTML response") {
        val controller = createMockController
        controller.newResource()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/exercises/new"))
      }
    }

    describe("creates a exercise") {
      it("succeeds with valid parameters") {
        val controller = createMockController
        controller.prepareParams(
          "name" -> "dummy",
          "description" -> "dummy"
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
      val exercise = newExercise
      val controller = createMockController
      controller.editResource(exercise.id)
      controller.status should equal(200)
      controller.renderCall.map(_.path) should equal(Some("/exercises/edit"))
    }

    it("updates a exercise") {
      val exercise = newExercise
      val controller = createMockController
      controller.prepareParams(
        "name" -> "dummy",
        "description" -> "dummy"
      )
      controller.updateResource(exercise.id)
      controller.status should equal(200)
    }

    it("destroys a exercise") {
      val exercise = newExercise
      val controller = createMockController
      controller.destroyResource(exercise.id)
      controller.status should equal(200)
    }

  }

}
