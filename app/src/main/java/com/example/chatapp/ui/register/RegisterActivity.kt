package com.example.chatapp.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.R
import com.example.chatapp.base.BaseActivity
import com.example.chatapp.databinding.ActivityRegisterBinding
import com.example.chatapp.ui.home.HomeActivity
import com.example.chatapp.ui.login.LoginActivity


class RegisterActivity : BaseActivity<ActivityRegisterBinding,RegisterViewModel>(), Navigator {


//    lateinit var viewDataBinding: ActivityRegisterBinding
//    lateinit var viewModel: RegisterViewModel

//    lateinit var createBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_register)

//        viewDataBinding.createAccountBtn.setOnClickListener {
//            viewModel.createAccount()
//        }
        viewDataBinding.vm = viewModel

        viewModel.navigator = this

//        viewModel.messageLiveData.observe(this){
//            if (it.equals("Successful Registration")){
//                val intent = Intent(this,LoginActivity::class.java)
//                startActivity(intent)
//            }
//        }


    }

    override fun openHomeScreen() {
        val intent = Intent(this,HomeActivity::class.java)
        startActivity(intent)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun initViewModel(): RegisterViewModel {
        return ViewModelProvider(this).get(RegisterViewModel::class.java)
    }
}