package ru.home.starter.resources

import cats.effect.{Async, Resource}
import ru.home.starter.configs.AppConfig
import ru.home.starter.services.app.AboutService
import ru.home.starter.services.file.FileManager
import ru.home.starter.services.user.UserService

case class ServiceResources[F[_]: Async](aboutService: AboutService[F], userService: UserService[F])

object ServiceResources {

  def apply[F[_]: Async](repositories: RepositoryResources[F], appConfig: AppConfig): Resource[F, ServiceResources[F]] = {
    Resource.pure {
      val fileManager = FileManager[F]()
      val aboutService =
        AboutService[F](appConfig.about.version, appConfig.about.configPath, fileManager)
      val userService = UserService[F](repositories.userRepo)

      new ServiceResources(aboutService, userService)
    }
  }

}
