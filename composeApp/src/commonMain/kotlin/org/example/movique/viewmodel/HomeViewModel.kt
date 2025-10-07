package org.example.movique.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.movique.data.models.MovieResponseModel
import org.example.movique.data.models.TvSeriesResponseModel
import org.example.movique.data.repository.TmdbRepository
import org.example.movique.util.NetworkError
import org.example.movique.util.Result

class HomeViewModel(
	private val tmdbRepository: TmdbRepository
) : ViewModel() {
	private val _isLoading = MutableStateFlow(false)
	val isLoading: StateFlow<Boolean> get() = _isLoading

	private val _getPopularTvShows =
		MutableStateFlow<Result<TvSeriesResponseModel, NetworkError>?>(null)
	val getPopularTvShows: StateFlow<Result<TvSeriesResponseModel, NetworkError>?> =
		_getPopularTvShows

	private val _getPopularMovies =
		MutableStateFlow<Result<MovieResponseModel, NetworkError>?>(null)
	val getPopularMovies: StateFlow<Result<MovieResponseModel, NetworkError>?> =
		_getPopularMovies

	fun fetchPopularTvShows(page: Int = 1) {
		viewModelScope.launch {
			_isLoading.value = true
			_getPopularTvShows.value = null
			val result = tmdbRepository.getPopularTvShows(page)
			_getPopularTvShows.value = result
			_isLoading.value = false
		}
	}

	fun fetchPopularMovies(page: Int = 1) {
		viewModelScope.launch {
			_isLoading.value = true
			_getPopularMovies.value = null // clear old data first
			val result = tmdbRepository.getPopularMovies(page)
			_getPopularMovies.value = result
			_isLoading.value = false
		}
	}
}