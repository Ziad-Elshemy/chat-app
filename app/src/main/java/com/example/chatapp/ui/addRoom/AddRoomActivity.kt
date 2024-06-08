package com.example.chatapp.ui.addRoom

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.R
import com.example.chatapp.base.BaseActivity
import com.example.chatapp.databinding.ActivityAddRoomBinding

class AddRoomActivity: BaseActivity<ActivityAddRoomBinding,AddRoomViewModel>(),Navigator {

    lateinit var adapter: CategoriesSpinnerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm = viewModel
        viewModel.navigator = this

        adapter = CategoriesSpinnerAdapter(viewModel.categoriesList)
        viewDataBinding.spinner.adapter = adapter
        viewDataBinding.spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.selectedCategory = viewModel.categoriesList[position]
//                Toast.makeText(applicationContext,viewModel.selectedCategory.name +" is selected",Toast.LENGTH_LONG).show()
                
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        viewModel.isAdded.observe(this)
            {added->
                if (added){
                    showDialog("Room added successfully",
                        posActionName = "Ok",
                        posAction = { dialogInterface, i ->
                            dialogInterface.dismiss()
                        finish()
                        },
                        cancelable = false)
                }
            }

    }
    override fun getLayoutId(): Int {
        return R.layout.activity_add_room
    }

    override fun initViewModel(): AddRoomViewModel {
        return ViewModelProvider(this).get(AddRoomViewModel::class.java)
    }
}