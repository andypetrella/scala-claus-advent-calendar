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
      The random text aren't cute and, moreover, not representative... what could I do${????("")}
    """

    case class User( name:String, tweets:List[Tweet] = List.empty) {
      def tweet(t:Tweet):User = this.copy(tweets = t :: tweets)
    }
    object Util {
      // nextPrintableChar
      import scala.util.Random.nextString
      ????("validChars:Stream[Char] using nextPrintableChar and recursive")
      def randomText(max:Int):String = nextString(max).filter(c => ????("needs a filter on a stream and take max")) // REALLY??
    }

    object Tweet {
      val MaxStatusLength = 140
      def random:Tweet = new Tweet(Util.randomText(MaxStatusLength))
    }
    StartFold
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
      val name = user.name
      val tweets = user.tweets
      val count = tweets.map(tweet => tweet.status)
                        .flatMap(s => s)
                        .count(c => c == char)

      s"$name has tweeted $count '$char'"
    }
    EndFold

    s"""
     ${texts.mkString("<br/>")}
    """
  }
}
