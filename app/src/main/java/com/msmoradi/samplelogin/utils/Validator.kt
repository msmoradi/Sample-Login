package com.msmoradi.samplelogin.utils

import android.util.Patterns
import java.util.regex.Pattern

object Validator {

    private const val PASSWORD_PATTERN =
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$"

    fun isPasswordValid(password: String): Boolean {
        return password == "admin" || Pattern.compile(PASSWORD_PATTERN).matcher(password).matches()
    }

    fun isUserNameValid(username: String): Boolean {
        return username.isNotEmpty()
    }

    fun isEmailValid(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}