package controllers

import play.api.mvc._
import play.api.templates.HtmlFormat
import org.joda.time.DateTime
import org.joda.time.DateTime.now
import scala.util.Random._
import scala.List

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
      What about Scala ${????("User is a case and as a copy")}
    """
    import collection.mutable.ListBuffer

    // Time to switch to vals and case class
    ????("we use val.... but case doesn't need it")
    class User(val name:String, val tweets:ListBuffer[Tweet] = ????("use List")) {
      ????("What about def tweet(s) with List => copy")
    }
    class Tweet(val status:String, val tm:DateTime) {
      override def toString() = s"$status at $tm"
    }

    ????("what about using a val?")
    var dude = new User("Dude", ListBuffer.empty) /// WARN!
    def createTweet() = nextString(nextInt(140))
    s"How to tweet ${????("val tweetedDude = dude.tweet(createTweet())")}"

    s"""
      ${dude.name} has tweeted <strong>${dude.tweets(0) /*look how to access an indexed elem*/}</strong>!
    """
  }
}