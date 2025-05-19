package ru.home.starter.services.file

import cats.effect.IO
import fs2.io.file.Path
import ru.home.starter.BaseSpec

import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, StandardOpenOption, Path => JPath}
import java.util.Comparator

class FileManagerSpec extends BaseSpec {
  private var tempDir: JPath = _

  before {
    tempDir = Files.createTempDirectory("mockito-test")
  }

  after {
    Files
      .walk(tempDir)
      .sorted(Comparator.reverseOrder())
      .forEach(Files.delete(_))
  }

  private val folderName = "test"
  private val fileName = "starter.conf"
  private val separator = File.separator

  "getConfigInfo" should "return config" in {
    val path = Path(s"${tempDir.toString}$separator$folderName$separator$fileName")

    val expectedText =
      s"""server: {
            host: "0.0.0.0"
            port: "9090"
          }"""

    val dir = Files.createDirectory(tempDir.resolve(folderName))
    val file = Files.createFile(dir.resolve(fileName))
    Files.write(file, expectedText.getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE)

    new FileManager[IO]()
      .readFile(path)
      .map(_ shouldBe Some(expectedText))
  }

  it should "return None" in {
    val wrongPath = Path(s"${tempDir.toString}$separator$folderName${separator}any.conf")

    val expectedText =
      s"""server: {
            host: "0.0.0.0"
            port: "9090"
          }"""

    val dir = Files.createDirectory(tempDir.resolve(folderName))
    val file = Files.createFile(dir.resolve(fileName))
    Files.write(file, expectedText.getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE)

    new FileManager[IO]()
      .readFile(wrongPath)
      .map(_ shouldBe None)
  }

}
