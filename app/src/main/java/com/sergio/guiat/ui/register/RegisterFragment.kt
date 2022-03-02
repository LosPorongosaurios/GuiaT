package com.sergio.guiat.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sergio.guiat.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var registerBinding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerBinding = FragmentRegisterBinding.inflate(inflater, container, false)
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        return registerBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerViewModel.msgDone.observe(viewLifecycleOwner, { result ->
            onMsgDoneSubscribe(result)
        })

        registerViewModel.dataValidated.observe(viewLifecycleOwner, { result ->
            onDataValidatedSubscribe(result)
        })

        with(registerBinding) {
            registerButton.setOnClickListener {
                registerViewModel.validateFields(
                    nameEditText.text.toString(),
                    emailEditText.text.toString(),
                    celEditText.text.toString(),
                    passwordEditText.text.toString(),
                    passwordlEditText.text.toString()

                )
            }
        }
    }

    private fun onDataValidatedSubscribe(result: Boolean?) {
        with(registerBinding) {
            val name: String = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val cel  = celEditText.text.toString()
            val password = passwordEditText.text.toString()

            registerViewModel.saveUser(name,email,cel,password)

            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())

        }

    }

    private fun onMsgDoneSubscribe(msg: String?) {
        Toast.makeText(
            requireContext(),
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }


}