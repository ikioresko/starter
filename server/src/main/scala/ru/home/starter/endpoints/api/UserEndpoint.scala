package ru.home.starter.endpoints.api

import cats.effect.Async
import ru.home.starter.endpoints.BaseEndpoint
import ru.home.starter.handlers.UserHandler
import ru.home.starter.models.UserResponse
import sttp.tapir.server.ServerEndpoint

class UserEndpoint[F[_]: Async](handler: UserHandler[F]) extends BaseEndpoint {

  private val users =
    openEndpoint
      .tag("Users")
      .summary("Users info")
      .get
      .in("users")
      .out(jsonBody[List[UserResponse]])
      .serverLogic(_ => handler.getUsersInfo.value)

  override def endpoints: List[ServerEndpoint[Any, F]] = List(users)

}

object UserEndpoint {

  def apply[F[_]: Async](handler: UserHandler[F]): UserEndpoint[F] = {
    new UserEndpoint(handler)
  }

}
