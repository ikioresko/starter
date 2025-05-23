package ru.home.starter.endpoints

import cats.effect.Async
import cats.effect.kernel.Resource
import cats.syntax.all._
import org.http4s.HttpApp
import ru.home.starter.endpoints.api.{AboutEndpoint, UserEndpoint}
import ru.home.starter.interceptors.ServerRequestInterceptor
import ru.home.starter.resources.{HandlerResources, ServiceResources}
import sttp.tapir.server.http4s.{Http4sServerInterpreter, Http4sServerOptions}

object EndpointInterpreter {

  def apply[F[_]: Async](serviceResources: ServiceResources[F], handlerRes: HandlerResources[F]): Resource[F, HttpApp[F]] =
    Resource.eval {
      for {
        versionInfo <- serviceResources.aboutService.getVersionInfo
      } yield {

        val endpoints = List(AboutEndpoint[F](handlerRes.aboutHandler), UserEndpoint[F](handlerRes.userHandler))
          .flatMap(_.endpoints)

        val swagger = SwaggerEndpoint(versionInfo.version, endpoints).endpoints

        val serverOptions: Http4sServerOptions[F] = Http4sServerOptions.customiseInterceptors
          .addInterceptor(new ServerRequestInterceptor())
          .options

        Http4sServerInterpreter(serverOptions)
          .toRoutes(endpoints ++ swagger)
          .orNotFound
      }
    }

}
