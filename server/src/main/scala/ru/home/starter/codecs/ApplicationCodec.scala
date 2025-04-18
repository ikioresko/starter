package ru.home.starter.codecs

import io.circe.generic.semiauto.deriveEncoder
import io.circe.{Codec, Decoder}

trait ApplicationCodec {

  implicit val jsonThrowableCodec: Codec[Throwable] = Codec.from(
    Decoder.failedWithMessage("Not implemented"),
    deriveEncoder
  )
}
