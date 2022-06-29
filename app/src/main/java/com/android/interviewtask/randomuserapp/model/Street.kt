package com.android.interviewtask.randomuserapp.model


import com.google.gson.annotations.SerializedName

data class Street(
    @SerializedName("name")
    val name: String,
    @SerializedName("number")
    val number: Int
)