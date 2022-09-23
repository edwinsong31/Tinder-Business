package com.tinderbusiness.activities.tabs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.pixplicity.easyprefs.library.Prefs
import com.tinderbusiness.R
import com.tinderbusiness.activities.BaseActivity
import com.tinderbusiness.activities.LogInActivity
import com.tinderbusiness.databinding.ActivitySettingBinding
import com.tinderbusiness.utils.Constants
import com.tinderbusiness.utils.ContinentDialog
import com.tinderbusiness.utils.Utils

class SettingActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding : ActivitySettingBinding
    private val open : Boolean = true
    private val close : Boolean = false
    var continentDialog : ContinentDialog = ContinentDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_setting)

        loadLayout()
    }

    private fun loadLayout(){

        binding.headerSetting.imvInter.setOnClickListener(this)
        binding.headerSetting.imvMenu.visibility = View.GONE
        binding.headerSetting.imvClose.visibility = View.VISIBLE
        binding.headerSetting.imvClose.setOnClickListener(this)

        binding.imvEmailDown.setOnClickListener(this)
        binding.imvEmailCancel.setOnClickListener(this)
        binding.imvNotiDown.setOnClickListener(this)
        binding.imvNotiCancel.setOnClickListener(this)
        binding.imvPassDown.setOnClickListener(this)
        binding.imvPassCancel.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)
        binding.txvLogout.setOnClickListener(this)

    }

    private fun itemSelected(a : Boolean, b:Boolean, c:Boolean){

        emailSelected(a)
        notiSelected(b)
        passSelected(c)
    }

    private fun emailSelected(isOpen : Boolean){

        if (isOpen){
            binding.rltEmailHide.visibility = View.VISIBLE
            binding.imvEmailDown.visibility = View.GONE
            binding.imvEmailCancel.visibility = View.VISIBLE
        }
        else{
            binding.rltEmailHide.visibility = View.GONE
            binding.imvEmailDown.visibility = View.VISIBLE
            binding.imvEmailCancel.visibility = View.GONE
        }
    }

    private fun notiSelected(isOpen : Boolean){

        if (isOpen){
            binding.rltNotiHide.visibility = View.VISIBLE
            binding.imvNotiDown.visibility = View.GONE
            binding.imvNotiCancel.visibility = View.VISIBLE
        }
        else{
            binding.rltNotiHide.visibility = View.GONE
            binding.imvNotiDown.visibility = View.VISIBLE
            binding.imvNotiCancel.visibility = View.GONE
        }
    }

    private fun passSelected(isOpen : Boolean){

        if (isOpen){
            binding.rltPassHide.visibility = View.VISIBLE
            binding.imvPassDown.visibility = View.GONE
            binding.imvPassCancel.visibility = View.VISIBLE
        }
        else{
            binding.rltPassHide.visibility = View.GONE
            binding.imvPassDown.visibility = View.VISIBLE
            binding.imvPassCancel.visibility = View.GONE
        }
    }

    private fun checkValidation(){

        if (Utils.checkEdtValidation(binding.edtNewPassword, resources.getString(R.string.enter_password))
            && Utils.checkEdtValidation(binding.edtConfirmPassword, resources.getString(R.string.confirm_password))){

            Utils.hideKeyBoard(this)

            //call update password API
        }

    }

    override fun onClick(p0: View?) {

        when(p0?.id){

            R.id.imvClose ->{

                gotoHome(this)
            }
            R.id.imvInter ->{
                continentDialog.internationalDialog(this)
            }
            R.id.imvEmailDown ->{
                itemSelected(open, close, close)
            }
            R.id.imvEmailCancel ->{ emailSelected(close) }
            R.id.imvNotiDown ->{
                itemSelected(close, open, close)
            }
            R.id.imvNotiCancel ->{notiSelected(close)}
            R.id.imvPassDown ->{itemSelected(close, close, open)}
            R.id.imvPassCancel ->{passSelected(close)}
            R.id.btnSubmit ->{
                checkValidation()
                passSelected(close)
            }
            R.id.txvLogout ->{

                val intent = Intent(this, LogInActivity::class.java)
                intent.putExtra(Constants.KEY_LOGOUT, true)
                startActivity(intent)

                Prefs.clear()
                finish()
            }
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        gotoHome(this)
    }
}