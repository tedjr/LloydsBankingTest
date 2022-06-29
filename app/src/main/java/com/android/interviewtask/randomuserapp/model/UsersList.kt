package com.android.interviewtask.randomuserapp.model


import com.google.gson.annotations.SerializedName

data class UsersList(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val userItems: MutableList<UserItem>
)