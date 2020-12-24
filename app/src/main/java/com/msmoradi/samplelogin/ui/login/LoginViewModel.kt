package com.msmoradi.samplelogin.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msmoradi.samplelogin.model.Result
import com.msmoradi.samplelogin.model.User
import com.msmoradi.samplelogin.model.UserType
import com.msmoradi.samplelogin.usecase.LoginUseCase
import com.msmoradi.samplelogin.utils.SingleLiveData
import com.msmoradi.samplelogin.utils.Validator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
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

    private val _emailErrorObservable = SingleLiveData<String>()
    val emailErrorObservable: LiveData<String> = _snackBarObservable

    private val _passwordErrorObservable = SingleLiveData<String>()
    val passwordErrorObservable: LiveData<String> = _snackBarObservable

    fun loginClicked(username: String, password: String) =
        viewModelScope.launch {
            if (validation(username, password)) {
                loginUseCase.login(username, password)
                    .flowOn(Dispatchers.Main)
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
        if (!Validator.isEmailValid(username)) {
            _emailErrorObservable.value = "Email not valid"
            isValid = false
        }

        if (!Validator.isPasswordValid(password)) {
            _passwordErrorObservable.value = "password not valid"
            isValid = false
        }
        return isValid
    }
}