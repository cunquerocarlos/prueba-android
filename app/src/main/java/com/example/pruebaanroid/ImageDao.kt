package com.example.pruebaanroid

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pruebaanroid.models.Image


@Dao
interface ImageDao {
    @Query("SELECT * FROM image")
    suspend fun getAll(): List<Image>

    @Query("SELECT * FROM image WHERE username LIKE  :query OR  alt_description LIKE :query OR description LIKE :query")
    suspend fun findByName(query: String): List<Image>

    @Insert
    suspend fun insertAll(vararg image: Image)

    @Delete
    suspend fun delete(image: Image)
}