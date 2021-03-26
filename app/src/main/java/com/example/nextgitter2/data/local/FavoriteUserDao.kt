package com.example.nextgitter2.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteUserDao {
    @Insert
    suspend fun addFavoriteUser(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM favorite_user ORDER BY username ASC")
    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>>

    @Query("SELECT COUNT(id) FROM favorite_user WHERE id = :id")
    suspend fun checkFavoriteUser(id: Int): Int

    @Query("DELETE FROM favorite_user WHERE id = :id")
    suspend fun deleteFavoriteUser(id: Int): Int
}