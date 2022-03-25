package com.sergio.guiat.server.serverrepository

import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sergio.guiat.server.RoutesServer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RoutesServerRepository {

    val db = Firebase.firestore

    fun saveTour(
      nameTour : String,
      guide : String,
      description : String,
      sites : String,
      schedule : String
    ){
        val documentTour = db.collection("tours").document()

        val routes = RoutesServer(
            id = documentTour.id,
            name = nameTour,
            guide = guide,
            description = description,
            sites = sites,
            schedule = schedule
        )

        db.collection("tours").document(documentTour.id).set(routes)
    }

    suspend fun loadTours() : QuerySnapshot {
        return withContext(Dispatchers.IO) {
            db.collection("tours").get().await()
        }
    }

    fun deleteTours(route: RoutesServer) {
        route.id?.let { id ->
            db.collection("tours")
                .document(id)
                .delete()
        }
    }

}