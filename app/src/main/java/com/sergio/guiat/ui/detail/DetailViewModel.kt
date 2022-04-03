package com.sergio.guiat.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.sergio.guiat.server.ChatModel
import com.sergio.guiat.server.serverrepository.ChatsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {

    private var chatRepository = ChatsRepository()
    private var currentUserUid : String = Firebase.auth.currentUser?.uid.toString()
    private var currentUserMail : String = Firebase.auth.currentUser?.email.toString()


    private val findOtherUserUid : MutableLiveData<String?> = MutableLiveData()
    val findOtherUserUidDone : LiveData<String?> = findOtherUserUid

    private val loadChatId : MutableLiveData<String?> = MutableLiveData()
    val loadChatIdDone : LiveData<String?> = loadChatId

    var chatCreated : Boolean = false



    fun searchOtherUserUid(OtherUserMail : String){
        GlobalScope.launch(Dispatchers.IO){
            val result = chatRepository.loadUsers()

            var isFoundUserMail = false
            for ( document in result){
                val users : com.sergio.guiat.server.User = document.toObject<com.sergio.guiat.server.User>()
                if (OtherUserMail == users.email){
                    findOtherUserUid.postValue(users.uid)
                    isFoundUserMail = true
                }
            }
            if (!isFoundUserMail) findOtherUserUid.postValue(null)
        }
    }

    fun isChatCreated(otherUserMail: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val users = listOf(currentUserMail, otherUserMail)
            Log.d("Lista de usuarios",users.toString())
            val querySnapshot = chatRepository.loadChats(currentUserUid)
            var isFoundChat = false
            for (document in querySnapshot) {
                val chats: ChatModel = document.toObject<ChatModel>()
                Log.d("Lista encontrados",chats.users.toString())
                if (users.toString() == chats.users.toString()) {
                    loadChatId.postValue(chats.id)
                    isFoundChat = true
                    chatCreated = true
                }
            }
            if (!isFoundChat) loadChatId.postValue(null)
        }
    }

    fun getChatId(): String {
       return chatRepository.getChatId()
    }

    fun newChat(guideMail: String, otherUserUid: String) {
        chatRepository.newChat(guideMail, otherUserUid)
    }




}