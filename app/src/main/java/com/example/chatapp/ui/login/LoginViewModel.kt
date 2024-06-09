package com.example.chatapp.ui.login

import android.util.Log
import androidx.databinding.ObservableField
import com.example.chatapp.DataUtils
import com.example.chatapp.base.BaseViewModel
import com.example.chatapp.database.signIn
import com.example.chatapp.database.model.AppUser
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel:BaseViewModel<Navigator>() {

    var email = ObservableField<String>()
    var emailError = ObservableField<String>()
    var password = ObservableField<String>()
    var passwordError = ObservableField<String>()


    val auth:FirebaseAuth = Firebase.auth
    fun loginAccount(){
        if (validate()){
            // call firebase to login
            loginAccountToFirebase()
        }
    }

    private fun loginAccountToFirebase() {
        showLoading.value = true
        auth.signInWithEmailAndPassword(email.get()!!,password.get()!!).addOnCompleteListener {
            task->
            showLoading.value = false
            if (!task.isSuccessful){
                // show error
                messageLiveData.value = "Login Error: " + task.exception?.localizedMessage
                Log.e("firebase: ", task.exception?.localizedMessage.toString())
            }else{
//                messageLiveData.value = "Login Successful"
                Log.e("firebase: ", "Success Login")
//                navigator?.openHomeScreen()
                getUserFromFireStore(task.result.user?.uid)
            }
        }

    }

    private fun getUserFromFireStore(uid: String?) {
        showLoading.value = true
        signIn(uid!!, OnSuccessListener
        {docSnapshot->
            showLoading.value = false
            val user = docSnapshot.toObject(AppUser::class.java)
            if (user==null){
                messageLiveData.value = "Invalid Email or Password"
                return@OnSuccessListener
            }
            DataUtils.user = user
            navigator?.openHomeScreen()
            }
        ) {
            showLoading.value = false
            messageLiveData.value = it.localizedMessage
        }
    }

    fun registerNewAccount(){
        navigator?.openRegisterScreen()
    }

    private fun validate():Boolean{
        var valid:Boolean = true
        if (email.get().isNullOrBlank()){
            emailError.set("Please enter you email")
            valid = false
        }
        else{
            emailError.set(null)
        }
        if (password.get().isNullOrBlank()){
            passwordError.set("Please enter you password")
            valid = false
        }
        else{
            passwordError.set(null)
        }
        return valid
    }



}