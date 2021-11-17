package com.example.moviejam.data.source.remote.response.tvshowdetail

import com.google.gson.annotations.SerializedName

data class TvShowDetailResponse(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("backdrop_path")
	val backdropPath: String,

	@field:SerializedName("poster_path")
	val posterPath: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("vote_average")
	val voteAverage: Double,

	@field:SerializedName("first_air_date")
	val firstAirDate: String,

	@field:SerializedName("genres")
	val genres: List<GenresItem>,

	@field:SerializedName("overview")
	val overview: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("original_language")
	val originalLanguage: String,

	@field:SerializedName("production_companies")
	val productionCompanies: List<ProductionCompaniesItem>
)