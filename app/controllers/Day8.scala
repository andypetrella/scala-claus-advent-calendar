package controllers

import play.api.mvc._
import play.api.templates.HtmlFormat
import scala.collection.mutable.ArrayBuffer
import org.joda.time.DateTime
import org.joda.time.DateTime.now

case class Day8[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  lazy val content: HtmlFormat.Appendable = views.html.day8()

  def sync:String = {
    s"""
      But wait there are some duplicated code ${????("create def for create tweets and transform to String")}
    """

    import util.Random._
    class User(var name:String, var tweets:List[Tweet])
    class Tweet(var status:String, var tm:DateTime)

    var dude = new User("Dude", List.fill(nextInt(50)){ new Tweet(nextString(nextInt(140)), now()) })
    var mate = new User("Mate", ????("oh not again"))

    ????("this can be a toTweets")
    var dudeTweets = new ArrayBuffer[String]()
    for (t <- dude.tweets) {
      dudeTweets.append(t.status)
    }
    var mateTweets = ????("use toTweets").asInstanceOf[ArrayBuffer[String]]

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