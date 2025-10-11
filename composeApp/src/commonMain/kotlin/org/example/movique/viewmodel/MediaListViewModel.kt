package org.example.movique.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.movique.data.models.media.MovieResponseModel
import org.example.movique.data.models.search.MultiSearchResponseModel
import org.example.movique.data.models.media.TvSeriesResponseModel
import org.example.movique.data.repository.TmdbRepository
import org.example.movique.util.NetworkError
import org.example.movique.util.Result
import org.example.movique.util.map

class MediaListViewModel(
	private val tmdbRepository: TmdbRepository
) : ViewModel() {
	private val _isLoading = MutableStateFlow(false)
	val isLoading: StateFlow<Boolean> get() = _isLoading

	private val _getTrendingThisWeek =
		MutableStateFlow<Result<MultiSearchResponseModel, NetworkError>?>(null)
	val getTrendingThisWeek: StateFlow<Result<MultiSearchResponseModel, NetworkError>?> =
		_getTrendingThisWeek

	private val _getPopularTvShows =
		MutableStateFlow<Result<TvSeriesResponseModel, NetworkError>?>(null)
	val getPopularTvShows: StateFlow<Result<TvSeriesResponseModel, NetworkError>?> =
		_getPopularTvShows

	private val _getPopularMovies =
		MutableStateFlow<Result<MovieResponseModel, NetworkError>?>(null)
	val getPopularMovies: StateFlow<Result<MovieResponseModel, NetworkError>?> =
		_getPopularMovies

	// Keep paging state per category
	private var moviePage = 1
	private var tvPage = 1
	private var trendingPage = 1

	private var movieTotal = Int.MAX_VALUE
	private var tvTotal = Int.MAX_VALUE
	private var trendingTotal = Int.MAX_VALUE

	fun fetchPopularMovies(page: Int = moviePage, append: Boolean = false) {
		if (page > movieTotal) return
		viewModelScope.launch {
			_isLoading.value = true
			if (!append) {
				_getPopularMovies.value = null
				moviePage = 1 // reset if not appending
			}
			val result = tmdbRepository.getPopularMovies(page)

			val old = (_getPopularMovies.value as? Result.Success)
				?.data?.results
				.orEmpty()

			_getPopularMovies.value = result.map { resp ->
				resp.copy(
					results = if (append)
						old + (resp.results ?: emptyList())
					else
						resp.results
				)
			}

			if (result.isSuccess) {
				moviePage++ // increment AFTER successful fetch
				movieTotal = result.getOrNull()?.totalPages ?: Int.MAX_VALUE
			}
			_isLoading.value = false
		}
	}


	fun fetchPopularTvShows(page: Int = tvPage, append: Boolean = false) {
		if (page > tvTotal) return
		viewModelScope.launch {
			_isLoading.value = true
			if (!append) {
				_getPopularTvShows.value = null
				tvPage = 1
			}
			val result = tmdbRepository.getPopularTvShows(page)
			val old = (_getPopularTvShows.value as? Result.Success)?.data?.results.orEmpty()
			_getPopularTvShows.value = result.map { resp ->
				resp.copy(results = if (append) old + (resp.results ?: emptyList()) else resp.results)
			}
			if (result.isSuccess) {
				tvPage++
				tvTotal = result.getOrNull()?.totalPages ?: Int.MAX_VALUE
			}
			_isLoading.value = false
		}
	}

	fun fetchTrendingThisWeek(page: Int = trendingPage, append: Boolean = false) {
		if (page > trendingTotal) return
		viewModelScope.launch {
			_isLoading.value = true
			if (!append) {
				_getTrendingThisWeek.value = null
				trendingPage = 1
			}
			val result = tmdbRepository.getTrendingAllWeek(page)
			val old = (_getTrendingThisWeek.value as? Result.Success)?.data?.results.orEmpty()
			_getTrendingThisWeek.value = result.map { resp ->
				resp.copy(results = if (append) old + (resp.results ?: emptyList()) else resp.results)
			}
			if (result.isSuccess) {
				trendingPage++
				trendingTotal = result.getOrNull()?.totalPages ?: Int.MAX_VALUE
			}
			_isLoading.value = false
		}
	}
}
