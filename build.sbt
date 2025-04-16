import Dependencies.*

import scala.collection.Seq

ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.16"

lazy val root = (project in file("."))
  .settings(
    name := "starter"
  )
  .settings(libDependencies)

lazy val libDependencies = Seq(libraryDependencies ++= List(cats, catsEffect, log4cats, logback))