package ru.home.starter.configs

import cats.effect.Async
import cats.effect.kernel.Resource
import pureconfig.error.ConfigReaderException
import pureconfig.generic.ProductHint
import pureconfig.generic.semiauto.deriveReader
import pureconfig._
import ru.home.starter.configs.entities._

case class AppConfig(server: Server, database: Database, transactor: Transactor, worker: Worker)

object AppConfig {

  implicit private val hint: ProductHint[AppConfig] =
    ProductHint[AppConfig](ConfigFieldMapping(CamelCase, SnakeCase))

  implicit private val configReader: ConfigReader[AppConfig] = deriveReader

  def read[F[_]: Async](): Resource[F, AppConfig] = Resource.eval {
    Async[F].blocking {
      val configFile = System.getProperty("starter.configFile")

      ConfigSource.systemProperties
        .withFallback(ConfigSource.resources(configFile))
        .load[AppConfig] match {
          case Right(appConfig) => appConfig
          case Left(error)      => throw ConfigReaderException(error)
        }
    }
  }

}
