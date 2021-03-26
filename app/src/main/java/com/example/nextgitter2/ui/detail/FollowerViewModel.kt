package com.example.nextgitter2.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nextgitter2.api.RetrofitClient
import com.example.nextgitter2.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel: ViewModel() {
    private val listFollowers = MutableLiveData<ArrayList<User>>()
    fun setFollower(username: String) {
        RetrofitClient.service
                .getFollowers(username)
                .enqueue(object : Callback<ArrayList<User>> {
                    override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                        if (response.isSuccessful) listFollowers.postValue(response.body())
                    }

                    override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                        t.message?.let { Log.d("Failure", it) }
                    }

                })
    }
    fun getFollower(): LiveData<ArrayList<User>> = listFollowers
}