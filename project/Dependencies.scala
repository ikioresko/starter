import sbt.*

object Dependencies {
  val catsV = "2.13.0"
  val catsEffectV = "3.6.1"

  val cats = "org.typelevel" %% "cats-core" % catsV
  val catsEffect = "org.typelevel" %% "cats-effect" % catsEffectV
}
