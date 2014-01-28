package controllers

import play.api.mvc._
import play.api.templates.HtmlFormat

case class Day5[A](parser:BodyParser[A]) extends DayTmpl[A, String] {
  lazy val content: HtmlFormat.Appendable = views.html.day5()

  def sync:String = {
    import javax.script._
    var engine:ScriptEngine = new ScriptEngineManager().getEngineByName("python")

    // String manipulation made easy.
    // No
    // *Really* easy
    var name:String = "Noootsab"

    var script:String = "def tuto(me):\n return \"Hello, \" + me\nmsg = tuto(\""+ ????("format this sh*t with | and use $name") +"\")"
    engine.eval(script)
    engine.get("msg").toString()
  }
}