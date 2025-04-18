package ru.home.starter.codecs

import cats.implicits.catsSyntaxOptionId
import sttp.tapir.{FieldName, Schema, SchemaType}
import sttp.tapir.generic.auto.SchemaDerivation

trait TapirSchema extends SchemaDerivation {

  implicit val sThrowable: Schema[Throwable] = Schema(
    SchemaType.SProduct(
      List(
        SchemaType.SProductField(FieldName("message"), Schema.string, _ => None),
        SchemaType.SProductField(FieldName("details"), Schema.string, _ => None)
      )
    ),
    name = Schema.SName("InternalServerException").some
  )
}
