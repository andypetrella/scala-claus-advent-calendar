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
    single(("day",number))
  )

  def apply(request: Request[A]): Future[SimpleResult] = {
    implicit val r = request

    day.bindFromRequest.fold(
      hasErrors = _ => Future.successful(BadRequest("Unknown day!")),
      success = day => {
        val tmpl: DayTmpl[A, _] = day match {
          case 1 => Day1(parser)
          case 2 => Day2(parser)
          case 3 => Day3(parser)
          case 4 => Day4(parser)
        }
        try {
          tmpl(request)
        } catch {
          case e:Throwable => {
            Future.successful(NotImplemented(views.html.todo(day, tmpl.file.toAbsolute.path.replaceAll("\\\\", "\\\\\\\\"), tmpl.code) ))
          }
        }
      }
    )

  }

  lazy val parser = action.parser

}