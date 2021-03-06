package controllers

import play.api.mvc._
import play.api.templates.HtmlFormat
import org.joda.time.DateTime
import org.joda.time.DateTime.now
import scala.List
import scala.collection.mutable.ListBuffer

case class Day14[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  lazy val content: HtmlFormat.Appendable = views.html.day14()

  implicit class ListOps[A](as:List[A]) {
    def addAll(os:List[A]) = as:::os
  }

  def sync:String = {
    s"""
      What do you think about removing even more noise${????("remove parenthesis for random")}
    """
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

    val tweets = List.fill(10) { Tweet.random() }
    val moreTweets = List.fill(5) { Tweet.random() }

    val allTweets = tweets.addAll(????("use  ++"))

    s"""
      <ul>
        ${for (t <- allTweets)
          yield "<li>" + t.toString() + "</li>"
        }
      </ul>
    """
  }
}