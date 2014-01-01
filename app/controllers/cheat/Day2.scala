package controllers.cheat

import play.api.mvc._
import play.api.templates.HtmlFormat
import scala.collection.JavaConversions._
import controllers.DayTmpl

case class Day2[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  lazy val content: String => HtmlFormat.Appendable = s => views.html.day2(s)

  var random = new java.util.Random();

  def sync:String = {
    //And we can even write Java-like code...
    //Create classes
    class User(var name:String, var tweets:java.util.List[Tweet]) {
    }
    class Tweet(var statu:String, var tm:org.joda.time.DateTime) {
    }

    //counter
    var i:Int = 0;
    //bag
    var users:java.util.Set[User] = new java.util.HashSet[User]();
    //while-loop for building
    while (i < 10) {
      var name:String = "user"+i;
      var tweets:java.util.List[Tweet] = new java.util.ArrayList[Tweet]();
      var j:Int = 0;
      while(j < random.nextInt()) {
        tweets.add(
          new Tweet("tweet-"+name+"-"+j, org.joda.time.DateTime.now())
        );
        j += 1;
      }

      users.add(
        new User(name, tweets)
      );
      i += 1;
    }
    var info:java.util.List[String] = new java.util.ArrayList[String]();
    //for-loop for conversion
    for (u <- users) {
      info.add(
        "User " + u.name + " has tweeted " + u.tweets.size() + "times"
      );
    }
    //String accumulation
    var sb:StringBuffer = new StringBuffer();
    var first = true;
    //for-loop to aggregate
    for (n <- info) {
      if (first) {
        first = false;
      } else {
        sb.append("<br/>");
      }
      sb.append(n);
    }
    return sb.toString();
  }
}