package org.example.movique.data.models.mediadetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.movique.data.models.mediadetails.common.Credits
import org.example.movique.data.models.mediadetails.common.Genre
import org.example.movique.data.models.mediadetails.common.PagedMediaList
import org.example.movique.data.models.mediadetails.common.ProductionCompany
import org.example.movique.data.models.mediadetails.common.SpokenLanguage
import org.example.movique.data.models.mediadetails.common.Videos

@Serializable
data class MovieDetailsResponseModel(
    @SerialName("adult")
    val adult: Boolean? = false,
    @SerialName("backdrop_path")
    val backdropPath: String? = "",
    @SerialName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection? = BelongsToCollection(),
    @SerialName("budget")
    val budget: Int? = 0,
    @SerialName("genres")
    val genres: List<Genre?>? = listOf(),
    @SerialName("homepage")
    val homepage: String? = "",
    @SerialName("id")
    val id: Int? = 0,
    @SerialName("imdb_id")
    val imdbId: String? = "",
    @SerialName("origin_country")
    val originCountry: List<String?>? = listOf(),
    @SerialName("original_language")
    val originalLanguage: String? = "",
    @SerialName("original_title")
    val originalTitle: String? = "",
    @SerialName("overview")
    val overview: String? = "",
    @SerialName("popularity")
    val popularity: Double? = 0.0,
    @SerialName("poster_path")
    val posterPath: String? = "",
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompany?>? = listOf(),
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountry?>? = listOf(),
    @SerialName("release_date")
    val releaseDate: String? = "",
    @SerialName("revenue")
    val revenue: Long? = 0,
    @SerialName("runtime")
    val runtime: Int? = 0,
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage?>? = listOf(),
    @SerialName("status")
    val status: String? = "",
    @SerialName("tagline")
    val tagline: String? = "",
    @SerialName("title")
    val title: String? = "",
    @SerialName("video")
    val video: Boolean? = false,
    @SerialName("vote_average")
    val voteAverage: Double? = 0.0,
    @SerialName("vote_count")
    val voteCount: Int? = 0,
    @SerialName("credits")
    val credits: Credits? = Credits(),
    @SerialName("videos")
    val videos: Videos? = Videos(),
    @SerialName("similar")
    val similar: PagedMediaList? = PagedMediaList(),
) {
    @Serializable
    data class BelongsToCollection(
        @SerialName("id")
        val id: Int? = 0,
        @SerialName("name")
        val name: String? = "",
        @SerialName("poster_path")
        val posterPath: String? = "",
        @SerialName("backdrop_path")
        val backdropPath: String? = ""
    )

    @Serializable
    data class ProductionCountry(
        @SerialName("iso_3166_1")
        val iso31661: String? = "",
        @SerialName("name")
        val name: String? = ""
    )
}