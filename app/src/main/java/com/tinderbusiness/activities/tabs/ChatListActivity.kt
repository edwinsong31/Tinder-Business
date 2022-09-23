package com.tinderbusiness.activities.tabs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tinderbusiness.R
import com.tinderbusiness.activities.BaseActivity
import com.tinderbusiness.adapter.ChatListAdapter
import com.tinderbusiness.databinding.ActivityChatListBinding
import com.tinderbusiness.models.Friend
import com.tinderbusiness.utils.ContinentDialog

class ChatListActivity : BaseActivity(), ChatListAdapter.OnChatUserClickListener, View.OnClickListener {

    private lateinit var binding : ActivityChatListBinding
    private var chatUser : ArrayList<Friend> = ArrayList()
    private lateinit var adapter: ChatListAdapter
    private var continentDialog : ContinentDialog? = ContinentDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_chat_list)

        loadLayout()
    }

    private fun loadLayout(){

        binding.headerChatList.imvInter.setOnClickListener(this)
        binding.headerChatList.imvMenu.visibility = View.GONE
        binding.headerChatList.imvClose.visibility = View.VISIBLE
        binding.headerChatList.imvClose.setOnClickListener(this)

        binding.rvChatUser.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        binding.rvChatUser.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = ChatListAdapter(this, chatUser)
        binding.rvChatUser.adapter = adapter
        adapter.setOnItemClickListener(this)

    }

    override fun onClick(p0: View?) {

        when(p0?.id){

            R.id.imvClose ->{
                gotoHome(this)
            }

            R.id.imvInter ->{
                continentDialog?.internationalDialog(this)
            }
        }
    }

    override fun onChatUserClick(position: Int) {

        startActivity(Intent(this, ChatActivity::class.java))

    }

    override fun onBackPressed() {
        gotoHome(this)
    }
}