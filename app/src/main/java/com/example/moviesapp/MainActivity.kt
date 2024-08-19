package com.example.moviesapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.moviesapp.Adapter.CategoryAdapter
import com.example.moviesapp.Adapter.MoviesListAdapter
import com.example.moviesapp.Adapter.Slider_Adapter
import com.example.moviesapp.Model.CategoryItem
import com.example.moviesapp.Model.CategoryList
import com.example.moviesapp.Model.MoviesList
import com.example.moviesapp.Model.Slider_item
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2
    private val handler = Handler(Looper.getMainLooper())

    private lateinit var recyclerViewBestMovies: RecyclerView
    private lateinit var recyclerViewCategory: RecyclerView
    private lateinit var recyclerViewUpcoming: RecyclerView

    private lateinit var bestMoviesAdapter: RecyclerView.Adapter<*>
    private lateinit var categoryAdapter: RecyclerView.Adapter<*>
    private lateinit var upcomingMoviesAdapter: RecyclerView.Adapter<*>

    private lateinit var mRequestQueue: RequestQueue
    private lateinit var mStringRequest: StringRequest
    private lateinit var mStringRequest2: StringRequest
    private lateinit var mStringRequest3: StringRequest
    private lateinit var loading1: ProgressBar
    private lateinit var loading2: ProgressBar
    private lateinit var loading3: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        banners()
        sendRequestBestMovies();
        sendRequestUpComing();
        sendRequestCategory()
    }

    private fun sendRequestBestMovies() {
        // Initialize the RequestQueue
        mRequestQueue = Volley.newRequestQueue(this)

        // Show the progress bar while loading
        loading1.visibility = View.VISIBLE

        // Create the Gson object
        val gson = Gson()

        // Define the URL for the API request
        val url = "https://moviesapi.ir/api/v1/movies?page=1"

        // Create the StringRequest
        mStringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                // Hide the progress bar when the response is received
                loading1.visibility = View.INVISIBLE

                // Parse the JSON response using Gson
                val moviesList = gson.fromJson(response, MoviesList::class.java)

                // Set the adapter for RecyclerView
                val adapterBestMovies = MoviesListAdapter(moviesList.data)
                recyclerViewBestMovies.adapter = adapterBestMovies
            },
            { error ->
                // Hide the progress bar on error
                loading1.visibility = View.INVISIBLE

                // Log the error
                Log.e("VolleyError", error.toString())
            }
        )

        // Add the request to the RequestQueue
        mRequestQueue.add(mStringRequest)
    }

    private fun sendRequestUpComing() {
        // Initialize the RequestQueue
        mRequestQueue = Volley.newRequestQueue(this)

        // Show the progress bar while loading
        loading3.visibility = View.VISIBLE

        // Create the Gson object
        val gson = Gson()

        // Define the URL for the API request
        val url = "https://moviesapi.ir/api/v1/movies?page=2"

        // Create the StringRequest
        mStringRequest3 = StringRequest(
            Request.Method.GET, url,
            { response ->
                // Hide the progress bar when the response is received
                loading3.visibility = View.INVISIBLE

                // Parse the JSON response using Gson
                val moviesList = gson.fromJson(response, MoviesList::class.java)

                // Set the adapter for RecyclerView
                val upcomingMoviesAdapter  = MoviesListAdapter(moviesList.data)
                recyclerViewUpcoming.adapter = upcomingMoviesAdapter
            },
            { error ->
                // Hide the progress bar on error
                loading3.visibility = View.INVISIBLE

                // Log the error
                Log.e("VolleyError", error.toString())
            }
        )

        // Add the request to the RequestQueue
        mRequestQueue.add(mStringRequest3)
    }

    private fun sendRequestCategory() {
        // Initialize the RequestQueue
        mRequestQueue = Volley.newRequestQueue(this)

        // Show the progress bar while loading
        loading2.visibility = View.VISIBLE

        // Create the Gson object
        val gson = Gson()

        // Define the URL for the API request
        val url = "https://moviesapi.ir/api/v1/genres"

        // Create the StringRequest
        mStringRequest2 = StringRequest(
            Request.Method.GET, url,
            { response ->
                // Hide the progress bar when the response is received
                loading2.visibility = View.INVISIBLE

                // Parse the JSON response using Gson
                val listType = object : TypeToken<ArrayList<CategoryItem>>() {}.type
                val categoryList: ArrayList<CategoryItem> = gson.fromJson(response, listType)

                // Set the adapter for RecyclerView
                val categoryAdapter  = CategoryAdapter(categoryList)
                recyclerViewCategory.adapter = categoryAdapter
            },
            { error ->
                // Hide the progress bar on error
                loading2.visibility = View.INVISIBLE

                // Log the error
                Log.e("VolleyError", error.toString())
            }
        )

        // Add the request to the RequestQueue
        mRequestQueue.add(mStringRequest2)
    }

    private fun initView() {
        viewPager2 = findViewById(R.id.viewPagerSlider);

        // Initialize RecyclerViews
        recyclerViewBestMovies = findViewById(R.id.view1)
        recyclerViewUpcoming = findViewById(R.id.view3)
        recyclerViewCategory = findViewById(R.id.view2)

        // Set LayoutManagers for RecyclerViews
        recyclerViewBestMovies.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewUpcoming.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCategory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Initialize ProgressBars
        loading1 = findViewById(R.id.progressBar1)
        loading2 = findViewById(R.id.progressBar2)
        loading3 = findViewById(R.id.progressBar3)
    }

    private fun banners() {
        val sliderItems = listOf(
            Slider_item(R.drawable.wide),
            Slider_item(R.drawable.wide1),
            Slider_item(R.drawable.wide3)
        )

        viewPager2.adapter = Slider_Adapter(sliderItems, viewPager2)
        viewPager2.clipToPadding = true
        viewPager2.clipChildren = true
        viewPager2.offscreenPageLimit = 3
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        // Simple Page Transformer for scaling
        val simplePageTransformer = ViewPager2.PageTransformer { page, position ->
            page.apply {
                val scale = 0.85f + (1 - Math.abs(position)) * 0.15f
                scaleX = scale
                scaleY = scale
                alpha = 0.5f + (1 - Math.abs(position))
            }
        }


        viewPager2.currentItem = 0

        // Auto-scroll
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(sliderRunnable)
                handler.postDelayed(sliderRunnable, 2000L) // Delay to auto-scroll
            }
        })
    }

    private val sliderRunnable = Runnable {
        val nextItem = (viewPager2.currentItem + 1) % (viewPager2.adapter?.itemCount ?: 1)
        viewPager2.setCurrentItem(nextItem, true) // Smooth scroll to the next item
    }

    private fun startAutoScroll() {
        handler.postDelayed(sliderRunnable, 3000L) // Adjust delay as needed
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(sliderRunnable, 3000L) // Start auto-scrolling
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(sliderRunnable)
    }

}