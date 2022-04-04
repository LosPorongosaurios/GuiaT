package com.sergio.guiat.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sergio.guiat.R
import com.sergio.guiat.databinding.CardViewItemMessageBinding
import com.sergio.guiat.server.MessageModel

class MessageAdapter(
    //private var messageList: ArrayList<MessageModel>, //= emptyList(),
    private val user : String
   // private val onItemClicked: (MessageModel) -> Unit
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private var messages : List<MessageModel> = emptyList()

    fun setData(list: List<MessageModel>){
        messages = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): MessageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_view_item_message,parent,false)
        return MessageViewHolder(view)
    }



    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]

        val binding = CardViewItemMessageBinding.bind(holder.itemView)

        if(user == message.from){
            binding.myMessageLayout.visibility = View.VISIBLE
            binding.otherMessageLayout.visibility = View.GONE

            binding.myMessageTextView.text = message.message
        } else {
            binding.myMessageLayout.visibility = View.GONE
            binding.otherMessageLayout.visibility = View.VISIBLE

            binding.othersMessageTextView.text = message.message
        }

    }



    override fun getItemCount(): Int {
        return messages.size
    }

    class MessageViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

}