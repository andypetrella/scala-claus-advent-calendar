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

trait DayTmpl[A] extends Action[A] {
  lazy val file:Path = Path(".") / "app" / "controllers" / (this.getClass.getSimpleName+".scala")

  lazy val code = file.lines()
                          .zipWithIndex
                          .dropWhile { case (l,i) =>
                            !l.dropWhile(_.isSpaceChar).startsWith("def sync")
                          }
                          .init
                          .toList

  val content:HtmlFormat.Appendable

  def apply(request: Request[A]): Future[SimpleResult] = {
    sync
    Future.successful(Ok(content))
  }

  def sync:Unit

}
