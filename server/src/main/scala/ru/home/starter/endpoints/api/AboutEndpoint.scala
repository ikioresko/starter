package ru.home.starter.endpoints.api

import cats.effect.Async
import ru.home.starter.endpoints.BaseEndpoint
import ru.home.starter.haldlers.AboutHandler
import ru.home.starter.models.AboutResponse
import sttp.tapir.server.ServerEndpoint

class AboutEndpoint[F[_]: Async](handler: AboutHandler[F]) extends BaseEndpoint {

  private val about =
    openEndpoint
      .tag("About")
      .summary("About info")
      .get
      .in("about")
      .out(jsonBody[AboutResponse])
      .serverLogic(_ => handler.getAboutInfo.value)

  override def endpoints: List[ServerEndpoint[Any, F]] = List(about)
}

object AboutEndpoint {

  def apply[F[_]: Async](handler: AboutHandler[F]): AboutEndpoint[F] = {
    new AboutEndpoint(handler)
  }

}
