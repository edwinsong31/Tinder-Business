package com.tinderbusiness.activities.tabs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.tinderbusiness.R
import com.tinderbusiness.activities.BaseActivity
import com.tinderbusiness.databinding.ActivityChatBinding

class ChatActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding : ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_chat)

        loadLayout()
    }

    private fun loadLayout(){

        binding.imvClose.setOnClickListener(this)
        binding.txvSend.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.imvClose ->{
                finish()
            }
            R.id.txvSend ->{
                binding.edtMessage.setText("")
            }
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        finish()
    }
}