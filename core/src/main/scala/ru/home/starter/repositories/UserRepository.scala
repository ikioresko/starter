package ru.home.starter.repositories

import cats.effect.Sync
import doobie.implicits._

import doobie.util.transactor.Transactor
import ru.home.starter.repositories.entity.UserInfo

class UserRepository[F[_]: Sync](xa: Transactor[F]) {

  def getUsersInfo: F[List[UserInfo]] = {
    sql"""SELECT * FROM app_users"""
      .query[UserInfo]
      .to[List]
      .transact(xa)
  }

}
