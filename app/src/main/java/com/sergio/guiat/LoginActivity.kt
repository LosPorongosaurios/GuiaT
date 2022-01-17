package com.sergio.guiat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sergio.guiat.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        var emailReceived : String? = ""
        var passwordReceived : String? = ""

        val credentials = intent.extras
        if  (credentials != null){
            emailReceived = credentials.getString("email")
            passwordReceived = credentials.getString("password")
        }

        loginBinding.registerTextView.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        with(loginBinding){
            loginButton.setOnClickListener{
                val email=mailEditText.text.toString()
                val password = passwordlEditText.text.toString()

                if (email == emailReceived && password == passwordReceived && email.isNotEmpty() && password.isNotEmpty()){
                    val intent = Intent(this@LoginActivity,MainActivity::class.java)
                    intent.putExtra("email", emailReceived)
                    intent.putExtra("password", passwordReceived)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LoginActivity,"Usuario o contraseña incorrectos",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}