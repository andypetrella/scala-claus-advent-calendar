package controllers

import play.api.mvc._
import play.api.templates.HtmlFormat
import org.joda.time.DateTime
import org.joda.time.DateTime.now
import scala.List

case class Day25[A](parser: BodyParser[A]) extends DayTmpl[A, String] {
  val content: String => HtmlFormat.Appendable = s => views.html.day25(s)

  def sync: String = {
    s"""
      Don't you feel sometimes bored passing along the same parameters... again and again${???}
    """

    s"""
    """
  }
}
