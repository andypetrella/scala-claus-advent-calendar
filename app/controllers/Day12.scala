package controllers

import play.api.mvc._
import play.api.templates.HtmlFormat
import org.joda.time.DateTime
import org.joda.time.DateTime.now
import scala.List

case class Day12[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  lazy val content: HtmlFormat.Appendable = views.html.day12()

  implicit class ListOps[A](as:List[A]) {
    def add(a:A) = a::as
  }

  def sync:String = {
    s"""
      Object Oriented is all about classes...
      Why not objects as well ${????("define an object as Util.random as Tweet.random")}
    """
    ????("Object.random = Random.nextString")
    case class User(name:String, tweets:List[Tweet] = List.empty) {
      def tweet(t:Tweet):User = this.copy(tweets = tweets.add(t))
    }
    class Tweet(val status:String, val tm:DateTime = now()) {
      override def toString() = s"$status at $tm"
    }
    ????("Tweet.random with MaxLength")

    val dude = new User("Dude")
    def createTweet() = ????("remove and use Tweet.random() below")
    val tweetedDude = dude.tweet(createTweet())

    s"""
      ${tweetedDude.name} has tweeted <strong>${tweetedDude.tweets(0)}</strong>!
    """
  }
}