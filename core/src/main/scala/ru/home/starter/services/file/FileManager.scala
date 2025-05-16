package ru.home.starter.services.file

import cats.effect.Async
import fs2.io.file.{Files, Path}

class FileManager[F[_]]()(implicit private val F: Async[F]) {

  def readFile(path: Path): F[String] = {
    F.ifM(Files[F].exists(path))(
      Files[F]
        .readUtf8(path)
        .compile
        .string,
      F.raiseError(new Exception(s"Path '$path' not found"))
    )
  }

}

object FileManager {

  def apply[F[_]: Async](): FileManager[F] = {
    new FileManager()
  }

}
