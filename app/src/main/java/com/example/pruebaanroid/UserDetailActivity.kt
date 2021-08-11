package com.example.pruebaanroid

import android.net.Uri
import com.example.pruebaanroid.models.ImageResponse
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.pruebaanroid.models.Image
import com.squareup.picasso.Picasso

class UserDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        val  item: Any
        item = if(intent.extras?.getBoolean("favorite") == true)
            intent.extras?.getSerializable("item") as Image
        else
            intent.extras?.getSerializable("item") as ImageResponse


        val imageUser: ImageView = findViewById(R.id.ivUser)
        val textUser : TextView = findViewById(R.id.tvUser)
        val textCollection: TextView = findViewById(R.id.tvCollection)
        val textLike: TextView = findViewById(R.id.tvLike)
        val textPhoto: TextView = findViewById(R.id.tvPhoto)

        if(item is ImageResponse){
            Picasso.get().load(item.user.profile_image.large).into(imageUser)
            textUser.text = item.user.username
            textCollection.text = item.user.total_collections.toString()
            textLike.text = item.user.total_likes.toString()
            textPhoto.text = item.user.total_photos.toString()
        }else if(item is Image){
            imageUser.setImageURI(Uri.parse(item.uriUser))
            textUser.text = item.username
            textCollection.text = item.collectionsUser.toString()
            textLike.text = item.likesUser.toString()
            textPhoto.text = item.photosUser.toString()
        }


    }
}