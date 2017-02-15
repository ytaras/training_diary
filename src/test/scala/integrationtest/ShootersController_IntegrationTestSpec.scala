package integrationtest

import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import _root_.controller.Controllers
import model._

class ShootersController_IntegrationTestSpec extends SkinnyFlatSpec with SkinnyTestSupport with BeforeAndAfterAll with DBSettings {
  addFilter(Controllers.shooters, "/*")

  override def afterAll() {
    super.afterAll()
    Shooter.deleteAll()
  }

  def newShooter = FactoryGirl(Shooter).create()

  it should "show shooters" in {
    get("/shooters") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/shooters/") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/shooters.json") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/shooters.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show a shooter in detail" in {
    get(s"/shooters/${newShooter.id}") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/shooters/${newShooter.id}.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/shooters/${newShooter.id}.json") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show new entry form" in {
    get(s"/shooters/new") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "create a shooter" in {
    post(
      s"/shooters",
      "name" -> "dummy"
    ) {
        logBodyUnless(403)
        status should equal(403)
      }

    withSession("csrf-token" -> "valid_token") {
      post(
        s"/shooters",
        "name" -> "dummy",
        "csrf-token" -> "valid_token"
      ) {
          logBodyUnless(302)
          status should equal(302)
          val id = header("Location").split("/").last.toLong
          Shooter.findById(id).isDefined should equal(true)
        }
    }
  }

  it should "show the edit form" in {
    get(s"/shooters/${newShooter.id}/edit") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "update a shooter" in {
    put(
      s"/shooters/${newShooter.id}",
      "name" -> "dummy"
    ) {
        logBodyUnless(403)
        status should equal(403)
      }

    withSession("csrf-token" -> "valid_token") {
      put(
        s"/shooters/${newShooter.id}",
        "name" -> "dummy",
        "csrf-token" -> "valid_token"
      ) {
          logBodyUnless(302)
          status should equal(302)
        }
    }
  }

  it should "delete a shooter" in {
    delete(s"/shooters/${newShooter.id}") {
      logBodyUnless(403)
      status should equal(403)
    }
    withSession("csrf-token" -> "valid_token") {
      delete(s"/shooters/${newShooter.id}?csrf-token=valid_token") {
        logBodyUnless(200)
        status should equal(200)
      }
    }
  }

}
