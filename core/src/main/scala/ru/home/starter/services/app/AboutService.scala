package ru.home.starter.services.app

import cats.effect.Async
import cats.implicits.catsSyntaxApplicativeId
import ru.home.starter.services.entity.AboutInfo

case class AboutService[F[_]: Async](private val version: String) {

  def getAboutInfo: F[AboutInfo] = {
    AboutInfo(version).pure[F]
  }

}

object AboutService {
  private val version: String = Option(System.getProperty("starter.version")).getOrElse("unknown")

  def apply[F[_]: Async](): AboutService[F] = {
    new AboutService(version)
  }

}
