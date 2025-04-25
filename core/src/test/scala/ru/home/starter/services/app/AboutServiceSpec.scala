package ru.home.starter.services.app

import cats.effect.IO
import ru.home.starter.BaseSpec
import ru.home.starter.services.entity.AboutInfo

class AboutServiceSpec extends BaseSpec {

  "getAboutInfo" should "return info" in {
    val version = "1.0"

    new AboutService[IO](version).getAboutInfo
      .map(_ shouldBe AboutInfo(version))
  }

}
