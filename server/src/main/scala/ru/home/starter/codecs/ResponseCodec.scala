package ru.home.starter.codecs

import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec
import ru.home.starter.models.{ConfigResponse, UserResponse, VersionResponse}

trait ResponseCodec {
  implicit val jsonCodecVersionResponse: Codec[VersionResponse] = deriveCodec
  implicit val jsonCodecConfigResponse: Codec[ConfigResponse] = deriveCodec
  implicit val jsonCodecUserResponse: Codec[UserResponse] = deriveCodec

}
