package controllers.cheat

import play.api.mvc._
import play.api.templates.HtmlFormat
import scala.collection.mutable.ArrayBuffer
import org.joda.time.DateTime
import org.joda.time.DateTime.now
import controllers.DayTmpl

case class Day8[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  lazy val content: HtmlFormat.Appendable = views.html.day8()

  def sync:String = {
    s"""
      But wait there are some duplicated code
    """

    import util.Random._
    class User(var name:String, var tweets:List[Tweet])
    class Tweet(var status:String, var tm:DateTime)

    def createTweets(max:Int) = List.fill(nextInt(max)){ new Tweet(nextString(nextInt(140)), now()) }
    var dude = new User("Dude", createTweets(50))
    var mate = new User("Mate", createTweets(10))

    def toTweets(user:User) = {
      var ts = new ArrayBuffer[String]()
      for (t <- user.tweets) {
        ts.append(t.status)
      }
      ts
    }

    var dudeTweets = toTweets(dude)
    var mateTweets = toTweets(mate)

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