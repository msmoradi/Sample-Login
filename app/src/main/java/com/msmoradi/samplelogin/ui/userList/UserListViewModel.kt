package com.msmoradi.samplelogin.ui.userList

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msmoradi.samplelogin.model.User
import com.msmoradi.samplelogin.usecase.UserUseCase
import com.msmoradi.samplelogin.utils.SingleLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserListViewModel @ViewModelInject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _userObservable = MutableLiveData<List<User>>()
    val userObservable: LiveData<List<User>> = _userObservable

    private val _removeUserObservable = SingleLiveData<User>()
    val removeUserObservable: LiveData<User> = _removeUserObservable

    init {
        viewModelScope.launch {
            getUserList()
        }
    }

    fun removeUser(user: User) = viewModelScope.launch(context = Dispatchers.IO) {
        userUseCase.removeUser(user)
        withContext(Dispatchers.Main) {
            _removeUserObservable.value = user
        }
    }

    private suspend fun getUserList() {
        userUseCase.getUserList().collect {
            _userObservable.value = it
        }
    }
}