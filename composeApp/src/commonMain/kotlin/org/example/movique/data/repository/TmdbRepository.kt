package org.example.movique.data.repository

import org.example.movique.data.models.MovieResponseModel
import org.example.movique.networking.TmdbClient
import org.example.movique.util.NetworkError
import org.example.movique.util.Result

class TmdbRepository(
	private val client: TmdbClient
) {
	suspend fun getPopularMovies(page: Int = 1): Result<MovieResponseModel, NetworkError> {
		return client.getPopularMovies(page)
	}

	suspend fun searchMovies(query: String, page: Int = 1): Result<MovieResponseModel, NetworkError> {
		return try {
			client.searchMovies(query, page)
		} catch (e: IllegalArgumentException) {
			Result.Error(NetworkError.UNKNOWN) // Handle blank query
		}
	}
}