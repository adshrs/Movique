@file:OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)

package org.example.movique.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Precision
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import org.example.movique.MediaDetailsScreen
import org.example.movique.data.models.mediadetails.MovieDetailsResponseModel
import org.example.movique.data.models.mediadetails.TvSeriesDetailsResponseModel
import org.example.movique.theme.extraColors
import org.example.movique.theme.isSystemInDarkTheme
import org.example.movique.ui.components.card.MediaCard
import org.example.movique.util.Result
import org.example.movique.util.Result.Loading.isLoading
import org.example.movique.util.tools.Constants.NA
import org.example.movique.viewmodel.MediaDetailsViewModel
import org.example.movique.viewmodel.MediaListViewModel
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.PI
import kotlin.math.round

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
	val scope = rememberCoroutineScope()

	var showTopBar by remember { mutableStateOf(false) }

	// Detect scroll to show/hide TopAppBar
	LaunchedEffect(listState) {
		snapshotFlow { listState.value }
			.map { it > 1 } // show TopAppBar after scrolling 50px
			.distinctUntilChanged()
			.collect { showTopBar = it }
	}

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
					Box(
						modifier = Modifier
							.fillMaxWidth()
							.aspectRatio(16f / 13.5f)
					) {
						// Backdrop
						Box(
							modifier = Modifier
								.fillMaxWidth().aspectRatio(16f / 9f),
							contentAlignment = Alignment.Center
						) {
							// Backdrop Image
							AsyncImage(
								model = ImageRequest.Builder(LocalPlatformContext.current)
									.data(
										"https://image.tmdb.org/t/p/original${
											when (mediaType) {
												"movie" -> movie?.backdropPath
												"tv" -> tvSeries?.backdropPath
												else -> null
											}
										}"
									)
									.crossfade(true)
									.precision(Precision.INEXACT)
									.build(),
								contentDescription = when (mediaType) {
									"movie" -> movie?.title
									"tv" -> tvSeries?.name
									else -> ""
								},
								modifier = Modifier
									.fillMaxSize(),
								contentScale = ContentScale.Crop,
							)
							// Backdrop gradient overlay
							Box(
								modifier = Modifier
									.fillMaxSize()
									.background(
										smoothCinematicVerticalGradientBrush(
											baseColor = MaterialTheme.colorScheme.background,
											topAlpha = 0.2f,
											midAlpha = 0.1f,
											bottomAlpha = 1f,
											midPosition = 0.35f,
											steps = 500
										)
									)
							)
						}
						// Poster and main info
						Row(
							modifier = Modifier
								.fillMaxWidth()
								.align(Alignment.BottomCenter)
								.padding(horizontal = 16.dp),
							verticalAlignment = Alignment.Top
						) {
							// Poster Image
							OutlinedCard(
								modifier = Modifier
									.width(112.dp)
									.clickable(
										indication = null,
										interactionSource = null,
										onClick = { }
									),
								colors = CardDefaults.cardColors(
									containerColor = MaterialTheme.colorScheme.surfaceContainer,
									contentColor = MaterialTheme.colorScheme.onSurface
								),
								border = BorderStroke(
									1.dp,
									MaterialTheme.colorScheme.onSurface.copy(alpha = 0.18f)
								)
							) {
								Box(
									modifier = Modifier
										.fillMaxWidth()
										.aspectRatio(2f / 3f)
										.background(MaterialTheme.colorScheme.surfaceVariant),
									contentAlignment = Alignment.Center
								) {
									AsyncImage(
										model = ImageRequest.Builder(LocalPlatformContext.current)
											.data(
												"https://image.tmdb.org/t/p/w500${
													when (mediaType) {
														"movie" -> movie?.posterPath
														"tv" -> tvSeries?.posterPath
														else -> null
													}
												}"
											)
											.crossfade(true)
											.precision(Precision.INEXACT)
											.build(),
										contentDescription = when (mediaType) {
											"movie" -> movie?.title
											"tv" -> tvSeries?.name
											else -> ""
										},
										modifier = Modifier
											.fillMaxSize(),
										contentScale = ContentScale.Crop,
									)
								}
							}

							Spacer(Modifier.width(20.dp))

							// Main Info
							Column(
								modifier = Modifier.weight(1f),
								verticalArrangement = Arrangement.spacedBy(4.dp)
							) {
								val title = when (mediaType) {
									"movie" -> movie?.title
									"tv" -> tvSeries?.name
									else -> null
								}
								val originalTitle = when (mediaType) {
									"movie" -> movie?.originalTitle
									"tv" -> tvSeries?.originalName
									else -> null
								}
								val releaseDate = when (mediaType) {
									"movie" -> movie?.releaseDate
									"tv" -> tvSeries?.firstAirDate
									else -> null
								}
								val directors = when (mediaType) {
									"movie" -> movie?.credits?.crew
										?.filter { it?.job == "Director" }
										?.mapNotNull { it?.name } ?: emptyList()

									"tv" -> tvSeries?.credits?.crew
										?.filter { it?.job == "Director" || it?.job == "Series Director" }
										?.mapNotNull { it?.name } ?: emptyList()

									else -> emptyList()
								}

								Text(
									text = title ?: "",
									style = MaterialTheme.typography.titleLarge,
									color = MaterialTheme.colorScheme.onSurface,
								)
								if (!originalTitle.isNullOrEmpty() && originalTitle != title) {
									Text(
										text = originalTitle,
										style = MaterialTheme.typography.labelLarge,
										fontStyle = FontStyle.Italic,
										color = MaterialTheme.colorScheme.onSurface,
									)
								}
								if (!releaseDate.isNullOrEmpty()) {
									Text(
										text = releaseDate.take(4) + if (!directors.isEmpty()) {
											"  •  " + "DIRECTED BY"
										} else "",
										style = MaterialTheme.typography.labelLarge,
										color = MaterialTheme.colorScheme.onSurface,
									)
								}
								if (directors.isNotEmpty()) {
									Text(
										text = directors.joinToString(", "),
										style = MaterialTheme.typography.labelLarge,
										color = MaterialTheme.colorScheme.onSurface,
									)
								}

								Badge(
									modifier = Modifier.padding(top = 4.dp),
									containerColor =
										if (mediaType == "movie")
											MaterialTheme.colorScheme.primaryContainer.copy(0.9f)
										else
											MaterialTheme.colorScheme.tertiaryContainer.copy(0.9f),
									contentColor =
										if (mediaType == "movie")
											Color.White.copy(0.8f)
										else
											Color.White.copy(0.8f)
								) {
									Text(
										text = when (mediaType) {
											"movie" -> "Movie"
											"tv" -> "TV Series"
											else -> NA
										},
										style = MaterialTheme.typography.labelMedium,
										modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
									)
								}
							}
						}
					}
					Spacer(Modifier.height(1000.dp))
					Spacer(Modifier.height(84.dp))
				}
			}

			// Static Top Bar (Only Icon Buttons)
			TopAppBar(
				modifier = Modifier
					.height(80.dp)
					.background(Color.Transparent),
				title = { },
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

			// Animated Top Bar (Icon Buttons + Title)
			AnimatedVisibility(
				visible = showTopBar,
				enter = fadeIn(),
				exit = fadeOut()
			) {
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
}

@Composable
fun smoothCinematicVerticalGradientBrush(
	baseColor: Color = MaterialTheme.colorScheme.background,
	topAlpha: Float = 0.2f,
	midAlpha: Float = 0.05f,
	bottomAlpha: Float = 1f,
	midPosition: Float = 0.3f,
	steps: Int = 120
): Brush {
	val colors = buildList {
		for (i in 0 until steps) {
			val t = i / (steps - 1f)

			// Smooth curve across entire gradient (easeInOut-like)
			val smoothT = t * t * (3 - 2 * t) // Smoothstep for no sharp rate changes

			// Apply a valley at the midpoint (for that cinematic “depth”)
			val curve = when {
				t < midPosition -> {
					val localT = t / midPosition
					lerp(topAlpha, midAlpha, localT * localT * (3 - 2 * localT))
				}

				else -> {
					val localT = (t - midPosition) / (1 - midPosition)
					lerp(midAlpha, bottomAlpha, localT * localT * (3 - 2 * localT))
				}
			}

			add(baseColor.copy(alpha = curve))
		}

		// Ensure solid bottom
		add(baseColor.copy(alpha = bottomAlpha))
	}

	return Brush.verticalGradient(
		colors = colors,
		startY = 0f,
		endY = Float.POSITIVE_INFINITY
	)
}

// helper
private fun lerp(start: Float, end: Float, fraction: Float): Float =
	start + (end - start) * fraction
