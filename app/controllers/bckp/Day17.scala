package controllers.bckp

import play.api.mvc._
import play.api.templates.HtmlFormat
import org.joda.time.DateTime
import org.joda.time.DateTime.now
import scala.List
import controllers.DayTmpl

case class Day17[A](parser: BodyParser[A]) extends DayTmpl[A, String] {
  lazy val content: HtmlFormat.Appendable = views.html.day17()

  implicit class ListOps[A](as: List[A]) {
    def addAll(os: List[A]) = as ::: os
  }

  def sync: String = {
    s"""
      The random text aren't cute and, moreover, not representative... what could I do${???}
    """

    case class User( name:String, tweets:List[Tweet] = List.empty) {
      def tweet(t:Tweet):User = this.copy(tweets = t :: tweets)
    }
    object Util {
      import scala.util.Random.nextString
      def randomText(max:Int):String = nextString(max).filter(c => ???) // REALLY??
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
    val users = List.fill(nextInt(5)) {
      User( randomText(10),
        List.fill(10){
          Tweet.random
        })
    }

    val char = 'a'
    val texts = users.map { user =>
      val name = user.name
      val tweets = user.tweets
      val count = tweets.map(tweet => tweet.status)
                        .flatMap(s => s)
                        .count(c => c == char)

      s"$name has tweeted $count '$char'"
    }

    s"""
     ${texts.mkString("<br/>")}
    """
  }
}
