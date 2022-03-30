package com.sergio.guiat.ui.chat

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sergio.guiat.databinding.FragmentChatBinding
import com.sergio.guiat.models.MessageModel

class ChatFragment : Fragment() {

    private lateinit var chatBinding: FragmentChatBinding
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var messageAdapter: MessageAdapter
    private val args : ChatFragmentArgs by navArgs()
    private var auth = Firebase.auth
    private var db = Firebase.firestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        chatBinding = FragmentChatBinding.inflate(inflater,container,false)
        chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        return chatBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       chatViewModel.loadMessagesDone.observe(viewLifecycleOwner){result ->
            onLoadMessagesDoneSusbcribe(result)
        }

        chatViewModel.uploadMessagesDone.observe(viewLifecycleOwner){ result ->
            onUploadMessagesDoneSusbcribe(result)
        }

        initViews()

    }


    private fun initViews() {
        chatBinding.messagesRecylerView.layoutManager =
            LinearLayoutManager(this@ChatFragment.requireContext())
        chatBinding.messagesRecylerView.adapter = MessageAdapter(chatViewModel.getCurrentEmail())

        chatBinding.sendMessageButton.setOnClickListener {
            sendMessage()
        }


        chatViewModel.loadMessage(args.chatId)
        chatViewModel.uploadMessage(args.chatId)


    }

    private fun onLoadMessagesDoneSusbcribe(listMessages: MutableList<MessageModel>) {
        (chatBinding.messagesRecylerView.adapter as MessageAdapter).setData(listMessages)
    }
    private fun onUploadMessagesDoneSusbcribe(listMessages: MutableList<MessageModel>) {
        (chatBinding.messagesRecylerView.adapter as MessageAdapter).setData(listMessages)
    }

    private fun sendMessage() {
        val message = MessageModel(
            message = chatBinding.messageTextView.text.toString(),
            from = chatViewModel.getCurrentEmail()
        )
        chatViewModel.sendMessage(message,args.chatId)
        chatBinding.messageTextView.setText("")
    }



}