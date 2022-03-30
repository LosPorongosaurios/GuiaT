package com.sergio.guiat.ui.add_tour

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.sergio.guiat.databinding.FragmentAddTourBinding

class AddTourFragment : Fragment() {


    private val fileResult = 1
    private lateinit var addTourBinding: FragmentAddTourBinding
    private lateinit var addTourViewModel: AddTourViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        addTourBinding = FragmentAddTourBinding.inflate(inflater,container,false)
        addTourViewModel = ViewModelProvider(this)[AddTourViewModel::class.java]
        return addTourBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addTourViewModel.msgDone.observe(viewLifecycleOwner) { result ->
            onMsgDoneSubscribe(result)
        }

        addTourViewModel.dataValidated.observe(viewLifecycleOwner){result ->
            onDataValidatedSubscribe(result)
        }

        addTourBinding.addButton.setOnClickListener{
            addTourViewModel.validateFields(
                addTourBinding.nameTourEditText.text.toString(),
                addTourBinding.descriptionEditText.text.toString(),
                addTourBinding.sitesEditText.text.toString(),
                addTourBinding.scheduleEditText.text.toString()
            )
        }

        addTourBinding.tourPhotoImageView.setOnClickListener {
            fileManager()
        }

    }

    private fun fileManager() {
        val intent = Intent(Intent.ACTION_VIEW)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {

        }
        intent.type = "image/*"
        startActivityForResult(intent, fileResult)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == fileResult) {
            if (resultCode == RESULT_OK && data != null) {
                val clipData = data.clipData
                if (clipData != null) {
                    for (i in 0 until clipData.itemCount) {
                        val uri = clipData.getItemAt(i).uri
                        uri?.let { addTourViewModel.fileUpload(it) }
                    }
                } else {
                    val uri = data.data
                    uri?.let { addTourViewModel.fileUpload(it) }
                }
            }
        }
    }



    private fun onDataValidatedSubscribe(result: Boolean?) {
        with(addTourBinding){
            val nameTour: String = nameTourEditText.text.toString()
            val description : String = descriptionEditText.text.toString()
            val sites : String = sitesEditText.text.toString()
            val schedule : String = scheduleEditText.text.toString()

            addTourViewModel.saveTour(nameTour,description,sites,schedule,addTourViewModel.urlPicture)


        }

    }

    private fun onMsgDoneSubscribe(msg: String?) {
        Toast.makeText(
            requireContext(),
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }


}