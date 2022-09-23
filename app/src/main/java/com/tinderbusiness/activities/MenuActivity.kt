package com.tinderbusiness.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.tinderbusiness.R
import com.tinderbusiness.activities.tabs.BoostActivity
import com.tinderbusiness.activities.tabs.HomeActivity
import com.tinderbusiness.databinding.ActivityMenuBinding
import com.tinderbusiness.utils.Constants

class MenuActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding : ActivityMenuBinding
    private val open : Boolean = true
    private val close : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_menu)

        loadLayout()

    }

    private fun loadLayout(){

        binding.imvClose.setOnClickListener(this)
        binding.txvEditProfile.setOnClickListener(this)
        binding.rltGoPremium.setOnClickListener(this)
        binding.btnGetPremium.setOnClickListener(this)
        binding.imvPCancel.setOnClickListener(this)
        binding.rltCurrentLocation.setOnClickListener(this)
        binding.btnAddLocation.setOnClickListener(this)
        binding.imvLCancel.setOnClickListener(this)
        binding.rltAdvertis.setOnClickListener(this)
        binding.txvSubmit.setOnClickListener(this)
        binding.imvACancel.setOnClickListener(this)
        binding.rltReceiptArchieve.setOnClickListener(this)
        binding.btnEmailReceipts.setOnClickListener(this)
        binding.imvRCancel.setOnClickListener(this)
        binding.rltLegal.setOnClickListener(this)
        binding.txvLicenses.setOnClickListener(this)
        binding.txvPreferences.setOnClickListener(this)
        binding.txvPolicy.setOnClickListener(this)
        binding.txvTerms.setOnClickListener(this)
        binding.imvLeCancel.setOnClickListener(this)
        binding.rltContactUs.setOnClickListener(this)
        binding.imvContactCancel.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)

    }

    private fun itemSelected(a : Boolean, b:Boolean, c:Boolean, d:Boolean, e:Boolean, f : Boolean){

        premiumSelected(a)
        currentLocationSelected(b)
        advertiseSelected(c)
        receiptSelected(d)
        legalSelected(e)
        contactUsSelected(f)
    }

    private fun premiumSelected(isOpen: Boolean){

        if (isOpen){
            binding.rltCardHide.visibility = View.VISIBLE
            binding.imvPDropDown.visibility = View.GONE
            binding.imvPCancel.visibility = View.VISIBLE
        }
        else {
            binding.rltCardHide.visibility = View.GONE
            binding.imvPDropDown.visibility = View.VISIBLE
            binding.imvPCancel.visibility = View.GONE
        }
    }

    private fun currentLocationSelected(isOpen: Boolean){

        if (isOpen){
            binding.lytLocationHide.visibility = View.VISIBLE
            binding.imvLDropDown.visibility = View.GONE
            binding.imvLCancel.visibility = View.VISIBLE
        }
        else{
            binding.lytLocationHide.visibility = View.GONE
            binding.imvLCancel.visibility = View.GONE
            binding.imvLDropDown.visibility = View.VISIBLE
        }
    }

    private fun advertiseSelected(isOpen: Boolean){

        if (isOpen){
            binding.txvSubmit.visibility = View.VISIBLE
            binding.imvADropDown.visibility = View.GONE
            binding.imvACancel.visibility = View.VISIBLE
        }
        else{
            binding.txvSubmit.visibility = View.GONE
            binding.imvADropDown.visibility = View.VISIBLE
            binding.imvACancel.visibility = View.GONE
        }
    }

    private fun receiptSelected(isOpen: Boolean){

        if (isOpen){
            binding.lytReceiptHide.visibility = View.VISIBLE
            binding.imvRDropDown.visibility = View.GONE
            binding.imvRCancel.visibility = View.VISIBLE
        }
        else{
            binding.lytReceiptHide.visibility = View.GONE
            binding.imvRDropDown.visibility = View.VISIBLE
            binding.imvRCancel.visibility = View.GONE
        }
    }

    private fun legalSelected(isOpen: Boolean){

        if (isOpen){
            binding.lytLegalHide.visibility = View.VISIBLE
            binding.imvLeDropDown.visibility = View.GONE
            binding.imvLeCancel.visibility = View.VISIBLE
        }
        else{
            binding.lytLegalHide.visibility = View.GONE
            binding.imvLeDropDown.visibility = View.VISIBLE
            binding.imvLeCancel.visibility = View.GONE
        }
    }

    private fun contactUsSelected(isOpen: Boolean){

        if (isOpen){
            binding.lytContactUsHide.visibility = View.VISIBLE
            binding.imvContactDown.visibility = View.GONE
            binding.imvContactCancel.visibility = View.VISIBLE
        }
        else{
            binding.lytContactUsHide.visibility = View.GONE
            binding.imvContactDown.visibility = View.VISIBLE
            binding.imvContactCancel.visibility = View.GONE
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){


            R.id.imvClose ->{
                gotoHome(this)
            }

            R.id.txvEditProfile ->{
                startActivity(Intent(this, EditProfileActivity::class.java))
                itemSelected(close, close, close, close, close, close)
            }
            R.id.rltGoPremium ->{
                itemSelected(open, close, close, close, close, close)
            }
            R.id.btnGetPremium ->{
                startActivity(Intent(this, BoostActivity::class.java))
                itemSelected(close, close, close, close, close, close)
            }
            R.id.imvPCancel ->{ premiumSelected(close) }
            R.id.rltCurrentLocation ->{
                itemSelected(close, open, close, close, close, close)
            }
            R.id.btnAddLocation ->{
                currentLocationSelected(close)
            }
            R.id.imvLCancel ->{currentLocationSelected(close)}
            R.id.rltAdvertis ->{
                itemSelected(close, close, open, close, close, close)
            }
            R.id.imvACancel ->{advertiseSelected(close)}
            R.id.rltReceiptArchieve ->{
                itemSelected(close, close, close, open, close, close)
            }
            R.id.imvRCancel ->{receiptSelected(close)}
            R.id.rltLegal ->{
                itemSelected(close, close, close, close, open, close)
            }
            R.id.imvLeCancel ->{legalSelected(close)}
            R.id.rltContactUs ->{
                itemSelected(close, close, close, close, close, open)
            }
            R.id.imvContactCancel ->{contactUsSelected(close)}
            R.id.btnSubmit ->{contactUsSelected(close)}

            R.id.txvLicenses ->{
                val intent = Intent(this, GeneralContextActivity::class.java)
                intent.putExtra(Constants.TITLE, "Licenses")
                intent.putExtra(Constants.CONTENT, "Licenses, Licenses")
                startActivity(intent)
            }

            R.id.txvPreferences ->{
                val intent = Intent(this, GeneralContextActivity::class.java)
                intent.putExtra(Constants.TITLE, "Privacy Preferences")
                intent.putExtra(Constants.CONTENT, "Privacy Preferences")
                startActivity(intent)
            }

            R.id.txvPolicy ->{
                val intent = Intent(this, GeneralContextActivity::class.java)
                intent.putExtra(Constants.TITLE, "Privacy Policy")
                intent.putExtra(Constants.CONTENT, "Privacy Policy")
                startActivity(intent)
            }
            R.id.txvTerms ->{
                val intent = Intent(this, GeneralContextActivity::class.java)
                intent.putExtra(Constants.TITLE, "Terms of Service")
                intent.putExtra(Constants.CONTENT, "Terms of Service")
                startActivity(intent)
            }
        }
    }

    override fun onBackPressed() {
        gotoHome(this)
    }
}