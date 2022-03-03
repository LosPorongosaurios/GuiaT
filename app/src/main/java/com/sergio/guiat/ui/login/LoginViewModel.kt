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

    private val usersRepository = UsersRepository()

    private val msg: MutableLiveData<String> = MutableLiveData()
    val msgDone: LiveData<String> = msg


    private val findUser: MutableLiveData<Users?> = MutableLiveData()
    val findUserDone: LiveData<Users?> = findUser

    fun searchUser(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()){
            msg.value = "Debe digitar los campos"
        } else {
            GlobalScope.launch(Dispatchers.IO) {
                findUser.postValue(usersRepository.searchUser(email))
            }
        }
    }
}