package com.example.chatapp.database.model

import android.os.Parcelable
import com.example.chatapp.model.Category
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room (
    var id:String?=null,
    val name:String?=null,
    val desc:String?=null,
    val categoryId:String?=null,
):Parcelable{
    fun getCategoryImageId():Int?{
        return Category.getCategoryById(categoryId ?: Category.SPORTS).imageId
    }
    companion object {
        const val COLLECTION_NAME = "Rooms"
    }
}