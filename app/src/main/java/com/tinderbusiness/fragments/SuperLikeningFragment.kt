package com.tinderbusiness.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.tinderbusiness.R
import com.tinderbusiness.activities.tabs.BoostActivity
import com.tinderbusiness.databinding.FragmentSuperLikeningBinding

class SuperLikeningFragment : Fragment(), View.OnClickListener {
    private lateinit var binding : FragmentSuperLikeningBinding
    private var activity : BoostActivity = BoostActivity()
    private val selected : Boolean = true
    private val unSelected : Boolean = false
    private var price : Double = 0.0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_super_likening, container, false)
        val root : View = binding.root
        loadLayout()
        return root
    }

    private fun loadLayout(){

        binding.rlt35.setOnClickListener(this)
        binding.rlt510.setOnClickListener(this)
        binding.rlt1020.setOnClickListener(this)

        binding.btnMakePayment.setOnClickListener(this)
        //binding.btnMakePayment.isEnabled = false
    }

    private fun cardSelected(a : Boolean, b: Boolean, c: Boolean){
        //binding.btnMakePayment.isEnabled = true
        binding.rlt35.isSelected = a
        binding.rlt510.isSelected = b
        binding.rlt1020.isSelected = c
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btnMakePayment ->{
                //Toast.makeText(activity,"Make payment $$price", Toast.LENGTH_SHORT).show();
            }
            R.id.rlt3_5 ->{
                price = 5.99
                cardSelected(selected, unSelected, unSelected)
            }
            R.id.rlt5_10 ->{
                price = 4.99
                cardSelected(unSelected, selected, unSelected)
            }
            R.id.rlt10_20 ->{
                price = 2.99
                cardSelected(unSelected, unSelected, selected)
            }
        }
    }

    override fun onAttach(context: Context) {

        super.onAttach(context)
        activity = context as BoostActivity
    }
}