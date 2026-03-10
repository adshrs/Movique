package org.example.movique.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.movique.data.models.media.MovieResponseModel
import org.example.movique.data.models.search.MultiSearchResponseModel
import org.example.movique.data.models.media.TvSeriesResponseModel
import org.example.movique.data.models.mediadetails.MovieDetailsResponseModel
import org.example.movique.data.models.mediadetails.TvSeriesDetailsResponseModel
import org.example.movique.data.repository.TmdbRepository
import org.example.movique.util.NetworkError
import org.example.movique.util.Result
import org.example.movique.util.map

class MediaDetailsViewModel(
	private val tmdbRepository: TmdbRepository
) : ViewModel() {
	private val _isLoading = MutableStateFlow(false)
	val isLoading: StateFlow<Boolean> get() = _isLoading

	private val _getTvSeriesDetails =
		MutableStateFlow<Result<TvSeriesDetailsResponseModel, NetworkError>?>(null)
	val getTvSeriesDetails: StateFlow<Result<TvSeriesDetailsResponseModel, NetworkError>?> =
		_getTvSeriesDetails

	private val _getMovieDetails =
		MutableStateFlow<Result<MovieDetailsResponseModel, NetworkError>?>(null)
	val getMovieDetails: StateFlow<Result<MovieDetailsResponseModel, NetworkError>?> =
		_getMovieDetails


	fun fetchTvSeriesDetails(id: Int) {
		viewModelScope.launch {
			_isLoading.value = true
			_getTvSeriesDetails.value = null // clear old data first
			val result = tmdbRepository.getTvSeriesDetails(id, "credits,videos,similar")
			_getTvSeriesDetails.value = result
			_isLoading.value = false
		}
	}

	fun fetchMovieDetails(id: Int) {
		viewModelScope.launch {
			_isLoading.value = true
			_getMovieDetails.value = null // clear old data first
			val result = tmdbRepository.getMovieDetails(id, "credits,videos,similar")
			_getMovieDetails.value = result
			_isLoading.value = false
		}
	}
}
