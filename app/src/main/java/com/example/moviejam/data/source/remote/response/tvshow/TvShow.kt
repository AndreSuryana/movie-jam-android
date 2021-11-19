package com.example.moviejam.data.source.remote.response.tvshow

import com.google.gson.annotations.SerializedName

data class TvShow(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("poster_path")
	val posterPath: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("vote_average")
	val voteAverage: Double,

	@field:SerializedName("first_air_date")
	val firstAirDate: String
)