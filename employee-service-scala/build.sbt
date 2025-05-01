name := "employee-service-scala"
version := "1.0"
scalaVersion := "2.13.10"

val akkaVersion = "2.6.20"
val akkaHttpVersion = "10.2.10"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,

  "ch.qos.logback" % "logback-classic" % "1.4.7",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",

  // Configuration
  "com.typesafe" % "config" % "1.4.2"
)

// No assembly configuration needed for this simple example
