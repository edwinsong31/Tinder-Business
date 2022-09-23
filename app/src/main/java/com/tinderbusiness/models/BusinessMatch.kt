package com.tinderbusiness.models

import com.tinderbusiness.activities.HomeExtendActivity
import java.io.Serializable

data class BusinessMatch(

    val id: Long = counter++,
    val name: String,
    /*val city: String,
    val url: String,*/
    val industry : String,
    val country : String,
    val budget : String,
    val type_collaborations : String,
    val interested_industry : String,
    val description : String

) : Serializable {
    companion object {
        private var counter = 0L

        }
}
