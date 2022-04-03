package com.sergio.guiat.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.sergio.guiat.server.MessageModel
import com.sergio.guiat.server.serverrepository.ChatsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val auth : FirebaseAuth = Firebase.auth
    private var db = Firebase.firestore

    private val chatRepository = ChatsRepository()

    private val loadMessages : MutableLiveData<MutableList<MessageModel>> = MutableLiveData()
    val loadMessagesDone : LiveData<MutableList<MessageModel>> = loadMessages

    private val uploadMessages : MutableLiveData<MutableList<MessageModel>> = MutableLiveData()
    val uploadMessagesDone : LiveData<MutableList<MessageModel>> = uploadMessages

    private var messagesList : MutableList<MessageModel> = mutableListOf()

    fun getCurrentEmail(): String {
        return auth.currentUser?.email.toString()
    }

    fun loadMessage(chatId:String )// : MutableList<MessageModel> {
    {
        messagesList.clear()
        GlobalScope.launch(Dispatchers.IO){
            val querySnapshot = chatRepository.loadMessages(chatId)
            for (document in querySnapshot){
                val messages : MessageModel = document.toObject<MessageModel>()
                messagesList.add(messages)
            }

            loadMessages.postValue(messagesList)

        }

    }

    fun uploadMessage(chatId: String){


        db.collection("chats").document(chatId).collection("messages").orderBy("dob", Query.Direction.ASCENDING)
            .addSnapshotListener { messages, error ->
                if(error == null){
                    messages?.let {
                        val listMessages = it.toObjects(MessageModel::class.java)
                        uploadMessages.postValue(listMessages)
                    }
                }
            }


}



    fun sendMessage(message : MessageModel, chatId : String) {
        db.collection("chats").document(chatId).collection("messages").document().set(message)
    }


}