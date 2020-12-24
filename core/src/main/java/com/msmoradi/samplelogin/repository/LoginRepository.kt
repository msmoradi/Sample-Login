package com.msmoradi.samplelogin.repository

import com.msmoradi.samplelogin.DataSource.LocalUserDataSource
import com.msmoradi.samplelogin.model.LoginResponse
import com.msmoradi.samplelogin.model.Result
import com.msmoradi.samplelogin.model.UserType

class LoginRepository(
    private val localUserDataSource: LocalUserDataSource
) {

    fun login(username: String, password: String): Result<LoginResponse> {
        return if (username == "admin" && password == "admin") {
            Result.Success(LoginResponse(type = UserType.ADMIN))
        } else {
            val result = localUserDataSource.loginQuery(username, password)
            if (result is Result.Success)
                Result.Success(
                    LoginResponse(
                        type = UserType.REGULAR,
                        user = result.data
                    )
                )
            else
                Result.Error(Exception())
        }
    }
}