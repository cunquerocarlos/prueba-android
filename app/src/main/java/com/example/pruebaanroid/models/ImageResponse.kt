package com.example.pruebaanroid.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/*
Copyright (c) 2021 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class ImageResponse  (
    @SerializedName("id") var id : String,
    @SerializedName("created_at") var created_at : String,
    @SerializedName("updated_at") var updated_at : String,
    @SerializedName("promoted_at") var promoted_at : String,
    @SerializedName("width") var width : Int,
    @SerializedName("height") var height : Int,
    @SerializedName("color") var color : String,
    @SerializedName("blur_hash") var blur_hash : String,
    @SerializedName("description") var description : String,
    @SerializedName("alt_description") var alt_description : String,
    @SerializedName("urls") var urls : Urls,
    @SerializedName("links") var links : Links,
    @SerializedName("categories") var categories : List<String>,
    @SerializedName("likes") var likes : Int,
    @SerializedName("liked_by_user") var liked_by_user : Boolean,
    @SerializedName("current_user_collections") var current_user_collections : List<String>,
    @SerializedName("sponsorship") var sponsorship : Sponsorship,
    @SerializedName("user") var user : User,
    @SerializedName("tags") var tags : List<Tags>
): Serializable{

}