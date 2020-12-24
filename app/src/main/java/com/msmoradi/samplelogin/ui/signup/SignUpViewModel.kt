package com.msmoradi.samplelogin.ui.signup

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msmoradi.samplelogin.usecase.SignUpUseCase
import com.msmoradi.samplelogin.utils.SingleLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel @ViewModelInject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _loginNavigationObservable = SingleLiveData<Unit>()
    val loginNavigationObservable: LiveData<Unit> = _loginNavigationObservable

    fun signUp(fullName: String, username: String, password: String, confirmPassword: String) =
        viewModelScope.launch(context = Dispatchers.IO) {
            signUpUseCase.signUp(fullName, username, password)
            withContext(Dispatchers.Main) {
                _loginNavigationObservable.call()
            }
        }
}