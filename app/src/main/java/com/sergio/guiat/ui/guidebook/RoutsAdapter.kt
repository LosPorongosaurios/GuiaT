package com.sergio.guiat.ui.guidebook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sergio.guiat.R
import com.sergio.guiat.databinding.CardViewItemRoutesBinding
import com.sergio.guiat.server.RoutesServer
import com.squareup.picasso.Picasso


class RoutesAdapter (
    private val onItemClicked: (RoutesServer) ->Unit

) : RecyclerView.Adapter<RoutesAdapter.RoutesViewHolder>() {

    private val routesList: ArrayList<RoutesServer> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_view_item_routes, parent, false)
        return RoutesViewHolder(view)
    }
    override fun onBindViewHolder(holder: RoutesViewHolder, position: Int) {
        val tour = routesList[position]
        holder.bind(tour)
        holder.itemView.setOnClickListener{ onItemClicked(routesList[position])}
    }
    override fun getItemCount(): Int = routesList.size

    fun appendItems(newList: ArrayList<RoutesServer>) {
        routesList.clear()
        routesList.addAll(newList)
        notifyDataSetChanged()
    }

    class RoutesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding = CardViewItemRoutesBinding.bind(itemView)
        private val context = binding.root
        fun bind(route:RoutesServer){
            with(binding){
                titleTourTextView.text = route.name
                guideCardTextView.text = route.guide
                Picasso.get().load(route.urlPicture).into(posterTourImageView)
            }
        }


    }

}