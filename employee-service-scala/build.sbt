name := "employee-service-scala"
version := "1.0"
scalaVersion := "2.13.10"

val akkaVersion = "2.6.20"
val akkaHttpVersion = "10.2.10"
val circeVersion = "0.14.5"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  
  // JSON handling with circe
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "de.heikoseeberger" %% "akka-http-circe" % "1.39.2",
  
  // Logging
  "ch.qos.logback" % "logback-classic" % "1.4.7",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
  
  // Configuration
  "com.typesafe" % "config" % "1.4.2"
)
