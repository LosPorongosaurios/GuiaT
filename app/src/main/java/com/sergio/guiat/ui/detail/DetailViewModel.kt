package com.sergio.guiat.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.toObject
import com.sergio.guiat.models.ChatModel
import com.sergio.guiat.server.serverrepository.ChatsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {

    private var chatRepository = ChatsRepository()

    private val findOtherUserUid : MutableLiveData<String?> = MutableLiveData()
    val findOtherUserUidDone : LiveData<String?> = findOtherUserUid

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

    fun getChatId(): String {
       return chatRepository.getChatId()
    }

    fun newChat(guideMail: String, otherUserUid: String) {
        chatRepository.newChat(guideMail, otherUserUid)
    }


}