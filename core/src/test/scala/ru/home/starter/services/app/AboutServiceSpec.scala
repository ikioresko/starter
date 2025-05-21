package ru.home.starter.services.app

import cats.effect.IO
import fs2.io.file.Path
import ru.home.starter.BaseSpec
import ru.home.starter.services.entity.VersionInfo
import ru.home.starter.services.file.FileManager

class AboutServiceSpec extends BaseSpec {
  private val fileManagerMock = mock[FileManager[IO]]
  private val configPath = Path("/test")
  private val version = "1.0"

  "getVersionInfo" should "return version" in {
    new AboutService[IO](version, configPath, fileManagerMock).getVersionInfo
      .map(_ shouldBe VersionInfo(version))
  }

  "getConfigInfo" should "return config" in {
    fileManagerMock.readFile(any[Path]).returnsF(Some(""))

    new AboutService[IO](version, configPath, fileManagerMock).getConfigInfo
      .map(_ shouldBe Some(""))
  }

}
