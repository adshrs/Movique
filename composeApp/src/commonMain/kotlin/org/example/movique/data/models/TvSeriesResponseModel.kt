package org.example.movique.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvSeriesResponseModel(
	@SerialName("page")
	val page: Int? = null,
	@SerialName("results")
	val results: List<TvShow> = emptyList(),
	@SerialName("total_pages")
	val totalPages: Int = Int.MAX_VALUE,
	@SerialName("total_results")
	val totalResults: Int? = null
) {
	@Serializable
	data class TvShow(
		@SerialName("adult")
		val adult: Boolean? = null,
		@SerialName("backdrop_path")
		val backdropPath: String? = null,
		@SerialName("first_air_date")
		val firstAirDate: String? = null,
		@SerialName("genre_ids")
		val genreIds: List<Int> = emptyList(),
		@SerialName("id")
		val id: Int = 0,
		@SerialName("name")
		val name: String? = null,
		@SerialName("origin_country")
		val originCountry: List<String> = emptyList(),
		@SerialName("original_language")
		val originalLanguage: String? = null,
		@SerialName("original_name")
		val originalName: String? = null,
		@SerialName("overview")
		val overview: String? = null,
		@SerialName("popularity")
		val popularity: Double? = null,
		@SerialName("poster_path")
		val posterPath: String? = null,
		@SerialName("vote_average")
		val voteAverage: Double? = null,
		@SerialName("vote_count")
		val voteCount: Int? = null
	)
}