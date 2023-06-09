package com.nepalius.user.api

import com.nepalius.common.api.{BaseEndpoints, ErrorInfo}
import com.nepalius.user.api.{UserEndpoints, UserRegisterPayload, UserResponse}
import sttp.tapir.Endpoint
import sttp.tapir.generic.auto.*
import sttp.tapir.json.zio.jsonBody
import sttp.tapir.ztapir.*
import zio.ZLayer

import java.util.UUID

class UserEndpoints(base: BaseEndpoints):

  val registerEndPoint: Endpoint[Unit, UserRegisterPayload, ErrorInfo, UserResponse, Any] =
    base.publicEndpoint
      .summary("Register User")
      .post
      .in("api" / "users")
      .in(jsonBody[UserRegisterPayload].example(Examples.userRegisterRequest))
      .out(jsonBody[UserResponse].example(Examples.userResponse))

  private object Examples:
    val userRegisterRequest: UserRegisterPayload = UserRegisterPayload(
      email = "first.last@email.com",
      firstName = "First",
      lastName = "Last",
      password = "secret_password",
    )

    val userResponse: UserResponse = UserResponse(
      id = UUID.fromString("cbee098c-9fe5-4e0b-9e28-2d4a1db83345"),
      email = "user123@email.com",
      firstName = "First",
      lastName = "Last",
      token = Some(
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJTb2Z0d2FyZU1pbGwiLCJ1c2VyRW1haWwiOiJ1c2VyMTIzQGVtYWlsLmNvbSIsImV4cCI6MTY4MjU4MzY0NCwiaWF0IjoxNjgyNTgwMDQ0LCJqdGkiOiJkMmEzYThjZC1mNmFhLTQwNzgtYTk4Ni1jZmIwNTg5NTAxYmEifQ.SwY-ynkmR3-uYZU0K2cI0NY7Cs8oSgCU8RUVUagOAok",
      ),
    )

object UserEndpoints:
  val live: ZLayer[BaseEndpoints, Nothing, UserEndpoints] =
    ZLayer.fromFunction(new UserEndpoints(_))
