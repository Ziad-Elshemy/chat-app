package com.example.chatapp

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize


class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
//        FirebaseApp.initializeApp(this)
        Firebase.initialize(this)
    }
}