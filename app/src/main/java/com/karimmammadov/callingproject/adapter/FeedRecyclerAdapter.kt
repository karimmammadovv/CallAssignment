package com.karimmammadov.callingproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.karimmammadov.callingproject.databinding.RecyclerRowBinding
import com.karimmammadov.callingproject.model.User

class FeedRecyclerAdapter(private val userList : ArrayList<User>) : RecyclerView.Adapter<FeedRecyclerAdapter.PostHolder>() {

    class PostHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
            holder.binding.recyclerEmailText.text = userList.get(position).email
    }

    override fun getItemCount(): Int {
       return userList.size
    }
}