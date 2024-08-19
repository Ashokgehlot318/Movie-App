package com.example.moviesapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.moviesapp.Adapter.ActorsListAdapter
import com.example.moviesapp.Adapter.CategoryEachMovieAdapter
import com.example.moviesapp.Model.MovieItem
import com.google.gson.Gson

class MovieDetailActivity : AppCompatActivity() {
    private var nRequestQueue: RequestQueue? = null
    private var progressBar: ProgressBar? = null
    private var titleTxt: TextView? = null
    private var movieRateTxt: TextView? = null
    private var movieTimeTxt: TextView? = null
    private var movieSummaryInfo: TextView? = null
    private var movieActorsInfo: TextView? = null
    private var idFilm: Int? = null
    private lateinit var pic2: ImageView
    private var backImg: ImageView? = null
    private var adapterActorList: ActorsListAdapter? = null
    private var adapterCategory: CategoryEachMovieAdapter? = null
    private var recyclerViewActors: RecyclerView? = null
    private var recyclerViewCategory: RecyclerView? = null
    private var scrollView: NestedScrollView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        idFilm = intent.getIntExtra("id", 0)
        initView()
        sendRequest()
    }

    private fun sendRequest() {
        nRequestQueue = Volley.newRequestQueue(this)
        progressBar?.visibility = View.VISIBLE
        scrollView?.visibility = View.GONE

        val url = "https://moviesapi.ir/api/v1/movies/$idFilm"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("MovieDetailActivity", "Response: $response") // Log the response
                val gson = Gson()
                val item = gson.fromJson(response, MovieItem::class.java)

                Log.d("MovieDetailActivity", "Parsed Item: $item") // Log the parsed item

                progressBar?.visibility = View.GONE
                scrollView?.visibility = View.VISIBLE

                Glide.with(this)
                    .load(item.poster)
                    .into(pic2)

                titleTxt?.text = item.title
                movieRateTxt?.text = item.imdbRating
                movieTimeTxt?.text = item.runtime
                movieSummaryInfo?.text = item.plot
                movieActorsInfo?.text = item.actors

                if (item.images.isNotEmpty()) {
                    adapterActorList = ActorsListAdapter(item.images, this)
                    recyclerViewActors?.adapter = adapterActorList
                }

                if (item.genres.isNotEmpty()) {
                    adapterCategory = CategoryEachMovieAdapter(item.genres)
                    recyclerViewCategory?.adapter = adapterCategory
                }
            },
            { error ->
                progressBar?.visibility = View.GONE
                Log.e("MovieDetailActivity", "Error: ${error.message}") // Log the error
            }
        )

        nRequestQueue?.add(stringRequest)
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
