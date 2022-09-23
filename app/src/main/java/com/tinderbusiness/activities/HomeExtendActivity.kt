package com.tinderbusiness.activities

import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.pixplicity.easyprefs.library.Prefs
import com.tinderbusiness.R
import com.tinderbusiness.databinding.ActivityHomeExtendBinding
import com.tinderbusiness.models.BusinessMatch
import com.tinderbusiness.utils.Constants
import com.tinderbusiness.utils.ContinentDialog
import java.util.*

class HomeExtendActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding : ActivityHomeExtendBinding
    private lateinit var match : BusinessMatch
    private var continentDialog : ContinentDialog? = ContinentDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_home_extend)

        match = intent.extras?.get(Constants.MATCH) as BusinessMatch

        loadLayout()
    }

    private fun loadLayout(){

        binding.headerExtend.imvMenu.visibility = View.GONE
        binding.headerExtend.imvClose.visibility = View.VISIBLE
        binding.headerExtend.imvClose.setOnClickListener(this)
        binding.headerExtend.imvInter.setOnClickListener(this)

        val bgCr = Prefs.getInt(Constants.BG_COLOR, ContextCompat.getColor(this, R.color.light_yellow))
        val txCr = Prefs.getInt(Constants.TEXT_COLOR, ContextCompat.getColor(this, R.color.txt_dark_red))

        val drawableBg = binding.lytCard.background as GradientDrawable
        drawableBg.mutate()
        drawableBg.setColor(bgCr)

        binding.txvName.text = match.name
        binding.txvName.setTextColor(txCr)
        binding.txvIndustry.text = match.industry
        binding.txvIndustry.setTextColor(txCr)
        binding.txvCountry.text = match.country
        binding.txvCountry.setTextColor(txCr)
        binding.txvInternational.text = Prefs.getString(
            Constants.CONTINENT,
            "International")
        binding.txvInternational.setTextColor(txCr)
        binding.txvBudget.text = "BUDGET : USD${match.budget}"
        binding.txvBudget.setTextColor(txCr)
        binding.txvTypesTitle.setTextColor(txCr)
        binding.txvCallaborations.text = match.type_collaborations
        binding.txvCallaborations.setTextColor(txCr)
        binding.txvInterestedTitle.setTextColor(txCr)
        binding.txvInterestedIndustry.text = match.interested_industry
        binding.txvInterestedIndustry.setTextColor(txCr)
        binding.txvDesTitle.setTextColor(txCr)

        binding.txvDescription.text = match.description
        binding.txvDescription.setTextColor(txCr)

        binding.cancelButton.setOnClickListener(this)
        binding.superLikeButton.setOnClickListener(this)
        binding.likeButton.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){

            R.id.imvClose,
            R.id.cancelButton ->{
                finish()
            }
            R.id.imvInter ->{
                continentDialog?.internationalDialog(this)
            }
            R.id.super_like_button ->{
                showToast("Super Liked")
                finish()
            }
            R.id.like_button ->{
                showToast("Liked")
                finish()
            }
        }
    }

}