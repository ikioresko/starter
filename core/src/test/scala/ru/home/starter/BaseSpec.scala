package ru.home.starter

import cats.effect.testing.scalatest.AsyncIOSpec
import org.mockito.cats.IdiomaticMockitoCats
import org.mockito.scalatest.{AsyncIdiomaticMockito, ResetMocksAfterEachAsyncTest}
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should.Matchers

trait BaseSpec
    extends AsyncFlatSpec
    with AsyncIOSpec
    with Matchers
    with AsyncIdiomaticMockito
    with IdiomaticMockitoCats
    with ResetMocksAfterEachAsyncTest
