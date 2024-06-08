package com.example.chatapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.R
import com.example.chatapp.base.BaseActivity
import com.example.chatapp.databinding.ActivityLoginBinding
import com.example.chatapp.ui.home.HomeActivity
import com.example.chatapp.ui.register.RegisterActivity

class LoginActivity:BaseActivity<ActivityLoginBinding,LoginViewModel>(),Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm = viewModel

        // do not forget to pass this activity to the viewModel navigator
        viewModel.navigator = this

//        viewDataBinding.signUpTxt.setOnClickListener {
//            val intent = Intent(this,RegisterActivity::class.java)
//            startActivity(intent)
//        }

    }

    override fun openHomeScreen() {
        val intent = Intent(this,HomeActivity::class.java)
        intent.putExtra("user",viewModel.email.get().toString().endsWith("@"))
        startActivity(intent)
    }

    override fun openRegisterScreen() {
        val intent = Intent(this,RegisterActivity::class.java)
        startActivity(intent)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initViewModel(): LoginViewModel {
        return ViewModelProvider(this).get(LoginViewModel::class.java)
    }
}