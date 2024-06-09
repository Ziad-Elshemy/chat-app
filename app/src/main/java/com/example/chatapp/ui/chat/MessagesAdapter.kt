package com.example.chatapp.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.DataUtils
import com.example.chatapp.R
import com.example.chatapp.database.model.Message
import com.example.chatapp.databinding.ItemMessageReceivedBinding
import com.example.chatapp.databinding.ItemMessageSentBinding

class MessagesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = mutableListOf<Message?>()

    val RECEIVED = 1
    val SENT = 2
    override fun getItemViewType(position: Int): Int {
        val message = items[position]
        if (message?.senderId == DataUtils.user?.id) {
            return SENT
        }
        return RECEIVED
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == RECEIVED) {
            // inflate received item xml
            val itemBinding: ItemMessageReceivedBinding =
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_message_received,
                    parent, false
                )
            return ReceivedMessageViewHolder(itemBinding)
        }
        val itemBinding: ItemMessageSentBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_message_sent,
                parent, false
            )
        return SentMessageViewHolder(itemBinding)
        //inflate sent item xml
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SentMessageViewHolder) {
            // kotlin automatically cast the holder ( an implicit casting )
            holder.bind(items[position])
        } else if (holder is ReceivedMessageViewHolder) {
            holder.bind(items[position])
        }

//        // anther way put her me must cast the holder manually
//        val type = getItemViewType(position)
//        if (type == RECEIVED){
//            holder as ReceivedMessageViewHolder
//
//        }else if (type == SENT){
//            holder as SentMessageViewHolder
//
//        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun appendMessages(messageList:MutableList<Message>){
        items.addAll(messageList)
        notifyItemRangeInserted(items.size+1,messageList.size)
    }

    class SentMessageViewHolder(val viewDataBinding: ItemMessageSentBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        fun bind(message: Message?) {
            viewDataBinding.itemMessage = message
            viewDataBinding.invalidateAll()
        }

    }

    class ReceivedMessageViewHolder(val viewDataBinding: ItemMessageReceivedBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
            fun bind(message: Message?){
                viewDataBinding.itemMessage = message
                viewDataBinding.invalidateAll()
            }
    }

}