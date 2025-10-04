package org.example.movique.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.movique.data.models.MovieResponseModel
import org.example.movique.data.repository.TmdbRepository
import org.example.movique.util.NetworkError
import org.example.movique.util.Result

class SearchViewModel(
	private val tmdbRepository: TmdbRepository
) : ViewModel() {
	private val _isLoading = MutableStateFlow(false)
	val isLoading: StateFlow<Boolean> get() = _isLoading

	private val _getSearchResults =
		MutableStateFlow<Result<MovieResponseModel, NetworkError>?>(null)
	val getSearchResults: StateFlow<Result<MovieResponseModel, NetworkError>?> =
		_getSearchResults

	fun fetchSearchResults(query: String, page: Int = 1) {
		viewModelScope.launch {
			_isLoading.value = true
			_getSearchResults.value = null // clear old data first
			val result = tmdbRepository.searchMovies(query, page)
			_getSearchResults.value = result
			_isLoading.value = false
		}
	}
}