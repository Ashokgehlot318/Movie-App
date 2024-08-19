package com.example.moviesapp.Model

import com.google.gson.annotations.SerializedName

data class CategoryItem(
 @SerializedName("id") var id: Int? = null,
 @SerializedName("name") var name: String? = null
)
