package com.msmoradi.samplelogin.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msmoradi.samplelogin.R
import com.msmoradi.samplelogin.model.Result
import com.msmoradi.samplelogin.model.User
import com.msmoradi.samplelogin.model.UserType
import com.msmoradi.samplelogin.usecase.LoginUseCase
import com.msmoradi.samplelogin.utils.SingleLiveData
import com.msmoradi.samplelogin.utils.Validator
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _profileNavigationObservable = SingleLiveData<User>()
    val profileNavigationObservable: LiveData<User> = _profileNavigationObservable

    private val _userListNavigationObservable = SingleLiveData<Unit>()
    val userListNavigationObservable: LiveData<Unit> = _userListNavigationObservable

    private val _snackBarObservable = SingleLiveData<String>()
    val snackBarObservable: LiveData<String> = _snackBarObservable

    private val _usernameErrorObservable = SingleLiveData<Int>()
    val userNameErrorObservable: LiveData<Int> = _usernameErrorObservable

    private val _passwordErrorObservable = SingleLiveData<Int>()
    val passwordErrorObservable: LiveData<Int> = _passwordErrorObservable

    fun loginClicked(username: String, password: String) =
        viewModelScope.launch {
            if (validation(username, password)) {
                loginUseCase.login(username, password)
                    .collect { result ->
                        if (result is Result.Success) {
                            when (result.data.type) {
                                UserType.ADMIN -> _userListNavigationObservable.call()
                                UserType.REGULAR -> _profileNavigationObservable.value =
                                    result.data.user
                            }
                        } else {
                            _snackBarObservable.value = "Error Login"
                        }
                    }

            }
        }

    private fun validation(username: String, password: String): Boolean {
        var isValid = true
        if (!Validator.isUserNameValid(username)) {
            _usernameErrorObservable.value = R.string.error_user_name
            isValid = false
        }

        if (!Validator.isPasswordValid(password)) {
            _passwordErrorObservable.value = R.string.error_password
            isValid = false
        }
        return isValid
    }
}