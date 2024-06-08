package com.example.chatapp.ui.chat

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.Constants
import com.example.chatapp.R
import com.example.chatapp.base.BaseActivity
import com.example.chatapp.databinding.ActivityChatBinding
import com.example.chatapp.model.Room

class ChatActivity : BaseActivity<ActivityChatBinding,ChatViewModel>() {

    lateinit var room: Room
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        room = intent.getParcelableExtra(Constants.EXTRA_ROOM)!!


    }

    override fun getLayoutId(): Int {
        return R.layout.activity_chat
    }

    override fun initViewModel(): ChatViewModel {
        return ViewModelProvider(this).get(ChatViewModel::class.java)
    }
}