package com.example.moviesapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.moviesapp.API.ApiClient
import com.example.moviesapp.API.ApiService
import com.example.moviesapp.Adapter.ActorsListAdapter
import com.example.moviesapp.Adapter.CategoryEachMovieAdapter
import com.example.moviesapp.Model.MovieItem
import com.google.gson.Gson
import kotlinx.coroutines.launch

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var titleTxt: TextView
    private lateinit var movieRateTxt: TextView
    private lateinit var movieTimeTxt: TextView
    private lateinit var movieSummaryInfo: TextView
    private lateinit var movieActorsInfo: TextView
    private lateinit var pic2: ImageView
    private lateinit var backImg: ImageView
    private lateinit var adapterActorList: ActorsListAdapter
    private lateinit var adapterCategory: CategoryEachMovieAdapter
    private lateinit var recyclerViewActors: RecyclerView
    private lateinit var recyclerViewCategory: RecyclerView
    private lateinit var scrollView: NestedScrollView
    private var idFilm: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        idFilm = intent.getIntExtra("id", 0)
        initView()
        sendRequest()
    }

    private fun sendRequest() {
        progressBar.visibility = View.VISIBLE
        scrollView.visibility = View.GONE

        // Using Coroutines to make a network request
        lifecycleScope.launch {
            try {
                val movieDetails = ApiClient.api.getMovieDetails(idFilm)
                updateUI(movieDetails)
            } catch (e: Exception) {
                progressBar.visibility = View.GONE
                Log.e("MovieDetailActivity", "Error: ${e.message}")
            }
        }
    }

    private fun updateUI(item: MovieItem) {
        progressBar.visibility = View.GONE
        scrollView.visibility = View.VISIBLE

        Glide.with(this)
            .load(item.poster)
            .into(pic2)

        titleTxt.text = item.title
        movieRateTxt.text = item.imdbRating
        movieTimeTxt.text = item.runtime
        movieSummaryInfo.text = item.plot
        movieActorsInfo.text = item.actors

        if (item.images.isNotEmpty()) {
            adapterActorList = ActorsListAdapter(item.images, this)
            recyclerViewActors.adapter = adapterActorList
        }

        if (item.genres.isNotEmpty()) {
            adapterCategory = CategoryEachMovieAdapter(item.genres)
            recyclerViewCategory.adapter = adapterCategory
        }
    }

    private fun initView() {
        titleTxt = findViewById(R.id.MovieNameText)
        progressBar = findViewById(R.id.progressBarDetail)
        scrollView = findViewById(R.id.nestedScrollView)
        pic2 = findViewById(R.id.imageMoive)
        movieRateTxt = findViewById(R.id.movieStar)
        movieTimeTxt = findViewById(R.id.movieTime)
        movieSummaryInfo = findViewById(R.id.movieSummary)
        movieActorsInfo = findViewById(R.id.movieActorInfo)
        backImg = findViewById(R.id.imageView2)
        recyclerViewActors = findViewById(R.id.imageRecycler)
        recyclerViewCategory = findViewById(R.id.genreView4)

        recyclerViewActors?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCategory?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        backImg?.setOnClickListener { finish() }
    }
}
