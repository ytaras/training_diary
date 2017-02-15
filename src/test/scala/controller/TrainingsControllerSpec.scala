package controller

import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import model._

// NOTICE before/after filters won't be executed by default
class TrainingsControllerSpec extends FunSpec with Matchers with BeforeAndAfterAll with DBSettings {

  override def afterAll() {
    super.afterAll()
    Training.deleteAll()
  }

  def createMockController = new TrainingsController with MockController
  def newTraining = FactoryGirl(Training).create()

  describe("TrainingsController") {

    describe("shows trainings") {
      it("shows HTML response") {
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/trainings/index"))
        controller.contentType should equal("text/html; charset=utf-8")
      }

      it("shows JSON response") {
        implicit val format = Format.JSON
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/trainings/index"))
        controller.contentType should equal("application/json; charset=utf-8")
      }
    }

    describe("shows a training") {
      it("shows HTML response") {
        val training = newTraining
        val controller = createMockController
        controller.showResource(training.id)
        controller.status should equal(200)
        controller.getFromRequestScope[Training]("item") should equal(Some(training))
        controller.renderCall.map(_.path) should equal(Some("/trainings/show"))
      }
    }

    describe("shows new resource input form") {
      it("shows HTML response") {
        val controller = createMockController
        controller.newResource()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/trainings/new"))
      }
    }

    describe("creates a training") {
      it("succeeds with valid parameters") {
        val controller = createMockController
        controller.prepareParams(
          "date" -> skinny.util.DateTimeUtil.toString(new LocalDate()),
          "comment" -> "dummy")
        controller.createResource()
        controller.status should equal(200)
      }

      it("fails with invalid parameters") {
        val controller = createMockController
        controller.prepareParams() // no parameters
        controller.createResource()
        controller.status should equal(400)
        controller.errorMessages.size should be >(0)
      }
    }

    it("shows a resource edit input form") {
      val training = newTraining
      val controller = createMockController
      controller.editResource(training.id)
      controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/trainings/edit"))
    }

    it("updates a training") {
      val training = newTraining
      val controller = createMockController
      controller.prepareParams(
        "date" -> skinny.util.DateTimeUtil.toString(new LocalDate()),
        "comment" -> "dummy")
      controller.updateResource(training.id)
      controller.status should equal(200)
    }

    it("destroys a training") {
      val training = newTraining
      val controller = createMockController
      controller.destroyResource(training.id)
      controller.status should equal(200)
    }

  }

}
