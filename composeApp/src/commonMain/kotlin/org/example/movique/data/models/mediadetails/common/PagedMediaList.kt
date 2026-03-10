package org.example.movique.data.models.mediadetails.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagedMediaList(
	@SerialName("page")
	val page: Int? = 1,

	@SerialName("results")
	val results: List<SimilarMediaItem> = emptyList(),

	@SerialName("total_pages")
	val totalPages: Int? = 1,

	@SerialName("total_results")
	val totalResults: Int? = 0
)

@Serializable
data class SimilarMediaItem(
	// Core fields present in both movie & tv similar results
	@SerialName("adult")
	val adult: Boolean? = false,

	@SerialName("backdrop_path")
	val backdropPath: String? = "",

	@SerialName("id")
	val id: Int? = 0,

	@SerialName("original_language")
	val originalLanguage: String? = "",

	@SerialName("original_title")      // movie only — use title for movie
	val originalTitle: String? = "",

	@SerialName("original_name")        // tv only — use name for tv
	val originalName: String? = "",

	@SerialName("overview")
	val overview: String? = "",

	@SerialName("popularity")
	val popularity: Double? = 0.0,

	@SerialName("poster_path")
	val posterPath: String? = "",

	@SerialName("release_date")         // movie
	val releaseDate: String? = "",

	@SerialName("first_air_date")       // tv
	val firstAirDate: String? = "",

	@SerialName("title")                // movie
	val title: String? = "",

	@SerialName("name")                 // tv
	val name: String? = "",

	@SerialName("video")
	val video: Boolean? = false,

	@SerialName("vote_average")
	val voteAverage: Double? = 0.0,

	@SerialName("vote_count")
	val voteCount: Int? = 0,

	// Sometimes present
	@SerialName("media_type")
	val mediaType: String? = "",      // "movie" | "tv" — useful when mixed

	@SerialName("genre_ids")
	val genreIds: List<Int>? = emptyList()
)