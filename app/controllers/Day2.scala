package controllers

import play.api.mvc._
import play.api.mvc.Results._
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc
import scala.concurrent.Future
import play.api.templates.HtmlFormat

case class Day2[A](parser:BodyParser[A]) extends DayTmpl[A] {
  lazy val content: HtmlFormat.Appendable = views.html.day2()

  def sync {
    "ok"
  }
}