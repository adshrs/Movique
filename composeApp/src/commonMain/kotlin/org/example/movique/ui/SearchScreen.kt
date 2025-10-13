@file:OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)

package org.example.movique.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Precision
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import org.example.movique.MediaDetailsScreen
import org.example.movique.data.models.search.MultiSearchResponseModel
import org.example.movique.theme.extraColors
import org.example.movique.ui.components.card.MultiSearchResultCard
import org.example.movique.ui.components.searchbar.CustomSearchBar
import org.example.movique.util.Result
import org.example.movique.util.tools.Constants.NA
import org.example.movique.viewmodel.SearchViewModel
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.round

@Composable
fun SearchScreen(navController: NavHostController, innerPadding: PaddingValues) {
	val scope = rememberCoroutineScope()
	val searchViewModel = koinViewModel<SearchViewModel>()
	val state by searchViewModel.getMultiSearchResults.collectAsState()
	val searchResults by searchViewModel.searchResults.collectAsState()
	val isLoading by searchViewModel.isLoading.collectAsState()
	val listState = rememberLazyListState()
	val snackbarHostState = remember { SnackbarHostState() }
	var query by rememberSaveable { mutableStateOf("") }
	var activeCardId by rememberSaveable { mutableStateOf<Int?>(null) }

	// Live search: Trigger search only for non-empty queries with debounce
	LaunchedEffect(query) {
		snapshotFlow { query }
			.debounce(300L)
			.distinctUntilChanged()
			.collect { searchQuery ->
				if (searchQuery.isNotBlank()) {
					searchViewModel.fetchMultiSearchResults(searchQuery)
					activeCardId = null
				} else {
					searchViewModel.resetSearch()
				}
			}
	}

	// Pagination: Load next page when nearing the end
	LaunchedEffect(listState) {
		snapshotFlow { listState.layoutInfo.visibleItemsInfo }
			.debounce(300L)
			.collect { visibleItems ->
				val lastVisibleItem = visibleItems.lastOrNull()?.index
				if (lastVisibleItem != null && lastVisibleItem >= searchResults.size - 2 && !isLoading && query.isNotBlank()) {
					searchViewModel.fetchMultiSearchResults(query, append = true)
				}
			}
	}

	Box(
		modifier = Modifier.fillMaxSize(),
	) {
		// Screen Content
		Scaffold {
			LazyColumn(
				state = listState,
				modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
			) {
				item { Spacer(modifier = Modifier.height(100.dp)) }
				when {
					query.isBlank() && searchResults.isEmpty() -> {
						item {
							Text(
								text = AnnotatedString.Builder().apply {
									withStyle(SpanStyle(fontSize = 22.sp)) { append("⌕") }
									append("  Your search results will appear here.")
								}.toAnnotatedString(),
								modifier = Modifier
									.fillMaxSize()
									.wrapContentSize(Alignment.Center),
								style = MaterialTheme.typography.labelLarge,
								textAlign = TextAlign.Center,
								color = MaterialTheme.colorScheme.onSurfaceVariant
							)
						}
					}
					state?.isLoading == true && searchResults.isEmpty() -> {
						item {
							Box(modifier = Modifier.fillMaxSize()) {
								CircularProgressIndicator(
									modifier = Modifier
										.size(20.dp)
										.align(Alignment.TopCenter)
								)
							}
						}
					}
					state?.isError == true -> {
						item {
							LaunchedEffect(state) {
								snackbarHostState.showSnackbar("Error: ${(state as Result.Error).error}")
							}
							Column(
								modifier = Modifier
									.fillMaxSize()
									.padding(16.dp),
								horizontalAlignment = Alignment.CenterHorizontally,
								verticalArrangement = Arrangement.Center
							) {
								Text(
									text = "Error: ${(state as Result.Error).error}",
									style = MaterialTheme.typography.bodyLarge,
									color = MaterialTheme.colorScheme.error
								)
								Spacer(modifier = Modifier.height(8.dp))
								Button(onClick = { searchViewModel.fetchMultiSearchResults(query) }) {
									Text("Retry")
								}
							}
						}
					}
					state?.isSuccess == true || searchResults.isNotEmpty() -> {
						if (searchResults.isEmpty() && query.isNotBlank()) {
							item {
								Text(
									text = AnnotatedString.Builder().apply {
										append("No results found ")
										withStyle(SpanStyle(fontSize = 18.sp)) { append("☹") }
										append(".")
									}.toAnnotatedString(),
									modifier = Modifier
										.fillMaxSize()
										.wrapContentSize(Alignment.Center),
									style = MaterialTheme.typography.labelLarge,
									textAlign = TextAlign.Center,
									color = MaterialTheme.colorScheme.onSurfaceVariant
								)
							}
						} else {
							itemsIndexed(searchResults) { index, item ->
								val cardId = index
								MultiSearchResultCard(
									cardId = cardId,
									result = item,
									isActive = activeCardId == cardId,
									onCardClick = { clicked ->
										activeCardId = if (activeCardId == clicked) null else clicked
									},
									onAddActionClick = { },
									onDetailActionClick = {
										navController.navigate(
											MediaDetailsScreen(
												mediaId = item.id,
												mediaType = item.mediaType ?: "movie"
											)
										)
									}
								)
							}
							if (isLoading || state?.isLoading == true) {
								item {
									Column(
										modifier = Modifier
											.fillMaxWidth()
											.height(80.dp),
										horizontalAlignment = Alignment.CenterHorizontally
									) {
										CircularProgressIndicator(
											modifier = Modifier
												.size(20.dp)
										)
									}
								}
							} else {
								item {
									Spacer(modifier = Modifier.height(80.dp))
								}
							}
						}
					}
				}
				item { Spacer(modifier = Modifier.height(84.dp)) }
			}
		}

		// Top Bar
		CenterAlignedTopAppBar(
			modifier = Modifier
				.height(96.dp)
				.background(
					Brush.verticalGradient(
						colors = listOf(
							MaterialTheme.colorScheme.background,
							MaterialTheme.colorScheme.background.copy(0.8f)
						)
					)
				),
			title = {
				Box(
					modifier = Modifier
						.fillMaxHeight()
						.wrapContentWidth(),
					contentAlignment = Alignment.Center
				) {
					CustomSearchBar(
						modifier = Modifier.padding(horizontal = 4.dp),
						query = query,
						onQueryChange = { query = it },
						onSearch = { searchViewModel.fetchMultiSearchResults(it) },
						placeholder = "Search movies & TV series..."
					)
				}
			},
			colors = TopAppBarDefaults.topAppBarColors(
				containerColor = Color.Transparent,
			)
		)
	}
}