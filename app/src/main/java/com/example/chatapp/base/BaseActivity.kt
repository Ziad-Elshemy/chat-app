package com.example.chatapp.base

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.ui.register.RegisterActivity
import com.example.chatapp.ui.register.RegisterViewModel

abstract class BaseActivity<DB:ViewDataBinding,VM:BaseViewModel<*>> : AppCompatActivity() {

    lateinit var viewDataBinding: DB
    lateinit var viewModel:VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        viewModel = initViewModel()

        subscribeToLiveData()

    }

    fun subscribeToLiveData(){
        
        viewModel.messageLiveData.observe(this) {message->
            showDialog(message,"ok")
        }
        viewModel.showLoading.observe(this){
            if (it)
                showLoading()
            else
                hideLoading()
        }
    }

    val defAction = DialogInterface.OnClickListener { dialog, p1 ->
        dialog?.dismiss()
    }
    var alertDialog:AlertDialog?=null
    fun showDialog(
        message:String,
        posActionName:String?=null, posAction: DialogInterface.OnClickListener?=null,
        negActionName:String?=null, negAction: DialogInterface.OnClickListener?=null,
        cancelable:Boolean=true){
        val builder = AlertDialog.Builder(this)
            .setMessage(message)
        if(posActionName!=null)
            builder.setPositiveButton(posActionName, posAction ?: defAction)
        if (negActionName!=null)
            builder.setNegativeButton(negActionName,negAction ?: defAction)
        builder.setCancelable(cancelable)
        alertDialog = builder.show()
    }

    fun hideAlerDialog(){
        alertDialog?.dismiss()
        alertDialog = null
    }

    var progressDialog:ProgressDialog?=null
    fun showLoading(){
        progressDialog = ProgressDialog(this)
        progressDialog?.setMessage("Loading...")
        progressDialog?.setCancelable(false)
        progressDialog?.show()
    }
    fun hideLoading(){
        progressDialog?.dismiss()
        progressDialog = null
    }

    abstract fun getLayoutId():Int
    abstract fun initViewModel():VM

}