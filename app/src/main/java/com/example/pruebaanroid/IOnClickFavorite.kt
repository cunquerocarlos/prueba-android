package com.example.pruebaanroid

import android.widget.ImageView
import com.example.pruebaanroid.models.ImageResponse
import java.util.*

interface IOnClickFavorite {
    fun onClick(item: Any, image: ImageView, imageProfile: ImageView)
}