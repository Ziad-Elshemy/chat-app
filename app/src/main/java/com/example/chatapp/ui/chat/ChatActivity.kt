package com.example.chatapp.ui.chat

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.Constants
import com.example.chatapp.R
import com.example.chatapp.base.BaseActivity
import com.example.chatapp.database.getMessagesRef
import com.example.chatapp.database.model.Message
import com.example.chatapp.databinding.ActivityChatBinding
import com.example.chatapp.database.model.Room
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.Query

class ChatActivity : BaseActivity<ActivityChatBinding,ChatViewModel>(), Navigator {

    lateinit var room: Room
    val adapter = MessagesAdapter()
    lateinit var layoutManager:LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        room = intent.getParcelableExtra(Constants.EXTRA_ROOM)!!
        viewModel.room = room
        viewDataBinding.vm = viewModel
        viewModel.navigator = this
        layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        viewDataBinding.recyclerView.layoutManager = layoutManager
        viewDataBinding.recyclerView.adapter = adapter

        listenForMessageUpdate()
        viewModel.toastLiveDate.observe(this){
            Toast.makeText(this,it,Toast.LENGTH_LONG).show()
        }
    }

    fun listenForMessageUpdate(){
        getMessagesRef(room.id!!)
            .orderBy("dateTime",Query.Direction.ASCENDING)
            .addSnapshotListener{snapshots,exception->

                if (exception!=null){
//                    viewModel.messageLiveData.value = exception.localizedMessage
                    Toast.makeText(this,exception.localizedMessage,Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }else{
                    val newMessagesList = mutableListOf<Message>()
                    for (dc in snapshots!!.documentChanges) {
                        when (dc.type) {
                            DocumentChange.Type.ADDED -> {
                                val message = dc.document.toObject(Message::class.java)
                                newMessagesList.add(message)
                            }
//                            DocumentChange.Type.MODIFIED -> Log.d(TAG, "Modified city: ${dc.document.data}")
//                            DocumentChange.Type.REMOVED -> Log.d(TAG, "Removed city: ${dc.document.data}")
                            else -> {}
                        }
                    }
                    adapter.appendMessages(newMessagesList)
                    viewDataBinding.recyclerView.smoothScrollToPosition(adapter.itemCount)
                }
            }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_chat
    }

    override fun initViewModel(): ChatViewModel {
        return ViewModelProvider(this).get(ChatViewModel::class.java)
    }
}