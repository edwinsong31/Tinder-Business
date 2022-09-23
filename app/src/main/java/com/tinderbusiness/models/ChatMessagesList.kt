package com.tinderbusiness.models

import com.tinderbusiness.utils.Tools


class ChatMessagesList(var text: String?, var timestamp: String, var receiverId: String?, var receiverName: String?
                       , var receiverPhoto: String?, var senderId: String?, var senderName: String?
                       , var senderPhoto: String?, var isread: Boolean?, var email: String?
                       , var uploadUrl: String?, var dateSent: String?
                       , var displayTitle: String?, var msgType: String?, var chatDateStamp: String?, var isChatStampVisible: Boolean?) {




    val readableTime: String?
        get() {
            return try {
                Tools.formatTime(java.lang.Long.valueOf(timestamp))
            } catch (ignored: NumberFormatException) {
                null
            }

        }

    val receiver: Friend
        get() = Friend(receiverId!!, receiverName!!, receiverPhoto!!,email!!,false,"")

    val sender: Friend
        get() = Friend(senderId!!, senderName!!, senderPhoto!!,email!!,false,"")

    val comparableTimestamp: Long
        get() = java.lang.Long.parseLong(timestamp)


}
