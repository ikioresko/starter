package ru.home.starter.services.user

import cats.effect.IO
import ru.home.starter.BaseSpec
import ru.home.starter.repositories.UserRepository
import ru.home.starter.repositories.entity.UserInfo

class UserServiceSpec extends BaseSpec {
  private val userRepo = mock[UserRepository[IO]]

  "getUsersInfo" should "return users info" in {
    val usersInfo = List(UserInfo(0, "admin", None))

    userRepo.getUsersInfo.returnsF(usersInfo)

    UserService(userRepo).getUsersInfo
      .map(_ shouldBe usersInfo)
  }

  it should "return empty users info" in {
    userRepo.getUsersInfo.returnsF(Nil)

    UserService(userRepo).getUsersInfo
      .map(_ shouldBe Nil)
  }

}
