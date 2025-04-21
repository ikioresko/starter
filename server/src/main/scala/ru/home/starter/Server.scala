package ru.home.starter

import cats.effect.{Async, Resource}
import com.comcast.ip4s.{Host, Port}
import fs2.io.net.Network
import org.http4s.ember.server.EmberServerBuilder
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.http4s.Http4sServerInterpreter

object Server {

  def start[F[_]: Async: Network](
    host: Host,
    port: Port,
    endpoints: List[ServerEndpoint[Any, F]]
  ): Resource[F, Unit] = {
    val routes = Http4sServerInterpreter()
      .toRoutes(endpoints)
      .orNotFound

    for {
      _ <- EmberServerBuilder
        .default[F]
        .withHost(host)
        .withPort(port)
        .withHttpApp(routes)
        .build
    } yield {}
  }

}
