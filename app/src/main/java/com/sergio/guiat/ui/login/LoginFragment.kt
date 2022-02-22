package com.sergio.guiat.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sergio.guiat.R
import com.sergio.guiat.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var loginBinding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loginBinding = FragmentLoginBinding.inflate(inflater, container, false)
        loginViewModel = ViewModelProvider(this)[loginViewModel::class.java]
        return loginBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        with(loginBinding) {
            loginButton.setOnClickListener {
                loginViewModel.searchUser(mailEditText.text.toString())
            }
        }
    }
}