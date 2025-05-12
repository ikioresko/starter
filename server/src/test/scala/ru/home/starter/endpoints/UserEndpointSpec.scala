package ru.home.starter.endpoints

import cats.data.EitherT
import cats.effect.IO
import io.circe.syntax.EncoderOps
import ru.home.starter.endpoints.api.UserEndpoint
import ru.home.starter.handlers.UserHandler
import ru.home.starter.models.UserResponse
import sttp.client3._
import sttp.model.StatusCode

class UserEndpointSpec extends EndpointSpec {

  private val (handler, backend) = {
    val handler = mock[UserHandler[IO]]
    val endpoints = new UserEndpoint[IO](handler).endpoints
    val backend = sttpBackend(endpoints)

    (handler, backend)
  }

  private val expected = List(UserResponse(0, "admin", None))

  "getUsersInfo" should "return users" in {
    handler.getUsersInfo
      .returns(EitherT.rightT(expected))

    quickRequest
      .get(uri"$baseUrl/users")
      .send(backend)
      .asserting { response =>
        response.body shouldBe expected.asJson.noSpaces
        response.code shouldBe StatusCode.Ok
      }
      .assertNoException
  }

}
