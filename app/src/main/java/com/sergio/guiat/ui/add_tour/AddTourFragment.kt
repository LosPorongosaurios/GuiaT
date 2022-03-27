package com.sergio.guiat.ui.add_tour

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sergio.guiat.databinding.FragmentAddTourBinding

class AddTourFragment : Fragment() {


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

    }

    private fun onDataValidatedSubscribe(result: Boolean?) {
        with(addTourBinding){
            val nameTour: String = nameTourEditText.text.toString()
            val description : String = descriptionEditText.text.toString()
            val sites : String = sitesEditText.text.toString()
            val schedule : String = scheduleEditText.text.toString()

            addTourViewModel.saveTour(nameTour,description,sites,schedule)


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