package controllers.bckp

import play.api.mvc._
import play.api.templates.HtmlFormat
import org.joda.time.DateTime
import org.joda.time.DateTime.now
import scala.List
import scala.collection.mutable.ListBuffer
import controllers.DayTmpl

case class Day15[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  lazy val content: HtmlFormat.Appendable = views.html.day15()

  implicit class ListOps[A](as:List[A]) {
    def addAll(os:List[A]) = as:::os
  }

  def sync:String = {
    s"""
      Don't know you but it seems that I saw several time (quite) the same code, right${???}
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
    val users = List.fill(nextInt(5)) {
                  User( randomText(10),
                        List.fill(10){
                          Tweet.random
                        })
                }

    // build some report
    val texts = ListBuffer.empty[String]
    val char = 'a'
    for (u <- users) {
      val name = u.name
      val tweets = u.tweets
      var count = 0
      //count all 'char' in all tweets
      for (tweet <- tweets) {
        val status = tweet.status
        for (c <- status) { //Awesome no!!!
          if (c == char) {
            ???
          }
        }
      }
      texts += s"$name has tweeted $count '$char'"
    }


    s"""
     ${texts.mkString("<br/>")}
    """
  }
}