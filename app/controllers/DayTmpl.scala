package controllers

import play.api.mvc.{SimpleResult, Request, Action}
import scala.concurrent.Future
import play.api.mvc.Results._
import play.api.mvc.SimpleResult
import play.templates.TemplateMagic.RichDate
import play.api.templates.{Html, HtmlFormat}
import play.templates.Format
import scala.reflect.ClassTag
import scalax.file.Path

trait DayTmpl[A, M] extends Action[A] {
  val file:Path = Path(".") / "app" / "controllers" / (this.getClass.getSimpleName+".scala")

  implicit val code = file.lines()
                          .zipWithIndex
                          .dropWhile { case (l,i) =>
                            !l.dropWhile(_.isSpaceChar).startsWith("def sync")
                          }
                          .init
                          .toList

  val content:M => HtmlFormat.Appendable

  def apply(request: Request[A]): Future[SimpleResult] = {
    Future.successful(Ok(content(sync)))
  }

  def sync:M

}
