@file:OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)

package org.example.movique.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.MoreTime
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import org.example.movique.data.models.mediadetails.MovieDetailsResponseModel
import org.example.movique.data.models.mediadetails.TvSeriesDetailsResponseModel
import org.example.movique.theme.titleRegular
import org.example.movique.ui.components.mediadetailssection.MediaBackdrop
import org.example.movique.ui.components.mediadetailssection.MediaMainInfoSection
import org.example.movique.ui.components.tab.NoRippleTab
import org.example.movique.util.Result
import org.example.movique.util.tools.smoothCinematicVerticalGradientBrush
import org.example.movique.viewmodel.MediaDetailsViewModel
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
	val isScrolling = listState.isScrollInProgress
	val scope = rememberCoroutineScope()
	val localDensity = LocalDensity.current

	val topBarHeightPx = with(localDensity) { 20.dp.toPx() }
	val statusBarPx = with(localDensity) { WindowInsets.statusBars.getTop(localDensity).toFloat() }
	val topTriggerPx = statusBarPx + topBarHeightPx

	var titleTopPx by remember { mutableStateOf(Float.POSITIVE_INFINITY) } // measured in window co-ordinates
	var showTopBar by remember { mutableStateOf(false) }

	// Show Top Bar Logic (Show only when scroll reaches the media title text)
	LaunchedEffect(listState) {
		snapshotFlow { titleTopPx to listState.value }
			.map { (titleY, _) ->
				// When title's top is at or above the trigger Y, show top bar
				// add small fudge (e.g. 2 px) if needed
				titleY.isFinite() && titleY <= topTriggerPx + 2f
			}
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
			// Loading Indicator
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
				Box(
					modifier = Modifier.fillMaxSize()
				) {
					// Backdrop
					MediaBackdrop(mediaType, movie, tvSeries)
					Box(
						modifier = Modifier
							.fillMaxSize()
							.verticalScroll(listState)
					) {
						// Backdrop gradient overlay
						Column(modifier = Modifier.matchParentSize()) {
							Box(
								modifier = Modifier
									.fillMaxWidth()
									.aspectRatio(16f / 9f)
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
							Box(
								modifier = Modifier
									.fillMaxWidth()
									.weight(1f)
									.background(MaterialTheme.colorScheme.background)
							)
						}

						// Media Info
						Column(
							modifier = Modifier.fillMaxWidth()
						) {
							Spacer(modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues()))
							Spacer(modifier = Modifier.padding(top = 104.dp))
							// Main Info (including Poster, Tagline & Overview)
							MediaMainInfoSection(
								modifier = Modifier,
								mediaType = mediaType,
								movie = movie,
								tvSeries = tvSeries,
								retrieveTitleTopPx = { titleTopPx = it }
							)

							Spacer(modifier = Modifier.height(16.dp))

							// For Selectable Tabs
							val tabs = listOf(
								"Cast",
								"Crew",
								"Genres",
								"Details"
							)
							var selectedTabIndex by remember { mutableStateOf(0) }

							// Selectable Tabs
							TabRow(
								modifier = Modifier,
								selectedTabIndex = selectedTabIndex,
								containerColor = Color.Transparent,
								divider = {
									HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 0.5.dp)
								}
							) {
								tabs.forEachIndexed { index, title ->
									NoRippleTab(
										title = title,
										index = index,
										selectedTabIndex = selectedTabIndex,
										onClick = { index ->
											selectedTabIndex = index
										}
									)
								}
							}

							// Selected Tab Content
							Column(
								modifier = Modifier.fillMaxWidth()
							) {
								when (selectedTabIndex) {
									0 -> {
										MediaCastSection(
											modifier = Modifier,
											mediaType = mediaType,
											movie = movie,
											tvSeries = tvSeries
										)
									}

									1 -> {
										MediaCrewSection(
											modifier = Modifier,
											mediaType = mediaType,
											movie = movie,
											tvSeries = tvSeries
										)
									}

									2 -> {

									}

									3 -> {

									}
								}
							}

							Spacer(Modifier.height(1000.dp))
							Spacer(Modifier.height(76.dp))
							Spacer(modifier = Modifier.padding(WindowInsets.navigationBars.asPaddingValues()))
						}
					}

					// Bottom Action Bar
					Box(
						modifier = Modifier
							.align(Alignment.BottomCenter)
							.padding(WindowInsets.navigationBars.asPaddingValues())
					) {
						AnimatedVisibility(
							visible = !isScrolling,
							enter = fadeIn() + slideInVertically(
								initialOffsetY = { 120 },
							),
							exit = fadeOut() + slideOutVertically(
								targetOffsetY = { 120 },
							)
						) {
							Card(
								modifier = Modifier
									.padding(vertical = 4.dp),
								border = BorderStroke(
									0.5.dp,
									MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
								),
								shape = MaterialTheme.shapes.extraLarge,
								colors = CardDefaults.cardColors(
									containerColor = BottomAppBarDefaults.containerColor.copy(0.9f)
								)
							) {
								Row(
									modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
									verticalAlignment = Alignment.CenterVertically,
									horizontalArrangement = Arrangement.spacedBy(24.dp)
								) {
									Icon(
										imageVector = Icons.Rounded.FavoriteBorder,
										contentDescription = "Like",
										modifier = Modifier
											.size(32.dp)
											.clickable(
												interactionSource = remember { MutableInteractionSource() },
												indication = null,
												onClick = { }
											),
										tint = MaterialTheme.colorScheme.primary
									)
									Icon(
										imageVector = Icons.Rounded.StarOutline,
										contentDescription = "Rate",
										modifier = Modifier
											.size(38.dp)
											.clickable(
												interactionSource = remember { MutableInteractionSource() },
												indication = null,
												onClick = { }
											),
										tint = MaterialTheme.colorScheme.primary
									)
									Icon(
										imageVector = Icons.Outlined.Visibility,
										contentDescription = "Watch",
										modifier = Modifier
											.size(36.dp)
											.clickable(
												interactionSource = remember { MutableInteractionSource() },
												indication = null,
												onClick = { }
											),
										tint = MaterialTheme.colorScheme.primary
									)
									Icon(
										imageVector = Icons.Rounded.MoreTime,
										contentDescription = "Watchlist",
										modifier = Modifier
											.size(34.dp)
											.clickable(
												interactionSource = remember { MutableInteractionSource() },
												indication = null,
												onClick = { }
											),
										tint = MaterialTheme.colorScheme.primary
									)
								}
							}
						}
					}
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
						IconButton(
							onClick = { navController.popBackStack() },
							colors = IconButtonDefaults.iconButtonColors(
								containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(0.4f),
							)
						) {
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
				enter = fadeIn(
					animationSpec = tween(durationMillis = 400, easing = LinearOutSlowInEasing)
				) + slideInVertically(
					initialOffsetY = { -80 }, // start slightly above the screen
					animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
				),
				exit = fadeOut(
					animationSpec = tween(durationMillis = 300, easing = FastOutLinearInEasing)
				) + slideOutVertically(
					targetOffsetY = { -80 }, // slide smoothly upward when disappearing
					animationSpec = tween(durationMillis = 400, easing = FastOutLinearInEasing)
				)
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
								style = MaterialTheme.typography.titleRegular,
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
fun MediaCastSection(
	modifier: Modifier = Modifier,
	mediaType: String,
	movie: MovieDetailsResponseModel?,
	tvSeries: TvSeriesDetailsResponseModel?
) {
	val castList = when (mediaType) {
		"movie" -> movie?.credits?.cast ?: emptyList()
		"tv" -> tvSeries?.credits?.cast ?: emptyList()
		else -> emptyList()
	}

	val batchSize = 6
	var visibleCount by remember { mutableStateOf(batchSize) }

	if (castList.isNotEmpty()) {
		Column(modifier = modifier.fillMaxWidth().animateContentSize()) {
			// Show limited items
			castList.take(visibleCount).forEachIndexed { index, cast ->
				Column(
					modifier = Modifier
						.fillMaxWidth()
						.clickable { /* TODO: navigate to cast details */ }
				) {
					if (index == 0) Spacer(modifier = Modifier.height(16.dp))
					else Spacer(modifier = Modifier.height(8.dp))

					Row(
						modifier = Modifier
							.fillMaxWidth()
							.padding(horizontal = 16.dp),
						verticalAlignment = Alignment.Top,
						horizontalArrangement = Arrangement.spacedBy(12.dp)
					) {
						OutlinedCard(
							modifier = Modifier.size(42.dp),
							colors = CardDefaults.cardColors(
								containerColor = MaterialTheme.colorScheme.surfaceContainer,
								contentColor = MaterialTheme.colorScheme.onSurface
							),
							border = BorderStroke(
								1.dp,
								MaterialTheme.colorScheme.onSurface.copy(alpha = 0.18f)
							)
						) {
							AsyncImage(
								model = ImageRequest.Builder(LocalPlatformContext.current)
									.data("https://image.tmdb.org/t/p/w200${cast?.profilePath}")
									.crossfade(true)
									.precision(Precision.INEXACT)
									.build(),
								contentDescription = cast?.name ?: "Cast Image",
								modifier = Modifier.fillMaxSize(),
								contentScale = ContentScale.Crop
							)
						}

						Column(
							modifier = Modifier.weight(1f),
							verticalArrangement = Arrangement.spacedBy(4.dp)
						) {
							Text(
								text = cast?.name ?: "Unknown",
								style = MaterialTheme.typography.bodyMedium,
								fontWeight = FontWeight.Medium,
								color = MaterialTheme.colorScheme.onSurface.copy(0.9f),
								maxLines = 1,
								overflow = TextOverflow.Ellipsis
							)
							Text(
								text = cast?.character ?: "Unknown",
								style = MaterialTheme.typography.bodySmall,
								color = MaterialTheme.colorScheme.onSurfaceVariant,
								maxLines = 1,
								overflow = TextOverflow.Ellipsis
							)
						}

						Icon(
							imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
							contentDescription = "Go to Cast Details",
							modifier = Modifier
								.size(16.dp)
								.align(Alignment.CenterVertically),
							tint = MaterialTheme.colorScheme.onSurfaceVariant
						)
					}

					Spacer(modifier = Modifier.height(8.dp))
				}
			}
		}
		// Show controls at bottom
		if (castList.size > batchSize) {
			Row(
				modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
				verticalAlignment = Alignment.CenterVertically
			) {
				HorizontalDivider(
					modifier = Modifier
						.weight(1f),
					thickness = 0.5.dp
				)
				// Collapse Button
				if (visibleCount > batchSize) {
					OutlinedButton(
						modifier = Modifier.height(24.dp),
						onClick = { visibleCount = batchSize },
						contentPadding = PaddingValues(
							top = 0.dp,
							bottom = 0.dp,
							start = 6.dp,
							end = 6.dp
						),
						colors = ButtonDefaults.outlinedButtonColors(
							contentColor = MaterialTheme.colorScheme.primary
						),
						border = BorderStroke(0.5.dp, DividerDefaults.color)
					) {
						Icon(
							imageVector = Icons.Default.KeyboardArrowUp,
							contentDescription = "Collapse",
							modifier = Modifier.size(20.dp)
						)
					}
					HorizontalDivider(
						modifier = Modifier
							.width(12.dp),
						thickness = 0.5.dp
					)
				}
				// Show More Button
				if (visibleCount < castList.size) {
					OutlinedButton(
						modifier = Modifier.height(24.dp),
						onClick = {
							visibleCount = (visibleCount + batchSize).coerceAtMost(castList.size)
						},
						contentPadding = PaddingValues(
							top = 0.dp,
							bottom = 0.dp,
							start = 12.dp,
							end = 6.dp
						),
						colors = ButtonDefaults.outlinedButtonColors(
							contentColor = MaterialTheme.colorScheme.primary
						),
						border = BorderStroke(0.5.dp, DividerDefaults.color)
					) {
						Text(
							text = "More",
							style = MaterialTheme.typography.labelMedium,
						)
						Icon(
							imageVector = Icons.Default.KeyboardArrowDown,
							contentDescription = "More",
							modifier = Modifier.size(20.dp)
						)
					}
				}
				HorizontalDivider(
					modifier = Modifier
						.weight(1f),
					thickness = 0.5.dp
				)
			}
		}
	} else {
		Box(
			modifier = modifier
				.fillMaxWidth()
				.padding(16.dp),
			contentAlignment = Alignment.Center
		) {
			Text(
				text = "No Cast Information Available",
				style = MaterialTheme.typography.bodyMedium,
				color = MaterialTheme.colorScheme.onSurfaceVariant,
				textAlign = TextAlign.Center
			)
		}
	}
}


@Composable
fun MediaCrewSection(
	modifier: Modifier = Modifier,
	mediaType: String,
	movie: MovieDetailsResponseModel?,
	tvSeries: TvSeriesDetailsResponseModel?
) {
	val crewList = when (mediaType) {
		"movie" -> movie?.credits?.crew ?: emptyList()
		"tv" -> tvSeries?.credits?.crew ?: emptyList()
		else -> emptyList()
	}

	val batchSize = 6
	var visibleCount by remember { mutableStateOf(batchSize) }

	if (crewList.isNotEmpty()) {
		Column(modifier = modifier.fillMaxWidth().animateContentSize()) {
			// Show limited items
			crewList.take(visibleCount).forEach { crew ->
				Column(
					modifier = Modifier
						.fillMaxWidth()
						.clickable(
							onClick = { }
						)
				) {
					if (crew == crewList.first()) {
						Spacer(modifier = Modifier.height(16.dp))
					} else {
						Spacer(modifier = Modifier.height(8.dp))
					}
					Row(
						modifier = modifier
							.fillMaxWidth()
							.padding(horizontal = 16.dp),
						verticalAlignment = Alignment.Top,
						horizontalArrangement = Arrangement.spacedBy(12.dp)
					) {
						OutlinedCard(
							modifier = Modifier
								.size(42.dp),
							colors = CardDefaults.cardColors(
								containerColor = MaterialTheme.colorScheme.surfaceContainer,
								contentColor = MaterialTheme.colorScheme.onSurface
							),
							border = BorderStroke(
								1.dp,
								MaterialTheme.colorScheme.onSurface.copy(alpha = 0.18f)
							)
						) {
							AsyncImage(
								model = ImageRequest.Builder(LocalPlatformContext.current)
									.data("https://image.tmdb.org/t/p/w200${crew?.profilePath}")
									.crossfade(true)
									.precision(Precision.INEXACT)
									.build(),
								contentDescription = crew?.name ?: "Crew Image",
								modifier = Modifier.fillMaxSize(),
								contentScale = ContentScale.Crop
							)
						}
						Column(
							modifier = Modifier.weight(1f),
							verticalArrangement = Arrangement.spacedBy(4.dp)
						) {
							Text(
								text = crew?.name ?: "Unknown",
								style = MaterialTheme.typography.bodyMedium,
								fontWeight = FontWeight.Medium,
								color = MaterialTheme.colorScheme.onSurface.copy(0.9f),
								maxLines = 1,
								overflow = TextOverflow.Ellipsis
							)
							Text(
								text = crew?.job ?: "Unknown",
								style = MaterialTheme.typography.bodySmall,
								color = MaterialTheme.colorScheme.onSurfaceVariant,
								maxLines = 1,
								overflow = TextOverflow.Ellipsis
							)
						}
						Box(
							modifier = Modifier.height(40.dp)
						) {
							Icon(
								imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
								contentDescription = "Go to Crew Details",
								modifier = Modifier
									.align(Alignment.Center)
									.size(16.dp),
								tint = MaterialTheme.colorScheme.onSurfaceVariant
							)
						}
					}

					Spacer(modifier = Modifier.height(8.dp))
				}
			}
		}
		// Show controls at bottom
		if (crewList.size > batchSize) {
			Row(
				modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
				verticalAlignment = Alignment.CenterVertically
			) {
				HorizontalDivider(
					modifier = Modifier
						.weight(1f),
					thickness = 0.5.dp
				)
				// Collapse Button
				if (visibleCount > batchSize) {
					OutlinedButton(
						modifier = Modifier.height(24.dp),
						onClick = { visibleCount = batchSize },
						contentPadding = PaddingValues(
							top = 0.dp,
							bottom = 0.dp,
							start = 6.dp,
							end = 6.dp
						),
						colors = ButtonDefaults.outlinedButtonColors(
							contentColor = MaterialTheme.colorScheme.primary
						),
						border = BorderStroke(0.5.dp, DividerDefaults.color)
					) {
						Icon(
							imageVector = Icons.Default.KeyboardArrowUp,
							contentDescription = "Collapse",
							modifier = Modifier.size(20.dp)
						)
					}
					HorizontalDivider(
						modifier = Modifier
							.width(12.dp),
						thickness = 0.5.dp
					)
				}
				// Show More Button
				if (visibleCount < crewList.size) {
					OutlinedButton(
						modifier = Modifier.height(24.dp),
						onClick = {
							visibleCount = (visibleCount + batchSize).coerceAtMost(crewList.size)
						},
						contentPadding = PaddingValues(
							top = 0.dp,
							bottom = 0.dp,
							start = 12.dp,
							end = 6.dp
						),
						colors = ButtonDefaults.outlinedButtonColors(
							contentColor = MaterialTheme.colorScheme.primary
						),
						border = BorderStroke(0.5.dp, DividerDefaults.color)
					) {
						Text(
							text = "More",
							style = MaterialTheme.typography.labelMedium,
						)
						Icon(
							imageVector = Icons.Default.KeyboardArrowDown,
							contentDescription = "More",
							modifier = Modifier.size(20.dp)
						)
					}
				}
				HorizontalDivider(
					modifier = Modifier
						.weight(1f),
					thickness = 0.5.dp
				)
			}
		}
	} else {
		Box(
			modifier = modifier
				.fillMaxWidth()
				.padding(16.dp),
			contentAlignment = Alignment.Center
		) {
			Text(
				text = "No Crew Information Available",
				style = MaterialTheme.typography.bodyMedium,
				color = MaterialTheme.colorScheme.onSurfaceVariant,
				textAlign = TextAlign.Center
			)
		}
	}
}