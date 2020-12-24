package com.msmoradi.samplelogin.model

data class LoginResponse(
    val type: UserType,
    val user: User? = null
)