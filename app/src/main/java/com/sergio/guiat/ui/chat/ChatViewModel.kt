package com.sergio.guiat.ui.chat

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sergio.guiat.models.MessageModel

class ChatViewModel : ViewModel() {

    private val auth : FirebaseAuth = Firebase.auth
    private var db = Firebase.firestore


    fun getCurrentEmail(): String {

        return auth.currentUser?.email.toString()
    }

    fun loadMessage(chatId:String ) {
/*
        val chatRef = db.collection("chats").document(chatId)

        chatRef.collection("messages").orderBy("dob", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { messages ->
                val listMessages = messages.toObjects(MessageModel::class.java)
                (messagesRecylerView.adapter as MessageAdapter).setData(listMessages)
            }

        chatRef.collection("messages").orderBy("dob", Query.Direction.ASCENDING)
            .addSnapshotListener { messages, error ->
                if(error == null){
                    messages?.let {
                        val listMessages = it.toObjects(MessageModel::class.java)
                        (messagesRecylerView.adapter as MessageAdapter).setData(listMessages)
                    }
                }
            }
*/
    }

    fun sendMessage(message : MessageModel) {
        db.collection("chats").document(getCurrentEmail()).collection("messages").document().set(message)
    }


}