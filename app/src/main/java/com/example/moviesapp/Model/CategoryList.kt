package com.example.moviesapp.Model

import com.google.gson.annotations.SerializedName


data class CategoryList (
  @SerializedName("genres") var genres: List<CategoryItem> = listOf(),
)