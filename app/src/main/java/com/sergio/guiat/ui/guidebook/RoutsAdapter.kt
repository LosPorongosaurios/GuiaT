package com.sergio.guiat.ui.guidebook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sergio.guiat.R
import com.sergio.guiat.databinding.CardViewItemRoutesBinding
//import com.sergio.guiat.databinding.CardViewItemRoutesBinding
import com.sergio.guiat.local.Users


/*class RoutesAdapter (
    private val routesList: ArrayList<Routes>
) : RecyclerView.Adapter<RoutesAdapter.RoutesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_view_item_routes, parent, false)
        return RoutesViewHolder(view)
    }
    override fun onBindViewHolder(holder: RoutesViewHolder, position: Int) {
        val tour = routesList[position]
        holder.bind(tour)
    }
    override fun getItemCount(): Int = routesList.size
    fun appendItems(newList: ArrayList<Users>) {
        routesList.clear()
        routesList.addAll(newList)
        notifyDataSetChanged()
    }
    class RoutesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = CardViewItemRoutesBinding.bind(itemView)
        fun bind(routes: Routes) {
            with(binding){
                titleTourTextView.text = "Titulo del tour"
                descriptionTextView.text = "Descripcion del tour"
            }
        }
    }
}*/