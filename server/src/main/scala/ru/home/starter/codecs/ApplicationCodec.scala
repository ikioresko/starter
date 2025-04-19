package ru.home.starter.codecs

import io.circe.generic.semiauto.deriveEncoder
import io.circe.{Codec, Decoder}
import sttp.tapir.json.circe.TapirJsonCirce

trait ApplicationCodec extends TapirJsonCirce with ResponseCodec {

  implicit val jsonThrowableCodec: Codec[Throwable] = Codec.from(
    Decoder.failedWithMessage("Not implemented"),
    deriveEncoder
  )
}
