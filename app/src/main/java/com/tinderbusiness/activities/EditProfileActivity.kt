package com.tinderbusiness.activities

import android.app.AlertDialog
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.util.SharedPref
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hbb20.countrypicker.dialog.launchCountryPickerDialog
import com.hbb20.countrypicker.models.CPCountry
import com.pixplicity.easyprefs.library.Prefs
import com.tinderbusiness.R
import com.tinderbusiness.TinderBusinessApplication
import com.tinderbusiness.databinding.ActivityEditProfileBinding
import com.tinderbusiness.utils.Constants
import com.tinderbusiness.utils.Constants.g_user
import com.tinderbusiness.utils.Utils
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

class EditProfileActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding : ActivityEditProfileBinding
    private var mColor = 0
    private var txColor = 0
    private var bgColor = 0

    private var contactName : String = ""
    private var email : String = ""
    private var countryName : String = ""
    private var continent : String = ""
    private var industry : String = ""
    private var typeCollaborations : String = ""
    private var interestedIndustry : String = ""
    private var budget : String = ""
    private var description : String = ""

    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_edit_profile)

        txColor = Prefs.getInt(Constants.TEXT_COLOR, ContextCompat.getColor(this, R.color.txt_dark_red))
        bgColor = Prefs.getInt(Constants.BG_COLOR, ContextCompat.getColor(this, R.color.light_yellow))

        loadLayout()
    }

    private fun loadLayout(){

        binding.txvBgColor.setOnClickListener(this)
        binding.txvTxtColor.setOnClickListener(this)
        binding.btnOk.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
        binding.txvCountry.setOnClickListener(this)
        binding.txvIndustry.setOnClickListener(this)
        binding.txvInterestedIndustry.setOnClickListener(this)
        binding.txvCollaborations.setOnClickListener(this)

        binding.seekBudget.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed
                binding.txvMin.text = progress.toString() + "K"
                binding.edtBudget.text = progress.toString().toEditable()
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
            }
        })

        binding.edtBudget.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                if (!s.isNullOrEmpty() && s.length < 3){
                    val enteredProgress: Int = Integer.valueOf(s.toString())
                    binding.seekBudget.progress = enteredProgress
                }
                else if (s.isNullOrEmpty()){
                    binding.seekBudget.progress = 0
                }
                else if (s.toString() == "100"){
                    binding.seekBudget.progress = 100
                }
                else if (s.length >= 3 && s.toString() != "100"){
                    showToast("Invalid budget")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        binding.edtDescription.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty() && s.length >= 150){
                    showToast("Description is limited to 150 words only")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


            }
        })

        setColor(txColor, bgColor)
        setProfile()
    }

    private fun setProfile(){

        countryName = g_user.country
        continent = g_user.continent
        industry = g_user.industry
        interestedIndustry = g_user.interestedIndustry
        typeCollaborations = g_user.typesCollaborations

        binding.edtContact.text = g_user.contactName.toEditable()
        binding.edtEmail.text = g_user.email
        binding.txvCountry.text = g_user.country
        binding.txvIndustry.text = industry
        binding.txvInterestedIndustry.text = interestedIndustry
        binding.txvCollaborations.text = typeCollaborations
        binding.edtBudget.text = g_user.budget.toEditable()
        binding.seekBudget.progress = Integer.valueOf(g_user.budget.toString())
        binding.edtDescription.text = g_user.description.toEditable()

    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    private fun setColor(txCr : Int, bgCr : Int){

        val drawableBg = binding.txvBgColor.background as GradientDrawable
        drawableBg.mutate()
        drawableBg.setColor(bgCr)
        drawableBg.setStroke(1, ContextCompat.getColor(this, R.color.txt_grey))
        binding.txvBgColor.setTextColor(txCr)

        val drText = binding.txvTxtColor.background as GradientDrawable
        drText.mutate()
        drText.setColor(txCr)
        drText.setStroke(1, ContextCompat.getColor(this, R.color.txt_grey))
        binding.txvTxtColor.setTextColor(bgCr)
    }

    private fun showColorPicker(isText : Boolean){

        var  title = "Select Text Color"
        if (!isText)
            title = "Select Background Color"

        val primaryColor = ContextCompat.getColor(this, R.color.colorPrimary)
        mColor = SharedPref(this).getRecentColor(primaryColor)

        ColorPickerDialog
            .Builder(this)
            .setTitle(title)
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

    private fun setUpCountryPicker(){

        this.launchCountryPickerDialog { selectedCountry: CPCountry? ->
            binding.txvCountry.text = selectedCountry?.flagEmoji + "    " + "+" + selectedCountry?.phoneCode.toString() + "     " + selectedCountry?.name
            countryName = selectedCountry!!.name
            continent = getContinent(selectedCountry.alpha2.uppercase(Locale.getDefault()))
        }
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

    private fun selectCollaborations(){

        val itemsArray = arrayOf(
            resources.getStringArray(R.array.types_collaborations_array)
        )
        val items = itemsArray[0]

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Types of Collaborations *")
        builder.setItems(
            items
        ) { _, item ->
            binding.txvCollaborations.text = items[item]
            typeCollaborations = items[item]
        }

        val alert = builder.create()

        alert.show()
    }

    private fun selectInterestedIndustry(){

        val itemsArray = arrayOf(
            resources.getStringArray(R.array.industry_array)
        )
        val items = itemsArray[0]

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Interested Industry *")
        builder.setItems(
            items
        ) { _, item ->
            binding.txvInterestedIndustry.text = items[item]
            interestedIndustry = items[item]
        }

        val alert = builder.create()

        alert.show()
    }

    private fun checkValid(){

        if(Utils.checkEdtValidation(binding.edtContact, resources.getString(R.string.enter_contact_name))
            && Utils.checkTxvValidation(binding.txvIndustry, resources.getString(R.string.select_industry))
            && Utils.checkTxvValidation(binding.txvCountry, resources.getString(R.string.select_country))
            && Utils.checkTxvValidation(binding.txvInterestedIndustry, resources.getString(R.string.select_interested_industry))
            && Utils.checkTxvValidation(binding.txvCollaborations, resources.getString(R.string.select_collaborations))
            && Utils.checkEdtValidation(binding.edtBudget, resources.getString(R.string.enter_budget))
            && Utils.checkEdtValidation(binding.edtDescription, resources.getString(R.string.enter_description)))
        {
            contactName = binding.edtContact.text.toString()
            budget = binding.edtBudget.text.toString()
            description = binding.edtDescription.text.toString()

            updateProfile()
        }

    }

    private fun updateProfile(){

        showLoader(getString(R.string.please_wait))

        //db.collection(Constants.USERS).document(g_user.id).update("", "")
        Prefs.putInt(Constants.TEXT_COLOR, txColor)
        Prefs.putInt(Constants.BG_COLOR, bgColor)

        val userRef = db.collection(Constants.USERS).document(g_user.id)

        val batch = db.batch()

        batch.update(userRef, Constants.CONTACT_NAME , contactName)
        //batch.update(userRef, Constants.EMAIL , email)
        batch.update(userRef, Constants.COUNTRY , countryName)
        batch.update(userRef, Constants.CONTINENT , continent)
        batch.update(userRef, Constants.INDUSTRY , industry)
        batch.update(userRef, Constants.COLLABORATIONS , typeCollaborations)
        batch.update(userRef, Constants.INTERESTED_INDUSTRY , interestedIndustry)
        batch.update(userRef, Constants.BUDGET , budget)
        batch.update(userRef, Constants.DESCRIPTION , description)

        batch.commit().addOnCompleteListener{

            closeLoader()

            g_user.contactName = contactName
            g_user.country = countryName
            g_user.continent = continent
            g_user.industry = industry
            g_user.interestedIndustry = interestedIndustry
            g_user.typesCollaborations = typeCollaborations
            g_user.budget = budget
            g_user.description = description

            showToast("Updated")
            finish()
        }

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.txvBgColor ->{
                showColorPicker(false)
            }
            R.id.txvTxtColor ->{
                showColorPicker(true)
            }
            R.id.txvCountry ->
            {
                setUpCountryPicker()
            }
            R.id.txvIndustry ->{
                selectIndustry()
            }
            R.id.txvInterestedIndustry ->{
                selectInterestedIndustry()
            }
            R.id.txvCollaborations ->{
                selectCollaborations()
            }
            R.id.btnOk ->{
                checkValid()
            }
            R.id.btnCancel ->{
                finish()
            }


        }
    }
}