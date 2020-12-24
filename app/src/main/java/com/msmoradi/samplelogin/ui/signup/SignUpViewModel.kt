package com.msmoradi.samplelogin.ui.signup

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msmoradi.samplelogin.R
import com.msmoradi.samplelogin.usecase.SignUpUseCase
import com.msmoradi.samplelogin.utils.SingleLiveData
import com.msmoradi.samplelogin.utils.Validator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel @ViewModelInject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _loginNavigationObservable = SingleLiveData<Unit>()
    val loginNavigationObservable: LiveData<Unit> = _loginNavigationObservable

    private val _usernameErrorObservable = SingleLiveData<Int>()
    val userNameErrorObservable: LiveData<Int> = _usernameErrorObservable

    private val _passwordErrorObservable = SingleLiveData<Int>()
    val passwordErrorObservable: LiveData<Int> = _passwordErrorObservable

    private val _fullNameErrorObservable = SingleLiveData<Int>()
    val fullNameErrorObservable: LiveData<Int> = _fullNameErrorObservable

    private val _emailErrorObservable = SingleLiveData<Int>()
    val emailErrorObservable: LiveData<Int> = _emailErrorObservable

    private val _confirmPasswordErrorObservable = SingleLiveData<Int>()
    val confirmPasswordErrorObservable: LiveData<Int> = _confirmPasswordErrorObservable

    fun signUp(
        fullName: String,
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ) = viewModelScope.launch(context = Dispatchers.Main) {
        if (validation(fullName, username, email, password, confirmPassword)) {
            withContext(Dispatchers.IO) {
                signUpUseCase.signUp(fullName, username, email, password)
            }
            _loginNavigationObservable.call()
        }
    }

    private fun validation(
        fullName: String,
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        var isValid = true

        if (fullName.isEmpty()) {
            _fullNameErrorObservable.value = R.string.error_full_name
            isValid = false
        }

        if (!Validator.isUserNameValid(username)) {
            _usernameErrorObservable.value = R.string.error_user_name
            isValid = false
        }

        if (!Validator.isEmailValid(email)) {
            _emailErrorObservable.value = R.string.error_email
            isValid = false
        }

        if (!Validator.isPasswordValid(password)) {
            _passwordErrorObservable.value = R.string.error_password
            isValid = false
        }

        if (confirmPassword != password) {
            _confirmPasswordErrorObservable.value = R.string.error_confirm_password
            isValid = false
        }
        return isValid
    }
}