package com.tinderbusiness.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.tinderbusiness.R
import com.tinderbusiness.databinding.ActivityGeneralContextBinding
import com.tinderbusiness.utils.Constants
import com.tinderbusiness.utils.Constants.CONTENT
import com.tinderbusiness.utils.Constants.TITLE

class GeneralContextActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding : ActivityGeneralContextBinding
    private var title : String = ""
    private var content : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_general_context)

        title = intent.getStringExtra(TITLE)!!
        //content = intent.getStringExtra(CONTENT)!!

        loadLayout()
    }

    private fun loadLayout(){

        binding.headerGeneral.imvInter.setOnClickListener(this)
        binding.headerGeneral.imvMenu.visibility = View.GONE
        binding.headerGeneral.imvClose.visibility = View.VISIBLE
        binding.headerGeneral.imvClose.setOnClickListener(this)

        binding.txvTitle.text = title
        //binding.txVContent.text = content
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.imvClose ->{
                finish()
            }
        }
    }
}