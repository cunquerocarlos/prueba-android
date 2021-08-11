package com.example.pruebaanroid.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Image(

    @ColumnInfo(name = "alt_description") val altDescription: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "creation_date") val creationDate: String?,
    @ColumnInfo(name = "username") val username: String?,
    @ColumnInfo(name = "country") val country: String?,
    @ColumnInfo(name = "likes") val likes: Int?,
    @ColumnInfo(name = "height") val height: Int?,
    @ColumnInfo(name = "width") val width: Int?,
    @ColumnInfo(name = "uri") val uri: String?,
    @ColumnInfo(name = "uri_user") val uriUser: String?,
    @ColumnInfo(name = "collections_user") val collectionsUser: Int?,
    @ColumnInfo(name = "photos_user") val photosUser: Int?,
    @ColumnInfo(name = "likes_user") val likesUser: Int?,

): Serializable{
    @PrimaryKey(autoGenerate = true) var id: Int = 0

}
