import com.typesafe.sbt.packager.universal.UniversalPlugin.autoImport.Universal
import sbt.{Compile, file}

import scala.collection.Seq

initialize := {
  val _ = initialize.value
  val minRequiredJava = 17
  val currentJava = sys.props("java.specification.version").toInt
  assert(
    currentJava >= minRequiredJava,
    s"Unsupported java version: java.specification.version=$currentJava must be >= $minRequiredJava"
  )
}

ThisBuild / scalaVersion := "2.13.16"
ThisBuild / version := "1.0-SNAPSHOT"
ThisBuild / organization := "ru.home"
ThisBuild / organizationName := "home"
ThisBuild / fork := true

val catsV = "2.13.0"
val catsEffectV = "3.6.1"
val http4sV = "0.23.30"
val tapirV = "1.11.25"
val sttpV = "3.11.0"
val doobieV = "1.0.0-RC9"
val mockitoV = "1.17.37"

val catsEffect = "org.typelevel" %% "cats-effect" % catsEffectV

val log4cats = "org.typelevel" %% "log4cats-slf4j" % "2.7.0"
val logback = "ch.qos.logback" % "logback-classic" % "1.5.18"

val http4s = Seq(
  "org.http4s" %% "http4s-dsl" % http4sV,
  "org.http4s" %% "http4s-ember-server" % http4sV,
  "org.http4s" %% "http4s-circe" % http4sV
)

val tapir = Seq(
  "com.softwaremill.sttp.tapir" %% "tapir-core" % tapirV,
  "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % tapirV,
  "com.softwaremill.sttp.tapir" %% "tapir-http4s-server" % tapirV,
  "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % tapirV,
  "com.softwaremill.sttp.tapir" %% "tapir-cats" % tapirV
)

val pureConfig = "com.github.pureconfig" %% "pureconfig" % "0.17.9"

val sttp: Seq[ModuleID] = Seq(
  "com.softwaremill.sttp.client3" %% "core" % sttpV,
  "com.softwaremill.sttp.client3" %% "circe" % sttpV,
  "com.softwaremill.sttp.client3" %% "fs2" % sttpV,
  "com.softwaremill.sttp.client3" %% "slf4j-backend" % sttpV
).map(_.excludeAll(ExclusionRule("io.netty")))

val doobie = Seq(
  "org.tpolecat" %% "doobie-core" % doobieV,
  "org.tpolecat" %% "doobie-postgres" % doobieV,
  "org.tpolecat" %% "doobie-hikari" % doobieV,
  "org.tpolecat" %% "doobie-postgres-circe" % doobieV
)

val flywaydb = "org.flywaydb" % "flyway-core" % "11.8.0"

val testDependencies = Seq(
  "org.typelevel" %% "cats-effect-testing-scalatest" % "1.6.0" % Test,
  "org.scalatest" %% "scalatest" % "3.2.19" % Test,
  "org.mockito" %% "mockito-scala" % mockitoV % Test,
  "org.mockito" %% "mockito-scala-cats" % mockitoV % Test,
  "org.mockito" %% "mockito-scala-scalatest" % mockitoV % Test,
  "com.softwaremill.sttp.tapir" %% "tapir-sttp-stub-server" % tapirV % Test,
  "com.softwaremill.sttp.tapir" %% "tapir-testing" % tapirV % Test
)

aggregateProjects(server, core, clients)

lazy val root = (project in file("."))
  .settings(name := "starter")
  .disablePlugins(RevolverPlugin)

lazy val core = (project in file("core"))
  .dependsOn(clients)
  .settings(libraryDependencies ++= doobie ++ testDependencies ++ Seq(catsEffect, log4cats, logback))
  .disablePlugins(RevolverPlugin)

lazy val clients = (project in file("clients"))
  .settings(libraryDependencies ++= testDependencies ++ Seq(catsEffect, log4cats, logback))
  .disablePlugins(RevolverPlugin)

lazy val server = project
  .dependsOn(core)
  .settings(
    addCompilerPlugin("org.typelevel" % "kind-projector_2.13.1" % "0.13.3"),
    bashScriptExtraDefines ++= Seq(
      """addJava "-Dstarter.configFile=starter.conf"""",
      s"""addJava "-Dstarter.version=${version.value}""""
    ),
    Universal / packageName := "starter",
    Universal / javaOptions ++= Seq("-J-Xmx2G", "-J-XX:ActiveProcessorCount=2", "-J-XX:+UseG1GC"),
    Compile / run / mainClass := Some("ru.home.starter.App"),
    Compile / unmanagedResources ++= Seq(file("starter.conf")),
    executableScriptName := "starter",
    libraryDependencies ++= http4s ++ sttp ++ tapir ++ testDependencies ++ Seq(
      catsEffect,
      log4cats,
      logback,
      pureConfig,
      flywaydb
    )
  )
  .enablePlugins(JavaAppPackaging)
