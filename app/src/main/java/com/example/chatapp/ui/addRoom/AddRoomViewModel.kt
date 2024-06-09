package com.example.chatapp.ui.addRoom

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.chatapp.base.BaseViewModel
import com.example.chatapp.database.addRoomToFireStore
import com.example.chatapp.model.Category
import com.example.chatapp.database.model.Room

class AddRoomViewModel : BaseViewModel<Navigator>() {

    val roomName = ObservableField<String>()
    val roomNameError = ObservableField<String>()
    val roomDesc = ObservableField<String>()
    val roomDescError = ObservableField<String>()
    val isAdded = MutableLiveData<Boolean>()

    val categoriesList = Category.getCategoriesList()
    var selectedCategory = categoriesList[0]

    fun createRoom(){
        if (validate()){
            val room = Room(name = roomName.get(),
                desc = roomDesc.get(),
                categoryId = selectedCategory.id)
            showLoading.value = true
            addRoomToFireStore(room,
                onSuccessListener = {
                    showLoading.value = false
                    /* navigate back we can do that when isAdded is true
                    we well finish the AddRoomActivity
                     so it will back to the Home Activity
                     */
                    isAdded.value = true
                    Log.e("RoomS","Room added successfully")
            },
                onFailureListener = {
                    showLoading.value = false
                    messageLiveData.value = it.localizedMessage
            })

        }
    }

    private fun validate(): Boolean {
        if (roomName.get().isNullOrBlank()){
            roomNameError.set("Please enter room name")
            return false
        }else{
            roomNameError.set(null)
        }
        if (roomDesc.get().isNullOrBlank()){
            roomDescError.set("Please room description")
            return false
        }else{
            roomDescError.set(null)
        }
        return true
    }
}