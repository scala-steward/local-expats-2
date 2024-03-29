package com.nepalius.user.api

import com.nepalius.user.domain.{User, UserRegisterData}
import com.nepalius.user.{UserRegisterPayload, UserResponse}

object UserMapper:
  def toUserRegisterData(payload: UserRegisterPayload) =
    UserRegisterData(
      payload.email,
      payload.firstName,
      payload.lastName,
      payload.password,
    )

  def toUserResponse(user: User): UserResponse =
    UserResponse(
      user.id,
      user.data.email,
      user.data.firstName,
      user.data.lastName,
    )
