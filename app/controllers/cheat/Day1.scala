package controllers.cheat

import play.api.mvc._
import play.api.templates.HtmlFormat
import controllers.DayTmpl


case class Day1[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  val content = views.html.day1()

  def sync:String = { // this is a method|function
    // Scala is JVM based
    var date:java.util.Date = null;
    // and can use whatever Java libs
    var log:org.slf4j.Logger = org.slf4j.LoggerFactory.getLogger("Day1");
    var jodaTime:org.joda.time.DateTime = new org.joda.time.DateTime()
      .withDayOfMonth(1)
      .withMonthOfYear(org.joda.time.DateTimeConstants.DECEMBER);

    log.trace(jodaTime.toString());

    date = jodaTime.toDate();

    return org.apache.commons.lang3.time.DateFormatUtils.ISO_DATE_FORMAT.format(date);
  }
}