package ru.home.starter.endpoints

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import org.mockito.cats.IdiomaticMockitoCats
import org.mockito.scalatest.{AsyncIdiomaticMockito, ResetMocksAfterEachAsyncTest}
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should.Matchers
import ru.home.starter.codecs.ApplicationCodec
import sttp.client3.SttpBackend
import sttp.client3.httpclient.fs2.HttpClientFs2Backend
import sttp.client3.impl.cats.CatsImplicits
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.stub.TapirStubInterpreter

trait EndpointSpec
    extends AsyncFlatSpec
    with CatsImplicits
    with AsyncIOSpec
    with Matchers
    with AsyncIdiomaticMockito
    with IdiomaticMockitoCats
    with ResetMocksAfterEachAsyncTest
    with ApplicationCodec {

  protected val baseUrl = "http://localhost:9090/v1"

  protected def sttpBackend(endpoints: List[ServerEndpoint[Any, IO]]): SttpBackend[IO, Any] = {
    TapirStubInterpreter(HttpClientFs2Backend.stub[IO])
      .whenServerEndpointsRunLogic(endpoints)
      .backend()
  }

}
