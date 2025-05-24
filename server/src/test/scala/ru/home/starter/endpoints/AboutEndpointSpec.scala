package ru.home.starter.endpoints

import cats.data.EitherT
import cats.effect.IO
import io.circe.syntax.EncoderOps
import ru.home.starter.endpoints.api.AboutEndpoint
import ru.home.starter.handlers.AboutHandler
import ru.home.starter.models.{ConfigResponse, VersionResponse}
import sttp.client3._
import sttp.model.StatusCode

class AboutEndpointSpec extends EndpointSpec {

  private val (handler, backend) = {
    val handler = mock[AboutHandler[IO]]
    val endpoints = new AboutEndpoint[IO](handler).endpoints
    val backend = sttpBackend(endpoints)

    (handler, backend)
  }

  "getVersionInfo" should "return info" in {
    val expectedVersion = VersionResponse("1.0-SNAPSHOT")

    handler.getVersionInfo
      .returns(EitherT.rightT(expectedVersion))

    quickRequest
      .get(uri"$baseUrl/about/version")
      .send(backend)
      .asserting { response =>
        response.body shouldBe expectedVersion.asJson.noSpaces
        response.code shouldBe StatusCode.Ok
      }
      .assertNoException
  }

  "getConfigInfo" should "return info" in {
    val expectedConfig = ConfigResponse(None)

    handler.getConfigInfo
      .returns(EitherT.rightT(expectedConfig))

    quickRequest
      .get(uri"$baseUrl/about/config")
      .send(backend)
      .asserting { response =>
        response.body shouldBe expectedConfig.asJson.noSpaces
        response.code shouldBe StatusCode.Ok
      }
      .assertNoException
  }

}
