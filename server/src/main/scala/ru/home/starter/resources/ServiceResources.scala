package ru.home.starter.resources

import cats.effect.{Async, Resource}
import ru.home.starter.services.app.AboutService

case class ServiceResources[F[_]: Async](aboutService: AboutService[F])

object ServiceResources {

  def apply[F[_]: Async](): Resource[F, ServiceResources[F]] = {
    Resource.pure {
      new ServiceResources(AboutService())
    }
  }

}
