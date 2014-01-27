package controllers

import play.api.mvc._
import play.api.templates.HtmlFormat
import scala.collection.mutable.{StringBuilder, ArrayBuffer}
import org.joda.time.DateTime
import org.joda.time.DateTime.now
import scala.StringBuilder

case class Day7[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  val content: String => HtmlFormat.Appendable = s => views.html.day7(s)

  def sync:String = {
    s"""
      Did you notice that we needed to type so many times things
      that are so obvious ${????("remove types for users, arraybuffers")}
    """
    StartFold
    import util.Random._
    class User(var name:String, var tweets:List[Tweet])
    class Tweet(var status:String, var tm:DateTime)
    EndFold

    var dude:User = new User("Dude", List.fill(nextInt(50)){ new Tweet(nextString(nextInt(140)), now()) })
    var mate:User = new User("Mate", List.fill(nextInt(50)){ new Tweet(nextString(nextInt(140)), now()) })

    var dudeTweets:ArrayBuffer[String] = new ArrayBuffer[String]()
    for (t <- dude.tweets) {
      dudeTweets.append(t.status)
    }
    var mateTweets:ArrayBuffer[String] = new ArrayBuffer[String]()
    for (t <- mate.tweets) {
      mateTweets.append(t.status)
    }

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