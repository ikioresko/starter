package ru.home.starter.codecs

import io.circe.syntax.EncoderOps
import io.circe.{Codec, Decoder, Encoder, Json}
import sttp.tapir.json.circe.TapirJsonCirce

trait ApplicationCodec extends TapirJsonCirce with ResponseCodec with TapirSchema {

  implicit val jsonThrowableCodec: Codec[Throwable] = {
    Codec.from(
      Decoder { cursor => cursor.downField("Message").as[String].map(new Throwable(_)) },
      Encoder { _ => Json.obj("Message" -> "Something went wrong".asJson) }
    )
  }

}
