package com.example.nextgitter2.api

import com.example.nextgitter2.data.model.DetailUserResponse
import com.example.nextgitter2.data.model.User
import com.example.nextgitter2.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token 53f97eec89bd40e290d9068bfeeec31a1c702df5")
    fun getSearchUsers(
            @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 53f97eec89bd40e290d9068bfeeec31a1c702df5")
    fun getUserDetail(
            @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 53f97eec89bd40e290d9068bfeeec31a1c702df5")
    fun getFollowers(
            @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 53f97eec89bd40e290d9068bfeeec31a1c702df5")
    fun getFollowing(
            @Path("username") username: String
    ): Call<ArrayList<User>>
}