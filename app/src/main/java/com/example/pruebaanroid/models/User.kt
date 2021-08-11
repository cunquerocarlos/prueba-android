package com.example.pruebaanroid.models

import com.example.pruebaanroid.models.Links
import com.example.pruebaanroid.models.Profile_image
import com.example.pruebaanroid.models.Social
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/*
Copyright (c) 2021 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class User (

    @SerializedName("id") val id : String,
    @SerializedName("updated_at") val updated_at : String,
    @SerializedName("username") val username : String,
    @SerializedName("name") val name : String,
    @SerializedName("first_name") val first_name : String,
    @SerializedName("last_name") val last_name : String,
    @SerializedName("twitter_username") val twitter_username : String,
    @SerializedName("portfolio_url") val portfolio_url : String,
    @SerializedName("bio") val bio : String,
    @SerializedName("location") val location : String,
    @SerializedName("links") val links : Links,
    @SerializedName("profile_image") val profile_image : Profile_image,
    @SerializedName("instagram_username") val instagram_username : String,
    @SerializedName("total_collections") val total_collections : Int,
    @SerializedName("total_likes") val total_likes : Int,
    @SerializedName("total_photos") val total_photos : Int,
    @SerializedName("accepted_tos") val accepted_tos : Boolean,
    @SerializedName("for_hire") val for_hire : Boolean,
    @SerializedName("social") val social : Social
) : Serializable