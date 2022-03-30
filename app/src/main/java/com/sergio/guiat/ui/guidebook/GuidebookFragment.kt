package com.sergio.guiat.ui.guidebook

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sergio.guiat.databinding.FragmentGuidebookBinding
import com.sergio.guiat.server.RoutesServer

class GuidebookFragment : Fragment() {

    private lateinit var guidebookBinding: FragmentGuidebookBinding
    private lateinit var guidebookViewModel: GuidebookViewModel
    private lateinit var routesAdapter: RoutesAdapter
    private var routesList : ArrayList<RoutesServer> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        guidebookBinding = FragmentGuidebookBinding.inflate(inflater, container, false)
        guidebookViewModel = ViewModelProvider(this)[GuidebookViewModel::class.java]

        //guidebookViewModel.loadTours()
        return guidebookBinding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        guidebookViewModel.loadToursDone.observe(viewLifecycleOwner){ result ->
            onLoadToursDoneSubscribe(result)
        }

        guidebookViewModel.loadTours()

        routesAdapter = RoutesAdapter( onItemClicked = {onTourItemClicked(it)})

        guidebookBinding.routesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@GuidebookFragment.requireContext())
            adapter = routesAdapter
            setHasFixedSize(false)
        }

    }


    private fun onTourItemClicked(route: RoutesServer) {
        findNavController().navigate(GuidebookFragmentDirections.actionGuidebookFragmentToDetailFragment2(route))
       // route.name?.let { Log.d("nombreView",it) }
    }

    private fun onLoadToursDoneSubscribe(routesListLoaded: ArrayList<RoutesServer>) {
        routesList = routesListLoaded
        Log.d("list",routesList.toString())
        routesAdapter.appendItems(routesList)
    }



}