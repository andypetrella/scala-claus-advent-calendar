package controllers

import play.api.mvc._
import play.api.templates.HtmlFormat
import scala.collection.JavaConversions._

case class Day3[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  lazy val content: String => HtmlFormat.Appendable = s => views.html.day3(s)

  var random = new java.util.Random()


  def sync:String = {
    //TIME TO REMOVE SOME NOISE  | ; return import { } now
    ???

    class User(var name:String, var tweets:java.util.List[Tweet]) {
    }
    class Tweet(var status:String, var tm:org.joda.time.DateTime) {
    }

    var i:Int = 0;
    var users:java.util.Set[User] = new java.util.HashSet[User]();
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
    for (u <- users) {
      info.add(
        "User " + u.name + " has tweeted " + u.tweets.size() + "times"
      );
    }
    var sb:StringBuffer = new StringBuffer();
    var first:Boolean = true;
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