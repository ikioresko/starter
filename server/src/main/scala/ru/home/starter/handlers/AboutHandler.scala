package ru.home.starter.handlers

import cats.data.EitherT
import cats.effect.Async
import cats.syntax.all._
import ru.home.starter.models.{ConfigResponse, VersionResponse}
import ru.home.starter.services.app.AboutService

class AboutHandler[F[_]: Async](aboutService: AboutService[F]) {

  def getVersionInfo: EitherT[F, Throwable, VersionResponse] = {
    aboutService.getVersionInfo.map(info => VersionResponse(info.version))
  }.attemptT

  def getConfigInfo: EitherT[F, Throwable, ConfigResponse] = {
    aboutService.getConfigInfo.map(ConfigResponse)
  }.attemptT

}

object AboutHandler {

  def apply[F[_]: Async](aboutService: AboutService[F]): AboutHandler[F] = {
    new AboutHandler(aboutService)
  }

}
