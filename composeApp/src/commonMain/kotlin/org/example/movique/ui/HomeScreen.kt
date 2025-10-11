@file:OptIn(ExperimentalMaterial3Api::class)

package org.example.movique.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOutQuad
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ArrowCircleRight
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Badge
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Precision
import kotlinx.coroutines.launch
import org.example.movique.MediaDetailsScreen
import org.example.movique.MediaListScreen
import org.example.movique.theme.extraColors
import org.example.movique.ui.components.card.MediaCard
import org.example.movique.ui.components.cardshimmer.ShimmerLoadingRow
import org.example.movique.util.Result
import org.example.movique.util.tools.Constants.NA
import org.example.movique.viewmodel.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.round

@Composable
fun HomeScreen(
	navController: NavHostController,
	innerPadding: PaddingValues,
	drawerState: DrawerState
) {
	val scope = rememberCoroutineScope()
	val homeViewModel = koinViewModel<HomeViewModel>()
	val getPopularMovies = homeViewModel.getPopularMovies.collectAsState()
	val popularMovies = (getPopularMovies.value as? Result.Success)?.data?.results ?: emptyList()
	val getPopularTvShows = homeViewModel.getPopularTvShows.collectAsState()
	val popularTvShows = (getPopularTvShows.value as? Result.Success)?.data?.results ?: emptyList()
	val getTrendingThisWeek = homeViewModel.getTrendingThisWeek.collectAsState()
	val trending = (getTrendingThisWeek.value as? Result.Success)?.data?.results ?: emptyList()
	val isLoading by homeViewModel.isLoading.collectAsState()

	LaunchedEffect(Unit) {
		if ((homeViewModel.getPopularMovies.value as? Result.Success)?.data?.results.isNullOrEmpty()) {
			homeViewModel.fetchPopularMovies()
		}
		if ((homeViewModel.getPopularTvShows.value as? Result.Success)?.data?.results.isNullOrEmpty()) {
			homeViewModel.fetchPopularTvShows()
		}
		if ((homeViewModel.getTrendingThisWeek.value as? Result.Success)?.data?.results.isNullOrEmpty()) {
			homeViewModel.fetchTrendingThisWeek()
		}
	}

	var activeCardId by remember { mutableStateOf<Int?>(null) }

	Box(
		modifier = Modifier.fillMaxSize()
	) {
		// Screen Content
		Scaffold {
			LazyColumn(
				modifier = Modifier.fillMaxSize()
			) {
				item { Spacer(modifier = Modifier.height(96.dp)) }
				// Popular Movies Section
				item {
					Column(
						modifier = Modifier.fillMaxWidth().padding(bottom = 28.dp),
						verticalArrangement = Arrangement.spacedBy(16.dp)
					) {
						Row(
							modifier = Modifier.padding(horizontal = 16.dp),
							verticalAlignment = Alignment.CenterVertically,
						) {
							Box(
								modifier = Modifier
									.size(5.dp, 18.dp)
									.background(
										MaterialTheme.colorScheme.primary,
										shape = MaterialTheme.shapes.large
									)
							)
							Spacer(modifier = Modifier.width(8.dp))
							Text(
								modifier = Modifier.fillMaxWidth().weight(1f),
								text = "Popular - Movies",
								style = MaterialTheme.typography.titleMedium,
								fontWeight = FontWeight.Bold,
								color = MaterialTheme.colorScheme.onSurface
							)
							TextButton(
								modifier = Modifier.height(30.dp),
								onClick = { navController.navigate(MediaListScreen("popular_movies")) },
								contentPadding = PaddingValues(
									start = 10.dp,
									end = 4.dp,
									top = 4.dp,
									bottom = 4.dp
								)
							) {
								Row(
									verticalAlignment = Alignment.CenterVertically,
									horizontalArrangement = Arrangement.spacedBy(2.dp)
								) {
									Text(
										text = "View more",
										style = MaterialTheme.typography.labelSmall,
									)
									Icon(
										imageVector = Icons.Default.ChevronRight,
										contentDescription = "View More",
										modifier = Modifier.size(16.dp),
										tint = MaterialTheme.colorScheme.onSurface
									)
								}
							}
						}
						if (isLoading || popularMovies.isEmpty()) {
							ShimmerLoadingRow()
						} else {
							LazyRow(
								modifier = Modifier.fillMaxSize(),
							) {
								item { Spacer(modifier = Modifier.width(16.dp)) }
								itemsIndexed(popularMovies) { index, item ->
									val cardId = item.id?.plus(index)?.plus(1) ?: (index + 1)

									MediaCard(
										cardId = cardId,
										mediaType = "movie",
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
													mediaType = "movie"
												)
											)
										}
									)
									if (index < popularMovies.size - 1) {
										Spacer(modifier = Modifier.width(8.dp))
									}
								}
								item { Spacer(modifier = Modifier.width(16.dp)) }
							}
						}
					}
				}
				// Popular Tv Series Section
				item {
					Column(
						modifier = Modifier.fillMaxWidth().padding(bottom = 28.dp),
						verticalArrangement = Arrangement.spacedBy(16.dp)
					) {
						Row(
							modifier = Modifier.padding(horizontal = 16.dp),
							verticalAlignment = Alignment.CenterVertically,
						) {
							Box(
								modifier = Modifier
									.size(5.dp, 18.dp)
									.background(
										MaterialTheme.colorScheme.primary,
										shape = MaterialTheme.shapes.large
									)
							)
							Spacer(modifier = Modifier.width(8.dp))
							Text(
								modifier = Modifier.fillMaxWidth().weight(1f),
								text = "Popular - TV Series",
								style = MaterialTheme.typography.titleMedium,
								fontWeight = FontWeight.Bold,
								color = MaterialTheme.colorScheme.onSurface
							)
							TextButton(
								modifier = Modifier.height(30.dp),
								onClick = { navController.navigate(MediaListScreen("popular_tv")) },
								contentPadding = PaddingValues(
									start = 10.dp,
									end = 4.dp,
									top = 4.dp,
									bottom = 4.dp
								)
							) {
								Row(
									verticalAlignment = Alignment.CenterVertically,
									horizontalArrangement = Arrangement.spacedBy(2.dp)
								) {
									Text(
										text = "View more",
										style = MaterialTheme.typography.labelSmall,
									)
									Icon(
										imageVector = Icons.Default.ChevronRight,
										contentDescription = "View More",
										modifier = Modifier.size(16.dp),
										tint = MaterialTheme.colorScheme.onSurface
									)
								}
							}
						}
						if (isLoading || popularTvShows.isEmpty()) {
							ShimmerLoadingRow()
						} else {
							LazyRow(
								modifier = Modifier.fillMaxSize(),
							) {
								item { Spacer(modifier = Modifier.width(16.dp)) }
								itemsIndexed(popularTvShows) { index, item ->
									val cardId = item.id + index + 2

									MediaCard(
										cardId = cardId,
										mediaType = "tv",
										posterPath = item.posterPath,
										title = item.name,
										voteAverage = item.voteAverage,
										releaseDate = item.firstAirDate,
										isActive = activeCardId == cardId,
										onCardClick = { clicked ->
											activeCardId = if (activeCardId == clicked) null else clicked
										},
										onAddActionClick = { },
										onDetailActionClick = {
											navController.navigate(
												MediaDetailsScreen(
													mediaId = item.id ?: 0,
													mediaType = "tv"
												)
											)
										}
									)
									if (index < popularTvShows.size - 1) {
										Spacer(modifier = Modifier.width(8.dp))
									}
								}
								item { Spacer(modifier = Modifier.width(16.dp)) }
							}
						}
					}
				}
				// Trending This Week Section
				item {
					Column(
						modifier = Modifier.fillMaxWidth(),
						verticalArrangement = Arrangement.spacedBy(16.dp)
					) {
						Row(
							modifier = Modifier.padding(horizontal = 16.dp),
							verticalAlignment = Alignment.CenterVertically,
						) {
							Box(
								modifier = Modifier
									.size(5.dp, 18.dp)
									.background(
										MaterialTheme.colorScheme.primary,
										shape = MaterialTheme.shapes.large
									)
							)
							Spacer(modifier = Modifier.width(8.dp))
							Text(
								modifier = Modifier.fillMaxWidth().weight(1f),
								text = "Trending - This Week",
								style = MaterialTheme.typography.titleMedium,
								fontWeight = FontWeight.Bold,
								color = MaterialTheme.colorScheme.onSurface
							)
							TextButton(
								modifier = Modifier.height(30.dp),
								onClick = { navController.navigate(MediaListScreen("trending_this_week")) },
								contentPadding = PaddingValues(
									start = 10.dp,
									end = 4.dp,
									top = 4.dp,
									bottom = 4.dp
								)
							) {
								Row(
									verticalAlignment = Alignment.CenterVertically,
									horizontalArrangement = Arrangement.spacedBy(2.dp)
								) {
									Text(
										text = "View more",
										style = MaterialTheme.typography.labelSmall,
									)
									Icon(
										imageVector = Icons.Default.ChevronRight,
										contentDescription = "View More",
										modifier = Modifier.size(16.dp),
										tint = MaterialTheme.colorScheme.onSurface
									)
								}
							}
						}
						if (isLoading || popularMovies.isEmpty()) {
							ShimmerLoadingRow()
						} else {
							LazyRow(
								modifier = Modifier.fillMaxSize(),
							) {
								item { Spacer(modifier = Modifier.width(16.dp)) }
								itemsIndexed(trending) { index, item ->
									val mediaType = item.mediaType
									val cardId = item.id + index + 3

									MediaCard(
										cardId = cardId,
										mediaTypeEnabled = true,
										mediaType = mediaType,
										posterPath = item.posterPath,
										title = if (mediaType == "movie") item.title else item.name,
										voteAverage = item.voteAverage,
										releaseDate = if (mediaType == "movie") item.releaseDate else item.firstAirDate,
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
									if (index < trending.size - 1) {
										Spacer(modifier = Modifier.width(8.dp))
									}
								}
								item { Spacer(modifier = Modifier.width(16.dp)) }
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
				.height(80.dp)
				.background(
					Brush.verticalGradient(
						colors = listOf(
							BottomAppBarDefaults.containerColor,
							BottomAppBarDefaults.containerColor.copy(0.8f)
						)
					)
				),
			title = {
				Box(
					modifier = Modifier
						.fillMaxHeight(),
					contentAlignment = Alignment.Center
				) {
					Text(
						text = "Movique",
						style = MaterialTheme.typography.titleLarge,
						color = MaterialTheme.colorScheme.onSurface,
						textAlign = TextAlign.Center
					)
				}
			},
			navigationIcon = {
				Box(
					modifier = Modifier
						.fillMaxHeight(),
					contentAlignment = Alignment.Center
				) {
					IconButton(onClick = { scope.launch { drawerState.open() } }) {
						Icon(
							imageVector = Icons.Outlined.Menu,
							contentDescription = "Menu",
							tint = MaterialTheme.colorScheme.onSurface
						)
					}
				}
			},
			colors = TopAppBarDefaults.topAppBarColors(
				containerColor = Color.Transparent,
				titleContentColor = MaterialTheme.colorScheme.onSurface,
				navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
				actionIconContentColor = MaterialTheme.colorScheme.onSurface
			)
		)
	}
}
