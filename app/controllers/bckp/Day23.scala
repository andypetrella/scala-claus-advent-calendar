package controllers.bckp

import scala.concurrent.ExecutionContext.Implicits.global
import play.api.mvc._
import scala.List
import scala.util.{Success, Failure}
import scala.concurrent.{Await, Future}
import controllers.DayTmpl

case class Day23[A](parser: BodyParser[A]) extends DayTmpl[A, Future[String]] {
  val content = views.html.day23()

  def sync: Future[String] = {
    s"""
      Ok, ok, now what if we have delayed, async computations ${???}
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
    var text:String = ???
    var answer:Future[String] = ???
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
