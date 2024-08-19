package com.example.moviesapp.Model

import com.google.gson.annotations.SerializedName

data class MovieItem (

  @SerializedName("id"          ) var id         : Int?              = null,
  @SerializedName("title"       ) var title      : String?           = null,
  @SerializedName("poster"      ) var poster     : String?           = null,
  @SerializedName("year"        ) var year       : String?           = null,
  @SerializedName("rated"       ) var rated      : String?           = null,
  @SerializedName("released"    ) var released   : String?           = null,
  @SerializedName("runtime"     ) var runtime    : String?           = null,
  @SerializedName("director"    ) var director   : String?           = null,
  @SerializedName("writer"      ) var writer     : String?           = null,
  @SerializedName("actors"      ) var actors     : String?           = null,
  @SerializedName("plot"        ) var plot       : String?           = null,
  @SerializedName("country"     ) var country    : String?           = null,
  @SerializedName("awards"      ) var awards     : String?           = null,
  @SerializedName("metascore"   ) var metascore  : String?           = null,
  @SerializedName("imdb_rating" ) var imdbRating : String?           = null,
  @SerializedName("imdb_votes"  ) var imdbVotes  : String?           = null,
  @SerializedName("imdb_id"     ) var imdbId     : String?           = null,
  @SerializedName("type"        ) var type       : String?           = null,
  @SerializedName("genres"      ) var genres     : ArrayList<String> = arrayListOf(),
  @SerializedName("images"      ) var images     : ArrayList<String> = arrayListOf()

)