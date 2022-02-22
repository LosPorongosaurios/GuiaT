package com.sergio.guiat.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sergio.guiat.local.Users
import com.sergio.guiat.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val usersRepository = UsersRepository()

    private val findUser: MutableLiveData<Users> = MutableLiveData()
    val findBookDone: LiveData<Users> = findUser

    fun searchUser(email: String) {
        GlobalScope.launch(Dispatchers.IO) {
            findUser.postValue(usersRepository.searchUser(email))
        }
    }
    // TODO: Implement the ViewModel
}