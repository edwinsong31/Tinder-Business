package com.tinderbusiness.utils;

import android.content.Context;
import android.net.Uri;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tinderbusiness.models.ChatMessagesList;

import java.util.ArrayList;
import java.util.List;

public class ParseFirebaseData {

//    private final SettingsAPI set;

    public ParseFirebaseData(Context context) {
//        set = new SettingsAPI(context);
    }

  /*  public ArrayList<Friend> getAllUser(DataSnapshot dataSnapshot) {
        ArrayList<Friend> frnds = new ArrayList<>();
        String name = null, id = null, photo = null;
        for (DataSnapshot data : dataSnapshot.getChildren()) {
            name = data.child(Constants.NODE_NAME).getValue().toString();
            id = data.child(Constants.NODE_USER_ID).getValue().toString();
            photo = data.child(Constants.NODE_PHOTO).getValue().toString();

            if (!set.readSetting(Constants.PREF_MY_ID).equals(id)) {
                frnds.add(new Friend(id, name, photo));
            }
        }
        return frnds;
    }*/

    /*public List<ChatMessagesList> getMessagesForSingleUser(DataSnapshot dataSnapshot, Context mContext) {
        List<ChatMessagesList> chats = new ArrayList<>();
        String text = "", msgTime = "", senderId =   "", senderName =    "", senderPhoto =   "",
            receiverId =   "", receiverName =   "", receiverPhoto =   "", uploadUrl =   "", dateSent = "",
                displayTitle = "", msgType = "", dateTimeStamp = "";
        boolean read = false;
        boolean isDateTimeVisible = false;
        String lastDate = "";
        for (DataSnapshot data : dataSnapshot.getChildren()) {
            text = data.child(Constants.NODE_TEXT).getValue().toString();
            msgTime = data.child(Constants.NODE_TIME_STAMP).getValue().toString();
            senderId = data.child(Constants.NODE_SENDER_ID).getValue().toString();
            senderName = data.child(Constants.NODE_SENDER_NAME).getValue().toString();
            if(data.child(Constants.NODE_SENDER_PHOTO).getValue()!=null){
            senderPhoto = data.child(Constants.NODE_SENDER_PHOTO).getValue().toString();
            }
            receiverId = data.child(Constants.NODE_RECEIVER_ID).getValue().toString();

            if(data.child(Constants.NODE_RECEIVER_NAME).getValue()!=null){
                receiverName = data.child(Constants.NODE_RECEIVER_NAME).getValue().toString();
            }
            if(data.child(Constants.NODE_RECEIVER_PHOTO).getValue()!=null){
            receiverPhoto = data.child(Constants.NODE_RECEIVER_PHOTO).getValue().toString();
            }

            if(data.child(Constants.NODE_UPLOAD_URL).getValue()!=null){
                uploadUrl = data.child(Constants.NODE_UPLOAD_URL).getValue().toString();
            }
            if(data.child(Constants.NODE_DATA_SENT).getValue()!=null){
                dateSent = data.child(Constants.NODE_DATA_SENT).getValue().toString();
            }

            if(data.child(Constants.NODE_DISPLAY_TITLE).getValue()!=null){
                displayTitle = data.child(Constants.NODE_DISPLAY_TITLE).getValue().toString();
            }
            if(data.child(Constants.NODE_MESSAGE_TYPE).getValue()!=null){
                msgType = data.child(Constants.NODE_MESSAGE_TYPE).getValue().toString();
            }
            //Node isRead is added later, may be null
            read = data.child(Constants.NODE_IS_READ).getValue() == null ||
                Boolean.parseBoolean(data.child(Constants.NODE_IS_READ).getValue().toString());

            if (!lastDate.equals(Utils.getFormattedDate(mContext, msgTime))){
                lastDate = Utils.getFormattedDate(mContext, msgTime);
                isDateTimeVisible = true;
                dateTimeStamp = lastDate;
            }else {
                isDateTimeVisible = false;
            }

            chats.add(new ChatMessagesList(text, msgTime, receiverId, receiverName, receiverPhoto
                    ,senderId, senderName, senderPhoto, read,"",uploadUrl, dateSent, displayTitle, msgType, dateTimeStamp, isDateTimeVisible));
        }
        return chats;
    }

    public ChatMessagesList getLastMessage(DataSnapshot dataSnapshot ){
//        long count = dataSnapshot.getChildrenCount();
        ChatMessagesList chatMessagesList = null;
        String text = "", msgTime = "", senderId =   "", senderName =    "", senderPhoto =   "",
                receiverId =   "", receiverName =   "", receiverPhoto =   "", uploadUrl =   "", dateSent = "",
                displayTitle = "", msgType = "";
        boolean read = false;
        long lastTimeStamp = 0;
        for (DataSnapshot data : dataSnapshot.getChildren()) {

            msgTime = data.child(Constants.NODE_TIME_STAMP).getValue().toString();

            if (Long.parseLong(msgTime) > lastTimeStamp) {
                lastTimeStamp = Long.parseLong(msgTime);
            }
            else continue;

            text = data.child(Constants.NODE_TEXT).getValue().toString();
            msgTime = data.child(Constants.NODE_TIME_STAMP).getValue().toString();
            senderId = data.child(Constants.NODE_SENDER_ID).getValue().toString();
            receiverId = data.child(Constants.NODE_RECEIVER_ID).getValue().toString();


            if(data.child(Constants.NODE_MESSAGE_TYPE).getValue()!=null){
                msgType = data.child(Constants.NODE_MESSAGE_TYPE).getValue().toString();
            }
            //Node isRead is added later, may be null
            read = data.child(Constants.NODE_IS_READ).getValue() == null ||
                    Boolean.parseBoolean(data.child(Constants.NODE_IS_READ).getValue().toString());

            chatMessagesList = new ChatMessagesList(text, msgTime, receiverId, receiverName, receiverPhoto
                    , senderId, senderName, senderPhoto, read,"",uploadUrl, dateSent, displayTitle, msgType, "", false);
        }
        return chatMessagesList;
    }

    public ArrayList<ChatMessagesList> getAllLastMessages(DataSnapshot dataSnapshot) {
        // TODO: 11/09/18 Return only last messages of every conversation current user is
        //  involved in
        ArrayList<ChatMessagesList> lastChats = new ArrayList<>();
        ArrayList<ChatMessagesList> tempMsgList;
        ChatMessagesList chatMessagesList;
        long lastTimeStamp;
        String text =   "", msgTime =   "", senderId =   "", senderName =   "", senderPhoto =   "",
            receiverId =   "", receiverName =   "", receiverPhoto =   "", uploadUrl =   "", dateSent = "",
                displayTitle = "", msgType = "";
        Boolean read = Boolean.TRUE;
        for (DataSnapshot wholeChatData : dataSnapshot.getChildren()) {

            tempMsgList = new ArrayList<>();
            lastTimeStamp = 0;

            for (DataSnapshot data : wholeChatData.getChildren()) {
                msgTime = data.child(Constants.NODE_TIME_STAMP).getValue().toString();
                if (Long.parseLong(msgTime) > lastTimeStamp) {
                    lastTimeStamp = Long.parseLong(msgTime);
                }
                else break;

                text = data.child(Constants.NODE_TEXT).getValue().toString();
                senderId = data.child(Constants.NODE_SENDER_ID).getValue().toString();
                senderName = data.child(Constants.NODE_SENDER_NAME).getValue().toString();
                if(data.child(Constants.NODE_SENDER_PHOTO).getValue()!=null){
                senderPhoto = data.child(Constants.NODE_SENDER_PHOTO).getValue().toString();
                }
                receiverId = data.child(Constants.NODE_RECEIVER_ID).getValue().toString();
                receiverName = data.child(Constants.NODE_RECEIVER_NAME).getValue().toString();
                if(data.child(Constants.NODE_RECEIVER_PHOTO).getValue()!=null){
                receiverPhoto = data.child(Constants.NODE_RECEIVER_PHOTO).getValue().toString();
                }

                if(data.child(Constants.NODE_UPLOAD_URL).getValue()!=null){
                    uploadUrl = data.child(Constants.NODE_UPLOAD_URL).getValue().toString();
                }

                if(data.child(Constants.NODE_DATA_SENT).getValue()!=null){
                    dateSent = data.child(Constants.NODE_DATA_SENT).getValue().toString();
                }

                if(data.child(Constants.NODE_DISPLAY_TITLE).getValue()!=null){
                    displayTitle = data.child(Constants.NODE_DISPLAY_TITLE).getValue().toString();
                }
                if(data.child(Constants.NODE_MESSAGE_TYPE).getValue()!=null){
                    msgType = data.child(Constants.NODE_MESSAGE_TYPE).getValue().toString();
                }

                //Node isRead is added later, may be null
                read = data.child(Constants.NODE_IS_READ).getValue() == null ||
                    Boolean.parseBoolean(data.child(Constants.NODE_IS_READ).getValue().toString());

                chatMessagesList = new ChatMessagesList(text, msgTime, receiverId, receiverName, receiverPhoto,
                        senderId, senderName, senderPhoto, read,"",uploadUrl, dateSent, displayTitle,msgType, "", false);

                tempMsgList.add(
                    new ChatMessagesList(text, msgTime, receiverId, receiverName, receiverPhoto,
                        senderId, senderName, senderPhoto, read,"",uploadUrl, dateSent, displayTitle,msgType, "", false));
            }

            *//*for (ChatMessagesList oneTemp : tempMsgList) {
                if ((set.readSetting("007").equals(oneTemp.getReceiver().getId())) ||
                    (set.readSetting("myid").equals(oneTemp.getSender().getId()))) {
                    if (oneTemp.getTimestamp().equals(String.valueOf(lastTimeStamp))) {
                        lastChats.add(oneTemp);
                    }
                }
            }*//*
        }
        return lastChats;
    }

    public static boolean addMediaStorage(Uri uri, String userId) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        StorageReference mStorage = FirebaseStorage.getInstance().getReference("upload");
        StorageReference filepath = mStorage.child(System.currentTimeMillis() + ".png");
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        mDatabase.child(Constants.USERS_CHILD).child(userId).child(Constants.PHOTO_CHILD).setValue(uri.toString());
                    }
                });
            }
        });
        return true;
    }

    private String encodeText(String msg) {
        return msg.replace(",", "#comma#").replace("{", "#braceopen#").replace("}", "#braceclose#")
            .replace("=", "#equals#");
    }

    private String decodeText(String msg) {
        return msg.replace("#comma#", ",").replace("#braceopen#", "{").replace("#braceclose#", "}")
            .replace("#equals#", "=");
    }*/
}
