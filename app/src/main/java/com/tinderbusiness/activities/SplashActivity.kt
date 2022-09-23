package com.tinderbusiness.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tinderbusiness.R
import com.tinderbusiness.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var atSplashBinding : ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        atSplashBinding = DataBindingUtil.setContentView(this,
        R.layout.activity_splash)

        loadLayout()
    }

    private fun loadLayout(){

        Handler().postDelayed({
            intent = Intent(this, LogInActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }, 500)
    }
}