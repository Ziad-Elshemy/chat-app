package com.example.chatapp.ui.addRoom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.chatapp.R
import com.example.chatapp.model.Category

class CategoriesSpinnerAdapter(val items:List<Category>):BaseAdapter() {
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(index: Int): Any {
        return items[index]
    }

    override fun getItemId(index: Int): Long {
        return 0
    }

    override fun getView(index: Int, view: View?, container: ViewGroup?): View {
        // maybe like onBindViewHolder + the onCreateViewHolder
        var myView = view
        var viewHolder : ViewHolder
        if (myView==null){
            // there is no view so i will create the view
            myView = LayoutInflater.from(container!!.context)
                .inflate(R.layout.item_spinner_category,container,false)
            // we have to find view by id just one time here >> we will do it in the view holder
            viewHolder = ViewHolder(myView)
            // save the view holder inside the whole view
            myView.tag = viewHolder

        }else{
            // there is a view already so i need to do recycling here
            viewHolder = myView.tag as ViewHolder
        }

        val item = items[index]
        viewHolder.title.setText(item.name)
        viewHolder.image.setImageResource(item.imageId!!)

        return myView!!
    }
    class ViewHolder(val view:View?){
        val title:TextView = view!!.findViewById(R.id.spinner_title_id)
        val image:ImageView = view!!.findViewById(R.id.spinner_image_id)
    }
}