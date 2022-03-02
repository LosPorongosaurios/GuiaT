package com.sergio.guiat.ui.register

import androidx.core.util.PatternsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sergio.guiat.repository.UsersRepository
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

            /*GlobalScope.launch(Dispatchers.IO){
                      usersRepository.saveUser(name,email,cel,password)
                    }*/
        }
    }

    fun saveUser(name: String, email: String, cel: String, password: String) {
        GlobalScope.launch(Dispatchers.IO) {
            usersRepository.saveUser(name, email, cel, password)
        }
    }

}