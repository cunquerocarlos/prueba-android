package com.example.pruebaanroid

import com.example.pruebaanroid.models.ImageResponse
import com.example.pruebaanroid.models.Tags
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import com.example.pruebaanroid.models.Image
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat


class ImageDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)

        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val dateParse = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val  item: Any
        item = if(intent.extras?.getBoolean("favorite") == true)
            intent.extras?.getSerializable("item") as Image
        else
            intent.extras?.getSerializable("item") as ImageResponse

        val image: ImageView = findViewById(R.id.ivImage)
        val imageUser: ImageView = findViewById(R.id.ivUser)
        val textUser: TextView = findViewById(R.id.tvUser)
        val textCountry: TextView = findViewById(R.id.tvCountry)
        val textCreated: TextView = findViewById(R.id.tvCreated)
        val textTitle: TextView = findViewById(R.id.tvTitleImage)
        val textLike: TextView = findViewById(R.id.tvLike)
        val textTag : TextView = findViewById(R.id.tvTag)
        val textHeight : TextView = findViewById(R.id.tvHeight)
        val textWidth: TextView = findViewById(R.id.tvWidth)
        val layoutTag: LinearLayout = findViewById(R.id.lyTags)

        if(item is ImageResponse){
            Picasso.get().load(item.urls.full).centerCrop().resize(1920, 1080).onlyScaleDown().into(
                    image
            )
            Picasso.get().load(item.user.profile_image.medium).into(imageUser)

            textUser.text = item.user.username
            textCountry.text = item.user.location
            textCreated.text = getString(R.string.created_at) + dateFormat.format(dateParse.parse(item.created_at))
            textTitle.text = item.alt_description
            textLike.text = item.likes.toString()
            textHeight.text = getString(R.string.heigth) + item.height.toString()
            textWidth.text = getString(R.string.width) + item.width.toString()


            if(!item.tags.isNullOrEmpty()){
                for (tag: Tags in item.tags){
                    val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    params.setMargins(0, 0, 10, 0)
                    val  textTag = TextView(this)
                    textTag.background = getDrawable(R.drawable.bg_tag)
                    textTag.setTextColor(resources?.getColor(R.color.white) ?: 0)
                    textTag.text = tag.title
                    textTag.minWidth = 100
                    textTag.minHeight = 40
                    textTag.setPadding(20, 20, 20, 20)
                    textTag.layoutParams = params
                    textTag.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    layoutTag.addView(textTag)
                }
                if(layoutTag.childCount > 0)
                    textTag.visibility = TextView.VISIBLE
            }
        }else if(item is Image){
            image.setImageURI(Uri.parse(item.uri))
            imageUser.setImageURI(Uri.parse(item.uriUser))
            textUser.text = item.username
            textCountry.text = item.country
            textCreated.text = getString(R.string.created_at) + dateFormat.format(dateParse.parse(item.creationDate))
            textTitle.text = item.altDescription ?: item.description
            textLike.text = item.likes.toString()
            textHeight.text = getString(R.string.heigth) + item.height.toString()
            textWidth.text = getString(R.string.width) + item.width.toString()

        }
        imageUser.setOnClickListener(View.OnClickListener {
                val intent = Intent(this, UserDetailActivity::class.java)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    imageUser,
                    ViewCompat.getTransitionName(imageUser)!!
                )
                if(item is Image)
                    intent.putExtra("favorite" , true)

                intent.putExtra("item", item)
                startActivity(intent, options.toBundle())
        })
    }
}