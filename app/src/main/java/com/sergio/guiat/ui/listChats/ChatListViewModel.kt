package com.sergio.guiat.ui.listChats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.sergio.guiat.models.ChatModel
import com.sergio.guiat.server.serverrepository.ChatsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ChatListViewModel : ViewModel() {

    val chatRepository = ChatsRepository()

    private val auth: FirebaseAuth = Firebase.auth
    private val user  = auth.currentUser?.email
    private val uid = auth.currentUser?.uid.toString()

    private var chatsList : ArrayList<ChatModel> = ArrayList()

    private val loadChats : MutableLiveData<MutableList<ChatModel>> = MutableLiveData()
    val loadChatsDone : LiveData<MutableList<ChatModel>> = loadChats

    fun loadChats() {

        GlobalScope.launch(Dispatchers.IO){
            chatsList.clear()
            val querySnapshot = chatRepository.loadChats(uid)
            for (document in querySnapshot){
                val chats : ChatModel = document.toObject<ChatModel>()
                chatsList.add(chats)
            }

            loadChats.postValue(chatsList)

        }

    }
}