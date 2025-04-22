package ru.home.starter.configs.entities

import pureconfig.{CamelCase, ConfigFieldMapping, ConfigReader, SnakeCase}
import pureconfig.generic.ProductHint
import pureconfig.generic.semiauto.deriveReader

case class Database(url: String, user: String, password: String)

object Database {

  implicit val hint: ProductHint[Database] =
    ProductHint[Database](ConfigFieldMapping(CamelCase, SnakeCase))

  implicit val configReader: ConfigReader[Database] = deriveReader
}
