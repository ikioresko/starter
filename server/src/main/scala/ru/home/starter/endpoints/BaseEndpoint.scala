package ru.home.starter.endpoints

import ru.home.starter.codecs.{ApplicationCodec, TapirSchema}
import sttp.model.{Header, StatusCode}
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.{Endpoint, endpoint, headers, oneOf, oneOfDefaultVariant, statusCode}

class BaseEndpoint extends ApplicationCodec with TapirSchema {
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
}
