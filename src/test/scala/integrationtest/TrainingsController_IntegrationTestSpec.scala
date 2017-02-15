package integrationtest

import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import _root_.controller.Controllers
import model._

class TrainingsController_IntegrationTestSpec extends SkinnyFlatSpec with SkinnyTestSupport with BeforeAndAfterAll with DBSettings {
  addFilter(Controllers.trainings, "/*")

  override def afterAll() {
    super.afterAll()
    Training.deleteAll()
  }

  def newTraining = FactoryGirl(Training).create()

  it should "show trainings" in {
    get("/trainings") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/trainings/") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/trainings.json") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/trainings.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show a training in detail" in {
    get(s"/trainings/${newTraining.id}") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/trainings/${newTraining.id}.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/trainings/${newTraining.id}.json") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show new entry form" in {
    get(s"/trainings/new") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "create a training" in {
    post(s"/trainings",
      "date" -> skinny.util.DateTimeUtil.toString(new LocalDate()),
      "comment" -> "dummy") {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      post(s"/trainings",
        "date" -> skinny.util.DateTimeUtil.toString(new LocalDate()),
        "comment" -> "dummy",
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
        val id = header("Location").split("/").last.toLong
        Training.findById(id).isDefined should equal(true)
      }
    }
  }

  it should "show the edit form" in {
    get(s"/trainings/${newTraining.id}/edit") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "update a training" in {
    put(s"/trainings/${newTraining.id}",
      "date" -> skinny.util.DateTimeUtil.toString(new LocalDate()),
      "comment" -> "dummy") {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      put(s"/trainings/${newTraining.id}",
        "date" -> skinny.util.DateTimeUtil.toString(new LocalDate()),
        "comment" -> "dummy",
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
      }
    }
  }

  it should "delete a training" in {
    delete(s"/trainings/${newTraining.id}") {
      logBodyUnless(403)
      status should equal(403)
    }
    withSession("csrf-token" -> "valid_token") {
      delete(s"/trainings/${newTraining.id}?csrf-token=valid_token") {
        logBodyUnless(200)
        status should equal(200)
      }
    }
  }

}
