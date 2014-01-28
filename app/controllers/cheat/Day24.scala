package controllers.cheat

import scala.concurrent.ExecutionContext.Implicits.global
import play.api.mvc._
import play.api.templates.HtmlFormat
import scala.List
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}
import scala.concurrent.duration._
import controllers.DayTmpl

case class Day24[A](parser: BodyParser[A]) extends DayTmpl[A, Future[String]] {
  val content = views.html.day24()

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

  def sync: Future[String] = {
    s"""
      Ok, ok, now what if we have delayed, async computations... is even easier with functional style...
    """

    val text:Future[String] = for {
      question <- async(questioner, questions(nextInt(questions.size)))
      answer   <- async(answerer, question match {
                    case t if t.contains("quest")     => "To seek the Holy Grail."
                    case unknown                      => "I don't know that."
                  })
    } yield question + "\n" + answer

    text.recover{
      case ex    => "We're in trouble"
    }

  }
}
