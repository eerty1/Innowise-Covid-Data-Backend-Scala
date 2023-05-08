ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.2.2"

lazy val root = (project in file("."))
  .settings(
    organization := "com.innowise",
    name := "CovidInfoAPI"
  )

val AkkaVersion = "2.8.0"
val AkkaHttpVersion = "10.5.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka"             %% "akka-stream"            % AkkaVersion,
  "com.typesafe.akka"             %% "akka-http"              % AkkaHttpVersion,
  "com.typesafe.akka"             %% "akka-http-testkit"      % AkkaHttpVersion   % Test,
  "com.typesafe.akka"             %% "akka-stream-testkit"    % AkkaVersion       % Test,
  "com.typesafe.akka"             %% "akka-http-testkit"      % AkkaHttpVersion   % Test,
  "com.typesafe.akka"             %% "akka-http-spray-json"   % AkkaHttpVersion,
  "com.typesafe.akka"             %% "akka-actor-typed"       % AkkaVersion,
  "org.scalatest"                 %% "scalatest"              % "3.2.15"          % Test,
  "com.softwaremill.sttp.client3" %% "core"                   % "3.8.15"          % Test,
  "com.typesafe.scala-logging"    %% "scala-logging"          % "3.9.5",
  "ch.qos.logback"                 % "logback-classic"        % "1.4.6"
)
