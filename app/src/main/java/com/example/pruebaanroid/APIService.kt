package com.example.pruebaanroid

import com.example.pruebaanroid.models.ImageResponse
import com.example.pruebaanroid.models.Search
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url


interface APIService {
    @GET
    fun getImages(@Url url:String): Call<List<ImageResponse>>

    @GET
    fun getImagesSerch(@Url url:String): Call<Search>
}