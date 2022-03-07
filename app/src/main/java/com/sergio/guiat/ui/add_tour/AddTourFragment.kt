package com.sergio.guiat.ui.add_tour

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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



}