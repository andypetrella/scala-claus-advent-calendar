package controllers.cheat

import play.api.mvc._
import play.api.templates.HtmlFormat
import org.joda.time.DateTime
import org.joda.time.DateTime.now
import scala.List
import controllers.DayTmpl

case class Day16[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  val content: String => HtmlFormat.Appendable = s => views.html.day16(s)

  implicit class ListOps[A](as:List[A]) {
    def addAll(os:List[A]) = as:::os
  }

  def sync:String = {
    s"""
      There must be a way to clean that up... Isn't it
    """

    case class User( name:String, tweets:List[Tweet] = List.empty) {
      def tweet(t:Tweet):User = this.copy(tweets = t :: tweets)
    }
    object Util {
      import scala.util.Random.nextString
      def randomText(max:Int):String = nextString(max)
    }

    object Tweet {
      val MaxStatusLength = 140
      def random:Tweet = new Tweet(Util.randomText(MaxStatusLength))
    }
    class Tweet(val status:String, val tm:DateTime = now()) {
      override def toString = s"tweet: $status at $tm"
    }

    import Util._
    import scala.util.Random.nextInt
    //create a bunch of users with a bunch of tweets
    val users = List.fill(nextInt(50)) {
      User( randomText(10),
        List.fill(100){
          Tweet.random
        })
    }

    val char = 'a'
    val texts = users.map { user =>
      val ones = for {
        tweet <- user.tweets
        c     <- tweet.status if c == char
      } yield 1
      s"${user.name} has tweeted ${ones.sum} '$char'"
    }

    s"""
     ${texts.mkString("<br/>")}
    """
  }
}