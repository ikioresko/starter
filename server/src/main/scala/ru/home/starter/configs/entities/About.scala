package ru.home.starter.configs.entities

import fs2.io.file.Path
import pureconfig.generic.ProductHint
import pureconfig.{CamelCase, ConfigFieldMapping, ConfigReader, SnakeCase}

case class About(version: String, configPath: Path)

object About {

  implicit val hint: ProductHint[About] =
    ProductHint[About](ConfigFieldMapping(CamelCase, SnakeCase))

  implicit val configReader: ConfigReader[About] = ConfigReader.fromCursor(
    _.asObjectCursor
      .flatMap { cursor =>
        for {
          configPath <- cursor.atKey("config_path").flatMap(_.asString)
        } yield About(Option(System.getProperty("starter.version")).getOrElse("unknown"), Path(configPath))
      }
  )

}
