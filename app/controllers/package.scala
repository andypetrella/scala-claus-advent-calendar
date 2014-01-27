import scalax.file.Path

package object controllers {
  val StartFold = "StartFold"
  val EndFold = "EndFold"
  def ????(msg:String) : Nothing = throw new NotImplementedError

  implicit class WinPath(p:Path) {
    val updateForWin = p.toAbsolute.path.replaceAll("\\\\", "\\\\\\\\")
  }

}
