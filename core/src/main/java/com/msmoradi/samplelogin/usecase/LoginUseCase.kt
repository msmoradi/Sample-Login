package com.msmoradi.samplelogin.usecase

import com.msmoradi.samplelogin.model.LoginResponse
import com.msmoradi.samplelogin.model.Result
import com.msmoradi.samplelogin.repository.LoginRepository
import kotlinx.coroutines.flow.Flow

class LoginUseCase constructor(
    private val loginRepository: LoginRepository
) {
    fun login(username: String, password: String): Flow<Result<LoginResponse>> =
        loginRepository.login(username, password)
}