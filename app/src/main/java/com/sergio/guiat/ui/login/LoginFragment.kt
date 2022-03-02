package com.sergio.guiat.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sergio.guiat.R
import com.sergio.guiat.databinding.FragmentLoginBinding
import com.sergio.guiat.local.Users
import com.sergio.guiat.ui.splash.SplashFragmentDirections

class LoginFragment : Fragment() {

    private lateinit var loginBinding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginBinding = FragmentLoginBinding.inflate(inflater, container, false)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        return loginBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        loginViewModel.findUserDone.observe(viewLifecycleOwner, { result ->
            onFindUserDoneSubscribe(result)
        })

        loginViewModel.msgDone.observe(viewLifecycleOwner) { msg ->
            onMsgDoneSuscribe(msg)
        }

        loginBinding.registerTextView.setOnClickListener{
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        with(loginBinding) {
            loginButton.setOnClickListener {
                loginViewModel.searchUser(mailEditText.text.toString(),passwordlEditText.text.toString())
            }
        }
    }

    private fun onFindUserDoneSubscribe(user: Users?) {
        if (user == null) {
            Toast.makeText(requireContext(), "Email no encontrado", Toast.LENGTH_SHORT).show()
        } else if (loginBinding.passwordlEditText.text.toString() == user.password) {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainActivity())
            activity?.finish()
        } else {
            Toast.makeText(requireContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onMsgDoneSuscribe(msg: String?) {
        Toast.makeText(
            requireContext(),
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }
}