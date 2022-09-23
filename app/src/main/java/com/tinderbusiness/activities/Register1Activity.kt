package com.tinderbusiness.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.hbb20.countrypicker.dialog.launchCountryPickerDialog
import com.hbb20.countrypicker.models.CPCountry
import com.tinderbusiness.R
import com.tinderbusiness.databinding.ActivityRegister1Binding
import com.tinderbusiness.utils.Constants.g_user
import com.tinderbusiness.utils.Utils
import org.json.JSONObject
import java.io.*
import java.util.*


class Register1Activity : BaseActivity(), View.OnClickListener {

    private lateinit var binding : ActivityRegister1Binding

    private var contactName : String = ""
    private var email : String = ""
    private var password : String = ""
    private var confirmPass : String = ""
    private var countryName : String = ""
    private var continent : String = ""
    private var countryCode : String = ""
    private var industry : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_register1)
        loadLayout()
    }

    private fun loadLayout(){

        binding.lytCreate.setOnClickListener(this)
        binding.btnContinue.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
        binding.txvIndustry.setOnClickListener(this)
        binding.txvCountry.setOnClickListener(this)

        if (intent.action.equals("Register2")){
            showRegistration()
        }

    }

    private fun showRegistration(){

        binding.edtContact.text = g_user.contactName.toEditable()
        binding.edtEmail.text = g_user.email.toEditable()
        binding.txvCountry.text = g_user.country
        binding.txvIndustry.text = g_user.industry
    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    private fun setUpCountryPicker(){

        this.launchCountryPickerDialog { selectedCountry: CPCountry? ->
            binding.txvCountry.text = selectedCountry?.flagEmoji + "    " + "+" + selectedCountry?.phoneCode.toString() + "     " + selectedCountry?.name
            countryName = selectedCountry!!.name
            countryCode = selectedCountry.phoneCode.toString()
            continent = getContinent(selectedCountry.alpha2.uppercase(Locale.getDefault()))
        }
    }

    private fun selectIndustry(){

        val itemsArray = arrayOf(
            resources.getStringArray(R.array.industry_array)
        )
        val items = itemsArray[0]

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Industry")
        builder.setItems(
            items
        ) { _, item ->
            binding.txvIndustry.text = items[item]
            industry = binding.txvIndustry.text.toString()
        }

        val alert = builder.create()

        alert.show()
    }

    private fun getContinent(jsonContinent : String): String {

        val inputStream = resources.openRawResource(R.raw.json_continent)
        val byteArrayOutputStream = ByteArrayOutputStream()

        var ctr: Int
        try {
            ctr = inputStream.read()
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr)
                ctr = inputStream.read()
            }
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        try {
            // Parse the data into jsonobject to get original data in form of json.
            val jObject = JSONObject(
                byteArrayOutputStream.toString()
            )
            val jObjectResult = jObject.getJSONObject(jsonContinent)

            return jObjectResult.getString("continent")

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    private fun checkValid(){

        if(Utils.checkEdtValidation(binding.edtContact, resources.getString(R.string.enter_contact_name))
            && Utils.isEmailValid(binding.edtEmail, this)
            && Utils.checkEdtValidation(binding.edtPassword, resources.getString(R.string.enter_password))
            && Utils.checkPassWordMatch(binding.edtPassword, binding.edtConfirmPass, this)
            && Utils.checkTxvValidation(binding.txvIndustry, resources.getString(R.string.select_industry))
            && Utils.checkTxvValidation(binding.txvCountry, resources.getString(R.string.select_country)) )
        {
            contactName = binding.edtContact.text.toString()
            email = binding.edtEmail.text.toString()
            industry = binding.txvIndustry.text.toString()
            password = binding.edtPassword.text.toString()

            callRegistration1()
        }

        //callRegistration1()
    }

    private fun callRegistration1(){

        g_user.contactName = contactName
        g_user.email = email
        g_user.country = countryName
        g_user.continent = continent
        g_user.password = password
        g_user.industry = industry

        startActivity(Intent(this, Register2Activity::class.java))
        finish()
    }

    override fun onClick(v: View?) {
        when(v?.id){

            /*R.id.lytCreate ->{ checkValid() }*/

            R.id.txvIndustry ->{ selectIndustry() }

            R.id.txvCountry ->
            {
                setUpCountryPicker()
            }

            R.id.btnContinue ->{ checkValid() }

            R.id.btnCancel ->{

                startActivity(Intent(this, LogInActivity::class.java))
                finish()
            }
        }
    }

    override fun onBackPressed() {
        onExit()
    }
}