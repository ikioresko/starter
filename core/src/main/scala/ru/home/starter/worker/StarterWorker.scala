package ru.home.starter.worker

import cats.effect.Async
import cats.syntax.all._
import cats.effect.kernel.Resource
import org.typelevel.log4cats.LoggerFactory

import java.time.LocalDateTime
import scala.concurrent.duration.FiniteDuration

class StarterWorker[F[_]: LoggerFactory](interval: FiniteDuration)(implicit private val F: Async[F]) {
  private val L = LoggerFactory.getLogger

  def start(): Resource[F, Unit] = {
    def workWithInterval: F[Unit] = {
      L.info(s"StarterWorker LocalDateTime: ${LocalDateTime.now()}") >> F.sleep(interval)
    }

    F.background {
      L.info(s"Worker has started with interval: '$interval'") >>
        workWithInterval.foreverM.void
    }.void
  }

}

object StarterWorker {

  def apply[F[_]: Async: LoggerFactory](interval: FiniteDuration): StarterWorker[F] = {
    new StarterWorker(interval)
  }

}
