package com.example.chattest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val list: ArrayList<Message> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            1 -> ViewHolderMe(inflater.inflate(R.layout.item_message_me, parent, false))
            else -> ViewHolderYou(inflater.inflate(R.layout.item_message_you, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == 1) {
            onBindViewHolderMe(holder as ViewHolderMe, position)
        } else  {
            onBindViewHolderYou(holder as ViewHolderYou, position)
        }
    }

    fun addList(message: Message) {
        list.add(message)
        notifyDataSetChanged()
    }

    private fun onBindViewHolderMe(holder: ViewHolderMe, position: Int) {
        holder.message.text = list[position].message
    }

    private fun onBindViewHolderYou(holder: ViewHolderYou, position: Int) {
        holder.message.text = list[position].message
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].name == "me") {
            1
        } else {
            2
        }
    }

    inner class ViewHolderYou(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val message: AppCompatTextView = itemView.findViewById(R.id.message)
    }

    inner class ViewHolderMe(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val message: AppCompatTextView = itemView.findViewById(R.id.message)
    }
}