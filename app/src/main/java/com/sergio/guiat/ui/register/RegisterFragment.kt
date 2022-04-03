package com.sergio.guiat.ui.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sergio.guiat.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private val fileResult = 1
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var registerBinding: FragmentRegisterBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        registerBinding = FragmentRegisterBinding.inflate(inflater, container, false)
        registerViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        return registerBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        registerViewModel.msgDone.observe(viewLifecycleOwner) { result ->
            onMsgDoneSubscribe(result)
        }

        registerViewModel.dataValidated.observe(viewLifecycleOwner) { result ->
            onDataValidatedSubscribe(result)
        }


        with(registerBinding) {
            registerButton.setOnClickListener {
                registerViewModel.validateFields(
                    nameEditText.text.toString(),
                    emailEditText.text.toString(),
                    celEditText.text.toString(),
                    passwordEditText.text.toString(),
                    passwordlEditText.text.toString(),

                )
            }
        }

        registerBinding.porfilePhotoImageView.setOnClickListener {
            fileManager()
        }
    }

    private fun fileManager() {
        val intent = Intent(Intent.ACTION_VIEW)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {

        }
        intent.type = "image/*"
        startActivityForResult(intent, fileResult)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == fileResult) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val clipData = data.clipData
                if (clipData != null) {
                    for (i in 0 until clipData.itemCount) {
                        val uri = clipData.getItemAt(i).uri
                        uri?.let { registerViewModel.fileUpload(it) }
                    }
                } else {
                    val uri = data.data
                    uri?.let { registerViewModel.fileUpload(it) }
                }
            }
        }
    }

    private fun onDataValidatedSubscribe(result: Boolean?) {
        with(registerBinding) {
            val name: String = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val cel = celEditText.text.toString()
            val password = passwordEditText.text.toString()

            registerViewModel.registerUser(name, email, cel, password)

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