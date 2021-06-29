package com.harshit.bookhub.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.harshit.bookhub.R
import com.harshit.bookhub.adapter.FavouriteRecyclerAdaptor
import com.harshit.bookhub.database.BookDatabase
import com.harshit.bookhub.database.BookEntity
import com.harshit.bookhub.model.Book
import com.harshit.bookhub.util.ConnectionManager
import org.json.JSONException


class FavouritesFragment : Fragment() {

    lateinit var recyclerFavourite : RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter : FavouriteRecyclerAdaptor
    lateinit var progressLayout : RelativeLayout
    lateinit var progressBar : ProgressBar
    var dbBookList = listOf<BookEntity>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favourites, container, false)

        recyclerFavourite = view.findViewById(R.id.recyclerFavourite)
        progressLayout = view.findViewById(R.id.progressLayout)
        progressBar = view.findViewById(R.id.progressBar)
        // progressLayout.visibility = View.VISIBLE

        layoutManager = GridLayoutManager(activity as Context,2)
        dbBookList = RetrieveFavourites(activity as Context).execute().get()

        if(activity!= null){
            progressLayout.visibility = View.GONE
            recyclerAdapter = FavouriteRecyclerAdaptor(activity as Context, dbBookList)
            recyclerFavourite.adapter = recyclerAdapter
            recyclerFavourite.layoutManager = layoutManager
        }

        return view

}

    class RetrieveFavourites(val context: Context) : AsyncTask<Void,Void,List<BookEntity>>(){
        override fun doInBackground(vararg p0: Void?): List<BookEntity> {
            val db = Room.databaseBuilder(context,BookDatabase::class.java,"books-db").build()

            return db.bookDao().getAllBooks()
        }

    }

}