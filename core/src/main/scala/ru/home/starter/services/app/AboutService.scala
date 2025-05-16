package ru.home.starter.services.app

import cats.effect.Async
import cats.implicits.catsSyntaxApplicativeId
import fs2.io.file.Path
import ru.home.starter.services.entity.VersionInfo
import ru.home.starter.services.file.FileManager

class AboutService[F[_]: Async](version: String, fileManager: FileManager[F]) {

  def getVersionInfo: F[VersionInfo] = {
    VersionInfo(version).pure[F]
  }

  def getConfigInfo: F[String] = {
    fileManager.readFile(Path("/opt/starter/conf/starter.conf"))
  }

}

object AboutService {
  private val version: String = Option(System.getProperty("starter.version")).getOrElse("unknown")

  def apply[F[_]: Async](fileManager: FileManager[F]): AboutService[F] = {
    new AboutService(version, fileManager)
  }

}
