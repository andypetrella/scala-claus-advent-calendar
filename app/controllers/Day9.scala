package controllers

import play.api.mvc._
import play.api.templates.HtmlFormat
import scala.collection.mutable.ArrayBuffer
import org.joda.time.DateTime
import org.joda.time.DateTime.now
import scala.util.Random._

case class Day9[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  lazy val content: HtmlFormat.Appendable = views.html.day9()

  def sync:String = {
    s"""
      Can Scala classes be more than data container...${????("declare method User.tweet and override Tweet.toString")}
    """
    import collection.mutable.ListBuffer

    class User(var name:String, var tweets:ListBuffer[Tweet])
    class Tweet(var status:String, var tm:DateTime)
    ????("override def toString()") //prettify Tweet!

    var dude = new User("Dude", ????("ListBuffer.empty")) //let's start with an empty tweets' list

    def createTweet() = new Tweet(nextString(nextInt(140)), now())

    dude.tweets.append(createTweet())

    s"""
      ${dude.name} has tweeted <strong>${dude.tweets(0) /*look how to access an indexed elem*/}</strong>!
    """
  }
}