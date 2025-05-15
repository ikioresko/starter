package ru.home.starter.resources

import cats.effect.{Async, Resource}
import org.typelevel.log4cats.LoggerFactory
import ru.home.starter.configs.AppConfig
import ru.home.starter.worker.StarterWorker

case class WorkerResource[F[_]: Async: LoggerFactory](appConfig: AppConfig) {

  def start: Resource[F, Unit] = {
    val starterWorker = StarterWorker(appConfig.worker.interval)

    starterWorker.start()
  }

}
