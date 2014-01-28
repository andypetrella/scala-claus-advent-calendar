package controllers

import play.api.mvc._
import play.api.templates.HtmlFormat
import org.joda.time.DateTime
import org.joda.time.DateTime.now
import scala.List

case class Day13[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  lazy val content: HtmlFormat.Appendable = views.html.day13()

  implicit class ListOps[A](as:List[A]) {
    def add(a:A) = a::as
  }

  def sync:String = {
    s"""
      What about hierarchy of types${????("it is very extensible thanks to trait")}
    """
    abstract class Metadata(val openid:String = "") {
      val provider:String = ""
      def auth():Boolean = true
    }
    case class User( name:String,
                     oid:String = "",
                     tweets:List[Tweet] = List.empty,
                     linkedInStatuses:List[LinkedInStatus] = List.empty
                   ) extends Metadata(????("use oid here")) {
      def tweet(t:Tweet):User = this.copy(tweets = tweets.add(t))
      def postLinkedInStatus(s:LinkedInStatus):User = this.copy(linkedInStatuses = linkedInStatuses.add(s))

      s"So, we need to extend for each new 'social' network ${????("use trait for twitter and linkedin")}"
    }
    StartFold
    object Util {
      import scala.util.Random.nextString
      def randomText(max:Int):String = nextString(max)
    }

    object Tweet {
      val MaxStatusLength = 140
      def random():Tweet = new Tweet(Util.randomText(MaxStatusLength))
    }
    class Tweet(val status:String, val tm:DateTime = now()) {
      override def toString() = s"tweet: $status at $tm"
    }

    object LinkedInStatus {
      val MaxStatusLength = 500
      def random():LinkedInStatus = new LinkedInStatus(Util.randomText(MaxStatusLength))
    }
    class LinkedInStatus(val status:String, val tm:DateTime = now()) {
      override def toString() = s"linkedin: $status at $tm"
    }
    EndFold

    val dude = new User("Dude")
    val socialDude = dude .tweet(Tweet.random())
                          .postLinkedInStatus(LinkedInStatus.random())

    s"""
      ${socialDude.name} has tweeted <strong>${socialDude.tweets(0)}</strong>!
      <br/>
      ${socialDude.name} has posted <strong>${socialDude.linkedInStatuses(0)}</strong>!
    """
  }
}