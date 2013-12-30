package controllers.cheat

import play.api.mvc._
import play.api.templates.HtmlFormat
import scala.collection.JavaConversions._
import controllers.DayTmpl

case class Day4[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  lazy val content: String => HtmlFormat.Appendable = s => views.html.day4(s)

  def sync:String = {
    // next string interpolation
    // next helpers > 1 to 10 and mkString
    // next type inference
    // next local function > createTweet()
    // next class function > User.tweet(st:String)
    // next immutability > case class && copy
    // next operator : toString, now, ++
    // next higher level functions >> map, filter
    // next infix notation

    // The code can be cleaner thanks to Scala Collections!
    import collection.mutable.ListBuffer
    import org.joda.time.DateTime
    import org.joda.time.DateTime.now
    import scala.util.Random.{nextInt, nextString}

    class User(var name:String, var tweets:List[Tweet])
    class Tweet(var status:String, var tm:DateTime)

    //counter
    var i = -1
    //Create Set using List builder
    var users:Set[User] = Set(List.fill(10) {
      //Create less than 50 random tweets with random status of length <= 100
      ???
      i+=1
      // Create a User
      ???
    }:_*)

    var info:ListBuffer[String] = new ListBuffer[String]()
    for (u <- users) {
      info.add(
        "User " + u.name + " has tweeted " + u.tweets.size + "times"
      )
    }
    var sb = new java.lang.StringBuffer()
    var first = true
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