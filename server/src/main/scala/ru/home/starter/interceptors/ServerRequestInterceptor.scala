package ru.home.starter.interceptors

import cats.effect.Sync
import sttp.monad.MonadError
import sttp.tapir.model.ServerRequest
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.interceptor._

class ServerRequestInterceptor[F[_]: Sync] extends RequestInterceptor[F] {

  override def apply[R, B](
    responder: Responder[F, B],
    requestHandler: EndpointInterceptor[F] => RequestHandler[F, R, B]
  ): RequestHandler[F, R, B] =
    new ServerRequestHandler(requestHandler(EndpointInterceptor.noop))

  private class ServerRequestHandler[R, B](val next: RequestHandler[F, R, B]) extends RequestHandler[F, R, B] {

    override def apply(request: ServerRequest, endpoints: List[ServerEndpoint[R, F]])(implicit
      monad: MonadError[F]
    ): F[RequestResult[B]] = {
      next(request, endpoints)
    }

  }

}
