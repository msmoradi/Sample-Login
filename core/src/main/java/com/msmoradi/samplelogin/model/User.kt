package com.msmoradi.samplelogin.model

data class User(
    val id: Long? = null,
    val fullName: String,
    val username: String,
    val password: String
)