package ru.home.starter.endpoints

import cats.data.EitherT
import cats.effect.IO
import io.circe.syntax.EncoderOps
import ru.home.starter.endpoints.api.AboutEndpoint
import ru.home.starter.handlers.AboutHandler
import ru.home.starter.models.VersionResponse
import sttp.client3._
import sttp.model.StatusCode

class AboutEndpointSpec extends EndpointSpec {

  private val (handler, backend) = {
    val handler = mock[AboutHandler[IO]]
    val endpoints = new AboutEndpoint[IO](handler).endpoints
    val backend = sttpBackend(endpoints)

    (handler, backend)
  }

  private val expected = VersionResponse("1.0-SNAPSHOT")

  "getVersionInfo" should "return info" in {
    handler.getVersionInfo
      .returns(EitherT.rightT(expected))

    quickRequest
      .get(uri"$baseUrl/about/version")
      .send(backend)
      .asserting { response =>
        response.body shouldBe expected.asJson.noSpaces
        response.code shouldBe StatusCode.Ok
      }
      .assertNoException
  }

}
