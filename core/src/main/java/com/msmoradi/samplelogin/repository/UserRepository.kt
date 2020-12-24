package com.msmoradi.samplelogin.repository

import com.msmoradi.samplelogin.DataSource.LocalUserDataSource
import com.msmoradi.samplelogin.model.User

class UserRepository(
    private val userDataSource: LocalUserDataSource
) {

    fun insert(user: User) {
        userDataSource.insert(user)
    }

    fun update(user: User) {
        userDataSource.update(user)
    }

    fun delete(user: User) {
        userDataSource.delete(user)
    }

    fun getUsers(): List<User> {
        return userDataSource.readAll()
    }

}