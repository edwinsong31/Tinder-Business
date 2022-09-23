package com.tinderbusiness.fragments

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

class BoostFragment : Fragment(), View.OnClickListener {

    private lateinit var binding : FragmentBoostBinding
    private var activity : BoostActivity = BoostActivity()
    private val selected : Boolean = true
    private val unSelected : Boolean = false
    private var price : Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_boost, container, false)
        val root : View = binding.root
        loadLayout()
        return root
    }

    private fun loadLayout(){

        binding.rlt3Boots.setOnClickListener(this)
        binding.rlt5Boots.setOnClickListener(this)
        binding.rlt10Boots.setOnClickListener(this)

        binding.btnMakePayment.setOnClickListener(this)
        //binding.btnMakePayment.isEnabled = false

    }

    private fun cardSelected(a : Boolean, b: Boolean, c: Boolean){
        //binding.btnMakePayment.isEnabled = true
        binding.rlt3Boots.isSelected = a
        binding.rlt5Boots.isSelected = b
        binding.rlt10Boots.isSelected = c
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btnMakePayment ->{
                Toast.makeText(activity,"Make payment $$price",Toast.LENGTH_SHORT).show();
            }
            R.id.rlt3Boots ->{
                price = 5.99
                cardSelected(selected, unSelected, unSelected)
            }

            R.id.rlt5Boots ->{
                price = 4.99
                cardSelected(unSelected, selected, unSelected)
            }
            R.id.rlt10Boots ->{
                price = 2.99
                cardSelected(unSelected, unSelected, selected)
            }
        }
    }

    override fun onAttach(context: android.content.Context) {

        super.onAttach(context)
        activity = context as BoostActivity
    }
}