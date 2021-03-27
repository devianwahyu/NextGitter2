package com.example.nextgitter2.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.nextgitter2.data.local.FavoriteUser
import com.example.nextgitter2.data.local.FavoriteUserDatabase
import com.example.nextgitter2.data.local.FavoriteUserRepository

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private val repository: FavoriteUserRepository

    init {
        val favoriteUserDao = FavoriteUserDatabase.getDatabase(application).favoriteUserDao()
        repository = FavoriteUserRepository(favoriteUserDao)
    }

    fun getFavoriteUsers(): LiveData<List<FavoriteUser>> = repository.getAllFavoriteUsers
}