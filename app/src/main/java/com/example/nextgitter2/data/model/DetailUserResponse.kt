package com.example.nextgitter2.data.model

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(
        @SerializedName("login")
        val username: String,
        @SerializedName("avatar_url")
        val avatarUrl: String,
        @SerializedName("followers_url")
        val followersUrl: String,
        @SerializedName("following_url")
        val followingUrl: String,
        @SerializedName("public_repos")
        val repository: Int,
        val id: String,
        val name: String,
        val company: String,
        val location: String,
        val followers: Int,
        val following: Int
)
