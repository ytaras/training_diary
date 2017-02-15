package controller

import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import model._

// NOTICE before/after filters won't be executed by default
class ShootersControllerSpec extends FunSpec with Matchers with BeforeAndAfterAll with DBSettings {

  override def afterAll() {
    super.afterAll()
    Shooter.deleteAll()
  }

  def createMockController = new ShootersController with MockController
  def newShooter = FactoryGirl(Shooter).create()

  describe("ShootersController") {

    describe("shows shooters") {
      it("shows HTML response") {
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/shooters/index"))
        controller.contentType should equal("text/html; charset=utf-8")
      }

      it("shows JSON response") {
        implicit val format = Format.JSON
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/shooters/index"))
        controller.contentType should equal("application/json; charset=utf-8")
      }
    }

    describe("shows a shooter") {
      it("shows HTML response") {
        val shooter = newShooter
        val controller = createMockController
        controller.showResource(shooter.id)
        controller.status should equal(200)
        controller.getFromRequestScope[Shooter]("item") should equal(Some(shooter))
        controller.renderCall.map(_.path) should equal(Some("/shooters/show"))
      }
    }

    describe("shows new resource input form") {
      it("shows HTML response") {
        val controller = createMockController
        controller.newResource()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/shooters/new"))
      }
    }

    describe("creates a shooter") {
      it("succeeds with valid parameters") {
        val controller = createMockController
        controller.prepareParams(
          "name" -> "dummy"
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
      val shooter = newShooter
      val controller = createMockController
      controller.editResource(shooter.id)
      controller.status should equal(200)
      controller.renderCall.map(_.path) should equal(Some("/shooters/edit"))
    }

    it("updates a shooter") {
      val shooter = newShooter
      val controller = createMockController
      controller.prepareParams(
        "name" -> "dummy"
      )
      controller.updateResource(shooter.id)
      controller.status should equal(200)
    }

    it("destroys a shooter") {
      val shooter = newShooter
      val controller = createMockController
      controller.destroyResource(shooter.id)
      controller.status should equal(200)
    }

  }

}
