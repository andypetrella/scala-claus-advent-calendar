name := "scala-claus-advent-calendar"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "org.python" % "jython-standalone" % "2.5.2"
)     

play.Project.playScalaSettings