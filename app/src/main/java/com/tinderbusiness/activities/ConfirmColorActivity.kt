package com.tinderbusiness.activities

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.util.SharedPref
import com.pixplicity.easyprefs.library.Prefs
import com.tinderbusiness.R
import com.tinderbusiness.databinding.ActivityConfirmColorBinding
import com.tinderbusiness.utils.Constants

class ConfirmColorActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding : ActivityConfirmColorBinding
    private var mColor = 0
    private var txColor = 0
    private var bgColor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_confirm_color)

        txColor = Prefs.getInt(Constants.TEXT_COLOR, ContextCompat.getColor(this, R.color.txt_dark_red))
        bgColor = Prefs.getInt(Constants.BG_COLOR, ContextCompat.getColor(this, R.color.light_yellow))

        loadLayout()
    }

    private fun loadLayout(){

        binding.lytBackground.setOnClickListener(this)
        binding.lytText.setOnClickListener(this)
        binding.btnConfirmColor.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
        binding.lytRegister.setOnClickListener(this)
        binding.btnApplyColor.setOnClickListener(this)

        setColor(txColor, bgColor)
    }

    private fun setColor(txCr : Int, bgCr : Int){

        val drawablePreview = binding.lytPreview.background as GradientDrawable
        drawablePreview.mutate()
        drawablePreview.setColor(bgCr)
        //binding.txvPreview.setTextColor(txCr)
        binding.txvText.setTextColor(txCr)

        val drawableBg = binding.lytBackground.background as GradientDrawable
        drawableBg.mutate()
        drawableBg.setColor(bgCr)
        binding.txvBgColor.setTextColor(txCr)
        binding.txvEdtBg.setTextColor(txCr)

        val drText = binding.lytText.background as GradientDrawable
        drText.mutate()
        drText.setColor(txCr)
        binding.txvTextColor.setTextColor(bgCr)
        binding.txvEdText.setTextColor(bgCr)
    }

    private fun showColorPicker(isText : Boolean){

        if (isText)
            binding.txvSelected.text = "Text Color"
        else
            binding.txvSelected.text = "Background Color"

        val primaryColor = ContextCompat.getColor(this, R.color.colorPrimary)
        mColor = SharedPref(this).getRecentColor(primaryColor)

        ColorPickerDialog
            .Builder(this)
            .setTitle("Pick Theme")
            .setColorShape(ColorShape.SQAURE)
            .setDefaultColor(mColor)
            .setColorListener { color, colorHex ->

                if (isText)
                    txColor = color
                else
                    bgColor = color

                setColor(txColor, bgColor)
            }
            .show()
    }


    /*private fun gotoRegister(){

        val intent = Intent(this, Register1Activity::class.java)
        intent.action = "Register2"
        startActivity(intent)
        finish()
    }*/

    private fun gotoCreation(){
       startActivity(Intent(this, Register2Activity::class.java))
       finish()
    }

    private fun gotoGetStarted(){

        startActivity(Intent(this, GetStartedActivity::class.java))
        finish()
    }

    override fun onClick(v: View?) {

        when(v?.id){

            R.id.lytRegister ->{
                //gotoRegister()
            }

            R.id.lytBackground ->{
                showColorPicker(false)
            }
            R.id.lytText ->{
               showColorPicker(true)
            }

            R.id.btnConfirmColor ->{
                Prefs.putInt(Constants.TEXT_COLOR, txColor)
                Prefs.putInt(Constants.BG_COLOR, bgColor)
                finish()
            }
            R.id.btnCancel ->{
                finish()
            }
        }
    }
}