package com.msmoradi.samplelogin.usecase

import com.msmoradi.samplelogin.model.User
import com.msmoradi.samplelogin.repository.UserRepository

class SignUpUseCase(
    private val userRepository: UserRepository
) {
    suspend fun signUp(fullName: String, username: String, email: String, password: String) {
        userRepository.insert(
            User(
                fullName = fullName,
                username = username,
                email = email,
                password = password
            )
        )
    }
}