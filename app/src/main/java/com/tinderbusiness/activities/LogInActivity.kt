package com.tinderbusiness.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.pixplicity.easyprefs.library.Prefs
import com.tinderbusiness.R
import com.tinderbusiness.TinderBusinessApplication.TAG
import com.tinderbusiness.activities.tabs.HomeActivity
import com.tinderbusiness.databinding.ActivityLogInBinding
import com.tinderbusiness.models.UserModel
import com.tinderbusiness.utils.Constants
import com.tinderbusiness.utils.Constants.g_user
import com.tinderbusiness.utils.Utils

class LogInActivity : BaseActivity(), View.OnClickListener {

    private lateinit var logInActivity:ActivityLogInBinding
    var name : String = ""
    private var password : String = ""
    var email : String = ""
    var _isFromLogout = false
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        logInActivity = DataBindingUtil.setContentView(this,
            R.layout.activity_log_in)

        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result

                // Log
                Prefs.putString(Constants.TOKEN, token)
                Prefs.getString(Constants.TOKEN, "")
                Log.d(TAG, Prefs.getString(Constants.TOKEN, ""))
            })

        auth = Firebase.auth
        initValue()
        loadLayout()
    }

    private fun initValue() {
        _isFromLogout = intent.getBooleanExtra(Constants.KEY_LOGOUT, false)
    }

    private fun loadLayout(){

        logInActivity.btnStart.setOnClickListener(this)
        logInActivity.txvForget.setOnClickListener(this)
        logInActivity.txvNewUser.setOnClickListener(this)
        logInActivity.txvAboutCompany.setOnClickListener(this)


        if(_isFromLogout){
            logInActivity.edtName.text = "".toEditable()
            logInActivity.edtPassword.text = "".toEditable()
        }
        else
        {
            email = Prefs.getString(Constants.EMAIL, "")
            password = Prefs.getString(Constants.PASSWORD, "")

            logInActivity.edtName.text = email.toEditable()
            logInActivity.edtPassword.text = password.toEditable()

            if (!email.isNullOrEmpty() && !password.isNullOrEmpty()){
                callLogin()
            }
        }
    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    private fun checkValidation(){

        if (Utils.checkEdtValidation(logInActivity.edtName, resources.getString(R.string.enter_user_name))
            && Utils.checkEdtValidation(logInActivity.edtPassword, resources.getString(R.string.enter_password))){


            Utils.hideKeyBoard(this)

            callLogin()

        }
    }

    private fun callLogin(){

        showLoader(getString(R.string.please_wait))
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    Log.d(TAG, "signInWithEmail:success")

                    Prefs.putString(Constants.EMAIL, email)
                    Prefs.putString(Constants.PASSWORD, password)

                    val userUid : String = auth.currentUser!!.uid

                    db.collection(Constants.USERS).document(userUid).get()
                        .addOnSuccessListener { document ->
                            if (document != null){
                                closeLoader()

                                val refDoc = document.data

                                val user = UserModel()

                                user.id = userUid
                                user.contactName = refDoc?.get(Constants.CONTACT_NAME).toString()
                                user.email = refDoc?.get(Constants.EMAIL).toString()
                                user.country = refDoc?.get(Constants.COUNTRY).toString()
                                user.continent = refDoc?.get(Constants.CONTINENT).toString()
                                user.industry = refDoc?.get(Constants.INDUSTRY).toString()
                                user.typesCollaborations = refDoc?.get(Constants.COLLABORATIONS).toString()
                                user.interestedIndustry = refDoc?.get(Constants.INTERESTED_INDUSTRY).toString()
                                user.budget = refDoc?.get(Constants.BUDGET).toString()
                                user.description = refDoc?.get(Constants.DESCRIPTION).toString()

                                g_user = user

                                runOnUiThread{
                                    gotoHome()
                                }
                            }
                            else {
                                showToast("Doesn't exist this user.")
                            }
                        }
                        .addOnFailureListener{ exception ->
                            showToast(exception.message)
                            closeLoader()
                        }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    /*Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()*/
                    showToast(task.exception?.message)
                    closeLoader()
                }
            }
    }

    private fun gotoHome(){

        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnStart ->{
                email = logInActivity.edtName.text.toString()
                password = logInActivity.edtPassword.text.toString()
                checkValidation()
            }

            R.id.txvForget ->{
                val intent = Intent(this, ForgetPasswordActivity::class.java)
                startActivity(intent)
                //finish()
            }
            R.id.txvNewUser ->{
                val intent = Intent(this, Register1Activity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.txvAboutCompany ->{
                val intent = Intent(this, GeneralContextActivity::class.java)
                intent.putExtra(Constants.TITLE, "About Company")
                intent.putExtra(Constants.CONTENT, "About Company")
                startActivity(intent)
            }
        }
    }

    override fun onBackPressed() {
        onExit()
    }
}