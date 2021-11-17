package com.example.moviejam.data.source.remote.response.moviedetail

import com.google.gson.annotations.SerializedName

data class GenresItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)