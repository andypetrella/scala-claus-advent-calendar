package controllers

import play.api.mvc._
import scala.concurrent.Future
import play.api.mvc.Results._
import play.api.templates.HtmlFormat
import scalax.file.Path

import play.api.mvc.SimpleResult

trait DayTmpl[A, M] extends Action[A] {
  import DayTmpl._
  val day:Int = this.getClass.getSimpleName.drop("Day".length).toInt
  val file:Path = fileFor(day)

  implicit val code = extractCode(file)

  val content:M => HtmlFormat.Appendable

  def apply(request: Request[A]): Future[SimpleResult] = {
    Future.successful(Ok(content(sync)))
  }

  def sync:M
}
object DayTmpl {

  def fileFor(day:Int):Path = Path(".") / "app" / "controllers" / s"Day$day.scala"

  def actionFor[A](day:Int, parser:BodyParser[A]):DayTmpl[A, _] =
    day match {
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
      case 14 => Day14(parser)
      case 15 => Day15(parser)
      case 16 => Day16(parser)
      case 17 => Day17(parser)
      case 18 => Day18(parser)
      case 19 => Day19(parser)
      case 20 => Day20(parser)
      case 21 => Day21(parser)
      case 22 => Day22(parser)
      case 23 => Day23(parser)
      case 24 => Day24(parser)
      case 25 => Day25(parser)
    }
  def extractCode(file:Path)= file.lines()
                                  .zipWithIndex
                                  .dropWhile { case (l,i) =>
                                    !l.dropWhile(_.isSpaceChar).startsWith("def sync")
                                  }
                                  .init //last line must be last '}'
                                  .toList
}