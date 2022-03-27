package com.sergio.guiat.ui.add_tour

import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.sergio.guiat.server.serverrepository.RoutesServerRepository
import com.sergio.guiat.server.serverrepository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.timerTask

const val DEFAULT_DELAY = 2000L

class AddTourViewModel : ViewModel() {

    private val routeServerRepository = RoutesServerRepository()
    private var auth: FirebaseAuth = Firebase.auth
    private var guideTour : String? = null
    val usersRepository = UsersRepository()

    private val msg: MutableLiveData<String> = MutableLiveData()
    val msgDone: LiveData<String> = msg

    private val dataValidate: MutableLiveData<Boolean> = MutableLiveData()
    val dataValidated: LiveData<Boolean> = dataValidate



    fun validateFields(nameTour: String, description: String, sites: String, schedule : String) {
        if (nameTour.isEmpty() || description.isEmpty() || sites.isEmpty() || schedule.isEmpty()) {
            msg.value = "Debe digitar todos los campos"
        } else {
            dataValidate.value = true
        }
    }

    fun saveTour(
        nameTour: String,
        description: String,
        sites: String,
        schedule: String

    ){
        searchUser()

        GlobalScope.launch(Dispatchers.IO){
            guideTour?.let {
                routeServerRepository.saveTour(nameTour,
                    it,description,sites,schedule)
            }

        }


    }

    private fun searchUser() {
        val email : String = auth.currentUser?.email.toString()
        GlobalScope.launch(Dispatchers.IO){
            val result = usersRepository.searchUser()

            for (document in result){
                val user : com.sergio.guiat.server.User = document.toObject<com.sergio.guiat.server.User>()
                if (email == user.email){
                    guideTour = user.name
                    guideTour?.let { Log.d("holaaaa", it) }
                }
            }
        }
    }




}