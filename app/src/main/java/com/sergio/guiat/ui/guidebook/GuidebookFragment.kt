package com.sergio.guiat.ui.guidebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sergio.guiat.databinding.FragmentGuidebookBinding

class GuidebookFragment : Fragment() {

    private lateinit var guidebookBinding: FragmentGuidebookBinding
    private lateinit var guidebookViewModel: GuidebookViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        guidebookBinding = FragmentGuidebookBinding.inflate(inflater, container, false)
        guidebookViewModel = ViewModelProvider(this)[GuidebookViewModel::class.java]
        return guidebookBinding.root
    }

    /*  override fun onActivityCreated(savedInstanceState: Bundle?) {
          super.onActivityCreated(savedInstanceState)

          //       routesAdapter = RoutesAdapter(routesList)

            listBinding.routesRecyclerView.apply {
                layoutManager = LinearLayoutManager(this@ListFragment.requireContext())
                adapter = routesAdapter
                setHasFixedSize(false)
            }
    }*/

}