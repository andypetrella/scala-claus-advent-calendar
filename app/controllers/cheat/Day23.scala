package controllers.cheat

import scala.concurrent.ExecutionContext.Implicits.global
import play.api.mvc._
import play.api.templates.HtmlFormat
import scala.List
import scala.util.{Success, Failure}
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import controllers.DayTmpl
import java.lang.IllegalStateException

case class Day23[A](parser: BodyParser[A]) extends DayTmpl[A, Future[String]] {
  val content: Future[String] => HtmlFormat.Appendable = s => Await.result(s.map(r => views.html.day23(r)), 10 seconds)

  def sync: Future[String] = {
    s"""
      Ok, ok, now what if we have delayed, async computations... is even easier with functional style...
    """
    import scala.util.Random.{nextInt, nextDouble}
    case class Twitto(handle:String) {
      def tweets(status:String) = {
        Thread.sleep(nextInt(5000))
        if (nextDouble < 0.9)
          status
        else
          throw new IllegalStateException("<bam>")
      }
    }

    import scala.concurrent._

    val questioner = Twitto("Questioner")
    val answerer = Twitto("Answerer")
    val questions = List("What... is your quest?", "What's your favorite color?")

    def async(twitto:Twitto, tweet:String) = future { twitto.tweets(tweet) }

    val asking:Future[String] = async(questioner, questions(nextInt(questions.size)))
    asking.flatMap { tweet => tweet match {
        case t if tweet.contains("quest")     => async(answerer, "To seek the Holy Grail.").map{a => t + "\n" + a}
        case unknown                          => async(answerer, "I don't know that.").map{a => unknown + "\n" + a}
      }
    }.recover {
      case ex    => "We're in trouble"
    }

  }
}
