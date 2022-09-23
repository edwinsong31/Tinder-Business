package com.tinderbusiness.utils

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.pixplicity.easyprefs.library.Prefs
import com.tinderbusiness.R
import com.tinderbusiness.listener.ContinentListener

class ContinentDialog{

    var continentListener : ContinentListener? = null

    fun internationalDialog(context : Context){

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_international)
        dialog.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setBackgroundDrawable(
            ColorDrawable(ContextCompat.getColor(context, R.color.transparent))
        )
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)

        val checkInter : CheckBox = dialog.findViewById<View>(R.id.checkInter) as CheckBox
        val checkLocal : CheckBox = dialog.findViewById<View>(R.id.checkLocal) as CheckBox
        val checkAsia = dialog.findViewById<View>(R.id.checkAsia) as CheckBox
        val checkEurope = dialog.findViewById<View>(R.id.checkEurope) as CheckBox
        val checkNAmerica = dialog.findViewById<View>(R.id.checkNAmerica) as CheckBox
        val checkSAmerica = dialog.findViewById<View>(R.id.checkSAmerica) as CheckBox
        val checkAntartica = dialog.findViewById<View>(R.id.checkAntartica) as CheckBox
        val checkOceana = dialog.findViewById<View>(R.id.checkOceana) as CheckBox
        val checkAfrica = dialog.findViewById<View>(R.id.checkAfrica) as CheckBox
        val btnSelect = dialog.findViewById<View>(R.id.btnSelect)
        val imvCancel = dialog.findViewById<View>(R.id.imvCancel)

        val chkArray = arrayOfNulls<CheckBox>(9)
        chkArray[0] = checkInter
        chkArray[1] = checkLocal
        chkArray[2] = checkAsia
        chkArray[3] = checkAfrica
        chkArray[4] = checkEurope
        chkArray[5] = checkNAmerica
        chkArray[6] = checkSAmerica
        chkArray[7] = checkAntartica
        chkArray[8] = checkOceana

        var continent = ""

        val strCon : String = Prefs.getString(Constants.CONTINENT, context.resources.getString(R.string.international))
        when(strCon) {
            "International" -> {
                chkArray[0]?.isChecked = true
            }
            "Local" -> {
                chkArray[1]?.isChecked = true
            }

            "Asia" -> {
                chkArray[2]?.isChecked = true
            }
            "Africa" -> {
                chkArray[3]?.isChecked = true
            }
            "Europe" -> {
                chkArray[4]?.isChecked = true
            }
            "North America" -> {
                chkArray[5]?.isChecked = true
            }

            "South America" -> {
                chkArray[6]?.isChecked = true
            }
            "Antarctica" -> {
                chkArray[7]?.isChecked = true
            }
            "Oceania" -> {
                chkArray[8]?.isChecked = true
            }
        }

        val mListener = View.OnClickListener { p0 ->
            val checkedId : Int = p0!!.id
            for (element in chkArray) {
                val current = element!!
                current.isChecked = current.id == checkedId
                if (current.id == checkedId){
                    continent = current.text.toString()
                }

            }
        }

        chkArray[0]!!.setOnClickListener(mListener)
        chkArray[1]!!.setOnClickListener(mListener)
        chkArray[2]!!.setOnClickListener(mListener)
        chkArray[3]!!.setOnClickListener(mListener)
        chkArray[4]!!.setOnClickListener(mListener)
        chkArray[5]!!.setOnClickListener(mListener)
        chkArray[6]!!.setOnClickListener(mListener)
        chkArray[7]!!.setOnClickListener(mListener)
        chkArray[8]!!.setOnClickListener(mListener)

        btnSelect.setOnClickListener {
            continentListener?.onContinentChanged(continent)
            Prefs.putString(Constants.CONTINENT, continent)

            dialog.dismiss()
        }
        imvCancel.setOnClickListener{
            dialog.dismiss()
        }

        dialog.show()
    }

}