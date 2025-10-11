package org.example.movique.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.movique.data.models.search.MultiSearchResponseModel
import org.example.movique.data.repository.TmdbRepository
import org.example.movique.util.NetworkError
import org.example.movique.util.Result
import org.example.movique.util.map
import org.example.movique.util.mapSuccess

class SearchViewModel(
	private val tmdbRepository: TmdbRepository
) : ViewModel() {
	private val _isLoading = MutableStateFlow(false)
	val isLoading: StateFlow<Boolean> get() = _isLoading

	private val _getMultiSearchResults = MutableStateFlow<Result<MultiSearchResponseModel, NetworkError>?>(null)
	val getMultiSearchResults: StateFlow<Result<MultiSearchResponseModel, NetworkError>?> = _getMultiSearchResults

	private val _searchResults = MutableStateFlow<List<MultiSearchResponseModel.MultiResult>>(emptyList())
	val searchResults: StateFlow<List<MultiSearchResponseModel.MultiResult>> = _searchResults.asStateFlow()

	private var currentPage = 1
	private var currentQuery = ""
	private var totalPages = Int.MAX_VALUE

	fun fetchMultiSearchResults(query: String, page: Int = 1, append: Boolean = false) {
		if (query.isBlank()) {
			_getMultiSearchResults.value = Result.Success(MultiSearchResponseModel())
			_searchResults.value = emptyList()
			return
		}
		if (page > totalPages) return

		viewModelScope.launch {
			if (!append) {
				_isLoading.value = true
				_getMultiSearchResults.value = Result.Loading
				_searchResults.value = emptyList()
				currentPage = 1
				currentQuery = query
			} else if (query != currentQuery) {
				_isLoading.value = true
				_getMultiSearchResults.value = Result.Loading
				_searchResults.value = emptyList()
				currentPage = 1
				currentQuery = query
			}
			val result = tmdbRepository.multiSearch(query, currentPage)
			_getMultiSearchResults.value = result.map { response ->
				response.copy(
					results = response.results
						.filter { it.mediaType == "movie" || it.mediaType == "tv" }
						.sortedByDescending { it.popularity ?: 0.0 }
				)
			}
			if (result.isSuccess) {
				val data = result.getOrNull()
				if (data != null) {
					_searchResults.value = _searchResults.value + data.results
						.filter { it.mediaType == "movie" || it.mediaType == "tv" }
						.sortedByDescending { it.popularity ?: 0.0 }
					currentPage++
					totalPages = data.totalPages
				}
			}
			_isLoading.value = false
		}
	}

	fun resetSearch() {
		_searchResults.value = emptyList()
		_getMultiSearchResults.value = null
		currentPage = 1
		currentQuery = ""
		totalPages = Int.MAX_VALUE
		_isLoading.value = false
	}
}