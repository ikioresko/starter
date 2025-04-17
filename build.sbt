import scala.collection.Seq

organization := "ru.home"
version := "1.0-SNAPSHOT"
scalaVersion := "2.13.16"

lazy val root = (project in file("."))
  .settings(name := "home-starter",
    bashScriptExtraDefines ++= Seq(
      """addJava "-Dstarter.configFile=starter.conf""""
    ),
    reStart / javaOptions ++= Seq("-Dstarter.configFile=starter.conf"),
    Compile / unmanagedResources ++= Seq(file("starter.conf"))

  )

enablePlugins(JavaAppPackaging)
addCompilerPlugin("org.typelevel" % "kind-projector_2.13.1" % "0.13.3")


val catsV = "2.13.0"
val catsEffectV = "3.6.1"
val http4sV = "0.23.30"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % catsV,
  "org.typelevel" %% "cats-effect" % catsEffectV,

  "org.typelevel" %% "log4cats-slf4j" % "2.7.0",
  "ch.qos.logback" % "logback-classic" % "1.5.18",

  "org.http4s" %% "http4s-dsl" % http4sV,
  "org.http4s" %% "http4s-ember-server" % http4sV,
  "org.http4s" %% "http4s-circe" % http4sV,

  "com.github.pureconfig" %% "pureconfig" % "0.17.8"
)