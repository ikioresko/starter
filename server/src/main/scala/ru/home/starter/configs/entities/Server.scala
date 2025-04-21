package ru.home.starter.configs.entities

import com.comcast.ip4s.{Host, Port}
import pureconfig.generic.ProductHint
import pureconfig.generic.semiauto.deriveReader
import pureconfig.{CamelCase, ConfigFieldMapping, ConfigReader, SnakeCase}

case class Server(host: Host, port: Port)

object Server {

  implicit val hint: ProductHint[Server] =
    ProductHint[Server](ConfigFieldMapping(CamelCase, SnakeCase))

  implicit val configReader: ConfigReader[Server] = deriveReader
  implicit val hostReader: ConfigReader[Host] = ConfigReader.fromStringOpt(Host.fromString)
  implicit val portReader: ConfigReader[Port] = ConfigReader.fromStringOpt(Port.fromString)
}
