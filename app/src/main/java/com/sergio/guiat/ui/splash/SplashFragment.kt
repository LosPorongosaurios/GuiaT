package com.sergio.guiat.ui.splash

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sergio.guiat.R
import com.sergio.guiat.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private lateinit var splashBinding: FragmentSplashBinding
    private lateinit var splashViewModel: SplashViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        splashBinding = FragmentSplashBinding.inflate(inflater, container, false)
        splashViewModel = ViewModelProvider(this)[SplashViewModel::class.java]
        auth = Firebase.auth
        addSubscriptions()
        return splashBinding.root
    }


    private fun addSubscriptions() {
        splashViewModel.onTimer.observe(viewLifecycleOwner){
            if( auth.currentUser == null){
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
        }else findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToDrawerActivity2())
        }
    }


}