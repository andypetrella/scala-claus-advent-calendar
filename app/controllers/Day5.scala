package controllers

import play.api.mvc._
import play.api.templates.HtmlFormat

case class Day5[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  lazy val content: String => HtmlFormat.Appendable = s => views.html.day5(s)

  def sync:String = {
    import javax.script._
    val engine:ScriptEngine = new ScriptEngineManager().getEngineByName("python")

    // String manipulation made easy.
    // No
    // *Really* easy
    val name:String = "Noootsab"

    val script:String = "def tuto(me):\n return \"Hello, \" + me\nmsg = tuto(\""+ ??? +"\")"
    engine.eval(script)
    engine.get("msg").toString()
  }
}