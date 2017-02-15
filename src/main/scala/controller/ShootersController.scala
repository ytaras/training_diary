package controller

import skinny._
import skinny.validator._
import _root_.controller._
import model.Shooter

class ShootersController extends SkinnyResource with ApplicationController {
  protectFromForgery()

  override def model = Shooter
  override def resourcesName = "shooters"
  override def resourceName = "shooter"

  override def resourcesBasePath = s"/${toSnakeCase(resourcesName)}"
  override def useSnakeCasedParamKeys = true

  override def viewsDirectoryPath = s"/${resourcesName}"

  override def createParams = Params(params)
  override def createForm = validation(
    createParams,
    paramKey("name") is required & maxLength(512)
  )
  override def createFormStrongParameters = Seq(
    "name" -> ParamType.String
  )

  override def updateParams = Params(params)
  override def updateForm = validation(
    updateParams,
    paramKey("name") is required & maxLength(512)
  )
  override def updateFormStrongParameters = Seq(
    "name" -> ParamType.String
  )

}
