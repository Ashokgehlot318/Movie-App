package com.example.moviesapp.Model

data class Slider_item(val imageResId: Int) {
    fun getImage(): Int {
        return imageResId
    }
}
