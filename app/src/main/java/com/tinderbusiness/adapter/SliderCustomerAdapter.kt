package com.tinderbusiness.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tinderbusiness.R
import com.tinderbusiness.databinding.ItemTutorialSliderBinding
import com.tinderbusiness.models.SliderModel

class SliderCustomerAdapter : RecyclerView.Adapter<SliderCustomerAdapter.VHolder>() {

    private var items: MutableList<SliderModel> = mutableListOf()

    var onItemClick: ((SliderModel) -> Unit)? = null

    fun setItem(items: List<SliderModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }


    inner class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //private val binding = ItemSampleImageSliderBinding.bind(itemView)
        private var binding = ItemTutorialSliderBinding.bind(itemView)

        fun onBind(model: SliderModel) {
            binding.txvTitle.text = model.title
            binding.txvDes.text = model.description

            binding.root.setOnClickListener {
                onItemClick?.invoke(model)
            }
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_tutorial_slider, parent, false)
    )
}