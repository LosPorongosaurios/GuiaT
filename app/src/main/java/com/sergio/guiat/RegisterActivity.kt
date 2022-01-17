package com.sergio.guiat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sergio.guiat.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        with(registerBinding) {
            registerButton.setOnClickListener {
                val name = nameEditText.text.toString()
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()
                val repPassword = passwordlEditText.text.toString()

                if (password == repPassword) {
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    intent.putExtra("name",name)
                    intent.putExtra("email", email)
                    intent.putExtra("password", password)
                    startActivity(intent)
                } else
                    Toast.makeText(
                        applicationContext,
                        "Las Contraseñas deben ser iguales",
                        Toast.LENGTH_SHORT
                    ).show()

            }
        }

    }
}