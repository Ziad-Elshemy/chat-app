package com.example.chatapp.ui.register

import android.util.Log
import androidx.databinding.ObservableField
import com.example.chatapp.DataUtils
import com.example.chatapp.base.BaseViewModel
import com.example.chatapp.database.addUserToFireStore
import com.example.chatapp.database.model.AppUser
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterViewModel: BaseViewModel<Navigator>() {
    var firstName = ObservableField<String>()
    var firstNameError = ObservableField<String>()
    var lastName = ObservableField<String>()
    var lastNameError = ObservableField<String>()
    var userName = ObservableField<String>()
    var userNameError = ObservableField<String>()
    var email = ObservableField<String>()
    var emailError = ObservableField<String>()
    var password = ObservableField<String>()
    var passwordError = ObservableField<String>()

//    var messageLiveData = MutableLiveData<String>()
//    var showLoading = MutableLiveData<Boolean>()

    val auth:FirebaseAuth = Firebase.auth
    fun createAccount(){
        if(validate()){
            // call firebase here
            addAccountToFirebase()
        }

    }


    private fun addAccountToFirebase() {
        showLoading.value=true
        auth.createUserWithEmailAndPassword(email.get()!!,password.get()!!)
            .addOnCompleteListener {
                task->
                showLoading.value=false
                if (!task.isSuccessful){
                    // show error message
                    Log.e("firebase: ", "Authentication Failed: " + task.exception?.localizedMessage )
                    messageLiveData.value = "Authentication Failed: " + task.exception?.localizedMessage
                }
                else{
//                    showLoading.value=false
                    createFirestoreUser(task.result.user?.uid)
                    // show success message
                    messageLiveData.value="Successful Registration"
                    // redirect to the user home page
//                    navigator?.openLoginScreen()
                    Log.e("firebase: ", "Success Registration")
                }
            }
    }



    private fun createFirestoreUser(uid: String?) {
        showLoading.value = true
        val user = AppUser (uid,userName.get(),firstName.get(),lastName.get(),email.get())
        addUserToFireStore(user,
            onSuccessListener = {
                showLoading.value = false
                DataUtils.user = user
                navigator?.openHomeScreen()
        },
            onFailureListener = {
                showLoading.value = false
                messageLiveData.value = it.localizedMessage
            })


    }

    private fun validate(): Boolean {
        var valid:Boolean = true
        if (firstName.get().isNullOrBlank()){
            //set error message here
            firstNameError.set("Please enter your first name")
            valid = false
        }else {
            firstNameError.set(null)
        }
        if (lastName.get().isNullOrBlank()){
            lastNameError.set("Please enter your last name")
            valid=false
        }else{
            lastNameError.set(null)
        }
        if (userName.get().isNullOrBlank()){
            userNameError.set("Please enter your username")
            valid=false
        }else{
            userNameError.set(null)
        }
        if (email.get().isNullOrBlank()){
            emailError.set("Please enter your email")
            valid=false
        }else{
            emailError.set(null)
        }
        if (password.get().isNullOrBlank()){
            passwordError.set("Please enter your password")
            valid=false
        }else{
            passwordError.set(null)
        }
        return valid
    }


}