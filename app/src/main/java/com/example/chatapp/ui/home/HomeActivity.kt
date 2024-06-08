package com.example.chatapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.Constants
import com.example.chatapp.R
import com.example.chatapp.base.BaseActivity
import com.example.chatapp.database.getRoomsFromFireStore
import com.example.chatapp.databinding.ActivityHomeBinding
import com.example.chatapp.model.Room
import com.example.chatapp.ui.chat.ChatActivity
import com.example.chatapp.ui.addRoom.AddRoomActivity
import com.example.chatapp.ui.login.LoginActivity

class HomeActivity : BaseActivity<ActivityHomeBinding,HomeViewModel>() , Navigator {

    val adapter = RoomsAdapter(null)

//    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        var user:String = intent.getStringExtra("user").toString()
//        viewDataBinding.welcomeTxt.text = "Welcome zoz"
        viewDataBinding.vm = viewModel
        viewModel.navigator = this

        initRecyclerView()

    }

    fun initRecyclerView(){
        adapter.onItemClickListener = object :RoomsAdapter.OnItemClickListener{
            override fun onItemClick(pos: Int, room: Room) {
                // if we have any logic here or fetching data we have do it in viewModel them observe it here
//                viewModel.openChat()
                startChatActivity(room)

            }
        }
        viewDataBinding.recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        getRoomsFromFireStore(
            onSuccessListener = {querySnapshots->
                val rooms = querySnapshots.toObjects(Room::class.java)

                // another way

//                val roomsList = mutableListOf<Room?>()
//                querySnapshots.documents.forEach {doc->
//                    val room = doc.toObject(Room::class.java)
//                    roomsList.add(room)
//                }

                adapter.changeData(rooms)
            },
            onFailureListener = {
                Toast.makeText(this,"Error loading rooms",Toast.LENGTH_LONG).show()
            }
        )
    }
    fun startChatActivity(room: Room){
        val intent = Intent(this,ChatActivity::class.java)
        intent.putExtra(Constants.EXTRA_ROOM,room)
        startActivity(intent)
    }

    override fun gotoAddRoom() {
        val intent = Intent(this,AddRoomActivity::class.java)
        startActivity(intent)
    }

    override fun openLoginScreen() {
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }

//    override fun openChatScreen(room:Room) {
//        val intent = Intent(this,ChatActivity::class.java)
//        intent.putExtra(Constants.EXTRA_ROOM,room)
//        startActivity(intent)
//    }

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun initViewModel(): HomeViewModel {
        return ViewModelProvider(this).get(HomeViewModel::class.java)
    }
}