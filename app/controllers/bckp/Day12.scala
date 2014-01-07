package controllers.bckp

import play.api.mvc._
import play.api.templates.HtmlFormat
import org.joda.time.DateTime
import org.joda.time.DateTime.now
import scala.List
import controllers.DayTmpl

case class Day12[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  val content: String => HtmlFormat.Appendable = s => views.html.day12(s)

  implicit class ListOps[A](as:List[A]) {
    def add(a:A) = a::as
  }

  def sync:String = {
    s"""
      Object Oriented is all about classes...
      Why not objects as well ${???}
    """
    case class User(name:String, tweets:List[Tweet] = List.empty) {
      def tweet(t:Tweet):User = this.copy(tweets = tweets.add(t))
    }
    class Tweet(val status:String, val tm:DateTime = now()) {
      override def toString() = s"$status at $tm"
    }

    val dude = new User("Dude")
    def createTweet() = ???
    val tweetedDude = dude.tweet(createTweet())

    s"""
      ${tweetedDude.name} has tweeted <strong>${tweetedDude.tweets(0)}</strong>!
    """
  }
}