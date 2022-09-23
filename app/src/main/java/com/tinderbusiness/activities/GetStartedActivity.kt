package com.tinderbusiness.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.tinderbusiness.R
import com.tinderbusiness.activities.tabs.HomeActivity
import com.tinderbusiness.adapter.SliderCustomerAdapter
import com.tinderbusiness.databinding.ActivityGetStartedBinding
import com.tinderbusiness.models.SliderModel

class GetStartedActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding : ActivityGetStartedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_get_started)

        loadLayout()
    }

    private fun loadLayout(){

        binding.lytRegister.setOnClickListener(this)
        binding.lytCreate.setOnClickListener(this)
        binding.btnHomepage.setOnClickListener(this)

        val slideList = ArrayList<SliderModel>()

        slideList.add(SliderModel("How to use the app", "Set timer 10s rule for carousel scroll from left to right"))
        slideList.add(SliderModel("1", "I am using this app."))
        slideList.add(SliderModel("2", "I am using this app."))
        slideList.add(SliderModel("3", "I am using this app."))

        val mAdapter = SliderCustomerAdapter()
        mAdapter.setItem(slideList)
        binding.imageSlide.setImageListWithAdapter(mAdapter, slideList.size)

        mAdapter.onItemClick = {

        }
        binding.imageSlide.startSliding(1000) // with new period
        binding.imageSlide.startSliding()


    }

    private fun gotoHomePage(){
        startActivity(Intent(this, HomeActivity::class.java))
        finish()

    }

    private fun gotoRegister(){

        startActivity(Intent(this, Register1Activity::class.java))
        finish()
    }

    private fun gotoCreate(){
        startActivity(Intent(this, Register2Activity::class.java))
        finish()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnHomepage ->{
                gotoHomePage()
            }

            R.id.lytRegister ->{

                gotoRegister()
            }

            R.id.lytCreate ->{
                gotoCreate()
            }
        }
    }

    override fun onBackPressed() {
        onExit()
    }
}