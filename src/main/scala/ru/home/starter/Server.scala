package ru.home.starter

import cats.effect.{Async, Resource}
import com.comcast.ip4s.{Host, Port}
import fs2.io.net.Network
import org.http4s.ember.server.EmberServerBuilder


object Server {
  def start[F[_] : Async : Network](host: Host, port: Port): Resource[F, Unit] = {
    for {
      _ <- EmberServerBuilder
        .default[F]
        .withHost(host)
        .withPort(port)
        .build
    } yield {}
  }
}
