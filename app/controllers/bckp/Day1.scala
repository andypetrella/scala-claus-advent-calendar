package controllers.bckp

import play.api.mvc._
import play.api.templates.HtmlFormat
import controllers.DayTmpl

case class Day1[A](parser:BodyParser[A]) extends DayTmpl[A, Int] {
  lazy val content: Int => HtmlFormat.Appendable = i => views.html.day1(i)

  def sync:Int = {
    val a = 1
    ???
  }
}