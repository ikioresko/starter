package ru.home.starter.clients

import cats.effect.{Async, Resource}
import dev.profunktor.redis4cats.{Redis, RedisCommands}
import dev.profunktor.redis4cats.connection.{RedisClient => Redis4C}
import dev.profunktor.redis4cats.data.RedisCodec
import dev.profunktor.redis4cats.effect.Log.Stdout._
import io.lettuce.core.{ClientOptions, TimeoutOptions}

import java.time.Duration

object RedisClient {

  def apply[F[_]: Async](): Resource[F, RedisCommands[F, String, String]] = {
    val stringCodec: RedisCodec[String, String] = RedisCodec.Utf8

    val mkOpts: F[ClientOptions] =
      Async[F].delay {
        ClientOptions
          .builder()
          .autoReconnect(false)
          .pingBeforeActivateConnection(false)
          .timeoutOptions(
            TimeoutOptions
              .builder()
              .fixedTimeout(Duration.ofSeconds(10))
              .build()
          )
          .build()
      }

    for {
      opts <- Resource.eval(mkOpts)
      client <- Redis4C[F].withOptions("redis://localhost", opts)
      redis <- Redis[F].fromClient(client, stringCodec)
    } yield redis
  }

}
