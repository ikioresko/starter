package ru.home.starter.clients

import cats.effect.Sync
import cats.syntax.all._
import sttp.capabilities.WebSockets
import sttp.capabilities.fs2.Fs2Streams
import sttp.client3.circe._
import sttp.client3.{SttpBackend, _}

class HttpClient[F[_]: Sync](
  private val host: String,
  private val port: Int,
  private val backend: SttpBackend[F, Fs2Streams[F] with WebSockets]
) {
  val baseUrl = uri"http://$host:$port"

  def getData: F[List[String]] = {
    val url = uri"$baseUrl/api/data"

    basicRequest
      .acceptEncoding("gzip")
      .get(url)
      .response(asJson[List[String]])
      .send(backend)
      .flatMap { response =>
        response.body match {
          case Left(err) => Sync[F].raiseError(err)
          case Right(v)  => Sync[F].pure(v)
        }
      }
  }

}
