package com.msmoradi.samplelogin.ui.profile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msmoradi.samplelogin.R
import com.msmoradi.samplelogin.model.User
import com.msmoradi.samplelogin.usecase.UserUseCase
import com.msmoradi.samplelogin.utils.SingleLiveData
import com.msmoradi.samplelogin.utils.Validator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel @ViewModelInject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _loginNavigationObservable = SingleLiveData<Unit>()
    val loginNavigationObservable: LiveData<Unit> = _loginNavigationObservable

    private val _usernameErrorObservable = SingleLiveData<Int>()
    val userNameErrorObservable: LiveData<Int> = _usernameErrorObservable

    private val _emailErrorObservable = SingleLiveData<Int>()
    val emailErrorObservable: LiveData<Int> = _emailErrorObservable

    fun updateUser(user: User, username: String, email: String) =
        viewModelScope.launch(context = Dispatchers.Main) {
            if (validator(username, email)) {
                withContext(Dispatchers.IO) {
                    userUseCase.update(user.copy(username = username, email = email))
                }
                _loginNavigationObservable.call()
            }
        }

    private fun validator(username: String, email: String): Boolean {
        var isValid = true
        if (!Validator.isUserNameValid(username)) {
            _usernameErrorObservable.value = R.string.error_user_name
            isValid = false
        }
        if (!Validator.isEmailValid(email)) {
            _emailErrorObservable.value = R.string.error_email
            isValid = false
        }
        return isValid
    }

    fun removeUser(user: User) =
        viewModelScope.launch(context = Dispatchers.IO) {
            userUseCase.removeUser(user)
            withContext(Dispatchers.Main) {
                _loginNavigationObservable.call()
            }
        }
}