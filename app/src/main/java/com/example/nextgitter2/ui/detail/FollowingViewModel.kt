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

class FollowingViewModel: ViewModel() {
    private val listFollowing = MutableLiveData<ArrayList<User>>()
    fun setFollowing(username: String) {
        RetrofitClient.service
                .getFollowing(username)
                .enqueue(object : Callback<ArrayList<User>> {
                    override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                        if (response.isSuccessful) listFollowing.postValue(response.body())
                    }

                    override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                        t.message?.let { Log.d("Failure", it) }
                    }

                })
    }
    fun getFollowing(): LiveData<ArrayList<User>> = listFollowing
}