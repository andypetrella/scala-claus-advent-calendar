package controllers

import play.api.mvc._
import scala.concurrent.Future
import play.api.mvc.Results._
import play.api.templates.HtmlFormat
import scalax.file.Path

import play.api.mvc.SimpleResult
import scala.collection.SeqView

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
    day match { // needs a macro probably, even if not, some reflection
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
  def extractSync(file:Path)= file.lines()
                                  .zipWithIndex
                                  .dropWhile { case (l,i) =>
                                    !l.dropWhile(_.isSpaceChar).startsWith("def sync")
                                  }
                                  .init //last line must be last '}'
                                  .toList
  def extractFolds(code:List[(String, Int)]):List[((Int, Int),(Int, Int))] = {
    val starts = code.collect { case (line, idx) if line.dropWhile(_.isWhitespace).startsWith(StartFold) => (idx, 0)}
    val ends = code.collect { case (line, idx) if line.dropWhile(_.isWhitespace).startsWith(EndFold) => (idx, line.size)}
    //must be well balanced
    require(starts.length == ends.length, s"not well balanced: \n$starts\n---\n$ends")
    starts zip ends
  }
  private[this] def hasExtendedMark(line:String) = line.contains("????")
  private[this] def hasMark(line:String) = line.contains("???")
  private[this] val MarkMessage = new util.matching.Regex("""\?\?\?\?\("([^"]*)"\)""")
  private[this] def mark(line:String):String = MarkMessage.findFirstMatchIn(line).toList.head.group(1)
  def extractMarks(code:List[(String, Int)]):List[(Option[String], Int)] = {
    code.collect {
      case (line, idx) if hasExtendedMark(line) => (Some(mark(line)), idx)
      case (line, idx) if hasMark(line) => (None, idx)
    }
  }

  def extractCode(file:Path)= {
    val lines = extractSync(file)
    val folds = extractFolds(lines)
    val marks = extractMarks(lines)

    val pureLines = lines.map { case (line, i) =>
      val mark: Option[(Option[String], Int)] = marks.find(_._2 == i)
      val pureLine = mark match {
        case Some((Some(msg), _)) => line.replaceAllLiterally("????(\""+msg+"\")", "???")
        case x => line
      }
      (pureLine, i)
    }

    val prepareMarks = marks.map {
      case (None, x) => ("Do what you want...", x)
      case (Some(t), x) => (t, x)
    }
    Code(pureLines, folds, prepareMarks)
  }
}