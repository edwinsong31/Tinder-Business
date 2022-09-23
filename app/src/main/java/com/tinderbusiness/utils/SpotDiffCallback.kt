package com.yuyakaido.android.cardstackview.sample

import androidx.recyclerview.widget.DiffUtil
import com.tinderbusiness.models.BusinessMatch

class SpotDiffCallback(
        private val old: List<BusinessMatch>,
        private val new: List<BusinessMatch>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition].id == new[newPosition].id
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition] == new[newPosition]
    }

}
