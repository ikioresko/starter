package ru.home.starter.endpoints

import cats.effect.Async
import cats.effect.kernel.Resource
import cats.syntax.all._
import ru.home.starter.endpoints.api.{AboutEndpoint, UserEndpoint}
import ru.home.starter.resources.{HandlerResources, ServiceResources}
import sttp.tapir.server.ServerEndpoint

object EndpointInterpreter {

  def apply[F[_]: Async](
    serviceResources: ServiceResources[F],
    handlerRes: HandlerResources[F]
  ): Resource[F, List[ServerEndpoint[Any, F]]] = Resource.eval {
    for {
      versionInfo <- serviceResources.aboutService.getVersionInfo
    } yield {

      val endpoints = List(AboutEndpoint[F](handlerRes.aboutHandler), UserEndpoint[F](handlerRes.userHandler))
        .flatMap(_.endpoints)

      val swagger = SwaggerEndpoint(versionInfo.version, endpoints).endpoints

      endpoints ++ swagger
    }
  }

}
