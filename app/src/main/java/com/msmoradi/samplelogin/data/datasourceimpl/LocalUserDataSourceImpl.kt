package com.msmoradi.samplelogin.data.datasourceimpl

import com.msmoradi.samplelogin.DataSource.LocalUserDataSource
import com.msmoradi.samplelogin.data.UserDao
import com.msmoradi.samplelogin.model.Result
import com.msmoradi.samplelogin.model.User
import com.msmoradi.samplelogin.models.UserDto
import javax.inject.Inject

class LocalUserDataSourceImpl @Inject constructor(
    private val userDao: UserDao
) : LocalUserDataSource {

    override fun insert(user: User) {
        userDao.insert(user.toUserDto())
    }

    override fun update(user: User) {
        userDao.update(user.toUserDto())
    }

    override fun delete(user: User) {
        userDao.delete(user.toUserDto())
    }

    override fun readAll(): List<User> {
        return userDao.getUsers().map { it.toUser() }
    }

    override fun loginQuery(username: String, password: String): Result<User> {
        val user = userDao.loginQuery(username, password)
        return if (user != null)
            Result.Success(user)
        else
            Result.Error(Exception())
    }

}

fun User.toUserDto() = UserDto(
    id = id ?: 0,
    fullName = fullName,
    password = password,
    username = username
)

fun UserDto.toUser() = User(
    id = id,
    fullName = fullName,
    password = password,
    username = username
)
