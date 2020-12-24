package com.msmoradi.samplelogin.dataSource

import com.msmoradi.samplelogin.model.Result
import com.msmoradi.samplelogin.model.User
import kotlinx.coroutines.flow.Flow

interface LocalUserDataSource {
    suspend fun insert(user: User)
    suspend fun update(user: User)
    suspend fun delete(user: User)
    fun readAll(): Flow<List<User>>
    fun loginQuery(username: String, password: String): Flow<Result<User>>
}