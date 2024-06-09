package com.example.chatapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.chatapp.DataUtils
import com.example.chatapp.R
import com.example.chatapp.database.model.AppUser
import com.example.chatapp.database.signIn
import com.example.chatapp.ui.home.HomeActivity
import com.example.chatapp.ui.login.LoginActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper())
            .postDelayed(Runnable {
                checkLoggedInUser()
            }, 2000)

    }

    fun checkLoggedInUser() {
        val firebaseUser = Firebase.auth.currentUser
        if (firebaseUser == null) {
            startLoginActivity()
        } else {
            // retrieve user from FireStore
            signIn(firebaseUser.uid,
                onSuccessListener = {
                    val user = it.toObject(AppUser::class.java)
                    DataUtils.user = user
                    startHomeActivity()
//                    startLoginActivity()
                },
                onFailureListener = {
                    Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
                    startLoginActivity()
                })

        }
    }

    private fun startHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun startLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}