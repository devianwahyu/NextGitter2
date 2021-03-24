package com.example.nextgitter2.api

import com.example.nextgitter2.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token 53f97eec89bd40e290d9068bfeeec31a1c702df5")
    fun getSearchUsers(
            @Query("q") query: String
    ): Call<UserResponse>
}