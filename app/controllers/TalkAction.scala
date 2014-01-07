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
          case 5 => Day5(parser)
          case 6 => Day6(parser)
          case 7 => Day7(parser)
          case 8 => Day8(parser)
          case 9  => Day9(parser)
          case 10 => Day10(parser)
          case 11 => Day11(parser)
          case 12 => Day12(parser)
          case 13 => Day13(parser)
          //case 14 => Day14(parser)
          //case 15 => Day15(parser)
          //case 16 => Day16(parser)
          //case 17 => Day17(parser)
          //case 18 => Day18(parser)
          //case 19 => Day19(parser)
          //case 20 => Day20(parser)
          //case 21 => Day21(parser)
          //case 22 => Day22(parser)
          //case 23 => Day23(parser)
          //case 24 => Day24(parser)
          //case 25 => Day25(parser)
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