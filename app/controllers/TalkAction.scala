package controllers

import scala.concurrent.Future

import play.api.mvc._
import play.api.mvc.Results._
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc
import scalax.file.Path

object TalkAction extends ActionBuilder[Request] {
  protected def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[mvc.SimpleResult]): Future[mvc.SimpleResult] =
    block(request)

  override def composeAction[A](action: Action[A]) = DayAction(action)
}

case class DayAction[A](action: Action[A]) extends Action[A] {
  val day = Form(
    single(("day",number))
  )

  implicit class WinPath(p:Path) {
    val updateForWin = p.toAbsolute.path.replaceAll("\\\\", "\\\\\\\\")
  }

  def apply(request: Request[A]): Future[SimpleResult] = {
    implicit val r = request

    day.bindFromRequest.fold(
      hasErrors = _ => Future.successful(BadRequest("Unknown day!")),
      success = day => {
        val tmpl: DayTmpl[A, _] = DayTmpl.actionFor[A](day, parser)
        try {
          tmpl(request)
        } catch {
          case e:Throwable => {
            Future.successful(NotImplemented(views.html.todo(day, tmpl.file.updateForWin, tmpl.code) ))
          }
        }
      }
    )

  }

  lazy val parser = action.parser

}