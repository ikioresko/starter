package ru.home.starter.resources

import cats.effect.{Async, Resource}
import doobie.util.transactor.Transactor
import ru.home.starter.repositories.UserRepository

case class RepositoryResources[F[_]: Async](userRepo: UserRepository[F])

object RepositoryResources {

  def apply[F[_]: Async](xa: Transactor[F]): Resource[F, RepositoryResources[F]] = {
    Resource.pure {
      new RepositoryResources(UserRepository(xa))
    }
  }

}
