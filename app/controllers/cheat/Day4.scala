package controllers.cheat

import play.api.mvc._
import play.api.templates.HtmlFormat
import scala.collection.JavaConversions._
import controllers.DayTmpl

case class Day4[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  lazy val content: HtmlFormat.Appendable = views.html.day4()

  def sync:String = {
    // The code can be cleaner thanks to Scala Collections!
    import collection.mutable.ListBuffer
    import org.joda.time.DateTime
    import org.joda.time.DateTime.now
    import scala.util.Random.{nextInt, nextString}

    class User(var name:String, var tweets:List[Tweet])
    class Tweet(var status:String, var tm:DateTime)

    //counter
    var i:Int = -1
    //Create Set using List builder
    var users:Set[User] = Set(List.fill(10) {
      //Create less than 50 random tweets with random status of length <= 140
      var tweets:List[Tweet] = List.fill(nextInt(50)) {
        new Tweet(nextString(nextInt(140)), now())
      }
      i+=1
      // Create a User
      new User("User"+i, tweets)
    }:_*)

    var info:ListBuffer[String] = new ListBuffer[String]()
    for (u <- users) {
      info.add(
        "User " + u.name + " has tweeted " + u.tweets.size + "times"
      )
    }
    var sb:StringBuffer = new java.lang.StringBuffer()
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