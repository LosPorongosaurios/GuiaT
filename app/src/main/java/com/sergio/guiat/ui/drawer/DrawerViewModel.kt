package com.sergio.guiat.ui.drawer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.toObject
import com.sergio.guiat.server.User
import com.sergio.guiat.server.serverrepository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DrawerViewModel : ViewModel() {


    private val usersRepository = UsersRepository()

    private val loadProfile : MutableLiveData<User?> = MutableLiveData()
    val loadProfileDone: LiveData<User?> = loadProfile


    fun loadProfile() {
        GlobalScope.launch(Dispatchers.IO){
            val document = usersRepository.searchUser()
            val user : User? = document.toObject<User>()
            loadProfile.postValue(user)
        }
    }

}