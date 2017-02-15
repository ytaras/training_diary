package integrationtest

import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import _root_.controller.Controllers
import model._

class AttemptsController_IntegrationTestSpec extends SkinnyFlatSpec with SkinnyTestSupport with BeforeAndAfterAll with DBSettings {
  addFilter(Controllers.attempts, "/*")

  override def afterAll() {
    super.afterAll()
    Attempt.deleteAll()
    Training.deleteAll()
    Exercise.deleteAll()
    Shooter.deleteAll()
  }

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

  it should "show attempts" in {
    get("/attempts") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/attempts/") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/attempts.json") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/attempts.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show a attempt in detail" in {
    get(s"/attempts/${newAttempt.id}") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/attempts/${newAttempt.id}.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/attempts/${newAttempt.id}.json") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show new entry form" in {
    get(s"/attempts/new") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "create a attempt" in {
    post(
      s"/attempts",
      "training_id" -> trainingId.toString,
      "exercise_id" -> exerciseId.toString,
      "shooter_id" -> shooterId.toString,
      "result" -> 34.44.toString
    ) {
        logBodyUnless(403)
        status should equal(403)
      }

    withSession("csrf-token" -> "valid_token") {
      post(
        s"/attempts",
        "training_id" -> trainingId.toString,
        "exercise_id" -> exerciseId.toString,
        "shooter_id" -> shooterId.toString,
        "result" -> 34.44.toString,
        "csrf-token" -> "valid_token"
      ) {
          logBodyUnless(302)
          status should equal(302)
          val id = header("Location").split("/").last.toLong
          Attempt.findById(id).isDefined should equal(true)
        }
    }
  }

  it should "show the edit form" in {
    get(s"/attempts/${newAttempt.id}/edit") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "update a attempt" in {
    put(
      s"/attempts/${newAttempt.id}",
      "training_id" -> trainingId.toString,
      "exercise_id" -> exerciseId.toString,
      "shooter_id" -> shooterId.toString,
      "result" -> 34.44.toString
    ) {
        logBodyUnless(403)
        status should equal(403)
      }

    withSession("csrf-token" -> "valid_token") {
      put(
        s"/attempts/${newAttempt.id}",
        "training_id" -> trainingId.toString,
        "exercise_id" -> exerciseId.toString,
        "shooter_id" -> shooterId.toString,
        "result" -> 44.44.toString,
        "csrf-token" -> "valid_token"
      ) {
          logBodyUnless(302)
          status should equal(302)
        }
    }
  }

  it should "delete a attempt" in {
    delete(s"/attempts/${newAttempt.id}") {
      logBodyUnless(403)
      status should equal(403)
    }
    withSession("csrf-token" -> "valid_token") {
      delete(s"/attempts/${newAttempt.id}?csrf-token=valid_token") {
        logBodyUnless(200)
        status should equal(200)
      }
    }
  }

}
