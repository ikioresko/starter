package ru.home.starter.endpoints

import cats.effect.Async
import ru.home.starter.endpoints.api.AboutEndpoint
import ru.home.starter.resources.HandlerResources
import sttp.tapir.server.ServerEndpoint

object EndpointInterpreter {

  def apply[F[_]: Async](handlerRes: HandlerResources[F]): List[ServerEndpoint[Any, F]] = {
    val endpoints = List(AboutEndpoint[F](handlerRes.aboutHandler)).flatMap(_.endpoints)
    val swagger = SwaggerEndpoint(endpoints).endpoints
    endpoints ++ swagger
  }

}
