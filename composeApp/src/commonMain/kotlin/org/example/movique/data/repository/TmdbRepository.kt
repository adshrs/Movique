package org.example.movique.data.repository

import org.example.movique.data.models.media.MovieResponseModel
import org.example.movique.data.models.search.MultiSearchResponseModel
import org.example.movique.data.models.media.TvSeriesResponseModel
import org.example.movique.data.models.mediadetails.MovieDetailsResponseModel
import org.example.movique.data.models.mediadetails.TvSeriesDetailsResponseModel
import org.example.movique.networking.TmdbClient
import org.example.movique.util.NetworkError
import org.example.movique.util.Result

class TmdbRepository(
	private val client: TmdbClient
) {
	suspend fun getTvSeriesDetails(
		id: Int,
		append: String = "credits,videos,similar"
	): Result<TvSeriesDetailsResponseModel, NetworkError> {
		return client.getTvSeriesDetails(id, append)
	}

	suspend fun getMovieDetails(
		id: Int,
		append: String = "credits,videos,similar"
	): Result<MovieDetailsResponseModel, NetworkError> {
		return client.getMovieDetails(id, append)
	}

	suspend fun multiSearch(query: String, page: Int = 1): Result<MultiSearchResponseModel, NetworkError> {
		return try {
			client.multiSearch(query, page)
		} catch (e: IllegalArgumentException) {
			Result.Error(NetworkError.UNKNOWN)
		}
	}

	suspend fun searchMovies(query: String, page: Int = 1): Result<MovieResponseModel, NetworkError> {
		return try {
			client.searchMovies(query, page)
		} catch (e: IllegalArgumentException) {
			Result.Error(NetworkError.UNKNOWN)
		}
	}

	suspend fun getTrendingAllWeek(page: Int = 1): Result<MultiSearchResponseModel, NetworkError> {
		return client.getTrending("all", "week", page)
	}

	suspend fun getPopularTvShows(page: Int): Result<TvSeriesResponseModel, NetworkError> {
		return client.getPopularTvSeries(page)
	}

	suspend fun getPopularMovies(page: Int = 1): Result<MovieResponseModel, NetworkError> {
		return client.getPopularMovies(page)
	}
}