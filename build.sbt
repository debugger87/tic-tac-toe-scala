import AssemblyKeys._

organization := "com.debugger87.game"

name := "tic-tac-toe-scala"

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.jblas" % "jblas" % "1.2.3",
  "org.scalanlp" %% "breeze" % "0.11.2",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)

resolvers ++= Seq(
  "maven.mei.fm" at "http://maven.mei.fm/nexus/content/groups/public/"
)

assemblySettings

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) => {
  case m if m.toLowerCase.matches("meta-inf.*\\.mf$") => MergeStrategy.discard
  case m if m.toLowerCase.matches("meta-inf.*\\.sf$") => MergeStrategy.discard
  case m if m.toLowerCase.matches("meta-inf.*\\.rsa$") => MergeStrategy.discard
  case m if m.toLowerCase.matches("meta-inf.*\\.dsa$") => MergeStrategy.discard
  case "reference.conf" => MergeStrategy.concat
  case _ => MergeStrategy.first
}
}

net.virtualvoid.sbt.graph.Plugin.graphSettings