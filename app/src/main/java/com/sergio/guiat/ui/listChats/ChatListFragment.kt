package com.sergio.guiat.ui.listChats

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sergio.guiat.databinding.FragmentChatListBinding
import com.sergio.guiat.server.ChatModel

class ChatListFragment : Fragment() {


    private lateinit var chatListBinding: FragmentChatListBinding
    private lateinit var chatListViewModel: ChatListViewModel
    private lateinit var chatListAdapter: ChatListAdapter
    private var chatList: MutableList<ChatModel> = mutableListOf()
    private var user : String = Firebase.auth.currentUser?.email.toString()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        chatListBinding = FragmentChatListBinding.inflate(inflater,container,false)
        chatListViewModel = ViewModelProvider(this)[ChatListViewModel::class.java]
        return chatListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatListViewModel.loadChatsDone.observe(viewLifecycleOwner){ result ->
            onLoadChatsDoneSusbscribe(result)
        }

        chatListViewModel.loadChats()

        chatListAdapter = ChatListAdapter (onItemClicked = {onChatItemClicked(it)})

        chatListBinding.chatsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ChatListFragment.requireContext())
            adapter = chatListAdapter
            setHasFixedSize(false)
        }

    }

    private fun onLoadChatsDoneSusbscribe(chatLoaded: MutableList<ChatModel>) {
        chatList = chatLoaded
        chatListAdapter.appendItems(chatList)
    }

    private fun onChatItemClicked(chat: ChatModel) {
        findNavController().navigate(ChatListFragmentDirections.actionChatListFragmentToChatFragment(chat.id,user))
    }


}