package ru.home.starter.endpoints

import cats.effect.Async
import cats.syntax.all._
import org.http4s.HttpRoutes
import ru.home.starter.endpoints.api.AboutEndpoint
import ru.home.starter.resources.HandlerResources
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.http4s.Http4sServerInterpreter

class EndpointInterpreter[F[_]: Async](private val hRes: HandlerResources[F]) {

  val endpoints: List[ServerEndpoint[Any, F]] = List(
    AboutEndpoint[F](hRes.aboutHandler)
  ).map(_.endpoints)
    .reduce(_ combineK _)

  def routes:  HttpRoutes[F] = Http4sServerInterpreter[F]()
    .toRoutes(endpoints)

}
