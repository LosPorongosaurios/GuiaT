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
import com.sergio.guiat.R
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

        initViews()


    }

    private fun initViews() {
        chatBinding.messagesRecylerView.layoutManager = LinearLayoutManager(this@ChatFragment.requireContext())
        chatBinding.messagesRecylerView.adapter = MessageAdapter(chatViewModel.getCurrentEmail())

        chatBinding.sendMessageButton.setOnClickListener{
            sendMessage()
        }

        val chatRef = db.collection("chats").document(args.chatId)

        chatRef.collection("messages").orderBy("dob", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { messages ->
                val listMessages = messages.toObjects(MessageModel::class.java)
                (chatBinding.messagesRecylerView.adapter as MessageAdapter).setData(listMessages)
            }

        chatRef.collection("messages").orderBy("dob", Query.Direction.ASCENDING)
            .addSnapshotListener { messages, error ->
                if(error == null){
                    messages?.let {
                        val listMessages = it.toObjects(MessageModel::class.java)
                        (chatBinding.messagesRecylerView.adapter as MessageAdapter).setData(listMessages)
                    }
                }
            }

       // chatViewModel.loadMessage(args.chat.id)

    }

   /* private fun sendMessage() {
        val message = MessageModel(
            message = chatBinding.messageTextView.text.toString(),
            from = chatViewModel.getCurrentEmail()
        )
        chatViewModel.sendMessage(message)
        chatBinding.messageTextView.setText("")
    }*/

    private fun sendMessage(){
        val message = MessageModel(
            message = chatBinding.messageTextView.text.toString(),
            from = auth.currentUser?.email.toString()
        )

        db.collection("chats").document(args.chatId).collection("messages").document().set(message)

        chatBinding.messageTextView.setText("")


    }

}