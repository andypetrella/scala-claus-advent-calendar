package controllers

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import play.api.http.MimeTypes
import scalax.file.Path
import scala.annotation.tailrec
import play.api.templates.Html

object Application extends Controller {

  def index(cheat:Option[Boolean]=None) = Action {
    if (cheat.isDefined) {
      Ok(views.html.index("Information about the talk.", cheat))
    } else {
      Ok(views.html.start())
    }
  }

  def talk(currentDay:Int = 1) = Action {
    Ok(views.html.index("The show is going on.", None, currentDay))
  }

  def day(day:Int, cheat:Option[Boolean]=None) = TalkAction { implicit r =>
    Ok("Success!")
  }

  def cheat(day:Int) = Action {
    Ok(DayTmpl.actionFor(day, BodyParsers.parse.text, true).code.code)
  }

  private[this] def siblingPath(f:Path, suffix:Option[String]) =
    (f.toAbsolute.parent.get) / (f.name+".old"+suffix.getOrElse(""))

  @tailrec private[this] def sibling(f:Path, cnt:Option[Int]=None):Path = {
    val to:Path = siblingPath(f, cnt.map(_.toString()))
    if (to.exists) {
      sibling(f, cnt.map(_+1).orElse(Some(1)))
    } else {
      to
    }
  }

  private[this] def backup(f:Path) {
    f.copyTo(sibling(f))
  }
  
  private[this] def tempSave(content:List[String]) = {
    val out = Path.createTempFile("save", "scala")
    out.writeStrings(content, "\n")
    out
  }

  def save(day:Int, file:String) = Action { implicit r =>
    val code = r.body.asText
    code
      .map(code => {
        val f = Path.fromString(file)
        if (f.exists && f.canWrite) {
          //fetch where is the last code!
          val oldCode = DayTmpl.extractCode(f)
          val start = oldCode.lines.head._2
          val end = oldCode.lines.last._2

          //grab the previous implementation of the action
          // by splitting at the start of the old implementation of sync
          val (pre, post) = f.lines().toList.splitAt(start)

          //split to have all lines
          val codeLines = code.split("\n").toList

          //append the prefix and the suffix by skipping the old implementation
          val update = pre ++: codeLines ++: post.drop(end+1-start)

          //save in temp file to prepare the move
          val out = tempSave(update)

          //back here... just in case ;-)
          backup(f)

          // move temp file to current Day
          out.moveTo(f, true)
        }
        Ok("saved! => refresh asked with the right url")
      })
      .getOrElse(InternalServerError("Cannot save"))
  }

  def js = Action { implicit request =>
    Ok(Routes.javascriptRouter("jsRoutes")(
      routes.javascript.Application.day,
      routes.javascript.Application.cheat,
      routes.javascript.Application.save
    )).as(MimeTypes.JSON)
  }






}