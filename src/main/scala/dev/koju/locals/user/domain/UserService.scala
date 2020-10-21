package dev.koju.locals.user.domain

import cats.FlatMap
import cats.data.OptionT
import cats.implicits._
import tsec.passwordhashers.PasswordHasher

trait UserService[F[_]] {
  def getUserByEmail(email: String): OptionT[F, User]
  def signUp(request: SignUpRequest): F[NormalUser]
}

object UserService {

  def apply[F[_]: FlatMap, A](
      userRepo: UserRepo[F],
      passwordHasher: PasswordHasher[F, A],
  ): UserService[F] = new UserService[F] {

    override def getUserByEmail(email: String): OptionT[F, User] = userRepo.getUserByEmail(email)

    override def signUp(request: SignUpRequest): F[NormalUser] =
      for {
        passwordHash <- passwordHasher.hashpw(request.password)
        normalUser = request.asNormalUser(passwordHash)
        savedUser <- userRepo.create(normalUser)
      } yield savedUser
  }
}
