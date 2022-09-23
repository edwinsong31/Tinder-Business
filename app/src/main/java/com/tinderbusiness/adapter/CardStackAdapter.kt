package com.tinderbusiness.adapter

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.pixplicity.easyprefs.library.Prefs
import com.tinderbusiness.R
import com.tinderbusiness.activities.HomeExtendActivity
import com.tinderbusiness.activities.tabs.HomeActivity
import com.tinderbusiness.models.BusinessMatch
import com.tinderbusiness.utils.Constants
import java.io.Serializable

class CardStackAdapter (private var matchs: List<BusinessMatch>, private var activity: HomeActivity) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>(){

    //private lateinit var activity : HomeActivity
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_card, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val match = matchs[position]
        val activity = activity
        val bgCr = Prefs.getInt(Constants.BG_COLOR, ContextCompat.getColor(activity, R.color.light_yellow))
        val txCr = Prefs.getInt(Constants.TEXT_COLOR, ContextCompat.getColor(activity, R.color.txt_dark_red))

        val drawableBg : GradientDrawable = GradientDrawable()
        drawableBg.mutate()
        drawableBg.setColor(bgCr)
        drawableBg.setStroke(1, ContextCompat.getColor(activity, R.color.txt_grey))
        //holder.lytCard.setBackgroundDrawable(drawableBg)
        holder.scrollCard.setBackgroundDrawable(drawableBg)

        //holder.name.text = "${match.id}. ${match.name}"
        holder.name.text = match.name
        holder.name.setTextColor(txCr)
        holder.industry.text = match.industry
        holder.industry.setTextColor(txCr)
        holder.country.text = match.country
        holder.country.setTextColor(txCr)
        holder.international.text = Prefs.getString(Constants.CONTINENT,
            "International")
        holder.international.setTextColor(txCr)
        holder.budget.text = "BUDGET : USD${match.budget}"
        holder.budget.setTextColor(txCr)
        holder.txvTypesTitle.setTextColor(txCr)
        holder.collaborations.text = match.type_collaborations
        holder.collaborations.setTextColor(txCr)
        holder.txvInterestedTitle.setTextColor(txCr)
        holder.interestedIndustry.text = match.interested_industry
        holder.interestedIndustry.setTextColor(txCr)
        holder.txvDesTitle.setTextColor(txCr)

        if (match.description.length > 20){
            holder.description.text = match.description.substring(0,20)
        } else
            holder.description.text = match.description

        holder.description.setTextColor(txCr)

        holder.lytCard.setOnClickListener { v ->

            val intent : Intent = Intent(activity, HomeExtendActivity::class.java)
            intent.putExtra(Constants.MATCH, match as Serializable)
            activity.startActivity(intent)
        }
       /* holder.city.text = match.city
        Glide.with(holder.image)
            .load(match.url)
            .into(holder.image)
        holder.itemView.setOnClickListener { v ->
            Toast.makeText(v.context, match.name, Toast.LENGTH_SHORT).show()
        }*/
    }

    override fun getItemCount(): Int {
        return matchs.size
    }

    fun setSpots(spots: List<BusinessMatch>) {
        this.matchs = spots
    }

    fun getSpots(): List<BusinessMatch> {
        return matchs
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val scrollCard: NestedScrollView = view.findViewById(R.id.scrollCard)
        val lytCard: LinearLayout = view.findViewById(R.id.lytCard)
        val name: TextView = view.findViewById(R.id.item_name)
        val industry : TextView = view.findViewById(R.id.item_industry)
        val country : TextView = view.findViewById(R.id.item_country)
        val international : TextView = view.findViewById(R.id.item_international)
        val budget : TextView = view.findViewById(R.id.item_budget)
        val txvTypesTitle : TextView = view.findViewById(R.id.txvTypesTitle)
        val collaborations : TextView = view.findViewById(R.id.item_type_callaborations)
        val txvInterestedTitle : TextView = view.findViewById(R.id.txvInterestedTitle)
        val interestedIndustry : TextView = view.findViewById(R.id.item_interested_industry)
        val txvDesTitle : TextView = view.findViewById(R.id.txvDesTitle)
        val description : TextView = view.findViewById(R.id.item_description)
    }
}