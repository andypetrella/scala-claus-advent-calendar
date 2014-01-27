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
  val cheats: Boolean = this.getClass.getPackage.getName.endsWith("cheat")
  val file:Path = fileFor(day, cheats)

  implicit val code = extractCode(file)

  val content:M => HtmlFormat.Appendable

  def apply(request: Request[A]): Future[SimpleResult] = {
    Future.successful(Ok(content(sync)))
  }

  def sync:M
}
object DayTmpl {

  def defaultPath = Path(".") / "app" / "controllers"
  def cheatingPath = defaultPath / "cheat"
  def fileFor(day:Int, cheats:Boolean):Path = (if (cheats) cheatingPath else defaultPath) / s"Day$day.scala"

  def actionFor[A](day:Int, parser:BodyParser[A], cheating:Boolean):DayTmpl[A, _] =
    day match { // needs a macro probably, even if not, some reflection
      case 1 => if (cheating) cheat.Day1(parser) else Day1(parser)
      case 2 => if (cheating) cheat.Day2(parser) else Day2(parser)
      case 3 => if (cheating) cheat.Day3(parser) else Day3(parser)
      case 4 => if (cheating) cheat.Day4(parser) else Day4(parser)
      case 5 => if (cheating) cheat.Day5(parser) else Day5(parser)
      case 6 => if (cheating) cheat.Day6(parser) else Day6(parser)
      case 7 => if (cheating) cheat.Day7(parser) else Day7(parser)
      case 8 => if (cheating) cheat.Day8(parser) else Day8(parser)
      case 9  => if (cheating) cheat.Day9(parser) else Day9(parser)
      case 10 => if (cheating) cheat.Day10(parser) else Day10(parser)
      case 11 => if (cheating) cheat.Day11(parser) else Day11(parser)
      case 12 => if (cheating) cheat.Day12(parser) else Day12(parser)
      case 13 => if (cheating) cheat.Day13(parser) else Day13(parser)
      case 14 => if (cheating) cheat.Day14(parser) else Day14(parser)
      case 15 => if (cheating) cheat.Day15(parser) else Day15(parser)
      case 16 => if (cheating) cheat.Day16(parser) else Day16(parser)
      case 17 => if (cheating) cheat.Day17(parser) else Day17(parser)
      case 18 => if (cheating) cheat.Day18(parser) else Day18(parser)
      case 19 => if (cheating) cheat.Day19(parser) else Day19(parser)
      case 20 => if (cheating) cheat.Day20(parser) else Day20(parser)
      case 21 => if (cheating) cheat.Day21(parser) else Day21(parser)
      case 22 => if (cheating) cheat.Day22(parser) else Day22(parser)
      case 23 => if (cheating) cheat.Day23(parser) else Day23(parser)
      case 24 => if (cheating) cheat.Day24(parser) else Day24(parser)
      case 25 => if (cheating) cheat.Day25(parser) else Day25(parser)
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