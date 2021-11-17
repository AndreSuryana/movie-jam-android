package com.example.moviejam.data.source.remote.response.tvshow

import com.google.gson.annotations.SerializedName

data class TvShowsResponse(

    @field:SerializedName("page")
	val page: Int,

    @field:SerializedName("total_pages")
	val totalPages: Int,

    @field:SerializedName("results")
	val tvShows: List<TvShow>,

    @field:SerializedName("total_results")
	val totalResults: Int
)