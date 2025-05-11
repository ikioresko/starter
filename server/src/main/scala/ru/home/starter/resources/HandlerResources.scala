package ru.home.starter.resources

import cats.effect.{Async, Resource}
import ru.home.starter.handlers.{AboutHandler, UserHandler}

case class HandlerResources[F[_]: Async](aboutHandler: AboutHandler[F], userHandler: UserHandler[F])

object HandlerResources {

  def apply[F[_]: Async](serviceResources: ServiceResources[F]): Resource[F, HandlerResources[F]] = {
    Resource.pure(
      new HandlerResources[F](
        AboutHandler[F](serviceResources.aboutService),
        UserHandler[F](serviceResources.userService)
      )
    )
  }

}
