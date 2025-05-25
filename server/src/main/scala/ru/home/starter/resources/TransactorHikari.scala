package ru.home.starter.resources

import cats.effect.{Async, Resource}
import cats.implicits._
import doobie.Transactor
import doobie.hikari.HikariTransactor
import doobie.util.log.LogHandler
import doobie.util.{ExecutionContexts, log}
import org.typelevel.log4cats.LoggerFactory
import ru.home.starter.configs.AppConfig

object TransactorHikari {

  def apply[F[_]: Async: LoggerFactory](appConfig: AppConfig): Resource[F, Transactor[F]] = {
    for {
      ec <- ExecutionContexts.fixedThreadPool[F](appConfig.transactor.threadPoolSize)
      hikari <- HikariTransactor.newHikariTransactor[F](
        appConfig.transactor.driver,
        appConfig.database.url,
        appConfig.database.user,
        appConfig.database.password,
        ec,
        logHandler.some
      )
    } yield hikari
  }

  private def logHandler[F[_]: LoggerFactory]: LogHandler[F] = new LogHandler[F] {
    private val L = LoggerFactory.getLogger

    override def run(logEvent: log.LogEvent): F[Unit] = {
      logEvent match {
        case log.Success(sql, params, _, _, _) =>
          L.debug(s"""Success:
                     | ${sql.linesIterator.dropWhile(_.trim.isEmpty).mkString("\n  ")}
                     |
                     | params = [${params.allParams.flatten.mkString(", ")}]
              """.stripMargin)
        case log.ProcessingFailure(sql, params, label, _, _, failure) =>
          L.error(s"""ProcessingFailure:
                     |  ${sql.linesIterator.dropWhile(_.trim.isEmpty).mkString("\n  ")}
                     |
                     | params = [${params.allParams.flatten.mkString(", ")}]
                     | label = $label
                     | failure = ${failure.getMessage}
              """.stripMargin)
        case log.ExecFailure(sql, params, label, _, failure) =>
          L.error(s"""ExecFailure:
                     |  ${sql.linesIterator.dropWhile(_.trim.isEmpty).mkString("\n  ")}
                     |
                     | params = [${params.allParams.flatten.mkString(", ")}]
                     | label = $label
                     | failure = ${failure.getMessage}
              """.stripMargin)
      }
    }

  }

}
