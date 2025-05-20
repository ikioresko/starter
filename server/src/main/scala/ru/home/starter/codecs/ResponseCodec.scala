package ru.home.starter.codecs

import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec
import ru.home.starter.models.{VersionResponse, UserResponse}

trait ResponseCodec {
  implicit val jsonCodecAbout: Codec[VersionResponse] = deriveCodec
  implicit val jsonCodecUser: Codec[UserResponse] = deriveCodec

}
