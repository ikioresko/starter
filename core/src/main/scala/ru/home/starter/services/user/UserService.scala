package ru.home.starter.services.user

import cats.effect.Async
import ru.home.starter.repositories.UserRepository
import ru.home.starter.repositories.entity.UserInfo

case class UserService[F[_]: Async](userRepo: UserRepository[F]) {

  def getUsersInfo: F[List[UserInfo]] = userRepo.getUsersInfo

}

object UserService {

  def apply[F[_]: Async](userRepo: UserRepository[F]): UserService[F] = {
    new UserService(userRepo)
  }

}
