package ru.home.starter.handlers

import cats.data.EitherT
import cats.effect.Async
import cats.syntax.all._
import ru.home.starter.models.UserResponse
import ru.home.starter.services.user.UserService

class UserHandler[F[_]: Async](userService: UserService[F]) {

  def getAboutInfo: EitherT[F, Throwable, List[UserResponse]] = {
    for {
      users <- userService.getUsersInfo
    } yield users.map(i => UserResponse(i.id, i.login, i.email))
  }.attemptT

}

object UserHandler {

  def apply[F[_]: Async](userService: UserService[F]): UserHandler[F] = {
    new UserHandler(userService)
  }

}
