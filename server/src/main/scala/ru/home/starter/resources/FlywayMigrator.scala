package ru.home.starter.resources

import cats.effect.{Resource, Sync}
import org.flywaydb.core.Flyway
import ru.home.starter.configs.entities.Database

object FlywayMigrator {

  def migrate[F[_]: Sync](database: Database): Resource[F, Unit] = Resource.pure {

    val fluent = Flyway
      .configure()
      .dataSource(database.url, database.user, database.password)
      .loggers("slf4j")
      .locations("database")
      .baselineOnMigrate(true)
      .baselineVersion("1.0.0")
      .validateMigrationNaming(true)
      .failOnMissingLocations(true)

    fluent.load().migrate()
  }

}
