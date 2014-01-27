package controllers

import play.api.mvc._
import play.api.templates.HtmlFormat
import scala.List
import scala.util.{Try, Success, Failure}
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

case class Day23[A](parser: BodyParser[A]) extends DayTmpl[A, Future[String]] {
  val content: Future[String] => HtmlFormat.Appendable = s => Await.result(s.map(r => views.html.day23(r)), 10 seconds)

  def sync: Future[String] = {
    s"""
      Ok, ok, now what if we have delayed, async computations ${????("try to escape the callback hell")}
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

    val asking:Future[String] = future {
      questioner.tweets(questions(nextInt(questions.size)))
    }
    var text:String = ????("a var ... again")
    var answer:Future[String] = ????("come on...")
    asking.onSuccess {
      case tweet if tweet.contains("quest") =>
        text = tweet
        answer = future { answerer.tweets("To seek the Holy Grail.") }
      case unknown                          =>
        text = unknown
        answer = future { answerer.tweets("I don't know that.") }
    }

    asking.onFailure {
      case x:IllegalStateException  => answer = future { answerer.tweets("Could you repeat please?") }
      case x                        => answer = future { answerer.tweets("Is there anybody out there?") }
    }

    val result = Promise[String]()
    answer.onComplete {//Try[S] => R
      case Success(tweet) => result.success(text + "\n" + tweet)
      case Failure(ex)    => result.success("We're in trouble")
    }

    result.future
  }
}
