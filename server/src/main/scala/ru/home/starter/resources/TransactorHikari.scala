package ru.home.starter.resources

import cats.effect.{Async, Resource}
import doobie.Transactor
import doobie.hikari.HikariTransactor
import doobie.util.ExecutionContexts
import ru.home.starter.configs.AppConfig

object TransactorHikari {

  def apply[F[_]: Async](appConfig: AppConfig): Resource[F, Transactor[F]] = {
    for {
      ec <- ExecutionContexts.fixedThreadPool[F](appConfig.transactor.threadPoolSize)
      hikari <- HikariTransactor.newHikariTransactor[F](
        appConfig.transactor.driver,
        appConfig.database.url,
        appConfig.database.user,
        appConfig.database.password,
        ec
      )
    } yield hikari
  }

}
