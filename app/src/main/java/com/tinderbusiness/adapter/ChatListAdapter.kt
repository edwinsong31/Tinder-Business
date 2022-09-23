package com.tinderbusiness.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tinderbusiness.R
import com.tinderbusiness.activities.tabs.ChatActivity
import com.tinderbusiness.databinding.ItemChatUserBinding
import com.tinderbusiness.models.Friend

class ChatListAdapter(context: Context, private val chatUser : ArrayList<Friend>) :
    RecyclerView.Adapter<ChatListAdapter.ListViewHolder>() {

    var context: Context = context
    var chatUserList: java.util.ArrayList<Friend> = chatUser

    class ListViewHolder(var listBinding: ItemChatUserBinding) :
        RecyclerView.ViewHolder(listBinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val listBinding = DataBindingUtil.inflate<ItemChatUserBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_chat_user,
            parent,
            false
        )

        return ListViewHolder(
            listBinding
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        if (position % 2 == 0)
            holder.listBinding.imvLike.visibility = View.GONE
        else
            holder.listBinding.imvLike.visibility = View.VISIBLE

        holder.listBinding.lytUser.setOnClickListener{
            if (mItemClickListener != null){
                mItemClickListener?.onChatUserClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return /*chatUser.size*/8
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(chatUserProfiles: java.util.ArrayList<Friend>) {
        this.chatUserList = chatUserProfiles
        notifyDataSetChanged()
    }

    private var mItemClickListener: OnChatUserClickListener? = null

    fun setOnItemClickListener(mItemClickListener: OnChatUserClickListener?) {
        this.mItemClickListener = mItemClickListener
    }

    interface OnChatUserClickListener {
        fun onChatUserClick(position: Int)
    }
}