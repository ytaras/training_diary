package controller

import skinny._
import skinny.controller.AssetsController

object Controllers {

  def mount(ctx: ServletContext): Unit = {
    trainings.mount(ctx)
    exercises.mount(ctx)
    shooters.mount(ctx)
    root.mount(ctx)
    AssetsController.mount(ctx)
  }

  object root extends RootController with Routes {
    val indexUrl = get("/?")(index).as('index)
  }

  object shooters extends _root_.controller.ShootersController with Routes {
  }

  object exercises extends _root_.controller.ExercisesController with Routes {
  }

  object trainings extends _root_.controller.TrainingsController with Routes {
  }

}
