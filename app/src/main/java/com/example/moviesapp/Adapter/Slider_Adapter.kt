package com.example.moviesapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.moviesapp.Model.Slider_item
import com.example.moviesapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class Slider_Adapter(
    private val sliderItems: List<Slider_item>,
    private val viewPager2: ViewPager2
) : RecyclerView.Adapter<Slider_Adapter.SliderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.slider_item_container,
            parent,
            false
        )
        return SliderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        val sliderItem = sliderItems[position]
        holder.bind(sliderItem)
        if (position == sliderItems.size - 2) {
            viewPager2.post(runnable)
        }
    }

    inner class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageSlider)

        fun bind(sliderItem: Slider_item) {
            val requestOptions = RequestOptions()
                .centerCrop()
                .transform(RoundedCorners(60))

            Glide.with(itemView.context)
                .load(sliderItem.getImage())
                .apply(requestOptions)
                .into(imageView)
        }
    }

    private val runnable = Runnable {
        val newItems = sliderItems.toMutableList()
        newItems.addAll(sliderItems)
        notifyDataSetChanged()
    }
}
