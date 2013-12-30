package controllers.bckp

import play.api.mvc._
import play.api.templates.HtmlFormat
import controllers.DayTmpl

case class Day5[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  lazy val content: String => HtmlFormat.Appendable = s => views.html.day5(s)

  def sync:String = {
    import javax.script._
    val engine = new ScriptEngineManager().getEngineByName("python")

    // String manipulation made easy.
    // No
    // Really easy
    val name = "Noootsab"
    val script = "def tuto(me):\n return \"Hello, \" + me\nmsg = tuto(\""+name+"\")"
    engine.eval(script)
    engine.get("msg").toString()
  }
}