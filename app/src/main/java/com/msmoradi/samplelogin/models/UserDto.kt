package com.msmoradi.samplelogin.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserDto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val fullName: String,
    val username: String,
    val email: String,
    val password: String
)