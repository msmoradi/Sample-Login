package com.msmoradi.samplelogin.data.datasourceimpl

import com.msmoradi.samplelogin.data.UserDao
import com.msmoradi.samplelogin.dataSource.LocalUserDataSource
import com.msmoradi.samplelogin.model.Result
import com.msmoradi.samplelogin.model.User
import com.msmoradi.samplelogin.models.UserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalUserDataSourceImpl @Inject constructor(
    private val userDao: UserDao
) : LocalUserDataSource {

    override suspend fun insert(user: User) {
        userDao.insert(user.toUserDto())
    }

    override suspend fun update(user: User) {
        userDao.update(user.toUserDto())
    }

    override suspend fun delete(user: User) {
        userDao.delete(user.toUserDto())
    }

    override fun readAll(): Flow<List<User>> {
        return userDao.getUsers().map {
            it.map { userDto -> userDto.toUser() }
        }
    }

    override fun loginQuery(username: String, password: String): Flow<Result<User>> {
        return userDao.loginQuery(username, password)
            .map {
                if (it != null)
                    Result.Success(it)
                else
                    Result.Error(Exception())
            }
    }

}

fun User.toUserDto() = UserDto(
    id = id ?: 0,
    fullName = fullName,
    password = password,
    username = username,
    email = email
)

fun UserDto.toUser() = User(
    id = id,
    fullName = fullName,
    password = password,
    username = username,
    email = email
)
