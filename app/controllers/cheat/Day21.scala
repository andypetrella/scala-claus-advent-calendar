package controllers.cheat

import play.api.mvc._
import play.api.templates.HtmlFormat
import scala.List
import scala.util.Random._
import scala.Some
import controllers.DayTmpl

case class Day21[A](parser: BodyParser[A]) extends DayTmpl[A, String] {
  val content: String => HtmlFormat.Appendable = s => views.html.day21(s)

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
  trait Gender
  case object Male extends Gender
  case object Female extends Gender

  def walloon(gender:Gender) = if (gender == Male) "m'fi" else "m'feye"
  def flemish(gender:Gender) = if (gender == Male) "meneer" else "mevrouw"
  def deutsch(gender:Gender) = "arbeiter!"
  def english(gender:Gender) = "guy"
  def french(gender:Gender) = if (gender == Male) "toi" else "Mademoiselle"

  def randomizer[A](gs: List[(Double, A)], c:Double = nextDouble()):A =
    gs.sortBy(x => x._1).filter(x => c <= x._1).head._2

  def sync: String = {
    s"""
      You're not happy, still! We can do better!
    """

    object Person {
      import scala.util.Random.nextPrintableChar
      def validChars:Stream[Char] = (nextPrintableChar #:: validChars).filter(c => c.isWhitespace || (('a' to 'z') contains c))
      def randomText(max:Int):String = validChars.take(max).mkString("")
      def randomCountry:Option[Country] = randomizer(List(
        (1.0/6, Some(Belgium("FR"))),
        (2.0/6, Some(Belgium("NL"))),
        (3.0/6, Some(Belgium("DE"))),
        (4.0/6, Some(England)),
        (5.0/6, Some(France)),
        (6.0/6, None)
      ))

      def randomGender = randomizer(List((0.3, Some(Male)), (0.7, Some(Female)), (1.0, None)))
      def random = Person(randomText(10), randomCountry, randomGender)
    }
    case class Person(name:String, nationality:Option[Country], gender:Option[Gender])

    val persons = List.fill(1000)(Person.random)

    val greetings = persons.map { person =>
      val greet = for {
        nationality <- person.nationality
        gender      <- person.gender
      } yield {
        if (nationality.isInstanceOf[Belgium]) {
          val belgian = nationality.asInstanceOf[Belgium]
          if (belgian.lg == "FR") {
            Some(s"Salut, ${walloon(gender)}...")
          } else if (belgian.lg == "NL") {
            Some(s"Hallo, ${flemish(gender)}!")
          } else if (belgian.lg == "DE") {
            Some(s"Hallo, ${deutsch(gender)}!")
          } else {
            None
          }
        } else if (nationality == England) {
          Some(s"Hello, ${english(gender)}!")
        } else if (nationality == France) {
          Some(s"Salut, ${french(gender)} !")
        } else {
          None
        }
      }
      greet.getOrElse(s"...")
    }

    s"""
      <ul>
        <li>${greetings.mkString("</li><li>")}</li>
      </ul>
    """
  }
}
