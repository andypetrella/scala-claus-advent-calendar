package controllers

case class Code(lines:List[(String, Int)] = List.empty,
                folds:List[((Int,Int), (Int,Int))] = List.empty,
                marks:List[(String, Int)] = List.empty) {
  val code = lines.map(_._1).mkString("\n")
}
