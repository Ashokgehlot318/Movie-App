package com.example.moviesapp.Model

import com.google.gson.annotations.SerializedName


data class MoviesList (

  @SerializedName("data") var data     : ArrayList<Data> = arrayListOf(),
  @SerializedName("metadata") var metadata : Metadata?= Metadata()

) {

}