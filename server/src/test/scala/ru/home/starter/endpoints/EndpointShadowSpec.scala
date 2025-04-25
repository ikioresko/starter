package ru.home.starter.endpoints

import cats.effect.IO
import ru.home.starter.endpoints.api.AboutEndpoint
import ru.home.starter.handlers.AboutHandler
import sttp.tapir.testing.EndpointVerifier

class EndpointShadowSpec extends EndpointSpec {

  "Endpoints" should "not be shadow" in {
    val aboutHandler = mock[AboutHandler[IO]]

    val endpoints = List(AboutEndpoint(aboutHandler))
      .flatMap(_.endpoints)
      .map(_.endpoint)

    val endpointsVerify = EndpointVerifier(endpoints)

    endpointsVerify should equal(Set.empty)
  }

}
