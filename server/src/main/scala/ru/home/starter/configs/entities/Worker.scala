package ru.home.starter.configs.entities

import pureconfig.{CamelCase, ConfigFieldMapping, ConfigReader, SnakeCase}
import pureconfig.generic.ProductHint
import pureconfig.generic.semiauto.deriveReader

import scala.concurrent.duration.FiniteDuration

case class Worker(interval: FiniteDuration)

object Worker {

  implicit val hint: ProductHint[Worker] =
    ProductHint[Worker](ConfigFieldMapping(CamelCase, SnakeCase))

  implicit val configReader: ConfigReader[Worker] = deriveReader
}
