package ru.home.starter.resources

import cats.effect.{Async, Resource}
import ru.home.starter.haldlers.AboutHandler

case class HandlerResources[F[_]: Async](aboutHandler: AboutHandler[F])

object HandlerResources {

  def apply[F[_]: Async](): Resource[F, HandlerResources[F]] = {
    Resource.pure(new HandlerResources[F](AboutHandler[F]))
  }

}
