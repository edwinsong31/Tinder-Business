package com.tinderbusiness.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.tinderbusiness.R
import com.tinderbusiness.activities.tabs.BoostActivity
import com.tinderbusiness.databinding.FragmentBoostBinding
import com.tinderbusiness.databinding.FragmentMembershipPackagesBinding

class MembershipPackagesFragment : Fragment(), View.OnClickListener {

    private lateinit var binding : FragmentMembershipPackagesBinding
    private var activity : BoostActivity = BoostActivity()
    private val selected : Boolean = true
    private val unSelected : Boolean = false
    private var price : Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_membership_packages, container, false)
        val root : View = binding.root
        loadLayout()
        return root
    }

    private fun loadLayout(){

        binding.rltBronze.setOnClickListener(this)
        binding.rltSilver.setOnClickListener(this)
        binding.rltGold.setOnClickListener(this)

        binding.btnMakePayment.setOnClickListener(this)
        //binding.btnMakePayment.isEnabled = false

    }

    private fun cardSelected(a : Boolean, b: Boolean, c: Boolean){

        //binding.btnMakePayment.isEnabled = true

        binding.rltBronze.isSelected = a
        binding.rltSilver.isSelected = b
        binding.rltGold.isSelected = c
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btnMakePayment ->{
                Toast.makeText(activity,"Make payment $$price", Toast.LENGTH_SHORT).show();
            }
            R.id.rltBronze ->{
                price = 19.99
                cardSelected(selected, unSelected, unSelected)
            }

            R.id.rltSilver ->{
                price = 24.99
                cardSelected(unSelected, selected, unSelected)
            }
            R.id.rltGold ->{
                price = 29.99
                cardSelected(unSelected, unSelected, selected)
            }
        }
    }

    override fun onAttach(context: Context) {

        super.onAttach(context)
        activity = context as BoostActivity

    }
}