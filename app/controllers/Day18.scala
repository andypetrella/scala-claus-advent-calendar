package controllers

import play.api.mvc._
import play.api.templates.HtmlFormat
import org.joda.time.DateTime
import org.joda.time.DateTime.now
import scala.List
import scala.util.Random._

case class Day18[A](parser: BodyParser[A]) extends DayTmpl[A, String] {
  lazy val content: HtmlFormat.Appendable = views.html.day18()

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
      What about the 1 billion dollar mistake ${????("null to Option")}
    """

    object Person {
      import scala.util.Random.nextPrintableChar
      def validChars:Stream[Char] = (nextPrintableChar #:: validChars).filter(c => c.isWhitespace || (('a' to 'z') contains c))
      def randomText(max:Int):String = validChars.take(max).mkString("")
      def randomCountry:Country =
        if (nextBoolean())
          Belgium("FR")
        else if (nextBoolean())
          Belgium("NL")
        else if (nextBoolean())
          England
        else if (nextBoolean())
          France
        else
          ????("null ...")
      def random = Person(randomText(10), randomCountry)
    }
    case class Person(name:String, nationality:Country)
    ????("Use Option[Country]")

    val persons = List.fill(1000)(Person.random)

    val greetings = persons.map { person =>
      ????("return ... if country not defined!")
      ????("val nationality = person.nationality.get")
      if (person.nationality.isInstanceOf[Belgium]) {
        val belgian = person.nationality.asInstanceOf[Belgium]
        if (belgian.lg == "FR") {
          s"Salut, m'fi..."
        } else if (belgian.lg == "NL" || belgian.lg == "DE") {
          s"Hallo, ${person.name}!"
        }
      } else if (person.nationality == England) {
        s"Hello, ${person.name}!"
      } else if (person.nationality == France) {
        s"Salut, ${person.name} !"
      } else {
        s"..."
      }
    }

    s"""
      <ul>
        <li>${greetings.mkString("</li><li>")}</li>
      </ul>
    """
  }
}