package com.example.moviesapp.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.moviesapp.Model.Data
import com.example.moviesapp.Model.MoviesList
import com.example.moviesapp.R
import com.example.moviesapp.MovieDetailActivity

class MoviesListAdapter(private val movies: List<Data>) : RecyclerView.Adapter<MoviesListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movies_list_caontainer, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.titleText.text = movie.title

        val requestOptions = RequestOptions()
            .transform(CenterCrop(), RoundedCorners(30))

        Glide.with(holder.itemView.context)
            .load(movie.poster) // Loads the movie poster image
            .apply(requestOptions)
            .into(holder.imageView)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra("id", movie.id) // Pass the movie ID to MovieDetailActivity
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return movies.size  // Return the actual number of items
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val titleText: TextView = itemView.findViewById(R.id.titletxt)
    }
}
