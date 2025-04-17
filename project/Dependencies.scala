import sbt.*

object Dependencies {
  val catsV = "2.13.0"
  val catsEffectV = "3.6.1"
  val http4sV = "0.23.30"

  val cats = "org.typelevel" %% "cats-core" % catsV
  val catsEffect = "org.typelevel" %% "cats-effect" % catsEffectV

  val log4cats = "org.typelevel" %% "log4cats-slf4j" % "2.7.0"
  val logback = "ch.qos.logback" % "logback-classic" % "1.5.18"


  val http4s: Seq[ModuleID] = Seq(
    "org.http4s" %% "http4s-dsl" % http4sV,
    "org.http4s" %% "http4s-ember-server" % http4sV,
    "org.http4s" %% "http4s-circe" % http4sV
  )
}
