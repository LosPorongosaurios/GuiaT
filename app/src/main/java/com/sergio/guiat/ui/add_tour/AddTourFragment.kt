package com.sergio.guiat.ui.add_tour

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sergio.guiat.databinding.FragmentAddTourBinding


class AddTourFragment : Fragment() {


    private val fileResult = 1
    private lateinit var addTourBinding: FragmentAddTourBinding
    private lateinit var addTourViewModel: AddTourViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        addTourBinding = FragmentAddTourBinding.inflate(inflater, container, false)
        addTourViewModel = ViewModelProvider(this)[AddTourViewModel::class.java]
        return addTourBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addTourViewModel.msgDone.observe(viewLifecycleOwner) { result ->
            onMsgDoneSubscribe(result)
        }

        addTourViewModel.dataValidated.observe(viewLifecycleOwner) { result ->
            onDataValidatedSubscribe(result)
        }

        addTourBinding.addButton.setOnClickListener {
            addTourViewModel.validateFields(
                addTourBinding.nameTourEditText.text.toString(),
                addTourBinding.descriptionEditText.text.toString(),
                addTourBinding.sitesEditText.text.toString(),
                addTourBinding.scheduleEditText.text.toString(),
                addTourViewModel.urlPicture
            )
        }

        addTourBinding.tourPhotoImageView.setOnClickListener {
            fileManager()
        }

    }

    private fun fileManager() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
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
                // val imageBitmap: Bitmap = data.getParcelableExtra("data")!!
                // addTourBinding.tourPhotoImageView.setImageBitmap(imageBitmap)
                if (clipData != null) {
                    for (i in 0 until clipData.itemCount) {
                        val uri = clipData.getItemAt(i).uri
                        uri?.let { addTourViewModel.fileUpload(it) }
                    }
                } else {
                    val uri = data.data
                    uri?.let { addTourViewModel.fileUpload(it) }
                }

                val uri1 = data.data
                val bitmap1: Bitmap =
                    MediaStore.Images.Media.getBitmap(context?.contentResolver, uri1)

                addTourBinding.tourPhotoImageView.setImageBitmap(bitmap1)
            }
        }
    }


    private fun onDataValidatedSubscribe(result: Boolean?) {
        with(addTourBinding) {
            val nameTour: String = nameTourEditText.text.toString()
            val description: String = descriptionEditText.text.toString()
            val sites: String = sitesEditText.text.toString()
            val schedule: String = scheduleEditText.text.toString()

            addTourViewModel.saveTour(
                nameTour,
                description,
                sites,
                schedule,
                addTourViewModel.urlPicture
            )

            findNavController().navigate(AddTourFragmentDirections.actionAddTourFragmentToGuidebookFragment())


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