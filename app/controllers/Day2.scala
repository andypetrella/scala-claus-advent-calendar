package controllers

import play.api.mvc._
import play.api.templates.HtmlFormat
import scala.collection.JavaConversions._

case class Day2[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  lazy val content: String => HtmlFormat.Appendable = s => views.html.day2(s)

  var random = new java.util.Random();

  def sync:String = {
    //And we can even write Java-like code...
    //Create classes
    class User(var name:String, var friends:java.util.Set[User]) {
    }

    //counter
    var i = 0;
    //bag
    var users:java.util.Set[User] = ???;
    //while-loop for building
    while (i < 10) {
      users.add(new User("user"+i, new java.util.HashSet[User]()));
      i =  i+1;
    }
    var names:java.util.List[String] = new java.util.ArrayList[String]();
    //for-loop for conversion
    for (u <- users) {
      names.add(???);
    }
    //String accumulation
    var sb = new java.lang.StringBuffer();
    var first = true;
    //for-loop to aggregate
    for (n <- names) {
      if (first) {
        first = false;
      } else {
        sb.append(", ");
      }
      sb.append(n);
    }
    return sb.toString();
  }
}