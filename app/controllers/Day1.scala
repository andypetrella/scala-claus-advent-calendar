package controllers

import play.api.mvc._
import play.api.templates.HtmlFormat

case class Day1[A](parser:BodyParser[A]) extends DayTmpl[A] {
  lazy val content: HtmlFormat.Appendable = views.html.day1()

  def sync {
    val a = 1
    ???
  }
}