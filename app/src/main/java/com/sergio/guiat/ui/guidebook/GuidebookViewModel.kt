package com.sergio.guiat.ui.guidebook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.toObject
import com.sergio.guiat.server.RoutesServer
import com.sergio.guiat.server.serverrepository.RoutesServerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GuidebookViewModel : ViewModel() {

    val routesRepository = RoutesServerRepository()

    private var toursList: ArrayList<RoutesServer> = ArrayList()

    val loadTours : MutableLiveData<ArrayList<RoutesServer>> = MutableLiveData()
    val loadToursDone: LiveData<ArrayList<RoutesServer>> = loadTours

    fun loadTours(){
        GlobalScope.launch(Dispatchers.IO){
            val querySnapshot = routesRepository.loadTours()
            for ( document in querySnapshot){
                val routesServer : RoutesServer = document.toObject<RoutesServer>()
                toursList.add(routesServer)
            }
            loadTours.postValue(toursList)
        }
    }


}
