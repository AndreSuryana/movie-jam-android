package com.example.moviejam.data.source.remote.response.movie

import com.google.gson.annotations.SerializedName

data class Movie(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("poster_path")
	val posterPath: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("vote_average")
	val voteAverage: Double,

	@field:SerializedName("release_date")
	val releaseDate: String
)