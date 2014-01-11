package controllers

/**
 * Created by noootsab on 1/11/14.
 */

import play.api.mvc._
import play.api.templates.HtmlFormat
import org.joda.time.DateTime
import org.joda.time.DateTime.now
import scala.List

case class Day20[A](parser: BodyParser[A]) extends DayTmpl[A, String] {
  val content: String => HtmlFormat.Appendable = s => views.html.day20(s)

  def sync: String = {
    s"""
      ${???}
    """

    s"""
    """
  }
}
