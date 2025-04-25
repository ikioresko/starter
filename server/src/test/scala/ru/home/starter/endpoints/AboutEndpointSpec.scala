package ru.home.starter.endpoints

import cats.data.EitherT
import cats.effect.IO
import io.circe.syntax.EncoderOps
import ru.home.starter.endpoints.api.AboutEndpoint
import ru.home.starter.handlers.AboutHandler
import ru.home.starter.models.AboutResponse
import sttp.client3._
import sttp.model.StatusCode

class AboutEndpointSpec extends EndpointSpec {

  private val (handler, backend) = {
    val handler = mock[AboutHandler[IO]]
    val endpoints = new AboutEndpoint[IO](handler).endpoints
    val backend = sttpBackend(endpoints)

    (handler, backend)
  }

  private val expected = AboutResponse("1.0-SNAPSHOT")

  "getAboutInfo" should "return info" in {
    handler.getAboutInfo
      .returns(EitherT.rightT(expected))

    quickRequest
      .get(uri"$baseUrl/about")
      .send(backend)
      .asserting { response =>
        response.body shouldBe expected.asJson.noSpaces
        response.code shouldBe StatusCode.Ok
      }
      .assertNoException
  }

}
