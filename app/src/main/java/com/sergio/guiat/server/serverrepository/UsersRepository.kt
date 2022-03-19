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
import java.sql.Types.NULL

class UsersRepository {
    private lateinit var auth: FirebaseAuth


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




/*suspend fun saveUser(name: String, mail: String, cel: String, password: String) {
    val users = Users(
        id = NULL,
        name = name,
        mail = mail,
        cel = cel,
        password = password
    )
    val userDao : GuiaTDao = GuiaTProject.database.GuiaTDao()
    userDao.saveUser(users)

}

suspend fun searchUser(email: String): Users {
    val guiatDao: GuiaTDao = GuiaTProject.database.GuiaTDao()
    return guiatDao.searchUser(email)
}


}*/