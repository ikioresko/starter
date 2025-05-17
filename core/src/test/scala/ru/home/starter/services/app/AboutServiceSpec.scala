package ru.home.starter.services.app

import cats.effect.IO
import fs2.io.file.Path
import ru.home.starter.BaseSpec
import ru.home.starter.services.entity.VersionInfo
import ru.home.starter.services.file.FileManager

class AboutServiceSpec extends BaseSpec {
  private val fileManager = mock[FileManager[IO]]
  private val version = "1.0"

  "getVersionInfo" should "return version" in {
    new AboutService[IO](version, fileManager).getVersionInfo
      .map(_ shouldBe VersionInfo(version))
  }

  "getConfigInfo" should "return config" in {
    fileManager.readFile(any[Path]).returnsF("")

    new AboutService[IO](version, fileManager).getConfigInfo
      .map(_ shouldBe "")
  }

}
