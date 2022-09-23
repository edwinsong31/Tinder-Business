package com.tinderbusiness.models
import com.tinderbusiness.utils.Tools
import java.io.Serializable

class Friend(val id: String, val name: String, val photo: String, val email: String,val userChatStatus :Boolean, var userLastSeen: String) : Serializable{
    val readableTime: String?
        get() {
            return try {
                Tools.formatTime(java.lang.Long.valueOf(userLastSeen))
            } catch (ignored: NumberFormatException) {
                null
            }
        }

}
