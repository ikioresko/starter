package ru.home.starter.configs.entities

import pureconfig.{CamelCase, ConfigFieldMapping, ConfigReader, SnakeCase}
import pureconfig.generic.ProductHint
import pureconfig.generic.semiauto.deriveReader

case class Transactor(threadPoolSize: Int, driver: String)

object Transactor {

  implicit val hint: ProductHint[Transactor] =
    ProductHint[Transactor](ConfigFieldMapping(CamelCase, SnakeCase))

  implicit val configReader: ConfigReader[Transactor] = deriveReader
}
