package controllers

import play.api.mvc._
import play.api.mvc.Results._
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc
import scala.concurrent.Future
import play.api.templates.HtmlFormat
import scalax.file.Path

case class Day1[A](parser:BodyParser[A]) extends DayTmpl[A] {
  lazy val content: HtmlFormat.Appendable = views.html.day1()

  def sync {
    val a = 1
    println(a)
  }
}