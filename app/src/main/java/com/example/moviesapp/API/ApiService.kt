package com.example.moviesapp.API

import android.telecom.Call
import com.example.moviesapp.Model.CategoryItem
import com.example.moviesapp.Model.MovieItem
import com.example.moviesapp.Model.MoviesList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movies")
    suspend fun getBestMovies(@Query("page") page: Int): MoviesList

    @GET("movies")
    suspend fun getUpcomingMovies(@Query("page") page: Int): MoviesList

    @GET("genres")
    suspend fun getCategories(): List<CategoryItem>

    @GET("movies/{id}")
    suspend fun getMovieDetails(@Path("id") id: Int): MovieItem
}
