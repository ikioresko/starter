import com.typesafe.sbt.packager.universal.UniversalPlugin.autoImport.Universal
import sbt.{Compile, file}

import scala.collection.Seq

ThisBuild / scalaVersion := "2.13.16"
ThisBuild / version := "1.0-SNAPSHOT"
ThisBuild / organization := "ru.home"
ThisBuild / organizationName := "home"
ThisBuild / fork := true

val catsV = "2.13.0"
val catsEffectV = "3.6.1"
val http4sV = "0.23.30"

val catsEffect = "org.typelevel" %% "cats-effect" % catsEffectV

val log4cats = "org.typelevel" %% "log4cats-slf4j" % "2.7.0"
val logback = "ch.qos.logback" % "logback-classic" % "1.5.18"

val http4s = Seq(
  "org.http4s" %% "http4s-dsl" % http4sV,
  "org.http4s" %% "http4s-ember-server" % http4sV,
  "org.http4s" %% "http4s-circe" % http4sV,
)

val pureConfig = "com.github.pureconfig" %% "pureconfig" % "0.17.8"

aggregateProjects(server)


lazy val root = (project in file("."))
  .settings(name := "starter")
  .disablePlugins(RevolverPlugin)

lazy val server = project
  .settings(
    addCompilerPlugin("org.typelevel" % "kind-projector_2.13.1" % "0.13.3"),
    bashScriptExtraDefines ++= Seq(
      """addJava "-Dstarter.configFile=starter.conf""""
    ),
    Universal / packageName := "starter",
    Universal / javaOptions ++= Seq("-J-Xmx2G", "-J-XX:ActiveProcessorCount=2", "-J-XX:+UseG1GC"),
    Compile / run / mainClass := Some("ru.home.starter.App"),
    Compile / unmanagedResources ++= Seq(file("starter.conf")),
    executableScriptName := "starter",
    libraryDependencies ++= http4s ++ Seq(
      catsEffect, log4cats, logback, pureConfig
    )
  )
  .enablePlugins(JavaAppPackaging)