package controllers.cheat

import play.api.mvc._
import play.api.templates.HtmlFormat
import scala.List
import scala.util.Random._
import controllers.DayTmpl

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
      What about the 1 billion dollar mistake => Option
    """

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
          Some(England) 
        else if (nextBoolean()) 
          Some(France)
        else None
      def random = Person(randomText(10), randomCountry)
    }
    case class Person(name:String, nationality:Option[Country])

    val persons = List.fill(1000)(Person.random)

    val greetings = persons.map { person =>
      if (!person.nationality.isDefined) {
        "..."
      } else {
        val nationality = person.nationality.get
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
          s"..."
        }
      }
    }

    s"""
      <ul>
        <li>${greetings.mkString("</li><li>")}</li>
      </ul>
    """
  }
}