package ru.home.starter.services.app

import cats.effect.IO
import ru.home.starter.BaseSpec
import ru.home.starter.services.entity.VersionInfo
import ru.home.starter.services.file.FileManager

class AboutServiceSpec extends BaseSpec {
  private val fileManager = mock[FileManager[IO]]

  "getAboutInfo" should "return info" in {
    val version = "1.0"

    new AboutService[IO](version, fileManager).getVersionInfo
      .map(_ shouldBe VersionInfo(version))
  }

}
