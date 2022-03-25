package com.sergio.guiat.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sergio.guiat.local.Users
import com.sergio.guiat.server.serverrepository.UsersRepository

class LoginViewModel : ViewModel() {

    private val usersRepository = UsersRepository()
    private var auth: FirebaseAuth = Firebase.auth

    private val msg: MutableLiveData<String> = MutableLiveData()
    val msgDone: LiveData<String> = msg

    private val msgLogin: MutableLiveData<String> = MutableLiveData()
    val msgLoginDone: LiveData<String> = msgLogin


    private val findUser: MutableLiveData<Boolean> = MutableLiveData()
    val findUserDone: LiveData<Boolean> = findUser

    fun searchUser(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            msg.value = "Debe digitar los campos"
        } else {
            //GlobalScope.launch(Dispatchers.IO) {
            //  findUser.postValue(usersRepository.searchUser(email))

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        findUser.value = true

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Login", "signInWithEmail:failure", task.exception)
                        msgLogin.postValue("Authentication failed.")
                    }
                }
        }
    }
}
