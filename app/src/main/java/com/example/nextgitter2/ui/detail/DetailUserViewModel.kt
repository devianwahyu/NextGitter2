package com.example.nextgitter2.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nextgitter2.api.RetrofitClient
import com.example.nextgitter2.data.local.FavoriteUser
import com.example.nextgitter2.data.local.FavoriteUserDatabase
import com.example.nextgitter2.data.local.FavoriteUserRepository
import com.example.nextgitter2.data.model.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application): AndroidViewModel(application) {

    val user = MutableLiveData<DetailUserResponse>()
    private val repository: FavoriteUserRepository

    init {
        val favoriteUserDao = FavoriteUserDatabase.getDatabase(application).favoriteUserDao()
        repository = FavoriteUserRepository(favoriteUserDao)
    }

    fun addFavoriteUser(favoriteUser: FavoriteUser) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.addFavoriteUser(favoriteUser)
        }
    }

    fun deleteFavoriteUser(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteFavoriteUser(id)
        }
    }

    suspend fun checkFavoriteUser(id: Int) = repository.checkFavoriteUser(id)

    fun setUserDetail(username: String) {
        RetrofitClient.service
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) user.postValue(response.body())
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }
            })
    }

    fun getUserDetail(): MutableLiveData<DetailUserResponse> = user
}