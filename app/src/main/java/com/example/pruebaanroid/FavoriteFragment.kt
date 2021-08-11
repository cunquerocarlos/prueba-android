package com.example.pruebaanroid

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.pruebaanroid.models.Image
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FavoriteFragment : Fragment() {
    private lateinit var adapter: FavoriteAdapter
    private var imagesList: ArrayList<Image> = ArrayList<Image>()
    private var db: DataBase? = null
    private  lateinit var root: View ;
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        root = inflater.inflate(R.layout.fragment_favorite, container, false)
        val rvImages: RecyclerView = root.findViewById(R.id.rvImages);
        val cyEmpty : ConstraintLayout = root.findViewById(R.id.cyEmpty)
        val etSearch: EditText = root.findViewById(R.id.etSearch)
        db = Room.databaseBuilder(
            requireActivity().applicationContext,
            DataBase::class.java, "gallery"
        ) .fallbackToDestructiveMigration().build()
        runBlocking {
            launch {
                if(db!!.imageDao().getAll().isNotEmpty()){
                    cyEmpty.visibility = View.INVISIBLE
                    imagesList = db!!.imageDao().getAll() as ArrayList<Image>
                }
                adapter = FavoriteAdapter(imagesList, activity, object : IOnClickFavorite {
                    override fun onClick(item: Any, image: ImageView, imageProfile : ImageView) {
                        lifecycleScope.launch {
                            db!!.imageDao().delete(item as Image)
                            imagesList.remove(item)
                            adapter.notifyDataSetChanged()
                            Snackbar.make(image, getString(R.string.favorites_remove), Snackbar.LENGTH_SHORT).show()

                            if(imagesList.size == 0)
                                cyEmpty.visibility = View.VISIBLE
                        }
                    }
                })
                rvImages.layoutManager = LinearLayoutManager(activity)
                rvImages.adapter = adapter
            }
        }

        etSearch.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                onQueryTextSubmit(etSearch.text.toString())
            }
            false
        })
        return root
    }

    private fun onQueryTextSubmit(query: String?): Boolean {

            runBlocking {
                launch {
                    if(query?.isNotEmpty() == true) {
                        imagesList.clear()
                        imagesList.addAll(db!!.imageDao().findByName("%$query%") as ArrayList<Image>)
                    }else{
                        imagesList.clear()
                        imagesList.addAll( db!!.imageDao().getAll() as ArrayList<Image>)
                    }
                    hideKeyboard()
                    activity?.runOnUiThread(Runnable {
                        adapter.notifyDataSetChanged()
                    })
                }
            }

        return true
    }
    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(root.windowToken, 0)
    }
}