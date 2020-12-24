package com.msmoradi.samplelogin.model

import java.io.Serializable

data class User(
    val id: Long? = null,
    val fullName: String,
    val username: String,
    val password: String
) : Serializable