@file:OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)

package org.example.movique.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import org.example.movique.MediaDetailsScreen
import org.example.movique.data.models.mediadetails.MovieDetailsResponseModel
import org.example.movique.data.models.mediadetails.TvSeriesDetailsResponseModel
import org.example.movique.ui.components.card.MediaCard
import org.example.movique.util.Result
import org.example.movique.util.Result.Loading.isLoading
import org.example.movique.viewmodel.MediaDetailsViewModel
import org.example.movique.viewmodel.MediaListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MediaDetailsScreen(
	navController: NavHostController,
	mediaId: Int,
	mediaType: String
) {
	val mediaDetailsViewModel = koinViewModel<MediaDetailsViewModel>()
	val isLoading by mediaDetailsViewModel.isLoading.collectAsState()
	val getMovieDetails = mediaDetailsViewModel.getMovieDetails.collectAsState()
	val getTvSeriesDetails = mediaDetailsViewModel.getTvSeriesDetails.collectAsState()
	val movie = (getMovieDetails.value as? Result.Success)?.data
	val tvSeries = (getTvSeriesDetails.value as? Result.Success)?.data
	val listState = rememberScrollState()

	// Initial fetch
	LaunchedEffect(mediaType) {
		when (mediaType) {
			"movie" -> {
				if (movie?.id != mediaId) {
					mediaDetailsViewModel.fetchMovieDetails(mediaId)
				}
			}

			"tv" -> {
				if (tvSeries?.id != mediaId) {
					mediaDetailsViewModel.fetchTvSeriesDetails(mediaId)
				}
			}
		}
	}

	Box(Modifier.fillMaxSize()) {
		if (isLoading) {
			Scaffold(
				modifier = Modifier.fillMaxSize()
			) {
				Box(
					modifier = Modifier.fillMaxSize()
				) {
					CircularProgressIndicator(
						modifier = Modifier.size(20.dp).align(Alignment.Center),
						color = MaterialTheme.colorScheme.primary
					)
				}
			}
		} else {
			// Screen Content
			Scaffold {
				Column(
					modifier = Modifier
						.fillMaxSize()
						.verticalScroll(listState),
					horizontalAlignment = Alignment.CenterHorizontally,
					verticalArrangement = Arrangement.Top
				) {
					Spacer(Modifier.height(96.dp))
					Spacer(Modifier.height(84.dp))
				}
			}

			// Top Bar
			TopAppBar(
				modifier = Modifier
					.height(80.dp)
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
						modifier = Modifier.fillMaxHeight(),
						contentAlignment = Alignment.Center
					) {
						Text(
							text = when (mediaType) {
								"movie" -> movie?.title ?: "Movie Title"
								"tv" -> tvSeries?.name ?: "Tv Series Title"
								else -> ""
							},
							style = MaterialTheme.typography.titleLarge,
							color = MaterialTheme.colorScheme.onSurface,
							maxLines = 1,
							overflow = TextOverflow.Ellipsis
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
}