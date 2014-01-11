package controllers

import play.api.mvc._
import play.api.templates.HtmlFormat
import org.joda.time.DateTime
import org.joda.time.DateTime.now
import scala.List

case class Day17[A](parser: BodyParser[A]) extends DayTmpl[A, String] {
  val content: String => HtmlFormat.Appendable = s => views.html.day17(s)

  implicit class ListOps[A](as: List[A]) {
    def addAll(os: List[A]) = as ::: os
  }

  def sync: String = {
    s"""
      ${???}
    """

    s"""
    """
  }
}
