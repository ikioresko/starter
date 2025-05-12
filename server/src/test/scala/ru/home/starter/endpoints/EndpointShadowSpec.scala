package ru.home.starter.endpoints

import cats.effect.IO
import ru.home.starter.endpoints.api.{AboutEndpoint, UserEndpoint}
import ru.home.starter.handlers.{AboutHandler, UserHandler}
import sttp.tapir.testing.EndpointVerifier

class EndpointShadowSpec extends EndpointSpec {

  "Endpoints" should "not be shadow" in {
    val aboutHandler = mock[AboutHandler[IO]]
    val userHandler = mock[UserHandler[IO]]

    val endpoints = List(AboutEndpoint(aboutHandler), UserEndpoint(userHandler))
      .flatMap(_.endpoints)
      .map(_.endpoint)

    val endpointsVerify = EndpointVerifier(endpoints)

    endpointsVerify should equal(Set.empty)
  }

}
