package com.tinderbusiness.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tinderbusiness.R
import com.tinderbusiness.TinderBusinessApplication.TAG
import com.tinderbusiness.databinding.ActivityRegister2Binding
import com.tinderbusiness.utils.Constants
import com.tinderbusiness.utils.Constants.g_user
import com.tinderbusiness.utils.Utils


class Register2Activity : BaseActivity(), View.OnClickListener {

    private lateinit var binding : ActivityRegister2Binding
    private var typeCollaborations : String = ""
    private var interestedIndustry : String = ""
    private var budget : String = ""
    private var description : String = ""

    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_register2)

        auth = Firebase.auth
        loadLayout()
    }

    private fun loadLayout(){

        binding.lytRegister.setOnClickListener(this)
        binding.txvCollaborations.setOnClickListener(this)
        binding.txvInterestedIndustry.setOnClickListener(this)
        binding.btnAddProfile.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)
        binding.btnContinue.setOnClickListener(this)
        binding.btnAddBackground.setOnClickListener(this)
        binding.btnStarted.setOnClickListener(this)
        binding.checkTerms.setOnClickListener(this)
        binding.checkPolicy.setOnClickListener(this)


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

        showUserInfo()
    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    @SuppressLint("SetTextI18n")
    private fun showUserInfo(){

        binding.txvName.text = "Name: ${g_user.contactName}"
        binding.txvEmail.text = "Email: ${g_user.email}"
        binding.txvCountry.text = "Country: ${g_user.country}"
        binding.txvIndustry.text = "Industry: ${g_user.industry}"
    }

    private fun selectTypeOfCollaborations(){

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

    private fun showSetProfile(){

        binding.btnAddProfile.visibility = View.GONE
        binding.lytContinue.visibility = View.GONE
        binding.lytTerms.visibility = View.VISIBLE
        binding.btnAddBackground.visibility = View.VISIBLE
        binding.btnStarted.visibility = View.VISIBLE
        binding.txvProfile.text = "Profile (2)"
    }

    private fun checkValid(){

        if(Utils.checkTxvValidation(binding.txvCollaborations, resources.getString(R.string.select_collaborations))
            && Utils.checkTxvValidation(binding.txvInterestedIndustry, resources.getString(R.string.select_interested_industry))
            && Utils.checkEdtValidation(binding.edtBudget, resources.getString(R.string.enter_budget))
            && Utils.checkEdtValidation(binding.edtDescription, resources.getString(R.string.enter_description)))
        {
            budget = binding.edtBudget.text.toString()
            description = binding.edtDescription.text.toString()

            callRegistration2()
        }

    }

    private fun callRegistration2(){

        showLoader(getString(R.string.please_wait))
        auth.createUserWithEmailAndPassword(g_user.email, g_user.password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    g_user.id = auth.currentUser?.uid
                    createUsers(g_user.id)
                } else {
                    closeLoader()
                    showToast(task.exception?.message)
                }
            }
    }

    private fun createUsers(uid : String){

        val user = hashMapOf(
            Constants.ID to g_user.id,
            Constants.CONTACT_NAME to g_user.contactName,
            Constants.EMAIL to g_user.email,
            Constants.COUNTRY to g_user.country,
            Constants.CONTINENT to g_user.continent,
            Constants.INDUSTRY to g_user.industry,
            Constants.COLLABORATIONS to typeCollaborations,
            Constants.INTERESTED_INDUSTRY to interestedIndustry,
            Constants.BUDGET to budget,
            Constants.DESCRIPTION to description
        )

        db.collection(Constants.USERS).document(uid).set(user)
            .addOnSuccessListener { documentReference ->

                closeLoader()
                g_user.typesCollaborations
                g_user.interestedIndustry
                g_user.budget
                g_user.description

                gotoGetStarted()
            }
            .addOnFailureListener{ e ->
                Log.d("Error adding document ", e.toString())
                showToast(e.message)
                closeLoader()
            }
    }

    private fun gotoRegister(){

        /*val intent = Intent(this, Register1Activity::class.java)
        intent.action = "Register2"
        startActivity(intent)
        finish()*/
    }

    private fun gotoGetStarted(){

        startActivity(Intent(this, GetStartedActivity::class.java))
        finish()
    }

    private fun gotoSelectColor(){
        startActivity(Intent(this, ConfirmColorActivity::class.java))
    }

    override fun onClick(p0: View?) {
        when(p0?.id){

            R.id.lytRegister ->{
                gotoRegister()
            }

            R.id.txvCollaborations ->{
                selectTypeOfCollaborations()
            }

            R.id.txvInterestedIndustry ->{
                selectInterestedIndustry()
            }

            R.id.btnAddProfile ->{
                showSetProfile()
                //gotoSelectColor()
            }

            R.id.btnBack ->{
                gotoRegister()
            }

            R.id.btnStarted,
            R.id.btnContinue ->{
                checkValid()
            }

            R.id.btnAddBackground ->{
                gotoSelectColor()
            }

            R.id.checkTerms ->{

                val intent = Intent(this, GeneralContextActivity::class.java)
                intent.putExtra(Constants.TITLE, "Terms of Service")
                intent.putExtra(Constants.CONTENT, "Terms of Service")
                startActivity(intent)
            }

            R.id.checkPolicy ->{

                val intent = Intent(this, GeneralContextActivity::class.java)
                intent.putExtra(Constants.TITLE, "Data Policy")
                intent.putExtra(Constants.CONTENT, "Data Policy")
                startActivity(intent)
            }
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        onExit()
    }

}