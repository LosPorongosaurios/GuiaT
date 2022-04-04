package com.sergio.guiat.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sergio.guiat.databinding.FragmentDetailBinding
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {


    private lateinit var detailBinding: FragmentDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private val args: DetailFragmentArgs by navArgs()
    private val user: String = Firebase.auth.currentUser?.email.toString()
    private lateinit var otherUserUid: String
    private lateinit var chatId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailBinding = FragmentDetailBinding.inflate(inflater, container, false)
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        return detailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        with(detailBinding) {

            val tour = args.routes
            nameTourTextView.text = tour.name
            guideTextView.text = tour.guide
            descriptionTextView.text = tour.description
            scheduleTextView.text = tour.schedule
            Picasso.get().load(tour.urlPicture).into(tourImageView)
        }

        detailBinding.reserveNowButton.setOnClickListener {

            detailViewModel.isChatCreated(args.routes.guideMail.toString())

            detailViewModel.loadChatIdDone.observe(viewLifecycleOwner) { result ->
                onLoadChatsDoneSusbscribe(result)
            }

            detailViewModel.searchOtherUserUid(args.routes.guideMail.toString())

            detailViewModel.findOtherUserUidDone.observe(viewLifecycleOwner) { result ->
                onFinOtherUserUidDoneSubscribe(result)
            }


        }

    }

    private fun onLoadChatsDoneSusbscribe(chatId: String?) {
        if (chatId == null) {
            Toast.makeText(requireContext(), "Id del Chat no encontrado", Toast.LENGTH_SHORT).show()
        } else {
            findNavController().navigate(
                DetailFragmentDirections.actionDetailFragment2ToChatFragment(
                    chatId,
                    user
                )
            )
        }
    }

    private fun onFinOtherUserUidDoneSubscribe(otherUserUidd: String?) {
        if (otherUserUidd == null) {
            Toast.makeText(requireContext(), "Uid del usuario no encontrado", Toast.LENGTH_SHORT)
                .show()
        } else {
            if (detailViewModel.chatCreated == true) {
                detailViewModel.isChatCreated(args.routes.guideMail.toString())
            } else {
                otherUserUid = otherUserUidd
                detailViewModel.newChat(args.routes.guideMail.toString(), otherUserUid)

                chatId = detailViewModel.getChatId()
                findNavController().navigate(
                    DetailFragmentDirections.actionDetailFragment2ToChatFragment(
                        chatId,
                        user
                    )
                )
            }
        }
    }

}