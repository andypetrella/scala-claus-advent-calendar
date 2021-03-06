package controllers

import play.api.mvc._
import play.api.templates.HtmlFormat
import org.joda.time.DateTime
import scala.util.Random._
import scala.List
import org.joda.time.DateTime._

case class Day11[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  lazy val content: HtmlFormat.Appendable = views.html.day11()

  implicit class ListOps[A](as:List[A]) {
    def add(a:A) = a::as
  }

  def sync:String = {
    s"""
      So copy is special and has a special notation...
      Is it the only big deal ${????("def can have default params")}
    """
    case class User(name:String, tweets:List[Tweet]= ????("List.empty")) {
      def tweet(s:String = ????("still alive and well")):User = this.copy(tweets = tweets.add(new Tweet(s, ????("default to now"))))
    }
    class Tweet(val status:String, val tm:DateTime) {
      override def toString() = s"$status at $tm"
    }

    val dude = new User("Dude", ????("nothing..."))
    def createTweet() = nextString(nextInt(140))
    val tweetedDude = dude.tweet(????("nothing...")) //still alive and well

    s"""
      ${dude.name} has <strong>no tweets</strong>!
      <br/>
      ${tweetedDude.name} has tweeted <strong>${tweetedDude.tweets(0)}</strong>!
    """
  }
}