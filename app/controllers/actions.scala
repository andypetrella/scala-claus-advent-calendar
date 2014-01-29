package controllers

import scala.concurrent.Future

import play.api.mvc._
import play.api.mvc.Results._
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc

object TalkAction extends ActionBuilder[Request] {
  protected def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[mvc.SimpleResult]): Future[mvc.SimpleResult] =
    block(request)

  override def composeAction[A](action: Action[A]) = DayAction(action)
}

case class DayAction[A](action: Action[A]) extends Action[A] {
  val day = Form(
    tuple(
      ("day",number),
      ("cheat",optional(boolean))
    )
  )

  def apply(request: Request[A]): Future[SimpleResult] = {
    implicit val r = request

    day.bindFromRequest.fold(
      hasErrors = _ => Future.successful(BadRequest("Unknown day!")),
      success = {case (day, cheat) => {
        val tmpl: DayTmpl[A, _] = DayTmpl.actionFor[A](day, parser, cheat.getOrElse(false))
        try {
          tmpl(request)
        } catch {
          case e:Throwable => {
            println(">> Exception for Day: " + day)
            e.printStackTrace()
            println("Exception for Day: " + day + " <<")
            Future.successful(NotImplemented(views.html.todo(day, tmpl.file.updateForWin, tmpl.code) ))
          }
        }
      }}
    )

  }

  lazy val parser = action.parser

}