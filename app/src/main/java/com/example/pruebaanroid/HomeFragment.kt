package com.example.pruebaanroid


import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Context.MODE_PRIVATE
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.pruebaanroid.models.Image
import com.example.pruebaanroid.models.ImageResponse
import com.example.pruebaanroid.models.Search
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    private lateinit var adapter: ImageAdapter
    private val imagesList: ArrayList<ImageResponse> = ArrayList<ImageResponse>()
    private lateinit var root: View
    private var loading: Boolean = true
    private var page: Int  = 0
    private var db: DataBase? = null


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_home, container, false)
        val rvImages: RecyclerView = root.findViewById(R.id.rvImages);
        val svImage: EditText = root.findViewById(R.id.svImages);
        val layoutManager = LinearLayoutManager(activity)

        db = Room.databaseBuilder(
                requireActivity().applicationContext,
                DataBase::class.java, "gallery"
        ) .fallbackToDestructiveMigration().build()


        adapter = ImageAdapter(imagesList, activity, object : IOnClickFavorite {
            override fun onClick(item: Any, image: ImageView, imageProfile: ImageView) {
                insertImage(image, imageProfile, item as ImageResponse)
            }
        })
        rvImages.layoutManager = layoutManager
        rvImages.adapter = adapter
        randomPhotos()

        var  pastVisiblesItems: Int
        var visibleItemCount: Int
        var  totalItemCount : Int


        svImage.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                onQueryTextSubmit(svImage.text.toString())
            }
            false
        })


        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                 super.onScrollStateChanged(recyclerView, newState)

                if (newState > 0) {
                    visibleItemCount = layoutManager.childCount;
                    totalItemCount = layoutManager.itemCount;
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            if(svImage.text.toString().isNotEmpty())
                                searchByName(svImage.text.toString(), false)
                            else
                                randomPhotos()
                            loading = true;
                        }
                    }
                }

            }
        }
        rvImages.addOnScrollListener(scrollListener)
        return root
    }

    private fun getRetrofit(): APIService {
        return Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(APIService::class.java)
    }

    private fun randomPhotos(){
        page++

        val call = getRetrofit().getImages("photos/?page=${page}&client_id=brwOM8-CHUizHVrGhqB4O0YUBcWVaBsVIcU5IMnyNc0")
        call.enqueue(object : Callback<List<ImageResponse>> {
            override fun onResponse(
                    call: Call<List<ImageResponse>>?,
                    response: Response<List<ImageResponse>>?
            ) {
                if (response?.body() != null) {
                    imagesList.addAll(response.body()!!)
                    activity?.runOnUiThread(Runnable {
                        adapter.notifyDataSetChanged()
                    })
                }
            }
            override fun onFailure(call: Call<List<ImageResponse>>?, t: Throwable?) {
                Snackbar.make(root, getString(R.string.error_request), Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    private fun searchByName(query: String, clearList: Boolean){
        page++
        hideKeyboard()

        val call = getRetrofit().getImagesSerch("search/photos/?query=$query&page=${page}&client_id=brwOM8-CHUizHVrGhqB4O0YUBcWVaBsVIcU5IMnyNc0")

        call.enqueue(object : Callback<Search> {
            override fun onResponse(call: Call<Search>?, response: Response<Search>?) {
                if (response?.body() != null) {
                    if (clearList)
                        imagesList.clear()
                    imagesList.addAll(response.body()?.results!!)
                    activity?.runOnUiThread(Runnable {
                        adapter.notifyDataSetChanged()
                    })
                }
            }
            override fun onFailure(call: Call<Search>?, t: Throwable?) {
                Snackbar.make(root, getString(R.string.error_request), Snackbar.LENGTH_SHORT).show()
            }
        })

    }

    private fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            page = 0
            searchByName(query.toLowerCase(Locale.getDefault()), true)
        }
        return true
    }
    private fun hideKeyboard() {
        val imm = activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(root.windowToken, 0)
    }
    fun insertImage(img: ImageView, imgProfile: ImageView, item: ImageResponse) {
            if(img.drawable != null && imgProfile.drawable != null){
                val bitmap = (img.drawable as BitmapDrawable).bitmap
                val bitmapUser = (imgProfile.drawable as BitmapDrawable).bitmap
                val wrapper = ContextWrapper(requireActivity().applicationContext)
                var file = wrapper.getDir("Images", MODE_PRIVATE)
                var fileUser = wrapper.getDir("Images", MODE_PRIVATE)
                file = File(file, UUID.randomUUID().toString() + ".jpg")
                fileUser = File(fileUser, UUID.randomUUID().toString() + ".jpg")
                try {
                    var stream: OutputStream? = null
                    var streamUser: OutputStream? = null
                    stream = FileOutputStream(file)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                    stream.flush()
                    stream.close()
                    val savedImage: Uri = Uri.parse(file.absolutePath)

                    streamUser = FileOutputStream(fileUser)
                    bitmapUser.compress(Bitmap.CompressFormat.JPEG, 100, streamUser)
                    streamUser.flush()
                    streamUser.close()
                    val savedImageUser: Uri = Uri.parse(fileUser.absolutePath)
                    val dao = db?.imageDao()
                    val image = Image(
                            item.alt_description,
                            item.description,
                            item.created_at,
                            item.user.username,
                            item.user.location,
                            item.likes,
                            item.height,
                            item.width,
                            savedImage.toString(),
                            savedImageUser.toString(),
                            item.user.total_collections,
                            item.user.total_photos,
                            item.user.total_likes
                    )
                    runBlocking {
                        launch {
                            dao?.insertAll(image)
                            Snackbar.make(img, getString(R.string.favorites_add), Snackbar.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: IOException)
                {
                    e.printStackTrace()
                    Snackbar.make(img, getString(R.string.favorites_add_error), Snackbar.LENGTH_SHORT).show()
                }
            }else {
                Snackbar.make(img, getString(R.string.favorites_wait_image), Snackbar.LENGTH_SHORT).show()
            }
    }


}


