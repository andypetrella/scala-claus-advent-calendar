package controllers.cheat

import play.api.mvc._
import play.api.templates.HtmlFormat
import scala.collection.JavaConversions._
import controllers.DayTmpl

case class Day3[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  lazy val content: HtmlFormat.Appendable = views.html.day3()

  var random = new java.util.Random()


  def sync:String = {
    //TIME TO REMOVE SOME NOISE | ; return import
    import java.util._
    import org.joda.time.DateTime
    import org.joda.time.DateTime.now

    class User(var name:String, var tweets:List[Tweet]) {
    }
    class Tweet(var status:String, var tm:DateTime) {
    }

    var i:Int = 0
    var users:Set[User] = new HashSet[User]()
    while (i < 10) {
      var name:String = "user"+i
      var tweets:List[Tweet] = new ArrayList[Tweet]()
      var j:Int = 0
      while(j < random.nextInt()) {
        tweets.add(
          new Tweet("tweet-"+name+"-"+j, now())
        )
        j += 1
      }

      users.add(
        new User(name, tweets)
      )
      i += 1
    }
    var info:List[String] = new ArrayList[String]()
    for (u <- users) {
      info.add(
        "User " + u.name + " has tweeted " + u.tweets.size() + "times"
      )
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