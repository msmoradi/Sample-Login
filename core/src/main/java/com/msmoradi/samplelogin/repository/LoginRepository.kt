package com.msmoradi.samplelogin.repository

import com.msmoradi.samplelogin.dataSource.LocalUserDataSource
import com.msmoradi.samplelogin.model.LoginResponse
import com.msmoradi.samplelogin.model.Result
import com.msmoradi.samplelogin.model.UserType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class LoginRepository(
    private val localUserDataSource: LocalUserDataSource
) {
    fun login(username: String, password: String): Flow<Result<LoginResponse>> {
        return if (username == "admin" && password == "admin") {
            flow {
                Result.Success(LoginResponse(type = UserType.ADMIN))
            }
        } else {
            localUserDataSource
                .loginQuery(username, password)
                .map {
                    if (it is Result.Success)
                        Result.Success(
                            LoginResponse(
                                type = UserType.REGULAR,
                                user = it.data
                            )
                        )
                    else
                        Result.Error(Exception())
                }

        }
    }
}