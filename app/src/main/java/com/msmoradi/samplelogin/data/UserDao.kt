package com.msmoradi.samplelogin.data

import androidx.room.*
import com.msmoradi.samplelogin.model.User
import com.msmoradi.samplelogin.models.UserDto
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserDto)

    @Update
    fun update(user: UserDto)

    @Delete
    fun delete(user: UserDto)

    @Query("Select * From users")
    fun getUsers(): Flow<List<UserDto>>

    @Query("Select * From users Where username = :username AND password = :password")
    fun loginQuery(username: String, password: String): Flow<User?>

}