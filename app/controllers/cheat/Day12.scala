package controllers.cheat

import play.api.mvc._
import play.api.templates.HtmlFormat
import org.joda.time.DateTime
import org.joda.time.DateTime.now
import scala.List
import controllers.DayTmpl

case class Day12[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  lazy val content: HtmlFormat.Appendable = views.html.day12()

  implicit class ListOps[A](as:List[A]) {
    def add(a:A) = a::as
  }

  def sync:String = {
    s"""
      Object Oriented is all about classes...
      Why not objects as well, of course we can...
    """
    case class User(name:String, tweets:List[Tweet] = List.empty) {
      def tweet(t:Tweet):User = this.copy(tweets = tweets.add(t))
    }
    object Util {
      import scala.util.Random.nextString
      def randomText(max:Int):String = nextString(max)
    }
    object Tweet {
      val MaxStatusLength = 140
      def random():Tweet = new Tweet(Util.randomText(MaxStatusLength))
    }
    class Tweet(val status:String, val tm:DateTime = now()) {
      override def toString() = s"$status at $tm"
    }

    val dude = new User("Dude")
    
    val tweetedDude = dude.tweet(Tweet.random())

    s"""
      ${tweetedDude.name} has tweeted <strong>${tweetedDude.tweets(0)}</strong>!
    """
  }
}