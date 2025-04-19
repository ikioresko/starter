package ru.home.starter.endpoints

import cats.effect.Async
import ru.home.starter.codecs.{ApplicationCodec, TapirSchema}
import sttp.model.{Header, StatusCode}
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.{Endpoint, Tapir}

abstract class BaseEndpoint[F[_]: Async] extends Tapir with ApplicationCodec with TapirSchema {
  protected val openEndpoint: Endpoint[Unit, List[Header], Throwable, Unit, Any] =
    endpoint
      .in("v1")
      .in(headers)
      .errorOut(
        oneOf[Throwable](
          oneOfDefaultVariant(
            statusCode(StatusCode.InternalServerError)
              .and(jsonBody[Throwable]))
        )
      )

  def endpoints: List[ServerEndpoint[Any, F]]
}
