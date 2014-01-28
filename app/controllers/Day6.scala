package controllers

import play.api.mvc._
import play.api.templates.HtmlFormat
import scala.collection.mutable.ArrayBuffer

case class Day6[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  lazy val content: HtmlFormat.Appendable = views.html.day6()

  def sync:String = {
    // ordered type values has 'to'
    // StringBuilder is is still painful for simple tasks
    StartFold
    import util.Random._
    class User(var name:String)

    var speaker:User = new User("Dude")
    var listener:User = new User("Mate")
    EndFold

    var tellingWs:ArrayBuffer[String] = new ArrayBuffer[String]()
    //generate lower case word with less than 10 chars
    var word:StringBuilder = new StringBuilder()
    for (i <- 10 to 0 by ????("-1")) {
      for (c <- 'a' to ????("z")) {
        if (nextBoolean()) {
          word.append(c)
        }
      }
      tellingWs.append(word.toString())
    }
    var telling:String = ????("mkString with SPACE")

    var answerWs:ArrayBuffer[String] = new ArrayBuffer[String]()
    //generate lower case word with less than 30 chars
    for (i <- 30 to 0 by -1) {
      var word:StringBuilder = new StringBuilder()
      for (c <- 'A' to 'Z' ) {
        if (nextBoolean()) {
          word.append(c)
        }
      }
      answerWs.append(word.toString())
    }
    var answer:String = ????("mkString with DASH")

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