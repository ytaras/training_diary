package controller

import skinny._
import skinny.validator._
import _root_.controller._
import model.Attempt

class AttemptsController extends SkinnyResource with ApplicationController {
  protectFromForgery()

  override def model = Attempt
  override def resourcesName = "attempts"
  override def resourceName = "attempt"

  override def resourcesBasePath = s"/${toSnakeCase(resourcesName)}"
  override def useSnakeCasedParamKeys = true

  override def viewsDirectoryPath = s"/${resourcesName}"

  override def createParams = Params(params)
  override def createForm = validation(
    createParams,
    paramKey("training_id") is required & numeric & longValue,
    paramKey("exercise_id") is required & numeric & longValue,
    paramKey("shooter_id") is required & numeric & longValue,
    paramKey("result") is doubleValue & doubleMinMaxValue(0D, 99999.99D)
  )
  override def createFormStrongParameters = Seq(
    "training_id" -> ParamType.Long,
    "exercise_id" -> ParamType.Long,
    "shooter_id" -> ParamType.Long,
    "result" -> ParamType.Double
  )

  override def updateParams = Params(params)
  override def updateForm = validation(
    updateParams,
    paramKey("training_id") is required & numeric & longValue,
    paramKey("exercise_id") is required & numeric & longValue,
    paramKey("shooter_id") is required & numeric & longValue,
    paramKey("result") is doubleValue
  )
  override def updateFormStrongParameters = Seq(
    "training_id" -> ParamType.Long,
    "exercise_id" -> ParamType.Long,
    "shooter_id" -> ParamType.Long,
    "result" -> ParamType.Double
  )

}
