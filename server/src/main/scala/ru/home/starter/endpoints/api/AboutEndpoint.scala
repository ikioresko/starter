package ru.home.starter.endpoints.api

import cats.effect.Async
import ru.home.starter.endpoints.BaseEndpoint
import ru.home.starter.handlers.AboutHandler
import ru.home.starter.models.{ConfigResponse, VersionResponse}
import sttp.tapir.server.ServerEndpoint

class AboutEndpoint[F[_]: Async](handler: AboutHandler[F]) extends BaseEndpoint {

  private val version =
    openEndpoint
      .tag("About")
      .summary("Version info")
      .get
      .in("about" / "version")
      .out(jsonBody[VersionResponse])
      .serverLogic(_ => handler.getVersionInfo.value)

  private val config =
    openEndpoint
      .tag("About")
      .summary("Config info")
      .get
      .in("about" / "config")
      .out(jsonBody[ConfigResponse])
      .serverLogic(_ => handler.getConfigInfo.value)

  override def endpoints: List[ServerEndpoint[Any, F]] = List(version, config)
}

object AboutEndpoint {

  def apply[F[_]: Async](handler: AboutHandler[F]): AboutEndpoint[F] = {
    new AboutEndpoint(handler)
  }

}
