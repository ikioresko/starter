package ru.home.starter.codecs

import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec
import ru.home.starter.models.AboutResponse

trait ResponseCodec {
  implicit val jsonCodecAbout: Codec[AboutResponse] = deriveCodec

}
