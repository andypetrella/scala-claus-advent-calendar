package views

package object utils {
  def showCode(code:List[(String, Int)]) = code.map(_._1).mkString("\n")
}
