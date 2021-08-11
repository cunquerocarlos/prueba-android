package com.example.pruebaanroid

import com.example.pruebaanroid.models.ImageResponse
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class ImageAdapter(private val images: List<ImageResponse>, private val  context: FragmentActivity?, private val listener: IOnClickFavorite) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        return ImageViewHolder(layoutInflater.inflate(R.layout.item_image, parent, false))
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item: ImageResponse = images[position]
        Picasso.get().load(item.urls.full).centerCrop().resize(1920, 1080).onlyScaleDown().into(
            holder.image
        );
        Picasso.get().load(item.user.profile_image.medium).into(holder.imageUser);
        holder.textUser.text = item.user.username
        holder.textCountry.text = item.user.location
        holder.textTitle.text = if(item.alt_description == null) item.description else item.alt_description
        holder.textLike.text = item.likes.toString()

        holder.image.setOnClickListener(View.OnClickListener {
            if(context != null) {
                val intent = Intent(context, ImageDetailActivity::class.java)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    context,
                    holder.image,
                    ViewCompat.getTransitionName(holder.image)!!
                )
                intent.putExtra("item", item)

                context.startActivity(intent, options.toBundle())
            }

        })

        holder.imageUser.setOnClickListener(View.OnClickListener {
            if(context != null) {
                val intent = Intent(context, UserDetailActivity::class.java)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    context,
                    holder.imageUser,
                    ViewCompat.getTransitionName(holder.imageUser)!!
                )
                intent.putExtra("item", item)

                context.startActivity(intent, options.toBundle())
            }

        })
        holder.favorite.setOnClickListener(View.OnClickListener {
            listener.onClick(item, holder.image, holder.imageUser)
        })
    }

    class ImageViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val image:ImageView = view.findViewById(R.id.ivImage);
        val imageUser: ImageView = view.findViewById(R.id.ivUser);
        val textUser: TextView = view.findViewById(R.id.tvUser);
        val textCountry: TextView = view.findViewById(R.id.tvCountry);
        val textTitle : TextView = view.findViewById(R.id.tvTitleImage);
        val textLike : TextView = view.findViewById(R.id.tvLike)
        val favorite: CardView = view.findViewById(R.id.cvFavorite)
    }
}
