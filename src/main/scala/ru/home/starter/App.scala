package ru.home.starter

import cats.effect._
import com.comcast.ip4s.{Host, Port}
import org.typelevel.log4cats.LoggerFactory
import org.typelevel.log4cats.slf4j.Slf4jFactory

object App extends IOApp {
  implicit private val logging: Slf4jFactory[IO] = Slf4jFactory.create[IO]
  private val L = LoggerFactory.getLoggerFromClass[IO](App.getClass)

  override def run(args: List[String]): IO[ExitCode] = {
    val f =
      for {
        _ <- startLogInfo()
        host = Host.fromString("0.0.0.0").get // TODO: set as config
        port = Port.fromString("9090").get // TODO: set as config
        _ <- Server.start[IO](host, port)
      } yield {}

    f.useForever
      .as(ExitCode.Success)
  }

  private def startLogInfo(): Resource[IO, Unit] = {
    Resource.eval(L.info("Start of application"))
  }

}
