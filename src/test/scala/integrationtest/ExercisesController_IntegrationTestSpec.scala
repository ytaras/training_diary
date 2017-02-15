package integrationtest

import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import _root_.controller.Controllers
import model._

class ExercisesController_IntegrationTestSpec extends SkinnyFlatSpec with SkinnyTestSupport with BeforeAndAfterAll with DBSettings {
  addFilter(Controllers.exercises, "/*")

  override def afterAll() {
    super.afterAll()
    Exercise.deleteAll()
  }

  def newExercise = FactoryGirl(Exercise).create()

  it should "show exercises" in {
    get("/exercises") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/exercises/") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/exercises.json") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/exercises.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show a exercise in detail" in {
    get(s"/exercises/${newExercise.id}") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/exercises/${newExercise.id}.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/exercises/${newExercise.id}.json") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show new entry form" in {
    get(s"/exercises/new") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "create a exercise" in {
    post(
      s"/exercises",
      "name" -> "dummy",
      "description" -> "dummy"
    ) {
        logBodyUnless(403)
        status should equal(403)
      }

    withSession("csrf-token" -> "valid_token") {
      post(
        s"/exercises",
        "name" -> "dummy",
        "description" -> "dummy",
        "csrf-token" -> "valid_token"
      ) {
          logBodyUnless(302)
          status should equal(302)
          val id = header("Location").split("/").last.toLong
          Exercise.findById(id).isDefined should equal(true)
        }
    }
  }

  it should "show the edit form" in {
    get(s"/exercises/${newExercise.id}/edit") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "update a exercise" in {
    put(
      s"/exercises/${newExercise.id}",
      "name" -> "dummy",
      "description" -> "dummy"
    ) {
        logBodyUnless(403)
        status should equal(403)
      }

    withSession("csrf-token" -> "valid_token") {
      put(
        s"/exercises/${newExercise.id}",
        "name" -> "dummy",
        "description" -> "dummy",
        "csrf-token" -> "valid_token"
      ) {
          logBodyUnless(302)
          status should equal(302)
        }
    }
  }

  it should "delete a exercise" in {
    delete(s"/exercises/${newExercise.id}") {
      logBodyUnless(403)
      status should equal(403)
    }
    withSession("csrf-token" -> "valid_token") {
      delete(s"/exercises/${newExercise.id}?csrf-token=valid_token") {
        logBodyUnless(200)
        status should equal(200)
      }
    }
  }

}
