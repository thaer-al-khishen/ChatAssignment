package com.example.chatassignment.Adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chatassignment.Models.Message
import com.example.chatassignment.R
import com.example.chatassignment.databinding.MessageBinding
import java.text.SimpleDateFormat
import java.util.*

class MessageAdapter(private val messages: List<Message>?) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.message,
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int {
        //Recyclerview size
        return messages?.size!!
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        //Have sender message always to the right with the 2 resulting echoed messages on the left
        if (position % 3 == 0) {
            val params = holder.binding.parentCard.layoutParams as ConstraintLayout.LayoutParams
            params.startToStart = ConstraintLayout.LayoutParams.UNSET
            params.endToEnd = holder.binding.ourConstraintLayout.id
            params.marginEnd = 50
            holder.binding.parentCard.requestLayout()
            holder.binding.parentCard.setCardBackgroundColor(Color.parseColor("#add8e6"))
        } else {
            val params = holder.binding.parentCard.layoutParams as ConstraintLayout.LayoutParams
            params.marginStart = 50
            holder.binding.parentCard.requestLayout()
            holder.binding.parentCard.setCardBackgroundColor(Color.parseColor("#90EE90"))
        }
        //Populate message textview with the actual message
        holder.binding.messageTextView.text = messages?.get(position)?.message

        //Format date
        val sdf = SimpleDateFormat("hh:mm")
        val date: Date? = messages?.get(position)?.date
        val newDate: String = sdf.format(date)

        //Populate dateTextView with the time of the message
        holder.binding.dateTextView.text = newDate
    }

    class MessageViewHolder(val binding: MessageBinding) :
        RecyclerView.ViewHolder(binding.root)

}
