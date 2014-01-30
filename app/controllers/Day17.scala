package controllers

import play.api.mvc._
import play.api.templates.HtmlFormat
import org.joda.time.DateTime
import org.joda.time.DateTime.now
import scala.List

case class Day17[A](parser: BodyParser[A]) extends DayTmpl[A, String] {
  lazy val content: HtmlFormat.Appendable = views.html.day17()

  def sync: String = {
    s"""
      Map, flatMap, filter... there must be a better way, no ${????("for-comp!")}
    """

    StartFold
    case class User( name:String, tweets:List[Tweet] = List.empty) {
      def tweet(t:Tweet):User = this.copy(tweets = t :: tweets)
    }
    object Util {
      // nextPrintableChar
      import scala.util.Random.nextString
      import scala.util.Random.nextPrintableChar
      def validChars:Stream[Char] = (nextPrintableChar #:: validChars).filter(c => c.isWhitespace || (('a' to 'z') contains c))
      def randomText(max:Int):String = validChars.take(max).mkString("")
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
    EndFold

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
