package com.msmoradi.samplelogin.repository

import com.msmoradi.samplelogin.dataSource.LocalUserDataSource
import com.msmoradi.samplelogin.model.User
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val userDataSource: LocalUserDataSource
) {

    suspend fun insert(user: User) {
        userDataSource.insert(user)
    }

    suspend fun update(user: User) {
        userDataSource.update(user)
    }

    suspend fun delete(user: User) {
        userDataSource.delete(user)
    }

    fun getUsers(): Flow<List<User>> {
        return userDataSource.readAll()
    }

}