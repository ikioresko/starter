import cats.effect._
import org.typelevel.log4cats.LoggerFactory
import org.typelevel.log4cats.slf4j.Slf4jFactory

object App extends IOApp {
  implicit private val logging: Slf4jFactory[IO] = Slf4jFactory.create[IO]
  private val L = LoggerFactory.getLoggerFromClass[IO](App.getClass)

  override def run(args: List[String]): IO[ExitCode] = {
    for {
      _ <- L.info("Application start")
    } yield ExitCode.Success
  }
}
