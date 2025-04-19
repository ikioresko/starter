package ru.home.starter.haldlers

import cats.data.EitherT
import cats.effect.Async
import cats.syntax.all._
import ru.home.starter.models.AboutResponse

class AboutHandler[F[_]: Async] {

  def getAboutInfo: EitherT[F, Throwable, AboutResponse] = {
    AboutResponse("1.0-SNAPSHOT").pure[F]
  }.attemptT

}
