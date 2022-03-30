package com.sergio.guiat.ui.listChats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sergio.guiat.R
import com.sergio.guiat.databinding.CardViewItemChatsBinding
import com.sergio.guiat.models.ChatModel

class ChatListAdapter(
   // private var chatList: ArrayList<ChatModel>, //= emptyList(),
    private val onItemClicked: (ChatModel) -> Unit
) : RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder>() {

  //  private val chatList : MutableList<ChatModel> = mutableListOf()

    private var chatList: MutableList<ChatModel> = mutableListOf()

    /*fun setData(list: List<ChatModel>){
        chatList = list
        notifyDataSetChanged()
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_view_item_chats,parent,false)
        return ChatListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        val chat = chatList[position]
        holder.bind(chat)
        holder.itemView.setOnClickListener{ onItemClicked(chatList[position])}
    }

    fun appendItems(newList : List<ChatModel>){
        chatList.clear()
        chatList.addAll(newList)
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    class ChatListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding = CardViewItemChatsBinding.bind(itemView)
        private val context = binding.root
        fun bind(chat:ChatModel){
            with(binding){
                chatNameTextView.text = chat.name
                usersTextView.text = chat.users.toString()
            }
        }
    }
}