package controllers.cheat

import play.api.mvc._
import play.api.templates.HtmlFormat
import org.joda.time.DateTime
import scala.util.Random._
import scala.List
import org.joda.time.DateTime._
import controllers.DayTmpl

case class Day11[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  val content: String => HtmlFormat.Appendable = s => views.html.day11(s)

  implicit class ListOps[A](as:List[A]) {
    def add(a:A) = a::as
  }

  def sync:String = {
    s"""
      So copy is special and has a special notation...
      Is it the only big deal...
      No all parameters can be set with a default value and left alone at call side
    """
    case class User(name:String, tweets:List[Tweet] = List.empty) {
      def tweet(s:String = "still alive and well"):User = this.copy(tweets = tweets.add(new Tweet(s)))
    }
    class Tweet(val status:String, val tm:DateTime = now()) {
      override def toString() = s"$status at $tm"
    }

    val dude = new User("Dude")
    val tweetedDude = dude.tweet() //still alive and well

    s"""
      ${dude.name} has <strong>no tweets</strong>!
      <br/>
      ${tweetedDude.name} has tweeted <strong>${tweetedDude.tweets(0)}</strong>!
    """
  }
}