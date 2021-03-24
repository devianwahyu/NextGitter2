package com.example.nextgitter2.data.model

import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("login")
        val username: String,
        val id: String,
        @SerializedName("avatar_url")
        val avatarUrl: String
)
