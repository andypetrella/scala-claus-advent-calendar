package controllers.bckp

import play.api.mvc._
import play.api.templates.HtmlFormat
import org.joda.time.DateTime
import scala.util.Random._
import scala.List
import controllers.DayTmpl

case class Day10[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  lazy val content: HtmlFormat.Appendable = views.html.day10()

  implicit class ListOps[A](as:List[A]) {
    def add(a:A) = a::as
  }

  def sync:String = {
    s"""
      Nowadays, thee is a lot of buzz around the worth of immutability...
      And it's good...
      But we know that in Java it requires a lot of boilerplate and to be very careful
      What about Scala ${???}
    """
    import collection.mutable.ListBuffer

    // Time to switch to vals and case class
    class User(val name:String, val tweets:ListBuffer[Tweet]) {
    }
    ??? // OUCH mutable.ListBuffer
    class Tweet(val status:String, val tm:DateTime) {
      override def toString() = s"$status at $tm"
    }

    val dude = new User("Dude", ListBuffer.empty)
    def createTweet() = nextString(nextInt(140))
    s"How to tweet ${???}"

    s"""
      ${dude.name} has tweeted <strong>${dude.tweets(0) /*look how to access an indexed elem*/}</strong>!
    """
  }
}