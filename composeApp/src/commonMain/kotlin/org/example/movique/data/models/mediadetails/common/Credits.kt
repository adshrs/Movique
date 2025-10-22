package org.example.movique.data.models.mediadetails.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Credits(
	@SerialName("cast")
	val cast: List<Cast?>? = listOf(),
	@SerialName("crew")
	val crew: List<Crew?>? = listOf()
) {
	@Serializable
	data class Cast(
		@SerialName("adult")
		val adult: Boolean? = false,
		@SerialName("gender")
		val gender: Int? = 0,
		@SerialName("id")
		val id: Int? = 0,
		@SerialName("known_for_department")
		val knownForDepartment: String? = "",
		@SerialName("name")
		val name: String? = "",
		@SerialName("original_name")
		val originalName: String? = "",
		@SerialName("popularity")
		val popularity: Double? = 0.0,
		@SerialName("profile_path")
		val profilePath: String? = "",
		@SerialName("cast_id")
		val castId: Int? = 0,
		@SerialName("character")
		val character: String? = "",
		@SerialName("credit_id")
		val creditId: String? = "",
		@SerialName("order")
		val order: Int? = 0
	)

	@Serializable
	data class Crew(
		@SerialName("adult")
		val adult: Boolean? = false,
		@SerialName("gender")
		val gender: Int? = 0,
		@SerialName("id")
		val id: Int? = 0,
		@SerialName("known_for_department")
		val knownForDepartment: String? = "",
		@SerialName("name")
		val name: String? = "",
		@SerialName("original_name")
		val originalName: String? = "",
		@SerialName("popularity")
		val popularity: Double? = 0.0,
		@SerialName("profile_path")
		val profilePath: String? = "",
		@SerialName("credit_id")
		val creditId: String? = "",
		@SerialName("department")
		val department: String? = "",
		@SerialName("job")
		val job: String? = ""
	)
}