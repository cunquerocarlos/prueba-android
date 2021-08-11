package com.example.pruebaanroid

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pruebaanroid.models.Image

@Database(entities = arrayOf(Image::class), version = 12, exportSchema = false)
abstract class DataBase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
}