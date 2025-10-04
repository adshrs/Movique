package org.example.movique.networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import org.example.movique.data.models.MovieResponseModel
import org.example.movique.getTmdbApiKey
import org.example.movique.util.NetworkError
import org.example.movique.util.Result

class TmdbClient(
	private val httpClient: HttpClient,
) {
	private val apiKey = getTmdbApiKey()
	private val baseUrl = "https://api.themoviedb.org/3/"

	suspend fun getPopularMovies(page: Int = 1): Result<MovieResponseModel, NetworkError> {
		val response = try {
			httpClient.get(
				urlString = "${baseUrl}movie/popular"
			) {
				parameter("api_key", apiKey)
				parameter("page", page)
				parameter("language", "en-US")
			}
		} catch (e: UnresolvedAddressException) {
			return Result.Error(NetworkError.NO_INTERNET)
		} catch (e: SerializationException) {
			return Result.Error(NetworkError.SERIALIZATION)
		}

		return when (response.status.value) {
			in 200..299 -> {
				val data = response.body<MovieResponseModel>()
				Result.Success(data)
			}

			401 -> Result.Error(NetworkError.UNAUTHORIZED)
			409 -> Result.Error(NetworkError.CONFLICT)
			408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
			413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
			in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
			else -> Result.Error(NetworkError.UNKNOWN)
		}
	}

	suspend fun searchMovies(query: String, page: Int = 1): Result<MovieResponseModel, NetworkError> {
		if (query.isBlank()) throw IllegalArgumentException("Query cannot be blank")
		val response = try {
			httpClient.get(
				urlString = "${baseUrl}search/movie"
			) {
				parameter("api_key", apiKey)
				parameter("query", query)
				parameter("page", page)
			}
		} catch (e: UnresolvedAddressException) {
			return Result.Error(NetworkError.NO_INTERNET)
		} catch (e: SerializationException) {
			return Result.Error(NetworkError.SERIALIZATION)
		}

		return when (response.status.value) {
			in 200..299 -> {
				val data = response.body<MovieResponseModel>()
				Result.Success(data)
			}

			401 -> Result.Error(NetworkError.UNAUTHORIZED)
			409 -> Result.Error(NetworkError.CONFLICT)
			408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
			413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
			in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
			else -> Result.Error(NetworkError.UNKNOWN)
		}
	}
}