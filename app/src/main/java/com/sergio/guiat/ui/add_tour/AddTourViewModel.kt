package com.sergio.guiat.ui.add_tour

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.sergio.guiat.server.serverrepository.RoutesServerRepository
import com.sergio.guiat.server.serverrepository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AddTourViewModel : ViewModel() {

    private val routeServerRepository = RoutesServerRepository()
    private var guideTour: String? = null

    private val database = Firebase.database
    lateinit var urlPicture: String
    private val myRef = database.getReference("user")

    private val usersRepository = UsersRepository()
    private val guideMail: String = Firebase.auth.currentUser?.email.toString()

    private val msg: MutableLiveData<String> = MutableLiveData()
    val msgDone: LiveData<String> = msg

    private val dataValidate: MutableLiveData<Boolean> = MutableLiveData()
    val dataValidated: LiveData<Boolean> = dataValidate


    fun validateFields(
        nameTour: String,
        description: String,
        sites: String,
        schedule: String,
        urlPicture: String,
    ) {
        if (nameTour.isEmpty() || description.isEmpty() || sites.isEmpty() || schedule.isEmpty() || urlPicture.isEmpty()) {
            msg.value = "Debe digitar todos los campos e incluir una imagen"
        } else {
            dataValidate.value = true
        }
    }

    fun saveTour(
        nameTour: String,
        description: String,
        sites: String,
        schedule: String,
        urlPicture: String
        // urlPicture : String

    ) {

        GlobalScope.launch(Dispatchers.IO) {
            val result = usersRepository.searchUser()
            result.getString("name")?.let { Log.d("name", it) }
            guideTour = result.getString("name")
            guideTour?.let {
                routeServerRepository.saveTour(
                    nameTour,
                    it, guideMail, description, sites, schedule, urlPicture
                )
            }

        }
    }

    fun fileUpload(mUri: Uri) {
        val folder: StorageReference = FirebaseStorage.getInstance().reference.child("tours")
        val path = mUri.lastPathSegment.toString()
        val fileName: StorageReference = folder.child(path.substring(path.lastIndexOf('/') + 1))

        fileName.putFile(mUri).addOnSuccessListener {
            fileName.downloadUrl.addOnSuccessListener { uri ->
                val hashMap = HashMap<String, String>()
                hashMap["link"] = java.lang.String.valueOf(uri)

                urlPicture = uri.toString()


                myRef.child(myRef.push().key.toString()).setValue(hashMap)

                Log.i("message", "file upload successfully")

            }.addOnFailureListener {
                Log.i("message", "file upload error")
            }
        }
    }


}






