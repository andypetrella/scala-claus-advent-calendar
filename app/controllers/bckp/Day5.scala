package controllers.bckp

import play.api.mvc._
import play.api.templates.HtmlFormat
import controllers.DayTmpl

case class Day5[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  val content: String => HtmlFormat.Appendable = s => views.html.day5(s)

  def sync:String = {
    import javax.script._
    var engine:ScriptEngine = new ScriptEngineManager().getEngineByName("python")

    // String manipulation made easy.
    // No
    // *Really* easy
    var name:String = "Noootsab"

    var script:String = "def tuto(me):\n return \"Hello, \" + me\nmsg = tuto(\""+ ??? +"\")"
    engine.eval(script)
    engine.get("msg").toString()
  }
}