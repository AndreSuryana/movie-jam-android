package com.example.moviejam.data.source.remote.response.tvshowdetail

import com.google.gson.annotations.SerializedName

data class ProductionCompaniesItem(

	@field:SerializedName("logo_path")
	val logoPath: Any,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("origin_country")
	val originCountry: String
)