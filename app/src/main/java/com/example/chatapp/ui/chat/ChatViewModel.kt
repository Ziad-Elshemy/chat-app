package com.example.chatapp.ui.chat

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.chatapp.DataUtils
import com.example.chatapp.base.BaseViewModel
import com.example.chatapp.database.addMessageToFireStore
import com.example.chatapp.database.model.Message
import com.example.chatapp.database.model.Room
import java.util.Date

class ChatViewModel:BaseViewModel<Navigator>() {

    val messageField = ObservableField<String>()
    val messageFieldError = ObservableField<String>()
    val toastLiveDate = MutableLiveData<String>()
    var room:Room? = null

    fun sendMessage(){
        val message = Message(
            content = messageField.get(),
            roomId = room?.id,
            senderId = DataUtils.user?.id,
            senderName = DataUtils.user?.username,
            dateTime = Date().time

        )

        // save message in firebase

        addMessageToFireStore(message,
            onSuccessListener = {
//                messageField.set("")
                // we have to add mark here
            },
            onFailureListener = {
                toastLiveDate.value = it.localizedMessage
            })
        messageField.set("")

    }

}