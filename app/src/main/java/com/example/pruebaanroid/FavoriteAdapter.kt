package com.example.pruebaanroid



import android.content.Intent

import android.net.Uri
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
import com.example.pruebaanroid.models.Image


class FavoriteAdapter(private val images: List<Image>, private val context: FragmentActivity?, private val listener: IOnClickFavorite) : RecyclerView.Adapter<FavoriteAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        return ImageViewHolder(layoutInflater.inflate(R.layout.item_image, parent, false))
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item: Image = images[position]

        val imgUri: Uri = Uri.parse(item.uri)
        holder.image.setImageURI(imgUri)

        val imgUriUser: Uri = Uri.parse(item.uriUser)
        holder.imageUser.setImageURI(imgUriUser)

        holder.textUser.text = item.username
        holder.textCountry.text = item.country
        holder.textTitle.text = if(item.altDescription?.isEmpty() == true) item.description else item.altDescription
        holder.textLike.text = item.likes.toString()
        holder.ivFavorite.setImageDrawable(context?.resources?.getDrawable(R.drawable.ic_baseline_close_24, context?.theme))

        holder.image.setOnClickListener(View.OnClickListener {
            if (context != null) {
                val intent = Intent(context, ImageDetailActivity::class.java)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        context,
                        holder.image,
                        ViewCompat.getTransitionName(holder.image)!!
                )
                intent.putExtra("favorite", true)
                intent.putExtra("item", item)

                context.startActivity(intent, options.toBundle())
            }

        })

        holder.imageUser.setOnClickListener(View.OnClickListener {
            if (context != null) {
                val intent = Intent(context, UserDetailActivity::class.java)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        context,
                        holder.imageUser,
                        ViewCompat.getTransitionName(holder.imageUser)!!
                )
                intent.putExtra("item", item)
                intent.putExtra("favorite", true)
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
        val ivFavorite: ImageView = view.findViewById(R.id.ivFavorite)
    }
}
