package controllers.cheat

import play.api.mvc._
import play.api.templates.HtmlFormat
import org.joda.time.DateTime
import org.joda.time.DateTime.now
import scala.util.Random._
import scala.List
import controllers.DayTmpl

case class Day10[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  val content: String => HtmlFormat.Appendable = s => views.html.day10(s)

  implicit class ListOps[A](as:List[A]) {
    def add(a:A) = a::as
  }

  def sync:String = {
    s"""
      Nowadays, thee is a lot of buzz around the worth of immutability...
      And it's good...
      But we know that in Java it requires a lot of boilerplate and to be very careful
      Scala helps a lot thanks to case class and copy
    """
    // Time to switch to vals and case class
    case class User(name:String, tweets:List[Tweet]) {
      def tweet(s:String):User = this.copy(tweets = tweets.add(new Tweet(s, now())))
    }
    class Tweet(val status:String, val tm:DateTime) {
      override def toString() = s"$status at $tm"
    }

    val dude = new User("Dude", List.empty)
    def createTweet() = nextString(nextInt(140))
    s"How to tweet"
    val tweetedDude = dude.tweet(createTweet())

    s"""
      ${dude.name} has <strong>no tweets</strong>!
      <br/>
      ${tweetedDude.name} has tweeted <strong>${tweetedDude.tweets(0)}</strong>!
    """
  }
}