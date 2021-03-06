package controllers.cheat

import play.api.mvc._
import play.api.templates.HtmlFormat
import scala.collection.mutable.ArrayBuffer
import controllers.DayTmpl

case class Day6[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  lazy val content: HtmlFormat.Appendable = views.html.day6()

  def sync:String = {
    // ordered type values has 'to'
    // StringBuilder is is still painful for simple tasks
    import util.Random._
    class User(var name:String)

    var speaker:User = new User("Dude")
    var listener:User = new User("Mate")

    var tellingWs:ArrayBuffer[String] = new ArrayBuffer[String]()
    for (i <- 10 to 0 by -1) {
      var word:StringBuilder = new StringBuilder()
      for (c <- 'a' to 'z') {
        if (nextBoolean()) {
          word.append(c)
        }
      }
      tellingWs.append(word.toString())
    }
    var telling:String = tellingWs.mkString(" ")

    var answerWs:ArrayBuffer[String] = new ArrayBuffer[String]()
    for (i <- 30 to 0 by -1) {
      var word:StringBuilder = new StringBuilder()
      for (c <- 'A' to 'Z' ) {
        if (nextBoolean()) {
          word.append(c)
        }
      }
      answerWs.append(word.toString())
    }
    var answer:String = answerWs.mkString("-")

    s"""<p>Did you know that ${speaker.name} told the following to ${listener.name}?</p>
        <em><quote>
        $telling
        </quote></em>
        <hr/>
        <p>And you know what ${listener.name} answered? This:</p>
        <em><strong><quote>
        ${answer}
       </quote></strong></em>
    """
  }
}