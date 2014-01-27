package controllers

import play.api.mvc._
import play.api.templates.HtmlFormat
import scala.collection.JavaConversions._
import org.joda.time.DateTime

case class Day4[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  lazy val content: String => HtmlFormat.Appendable = s => views.html.day4(s)

  def sync:String = {
    // The code can be cleaner thanks to Scala Collections!
    import org.joda.time.DateTime
    import org.joda.time.DateTime.now
    import scala.util.Random.{nextInt, nextString}
    import collection.mutable.ListBuffer

    class User(var name:String, var tweets:List[Tweet])
    class Tweet(var status:String, var tm:DateTime)

    //counter
    var i:Int = -1
    //Create Set using List builder
    var users:Set[User] = Set(List.fill(10) {
      //Create less than 50 random tweets with random status of length <= 140
      ????("List.fill(nextInt(50)) Tweets")
      i+=1
      // Create a User
      ????("new User")
    }:_*)

    var info:ListBuffer[String] = new ListBuffer[String]()
    for (u <- users) {
      info += ("User " + u.name + " has tweeted " + u.tweets.size + "times")
    }
    var sb:StringBuffer = new StringBuffer()
    var first:Boolean = true
    //for-loop to aggregate
    for (n <- info) {
      if (first) {
        first = false
      } else {
        sb.append("<br/>")
      }
      sb.append(n)
    }
    sb.toString()
  }
}
