package org.example.movique.data.models.mediadetails.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCompany(
	@SerialName("id")
	val id: Int? = 0,
	@SerialName("logo_path")
	val logoPath: String? = "",
	@SerialName("name")
	val name: String? = "",
	@SerialName("origin_country")
	val originCountry: String? = ""
)