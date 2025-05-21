package ru.home.starter.services.app

import cats.effect.Async
import cats.implicits.catsSyntaxApplicativeId
import fs2.io.file.Path
import ru.home.starter.services.entity.VersionInfo
import ru.home.starter.services.file.FileManager

class AboutService[F[_]: Async](version: String, configPath: Path, fileManager: FileManager[F]) {

  def getVersionInfo: F[VersionInfo] = {
    VersionInfo(version).pure[F]
  }

  def getConfigInfo: F[Option[String]] = {
    fileManager.readFile(configPath)
  }

}

object AboutService {

  def apply[F[_]: Async](version: String, configPath: Path, fileManager: FileManager[F]): AboutService[F] = {
    new AboutService(version, configPath, fileManager)
  }

}
