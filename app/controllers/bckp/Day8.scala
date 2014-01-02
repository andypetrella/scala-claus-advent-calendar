package controllers.bckp

import play.api.mvc._
import play.api.templates.HtmlFormat
import scala.collection.mutable.ArrayBuffer
import org.joda.time.DateTime
import org.joda.time.DateTime.now
import controllers.DayTmpl

case class Day8[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  val content: String => HtmlFormat.Appendable = s => views.html.day8(s)

  def sync:String = {
    s"""
      But wait there are some duplicated code ${???}
    """

    import util.Random._
    class User(var name:String, var tweets:List[Tweet])
    class Tweet(var status:String, var tm:DateTime)

    var dude = new User("Dude", List.fill(nextInt(50)){ new Tweet(nextString(nextInt(140)), now()) })
    var mate = new User("Mate", ???)

    var dudeTweets = new ArrayBuffer[String]()
    for (t <- dude.tweets) {
      dudeTweets.append(t.status)
    }
    var mateTweets = ???.asInstanceOf[ArrayBuffer[String]]

    s"""
      <span onclick="$$('#dudeTweets-7').toggle()">${dude.name} tweeted ${dude.tweets.size}:</span>
      <ul id="dudeTweets-7" style="display:none">
        <li>${dudeTweets.mkString("</li><li>")}</li>
      </ul>
      <hr/>
      <span onclick="$$('#mateTweets-7').toggle()">${mate.name} tweeted ${mate.tweets.size}:</span>
      <ul id="mateTweets-7" style="display:none">
        <li>${mateTweets.mkString("</li><li>")}</li>
      </ul>
    """
  }
}