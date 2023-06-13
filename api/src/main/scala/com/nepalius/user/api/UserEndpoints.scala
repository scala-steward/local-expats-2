package com.nepalius.user.api

import com.nepalius.auth.UserSession
import com.nepalius.common.api.{BaseEndpoints, ErrorInfo}
import com.nepalius.user.api.{UserEndpoints, UserRegisterPayload, UserResponse}
import sttp.tapir.Endpoint
import sttp.tapir.generic.auto.*
import sttp.tapir.json.zio.jsonBody
import sttp.tapir.ztapir.*
import zio.ZLayer

import java.util.UUID

class UserEndpoints(base: BaseEndpoints):

  val registerEndPoint: Endpoint[Unit, UserRegisterPayload, ErrorInfo, UserWithAuthTokenResponse, Any] =
    base.publicEndpoint
      .summary("Register User")
      .post
      .in("api" / "users")
      .in(jsonBody[UserRegisterPayload].example(Examples.userRegisterPayload))
      .out(jsonBody[UserWithAuthTokenResponse].example(Examples.userWithAuthTokenResponse))

  val loginEndpoint: Endpoint[Unit, UserLoginPayload, ErrorInfo, UserWithAuthTokenResponse, Any] =
    base.publicEndpoint
      .summary("Login User")
      .post
      .in("api" / "users" / "login")
      .in(jsonBody[UserLoginPayload].example(Examples.userLoginPayload))
      .out(jsonBody[UserWithAuthTokenResponse].example(Examples.userWithAuthTokenResponse))

  val getCurrentUserEndpoint: ZPartialServerEndpoint[Any, String, UserSession, Unit, ErrorInfo, UserResponse, Any] =
    base.secureEndpoint
      .summary("Get Current User")
      .get
      .in("api" / "user")
      .out(jsonBody[UserResponse].example(Examples.getUserResponse))

  val updateUserEndpoint: ZPartialServerEndpoint[Any, String, UserSession, UserUpdatePayload, ErrorInfo, UserResponse, Any] =
    base.secureEndpoint
      .summary("Update Current User")
      .put
      .in("api" / "user")
      .in(jsonBody[UserUpdatePayload].example(Examples.userUpdatePayload))
      .out(jsonBody[UserResponse].example(Examples.updatedUserResponse))

  private object Examples:
    val userRegisterPayload: UserRegisterPayload = UserRegisterPayload(
      email = "first.last@email.com",
      firstName = "First",
      lastName = "Last",
      password = "secret_password",
    )

    val userLoginPayload: UserLoginPayload = UserLoginPayload(
      email = "first.last@email.com",
      password = "secret_password",
    )

    val getUserResponse: UserResponse = UserResponse(
      id = UUID.fromString("cbee098c-9fe5-4e0b-9e28-2d4a1db83345"),
      email = "user123@email.com",
      firstName = "First",
      lastName = "Last",
    )

    val userWithAuthTokenResponse: UserWithAuthTokenResponse = UserWithAuthTokenResponse(
      user = getUserResponse,
      authToken =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJTb2Z0d2FyZU1pbGwiLCJ1c2VyRW1haWwiOiJ1c2VyMTIzQGVtYWlsLmNvbSIsImV4cCI6MTY4MjU4MzY0NCwiaWF0IjoxNjgyNTgwMDQ0LCJqdGkiOiJkMmEzYThjZC1mNmFhLTQwNzgtYTk4Ni1jZmIwNTg5NTAxYmEifQ.SwY-ynkmR3-uYZU0K2cI0NY7Cs8oSgCU8RUVUagOAok",
    )

    val userUpdatePayload: UserUpdatePayload = UserUpdatePayload(
      email = Some("new@email.com"),
      password = Some("new_secure_password"),
      firstName = "First",
      lastName = "Lasting",
    )

    val updatedUserResponse: UserResponse = UserResponse(
      id = getUserResponse.id,
      email = userUpdatePayload.email.get,
      firstName = userUpdatePayload.firstName,
      lastName = userUpdatePayload.lastName,
    )

object UserEndpoints:
  val live: ZLayer[BaseEndpoints, Nothing, UserEndpoints] =
    ZLayer.fromFunction(new UserEndpoints(_))
