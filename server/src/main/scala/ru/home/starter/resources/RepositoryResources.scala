package ru.home.starter.resources

import cats.effect.{Async, Resource}
import doobie.util.transactor.Transactor

case class RepositoryResources[F[_]: Async]()

object RepositoryResources {

  def apply[F[_]: Async](t: Transactor[F]): Resource[F, RepositoryResources[F]] = {
    Resource.pure {
      new RepositoryResources()
    }
  }

}
