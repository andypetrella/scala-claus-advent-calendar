package controllers.cheat

import play.api.mvc._
import play.api.templates.HtmlFormat
import org.joda.time.DateTime
import org.joda.time.DateTime.now
import scala.util.Random._
import controllers.DayTmpl

case class Day9[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  val content: String => HtmlFormat.Appendable = s => views.html.day9(s)

  def sync:String = {
    s"""
      Can Scala classes be more than data container...
    """
    import collection.mutable.ListBuffer

    class User(var name:String, var tweets:ListBuffer[Tweet]) {
      def tweet(s:String) {
        // this block returns Unit !!! => void
        tweets.append(new Tweet(s, now()))
      }
    }
    class Tweet(var status:String, var tm:DateTime) {
      // single line implementation : Note the return type has been omitted
      override def toString() = s"$status at $tm"
    }

    var dude = new User("Dude", ListBuffer.empty) //let's start with an empty tweets' list
    def createTweet() = nextString(nextInt(140))
    dude.tweet(createTweet())

    s"""
      ${dude.name} has tweeted <strong>${dude.tweets(0) /*look how to access an indexed elem*/}</strong>!
    """
  }
}