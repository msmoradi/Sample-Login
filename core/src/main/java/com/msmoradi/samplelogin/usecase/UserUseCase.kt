package com.msmoradi.samplelogin.usecase

import com.msmoradi.samplelogin.model.User
import com.msmoradi.samplelogin.repository.UserRepository

class UserUseCase(
    private val userRepository: UserRepository
) {

    fun getUserList() = userRepository.getUsers()

    suspend fun removeUser(user: User) = userRepository.delete(user)

    suspend fun update(user: User) = userRepository.update(user)

}