package org.example.movique.data.models.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MultiSearchResponseModel(
	@SerialName("page")
	val page: Int = 0,
	@SerialName("results")
	val results: List<MultiResult> = emptyList(),
	@SerialName("total_pages")
	val totalPages: Int = 0,
	@SerialName("total_results")
	val totalResults: Int = 0
) {
	@Serializable
	data class MultiResult(
		@SerialName("id")
		val id: Int = 0,
		@SerialName("media_type")
		val mediaType: String? = null, // "movie", "tv", or "person"

		// Common fields across all types
		@SerialName("adult")
		val adult: Boolean? = null,
		@SerialName("backdrop_path")
		val backdropPath: String? = null,
		@SerialName("genre_ids")
		val genreIds: List<Int>? = null,
		@SerialName("original_language")
		val originalLanguage: String? = null,
		@SerialName("overview")
		val overview: String? = null,
		@SerialName("popularity")
		val popularity: Double? = null,
		@SerialName("poster_path")
		val posterPath: String? = null,
		@SerialName("vote_average")
		val voteAverage: Double? = null,
		@SerialName("vote_count")
		val voteCount: Int? = null,

		// Movie-specific fields
		@SerialName("original_title")
		val originalTitle: String? = null,
		@SerialName("release_date")
		val releaseDate: String? = null,
		@SerialName("title")
		val title: String? = null,
		@SerialName("video")
		val video: Boolean? = null,

		// TV-specific fields
		@SerialName("first_air_date")
		val firstAirDate: String? = null,
		@SerialName("name")
		val name: String? = null,
		@SerialName("original_name")
		val originalName: String? = null,
		@SerialName("origin_country")
		val originCountry: List<String>? = null,

		// Person-specific fields (we'll filter these out but include for completeness)
		@SerialName("gender")
		val gender: Int? = null,
		@SerialName("known_for")
		val knownFor: List<KnownFor>? = null,
		@SerialName("known_for_department")
		val knownForDepartment: String? = null,
		@SerialName("profile_path")
		val profilePath: String? = null
	) {
		@Serializable
		data class KnownFor(
			@SerialName("adult")
			val adult: Boolean? = null,
			@SerialName("backdrop_path")
			val backdropPath: String? = null,
			@SerialName("genre_ids")
			val genreIds: List<Int>? = null,
			@SerialName("id")
			val id: Int? = null,
			@SerialName("media_type")
			val mediaType: String? = null,
			@SerialName("original_language")
			val originalLanguage: String? = null,
			@SerialName("original_title")
			val originalTitle: String? = null,
			@SerialName("overview")
			val overview: String? = null,
			@SerialName("poster_path")
			val posterPath: String? = null,
			@SerialName("release_date")
			val releaseDate: String? = null,
			@SerialName("title")
			val title: String? = null,
			@SerialName("video")
			val video: Boolean? = null,
			@SerialName("vote_average")
			val voteAverage: Double? = null,
			@SerialName("vote_count")
			val voteCount: Int? = null
		)
	}
}