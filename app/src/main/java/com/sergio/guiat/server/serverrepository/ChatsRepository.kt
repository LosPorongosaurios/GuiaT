package com.sergio.guiat.server.serverrepository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sergio.guiat.models.ChatModel
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class ChatsRepository {

    private val auth: FirebaseAuth = Firebase.auth
    private val user  = auth.currentUser?.email.toString()
    private val uid = auth.currentUser?.uid.toString()
    private lateinit var documentChatId : String
    val db = Firebase.firestore

    fun newChat(
        otherUser:String,
        otherUserUid : String
    ){
        val documentChat = db.collection("chats").document()
        documentChatId = documentChat.id
        val users = listOf(user,otherUser)

        val chat = ChatModel(
            id = documentChat.id,
            name = "Chat con $otherUser",
            users = users
        )
        db.collection("chats").document(documentChat.id).set(chat)
        db.collection("users").document(uid).collection("chats").document(documentChat.id).set(chat)
        db.collection("users").document(otherUserUid).collection("chats").document(documentChat.id).set(chat)
    }



    suspend fun loadChats(uid : String) : QuerySnapshot {
        return withContext(Dispatchers.IO) {
            db.collection("users").document(uid).collection("chats").get().await()
        }
    }

    suspend fun loadUsers(): QuerySnapshot {
        return withContext(Dispatchers.IO){
            db.collection("users").get().await()
        }
    }

    fun getChatId(): String {
        return documentChatId
    }

    suspend fun loadMessages(chatId: String): QuerySnapshot {
        return withContext(Dispatchers.IO) {
            db.collection("chats").document(chatId).collection("messages").orderBy("dob",Query.Direction.ASCENDING).get().await()
        }
    }



}