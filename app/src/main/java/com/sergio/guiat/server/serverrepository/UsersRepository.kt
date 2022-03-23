package com.sergio.guiat.server.serverrepository

import android.util.Log
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sergio.guiat.GuiaTProject
import com.sergio.guiat.local.Users
import com.sergio.guiat.local.GuiaTDao
import kotlinx.coroutines.tasks.await

class UsersRepository {

    private val auth: FirebaseAuth = Firebase.auth

    suspend fun registerUser(mail: String, password: String): String? {
        return try {
            val result = auth.createUserWithEmailAndPassword(mail, password).await()
            result.user?.uid
        } catch (e: FirebaseAuthException) {
            Log.d("errorRegister", e.localizedMessage)
            e.localizedMessage
        } catch (e: FirebaseNetworkException){
            e.localizedMessage
        }
    }
    suspend fun searchUser(email: String): Users {
        val guiatDao: GuiaTDao = GuiaTProject.database.GuiaTDao()
        return guiatDao.searchUser(email)
    }
}


