package com.example.chatapp.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel<N>:ViewModel() {

    var messageLiveData = MutableLiveData<String>()
    var showLoading = MutableLiveData<Boolean>()
    var navigator:N?=null

}