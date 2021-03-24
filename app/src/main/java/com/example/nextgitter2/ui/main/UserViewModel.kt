package com.example.nextgitter2.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nextgitter2.api.RetrofitClient
import com.example.nextgitter2.data.model.User
import com.example.nextgitter2.data.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel: ViewModel() {
    val listUsers = MutableLiveData<ArrayList<User>>()
    fun setSearchUser(query: String) {
        RetrofitClient.service
                .getSearchUsers(query)
                .enqueue(object : Callback<UserResponse> {
                    override fun onResponse(
                            call: Call<UserResponse>,
                            response: Response<UserResponse>
                    ) {
                        if (response.isSuccessful) listUsers.postValue(response.body()?.items)
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        t.message?.let { Log.d("Failure", it) }
                    }
                })
    }

    fun getSearchUser(): LiveData<ArrayList<User>> = listUsers
}