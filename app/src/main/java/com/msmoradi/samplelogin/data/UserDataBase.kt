package com.msmoradi.samplelogin.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.msmoradi.samplelogin.models.UserDto

/**
 * SQLite Database for storing the users.
 */
@Database(entities = [UserDto::class], version = 1, exportSchema = false)
abstract class UserDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
}