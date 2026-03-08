@file:OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)

package org.example.movique.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import org.example.movique.MediaDetailsScreen
import org.example.movique.ui.components.card.MediaCard
import org.example.movique.util.Result
import org.example.movique.viewmodel.MediaListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MediaListScreen(
	navController: NavHostController,
	category: String,
) {
	val mediaListViewModel = koinViewModel<MediaListViewModel>(key = category)
	val isLoading by mediaListViewModel.isLoading.collectAsState()
	val getPopularMovies = mediaListViewModel.getPopularMovies.collectAsState()
	val getPopularTvShows = mediaListViewModel.getPopularTvShows.collectAsState()
	val getTrendingThisWeek = mediaListViewModel.getTrendingThisWeek.collectAsState()
	val listState = rememberLazyGridState()
	var activeCardId by rememberSaveable { mutableStateOf<Int?>(null) }

	val localDensity = LocalDensity.current
	val topBarHeight = with(localDensity) {
		TopAppBarDefaults.TopAppBarExpandedHeight
	}

	// Initial fetch
	LaunchedEffect(category) {
		activeCardId = null
		when (category) {
			"popular_movies" -> {
				val current = mediaListViewModel.getPopularMovies.value
				if ((current as? Result.Success)?.data?.results.isNullOrEmpty()) {
					mediaListViewModel.fetchPopularMovies()
				}
			}

			"popular_tv" -> {
				val current = mediaListViewModel.getPopularTvShows.value
				if ((current as? Result.Success)?.data?.results.isNullOrEmpty()) {
					mediaListViewModel.fetchPopularTvShows()
				}
			}

			"trending_this_week" -> {
				val current = mediaListViewModel.getTrendingThisWeek.value
				if ((current as? Result.Success)?.data?.results.isNullOrEmpty()) {
					mediaListViewModel.fetchTrendingThisWeek()
				}
			}
		}
	}

	// Pagination: load next page when near bottom
	LaunchedEffect(listState) {
		snapshotFlow { listState.layoutInfo.visibleItemsInfo }
			.debounce(300L)
			.collect { visible ->
				val lastVisible = visible.lastOrNull()?.index ?: return@collect
				val total = listState.layoutInfo.totalItemsCount
				if (lastVisible >= total - 4 && !isLoading) {
					when (category) {
						"popular_movies" -> mediaListViewModel.fetchPopularMovies(append = true)
						"popular_tv" -> mediaListViewModel.fetchPopularTvShows(append = true)
						"trending_this_week" -> mediaListViewModel.fetchTrendingThisWeek(append = true)
					}
				}
			}
	}

	val mediaItems = when (category) {
		"popular_movies" ->
			(getPopularMovies.value as? Result.Success)?.data?.results?.map {
				MediaItem(it.id, it.title, it.posterPath, it.voteAverage, it.releaseDate, "movie")
			} ?: emptyList()

		"popular_tv" ->
			(getPopularTvShows.value as? Result.Success)?.data?.results?.map {
				MediaItem(it.id, it.name, it.posterPath, it.voteAverage, it.firstAirDate, "tv")
			} ?: emptyList()

		"trending_this_week" ->
			(getTrendingThisWeek.value as? Result.Success)?.data?.results?.map {
				MediaItem(
					it.id,
					it.title ?: it.name,
					it.posterPath,
					it.voteAverage,
					it.releaseDate ?: it.firstAirDate,
					it.mediaType
				)
			} ?: emptyList()

		else -> emptyList()
	}

	Box(Modifier.fillMaxHeight()) {
		// Screen content
		Scaffold {
			LazyVerticalGrid(
				state = listState,
				modifier = Modifier
					.fillMaxSize()
					.padding(horizontal = 16.dp),
				columns = GridCells.Adaptive(100.dp),
				verticalArrangement = Arrangement.spacedBy(8.dp),
				horizontalArrangement = Arrangement.spacedBy(8.dp)
			) {
				item(span = { GridItemSpan(maxLineSpan) }) {
					Spacer(modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues()))
				}
				item(span = { GridItemSpan(maxLineSpan) }) {
					Spacer(modifier = Modifier.height(topBarHeight + 16.dp))
				}

				itemsIndexed(mediaItems) { index, item ->
					val cardId = index
					MediaCard(
						cardId = cardId,
						mediaTypeEnabled = category == "trending_this_week",
						titleEnabled = false,
						mediaType = item.mediaType,
						posterPath = item.posterPath,
						title = item.title,
						voteAverage = item.voteAverage,
						releaseDate = item.releaseDate,
						isActive = activeCardId == cardId,
						onCardClick = { clicked ->
							activeCardId = if (activeCardId == clicked) null else clicked
						},
						onAddActionClick = { },
						onDetailActionClick = {
							navController.navigate(
								MediaDetailsScreen(
									mediaId = item.id ?: 0,
									mediaType = item.mediaType ?: "movie"
								)
							)
						}
					)
				}

				if (isLoading) {
					item(span = { GridItemSpan(maxLineSpan) }) {
						Column(
							modifier = Modifier
								.fillMaxWidth()
								.height(32.dp),
							horizontalAlignment = Alignment.CenterHorizontally,
							verticalArrangement = Arrangement.Center
						) {
							CircularProgressIndicator(Modifier.size(20.dp))
						}
					}
				}

				item(span = { GridItemSpan(maxLineSpan) }) {
					Spacer(Modifier.height(84.dp))
				}
				item(span = { GridItemSpan(maxLineSpan) }) {
					Spacer(modifier = Modifier.padding(WindowInsets.navigationBars.asPaddingValues()))
				}
			}
		}

		//Top Bar
		TopAppBar(
			modifier = Modifier
//				.height(80.dp)
				.background(
					Brush.verticalGradient(
						listOf(
							BottomAppBarDefaults.containerColor,
							BottomAppBarDefaults.containerColor.copy(0.9f)
						)
					)
				),
			title = {
				Box(
					modifier = Modifier,
					contentAlignment = Alignment.Center
				) {
					Text(
						text = getTitleFromCategory(category),
						style = MaterialTheme.typography.titleLarge,
						color = MaterialTheme.colorScheme.onSurface
					)
				}
			},
			navigationIcon = {
				Box(
					modifier = Modifier.fillMaxHeight(),
					contentAlignment = Alignment.Center
				) {
					IconButton(onClick = { navController.popBackStack() }) {
						Icon(
							imageVector = Icons.AutoMirrored.Filled.ArrowBack,
							contentDescription = "Back",
							tint = MaterialTheme.colorScheme.onSurface
						)
					}
				}
			},
			colors = TopAppBarDefaults.topAppBarColors(
				containerColor = Color.Transparent
			)
		)
	}
}


private fun getTitleFromCategory(category: String) = when (category) {
	"popular_movies" -> "Popular Movies"
	"popular_tv" -> "Popular TV Series"
	"trending_this_week" -> "Trending This Week"
	else -> "Media"
}

data class MediaItem(
	val id: Int?,
	val title: String?,
	val posterPath: String?,
	val voteAverage: Double?,
	val releaseDate: String?,
	val mediaType: String?
)