package com.example.nextgitter2.data.local

import androidx.lifecycle.LiveData

class FavoriteUserRepository(private val favoriteUserDao: FavoriteUserDao) {
    val getAllFavoriteUsers: LiveData<List<FavoriteUser>> = favoriteUserDao.getAllFavoriteUsers()

    suspend fun addFavoriteUser(favoriteUser: FavoriteUser) = favoriteUserDao.addFavoriteUser(favoriteUser)

    suspend fun deleteFavoriteUser(id: Int) = favoriteUserDao.deleteFavoriteUser(id)

    suspend fun checkFavoriteUser(id: Int) = favoriteUserDao.checkFavoriteUser(id)
}