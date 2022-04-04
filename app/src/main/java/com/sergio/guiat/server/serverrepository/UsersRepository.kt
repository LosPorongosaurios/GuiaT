package com.sergio.guiat.server.serverrepository

import android.util.Log
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UsersRepository {

    val db = Firebase.firestore
    private val auth: FirebaseAuth = Firebase.auth

    suspend fun registerUser(mail: String, password: String): String? {
        return try {
            val result = auth.createUserWithEmailAndPassword(mail, password).await()
            result.user?.uid
        } catch (e: FirebaseAuthException) {
            Log.d("errorRegister", e.localizedMessage)
            e.localizedMessage
        } catch (e: FirebaseNetworkException) {
            e.localizedMessage
        }
    }

    suspend fun searchUser(): DocumentSnapshot {
        return withContext(Dispatchers.IO) {
            db.collection("users").document(auth.currentUser?.uid.toString()).get().await()
        }
    }
}


