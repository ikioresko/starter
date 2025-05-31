package ru.home.starter.services.redis

import cats.effect.{Async, Resource}
import cats.implicits._

import dev.profunktor.redis4cats.RedisCommands

case class RedisCacheService[F[_]: Async](redis: Resource[F, RedisCommands[F, String, String]]) {

  def getCachedData(id: Long): F[String] = {
    redis.use { commands =>
      val key = s"article:$id"
      commands.get(key).map {
        case Some(v) => v
        case None    => "DB Search" // TODO get from DB and add in Redis if exist
      }

    }
  }

}
