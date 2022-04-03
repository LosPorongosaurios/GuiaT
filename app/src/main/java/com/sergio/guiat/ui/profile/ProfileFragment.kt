package com.sergio.guiat.ui.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sergio.guiat.databinding.FragmentProfileBinding
import com.sergio.guiat.server.User
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {


    private lateinit var profileBinding: FragmentProfileBinding
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileBinding = FragmentProfileBinding.inflate(inflater,container,false)
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        return profileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.loadProfileDone.observe(viewLifecycleOwner){ result ->
            onLoadProfileDoneSubscribe(result)
        }

        profileViewModel.loadProfile()

    }

    private fun onLoadProfileDoneSubscribe(user: User?) {

        with(profileBinding){
            nameUserTextView.text = user?.name.toString()
            emailUserTextView.text = user?.email.toString()
            celUserTextView.text = user?.email.toString()
            Picasso.get().load(user?.urlPicture).into(profileImageView)
        }

    }


}