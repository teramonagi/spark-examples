name := """spark-streaming-twitter"""

version := "1.0"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-Xlint", "-deprecation", "-unchecked", "-feature")

//updateOptions := updateOptions.value.withCachedResolution(true)

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-streaming" % "1.6.0" % "provided",
  "org.apache.spark" %% "spark-streaming-twitter" % "1.6.0" exclude("org.spark-project.spark", "unused"),
  "org.apache.lucene" % "lucene-analyzers-common" % "5.2.1",
  "org.apache.lucene" % "lucene-analyzers-kuromoji" % "5.2.1"
)
//resolvers ++= Seq(
//  "mvnrepository" at "http://mvnrepository.com/artifact/"
//)

// Merge Strategy
assemblyMergeStrategy in assembly := {
  case PathList("javax", "servlet", xs @ _*)         => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".class" => MergeStrategy.first
  case "application.conf"                            => MergeStrategy.concat
  case "unwanted.txt"                                => MergeStrategy.discard
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
