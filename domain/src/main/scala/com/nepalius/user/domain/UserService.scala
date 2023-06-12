package com.nepalius.user.domain

import com.nepalius.common.Exceptions.AlreadyInUse
import com.nepalius.user.domain.UserService.UserWithEmailAlreadyInUseMessage
import zio.{Task, ZIO, ZLayer}

class UserService(userRepo: UserRepo) {

  def register(user: UserRegisterData): Task[User] = {
    val emailClean = user.email.toLowerCase.trim()
    val firstNameClean = user.firstName.trim()
    val lastNameClean = user.lastName.trim()

    for {
      _ <- checkUserDoesNotExistByEmail(emailClean)
      userDataClean = UserRegisterData(emailClean, firstNameClean, lastNameClean, user.password)
      user <- userRepo.create(userDataClean)
    } yield user
  }

  private def checkUserDoesNotExistByEmail(email: String): Task[Unit] =
    for {
      maybeUserByEmail <- userRepo.findUserByEmail(email)
      _ <- ZIO
        .fail(AlreadyInUse(UserWithEmailAlreadyInUseMessage(email)))
        .when(maybeUserByEmail.isDefined)
    } yield ()

}

object UserService:
  val live: ZLayer[UserRepo, Nothing, UserService] =
    ZLayer.fromFunction(new UserService(_))

  private val UserWithEmailAlreadyInUseMessage: String => String = (email: String) => s"User with email $email already in use"
