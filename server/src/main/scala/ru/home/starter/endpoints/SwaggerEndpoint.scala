package ru.home.starter.endpoints

import cats.effect.Async
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.swagger.SwaggerUIOptions
import sttp.tapir.swagger.bundle.SwaggerInterpreter

case class SwaggerEndpoint[F[_]: Async](private val version: String, private val api: List[ServerEndpoint[Any, F]])
    extends BaseEndpoint[F] {

  private val swagger: List[ServerEndpoint[Any, F]] = SwaggerInterpreter(swaggerUIOptions =
    SwaggerUIOptions.default.pathPrefix(List("docs", "swagger"))
  ).fromServerEndpoints[F](api, "Starter app", version)

  override def endpoints: List[ServerEndpoint[Any, F]] = swagger
}
