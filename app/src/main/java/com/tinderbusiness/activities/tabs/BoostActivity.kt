package com.tinderbusiness.activities.tabs

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.tinderbusiness.R
import com.tinderbusiness.activities.BaseActivity
import com.tinderbusiness.adapter.MembershipViewPagerAdapter
import com.tinderbusiness.databinding.ActivityBoostBinding
import com.tinderbusiness.fragments.BoostFragment
import com.tinderbusiness.fragments.MembershipPackagesFragment
import com.tinderbusiness.fragments.SuperLikeFragment
import com.tinderbusiness.fragments.SuperLikeningFragment
import com.tinderbusiness.utils.ContinentDialog

class BoostActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding : ActivityBoostBinding
    private lateinit var adapter : MembershipViewPagerAdapter
    private var continentDialog : ContinentDialog? = ContinentDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_boost)

        loadLayout()
    }

    private fun loadLayout(){

        binding.headerBoost.imvInter.setOnClickListener(this)
        binding.headerBoost.imvMenu.visibility = View.GONE
        binding.headerBoost.imvClose.visibility = View.VISIBLE
        binding.headerBoost.imvClose.setOnClickListener(this)

        adapter = MembershipViewPagerAdapter(supportFragmentManager, lifecycle)

        adapter.addFragment(BoostFragment())
        adapter.addFragment(SuperLikeFragment())
        adapter.addFragment(SuperLikeningFragment())
        adapter.addFragment(MembershipPackagesFragment())

        //binding.viewpagerBoost.orientation(ViewPager2.ORIENTATION_HORIZONTAL)
        binding.viewpagerBoost.adapter = adapter
        binding.indicator.setViewPager(binding.viewpagerBoost)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){

            R.id.imvClose ->{
                //startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
            R.id.imvInter ->{
                continentDialog?.internationalDialog(this)
            }
        }
    }

    override fun onBackPressed() {
        if (binding.viewpagerBoost.currentItem == 0) {
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            binding.viewpagerBoost.currentItem = binding.viewpagerBoost.currentItem - 1
        }
    }

}