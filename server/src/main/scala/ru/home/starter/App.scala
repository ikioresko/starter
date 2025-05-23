package ru.home.starter

import cats.effect._
import org.typelevel.log4cats.LoggerFactory
import org.typelevel.log4cats.slf4j.Slf4jFactory
import ru.home.starter.configs.AppConfig
import ru.home.starter.endpoints.EndpointInterpreter
import ru.home.starter.resources._

object App extends IOApp {
  implicit private val logging: Slf4jFactory[IO] = Slf4jFactory.create[IO]
  private val L = LoggerFactory.getLoggerFromClass[IO](App.getClass)

  override def run(args: List[String]): IO[ExitCode] = {
    val f =
      for {
        _ <- startLogInfo()
        appConfig <- AppConfig.read[IO]()
        _ <- FlywayMigrator.migrate[IO](appConfig.database)
        transactor <- TransactorHikari[IO](appConfig)
        _ <- WorkerResource[IO](appConfig).start
        repositories <- RepositoryResources[IO](transactor)
        serviceResources <- ServiceResources[IO](repositories, appConfig)
        handlerRes <- HandlerResources[IO](serviceResources)
        httpApp <- EndpointInterpreter[IO](serviceResources, handlerRes)
        _ <- Server.start[IO](appConfig.server.host, appConfig.server.port, httpApp)
      } yield {}

    f.useForever
      .as(ExitCode.Success)
  }

  private def startLogInfo(): Resource[IO, Unit] = {
    Resource.eval(L.info("Start of application"))
  }

}
