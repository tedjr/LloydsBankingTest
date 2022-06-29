package com.android.interviewtask.randomuserapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.interviewtask.randomuserapp.databinding.LayUseritemBinding
import com.android.interviewtask.randomuserapp.model.UserItem
import com.android.interviewtask.randomuserapp.utils.loadImagefromUrl

class UserListAdapter(
    private val listofUsers:MutableList<UserItem> = mutableListOf()
): RecyclerView.Adapter<UserViewHolder>() {

    var onItemClick: ((UserItem) -> Unit)? = null

    fun loadData(listOfAlerts: MutableList<UserItem>){
        this.listofUsers.clear()
        this.listofUsers.addAll(listOfAlerts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(LayUseritemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.setAlertData(listofUsers[position])
        holder.itemView.rootView.setOnClickListener {
            onItemClick?.invoke(listofUsers[position])
        }
    }

    override fun getItemCount(): Int {
        return listofUsers.size
    }
}

class UserViewHolder(itemView: LayUseritemBinding) : RecyclerView.ViewHolder(itemView.root) {
    val binding=itemView
    fun setAlertData(userItem: UserItem) {
        binding.userName.text="${userItem.name.title}. ${userItem.name.first} ${userItem.name.last}"
        binding.userImage.loadImagefromUrl(userItem.picture.medium)
        binding.userEmail.text=userItem.email
        binding.userCountry.text=userItem.location.country
        binding.userTelephone.text=userItem.phone
    }
}