package com.sergio.guiat.ui.register

import android.widget.Toast
import androidx.core.util.PatternsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sergio.guiat.server.User
import com.sergio.guiat.server.serverrepository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.regex.Pattern


class RegisterViewModel : ViewModel() {

    private val usersRepository = UsersRepository()

    private val msg: MutableLiveData<String> = MutableLiveData()
    val msgDone: LiveData<String> = msg

    private val dataValidate: MutableLiveData<Boolean> = MutableLiveData()
    val dataValidated: LiveData<Boolean> = dataValidate

    private val errorMsg: MutableLiveData<String?> = MutableLiveData()
    val errorMsgDone: LiveData<String?> = errorMsg

    private val registerSucess: MutableLiveData<String?> = MutableLiveData()
    val registerSucessDone: LiveData<String?> = registerSucess

    fun registerUser(name: String, email: String,cel: String, password: String) {

        GlobalScope.launch(Dispatchers.IO) {
            when (val result = usersRepository.registerUser(email, password)) {
                "The email address is already in use by another account." -> errorMsg.postValue("Ya existe una cuenta con ese correo electrónico")
                "The given password is invalid. [ Password should be at least 6 characters ]" -> errorMsg.postValue("La contraseña debe tener mínimo 6 digitos")
                "The email address is badly formatted." -> errorMsg.postValue("El formato de email es incorrecto")
                "A network error (such as timeout, interrupted connection or unreachable host) has occurred." -> errorMsg.postValue("No tiene conexión a internet")
                else -> { registerSucess.postValue(result)
                    createUser(uid = result , name = name , email = email , cel = cel)}
            }
        }
    }

    fun createUser(uid: String?,name: String , email: String, cel: String ) {

        val db = Firebase.firestore
        val user = User(uid = uid, name = name ,email = email, cel = cel )
        uid?.let { uid ->
            db.collection("users").document(uid).set(user)
                .addOnSuccessListener {
                    //Toast.makeText(baseContext, "Usuario creado exitosamente", Toast.LENGTH_SHORT).show()
                }
        }

    }

    val passwordRegul = Pattern.compile(
        "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +        //at least 1 lower case letter
                "(?=.*[A-Z])" +        //at least 1 upper case letter
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$"
    )

    val celRegul = Pattern.compile(
        "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                ".{10,}" +               //at least 4 characters
                "$"
    )

    fun validateFields(
        name: String,
        email: String,
        cel: String,
        password: String,
        re_password: String
    ) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            msg.value = "Debe digitar nombre , correo , telefono y contraseña"
        } else if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            msg.value = "No es un correo electronico valido"
        } else if (!celRegul.matcher(cel).matches()) {
            msg.value = "El número telefonico debe tener 10 caracteres"
        } else if (!passwordRegul.matcher(password).matches()) {
            msg.value = "La contraseña no es segura"
        } else if (password != re_password) {
            msg.value = "Las contraseñas deben coincidir"
        } else {

            dataValidate.value = true


        }
    }
}


