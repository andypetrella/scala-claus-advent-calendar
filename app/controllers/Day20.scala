package controllers

import play.api.mvc._
import play.api.templates.HtmlFormat
import scala.List
import scala.util.Random._
import controllers.DayTmpl

case class Day20[A](parser: BodyParser[A]) extends DayTmpl[A, String] {
  val content: String => HtmlFormat.Appendable = s => views.html.day20(s)

  trait Country {
    def lg:String
  }
  case class Belgium(lg:String) extends Country
  case object England extends Country {
    val lg = "EN"
  }
  case object France extends Country {
    val lg = "FR"
  }

  def sync: String = {
    s"""
      What, huh, duplication code...${????("use flatMap on country to remove the else equal to the getOrElse")}
    """
    StartFold
    object Person {
      import scala.util.Random.nextPrintableChar
      def validChars:Stream[Char] = (nextPrintableChar #:: validChars).filter(c => c.isWhitespace || (('a' to 'z') contains c))
      def randomText(max:Int):String = validChars.take(max).mkString("")
      def randomCountry:Option[Country] =
        if (nextBoolean())
          Some(Belgium("FR"))
        else if (nextBoolean())
          Some(Belgium("NL"))
        else if (nextBoolean())
          Some(Belgium("DE"))
        else if (nextBoolean())
          Some(England)
        else if (nextBoolean())
          Some(France)
        else None
      def random = Person(randomText(10), randomCountry)
    }
    case class Person(name:String, nationality:Option[Country])

    val persons = List.fill(1000)(Person.random)
    EndFold

    val greetings = persons.map { person =>
      person.nationality.map { nationality =>
        if (nationality.isInstanceOf[Belgium]) {
          val belgian = nationality.asInstanceOf[Belgium]
          if (belgian.lg == "FR") {
            s"Salut, m'fi..."
          } else if (belgian.lg == "NL" || belgian.lg == "DE") {
            s"Hallo, ${person.name}!"
          }
        } else if (nationality == England) {
          s"Hello, ${person.name}!"
        } else if (nationality == France) {
          s"Salut, ${person.name} !"
        } else {
          s"${????("first ...")}"
        }
      }.getOrElse(s"${????("second ...")}")
    }

    s"""
      <ul>
        <li>${greetings.mkString("</li><li>")}</li>
      </ul>
    """
  }
}