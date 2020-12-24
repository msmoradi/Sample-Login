package com.msmoradi.samplelogin.DataSource

import com.msmoradi.samplelogin.model.Result
import com.msmoradi.samplelogin.model.User

interface LocalUserDataSource {
    fun insert(user: User)
    fun update(user: User)
    fun delete(user: User)
    fun readAll(): List<User>
    fun loginQuery(username: String, password: String): Result<User>
}