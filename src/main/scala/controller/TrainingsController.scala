package controller

import skinny._
import skinny.validator._
import _root_.controller._
import model.Training

class TrainingsController extends SkinnyResource with ApplicationController {
  protectFromForgery()

  override def model = Training
  override def resourcesName = "trainings"
  override def resourceName = "training"

  override def resourcesBasePath = s"/${toSnakeCase(resourcesName)}"
  override def useSnakeCasedParamKeys = true

  override def viewsDirectoryPath = s"/${resourcesName}"

  override def createParams = Params(params).withDate("date")
  override def createForm = validation(
    createParams,
    paramKey("date") is required & dateFormat,
    paramKey("comment") is required & maxLength(512)
  )
  override def createFormStrongParameters = Seq(
    "date" -> ParamType.LocalDate,
    "comment" -> ParamType.String
  )

  override def updateParams = Params(params).withDate("date")
  override def updateForm = validation(
    updateParams,
    paramKey("date") is required & dateFormat,
    paramKey("comment") is required & maxLength(512)
  )
  override def updateFormStrongParameters = Seq(
    "date" -> ParamType.LocalDate,
    "comment" -> ParamType.String
  )

}
