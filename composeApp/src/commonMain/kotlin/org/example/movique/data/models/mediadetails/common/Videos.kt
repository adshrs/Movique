package org.example.movique.data.models.mediadetails.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Videos(
	@SerialName("results")
	val results: List<Video> = emptyList()
)

@Serializable
data class Video(
	@SerialName("iso_639_1")
	val iso6391: String? = "",

	@SerialName("iso_3166_1")
	val iso31661: String? = "",

	@SerialName("name")
	val name: String? = "",

	@SerialName("key")
	val key: String? = "",          // YouTube / Vimeo key

	@SerialName("site")
	val site: String? = "",         // "YouTube", "Vimeo"

	@SerialName("size")
	val size: Int? = 0,            // 360, 720, 1080…

	@SerialName("type")
	val type: String? = "",         // "Trailer", "Teaser", "Clip", "Featurette", "Behind the Scenes"…

	@SerialName("official")
	val official: Boolean? = false,

	@SerialName("published_at")
	val publishedAt: String? = "",

	@SerialName("id")
	val id: String? = ""
)