package com.example.chatapp.model

import com.example.chatapp.R

data class Category(
    val id:String?=null,
    val name:String?=null,
    val imageId:Int?=null,
) {
    companion object {

        const val MUSIC = "music"
        const val MOVIES = "movies"
        const val SPORTS = "sports"

        fun getCategoryById(catId: String):Category{
            when(catId){
                MUSIC-> {
                    return Category(MUSIC,"Music", R.drawable.music)
                }
                MOVIES->{
                    return Category(MOVIES,"Movies", R.drawable.movies)
                }
                else->{
                    return  Category(SPORTS,"Sports", R.drawable.sports)
                }
            }

        }

        fun getCategoriesList():List<Category>{
            return listOf(
                getCategoryById(MUSIC),
                getCategoryById(MOVIES),
                getCategoryById(SPORTS),
            )
        }
    }

}